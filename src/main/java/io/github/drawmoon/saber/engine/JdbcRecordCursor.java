/*
 *            _
 *  ___  __ _| |__   ___ _ __
 * / __|/ _` | '_ \ / _ \ '__|
 * \__ \ (_| | |_) |  __/ |
 * |___/\__,_|_.__/ \___|_|
 *
 * Copyright 2024 drash
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.drawmoon.saber.engine;

import static com.google.common.base.Preconditions.checkState;
import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import io.github.drawmoon.saber.common.Profiler;
import io.github.drawmoon.saber.exceptions.EngineException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.annotation.Nullable;

public class JdbcRecordCursor implements AutoCloseable {

  private final Connection connection;
  private final String query;
  private final int timeout;
  private final ExecutorService executor;
  private final SaberEventListener listener;

  @Nullable private Statement statement;
  @Nullable private ResultSet resultSet;
  private boolean closed;

  public JdbcRecordCursor(
      Connection connection,
      String query,
      int timeout,
      ExecutorService executor,
      SaberEventListener listener) {
    this.query = query;
    this.timeout = timeout;
    this.connection = connection;
    this.executor = executor;
    this.listener = listener;
  }

  // The following code is derived from Project Trino (https://github.com/trinodb/trino)
  // Licensed under the Apache License, Version 2.0 (the "License");
  // you may not use this file except in compliance with the License.
  // You may obtain a copy of the License at
  //
  //     http://www.apache.org/licenses/LICENSE-2.0
  //
  // Unless required by applicable law or agreed to in writing, software
  // distributed under the License is distributed on an "AS IS" BASIS,
  // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  // See the License for the specific language governing permissions and
  // limitations under the License.

  public boolean advanceNextPosition() {
    if (closed) {
      return false;
    }

    try {
      if (resultSet == null) {
        Future<ResultSet> resultSetFuture =
            executor.submit(() -> safeCreateStatement().executeQuery(query));
        try {
          // statement.executeQuery() may block uninterruptedly, using async way so we are able to
          // cancel remote query
          // See javadoc of java.sql.Connection.setNetworkTimeout
          resultSet = resultSetFuture.get();
        } catch (ExecutionException e) {
          if (e.getCause() instanceof SQLException) {
            SQLException cause = (SQLException) e.getCause();
            SQLException sqlException =
                new SQLException(cause.getMessage(), cause.getSQLState(), cause.getErrorCode(), e);
            if (cause.getNextException() != null) {
              sqlException.setNextException(cause.getNextException());
            }
            throw sqlException;
          }
          throw new EngineException(e);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          resultSetFuture.cancel(true);
          throw new EngineException(e);
        }
      }
      assert resultSet != null;
      return resultSet.next();
    } catch (Exception e) {
      throw handleSqlException(e);
    }
  }

  public ResultSetMetaData getMetaData() {
    checkState(!closed, "cursor is closed");
    checkNotNull(resultSet);

    try {
      return resultSet.getMetaData();
    } catch (SQLException e) {
      throw handleSqlException(e);
    }
  }

  public boolean getBoolean(int field) {
    checkState(!closed, "cursor is closed");
    checkNotNull(resultSet);

    try {
      return resultSet.getBoolean(field);
    } catch (SQLException e) {
      throw handleSqlException(e);
    }
  }

  public long getLong(int field) {
    checkState(!closed, "cursor is closed");
    checkNotNull(resultSet);

    try {
      return resultSet.getLong(field);
    } catch (SQLException e) {
      throw handleSqlException(e);
    }
  }

  public double getDouble(int field) {
    checkState(!closed, "cursor is closed");
    checkNotNull(resultSet);

    try {
      return resultSet.getDouble(field);
    } catch (SQLException e) {
      throw handleSqlException(e);
    }
  }

  public Object getObject(int field) {
    checkState(!closed, "cursor is closed");
    checkNotNull(resultSet);

    try {
      return resultSet.getObject(field);
    } catch (SQLException e) {
      throw handleSqlException(e);
    }
  }

  public String getString(int field) {
    checkState(!closed, "cursor is closed");
    checkNotNull(resultSet);

    try {
      return resultSet.getString(field);
    } catch (Exception e) {
      throw handleSqlException(e);
    }
  }

  public Integer getInteger(int field) {
    checkState(!closed, "cursor is closed");
    checkNotNull(resultSet);

    try {
      return resultSet.getInt(field);
    } catch (Exception e) {
      throw handleSqlException(e);
    }
  }

  private synchronized Statement safeCreateStatement() {
    if (statement != null) {
      return statement;
    }

    checkState(!closed, "cursor is closed");
    checkNotNull(connection);

    try {
      if (connection.isClosed()) {
        throw new EngineException("Connection is closed");
      }

      Statement statement = connection.createStatement();
      statement.setQueryTimeout(timeout);

      return statement;
    } catch (SQLException e) {
      try {
        connection.close();
      } catch (SQLException expected) {
        // safely close
      }
      throw new EngineException(e);
    }
  }

  private EngineException handleSqlException(Exception e) {
    try {
      close();
    } catch (Exception closeException) {
      // Self-suppression not permitted
      if (e != closeException) {
        e.addSuppressed(closeException);
      }
    }
    if (e instanceof EngineException) {
      return (EngineException) e;
    }
    return new EngineException(e);
  }

  @Override
  public void close() throws Exception {
    if (closed) {
      return;
    }
    closed = true;

    Profiler p = new Profiler();
    SQLException exception = null;
    try (Connection connection = this.connection;
        Statement statement = this.statement;
        ResultSet resultSet = this.resultSet) {
      if (statement != null) {
        try {
          // Trying to cancel running statement as close() may not do it
          statement.cancel();
        } catch (SQLException expected) {
          // statement already closed or cancel is not supported
        }
      }
    } catch (SQLException e) {
      // safely close
      exception = e;
    } finally {
      long elapsed = p.getMillis();
      listener.onAfterRecordCursorClose(this, elapsed, exception);
    }
  }
}
