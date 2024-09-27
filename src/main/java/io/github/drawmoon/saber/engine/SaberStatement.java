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

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import io.github.drawmoon.saber.common.Profiler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class SaberStatement implements Statement, SaberWrapper {

  private static final AtomicInteger counter = new AtomicInteger(0);
  protected final int wrapperId;
  protected final Statement statement;
  protected final SaberConnection connection;
  protected final RequestContext context;

  public SaberStatement(Statement statement, SaberConnection connection) {
    this.wrapperId = counter.getAndIncrement();
    this.statement = checkNotNull(statement);
    this.connection = checkNotNull(connection);
    this.context = connection.getContext();

    context.setStatement(this);
  }

  public SaberConnection getSaberConnection() {
    return connection;
  }

  @Override
  public int getWrapperId() {
    return wrapperId;
  }

  @Override
  public int getRequestId() {
    return context.getRequestId();
  }

  @Override
  public RequestContext getContext() {
    return context;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (iface.isAssignableFrom(getClass())) {
      return iface.cast(this);
    }
    return this.statement.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (iface.isAssignableFrom(getClass())) {
      return true;
    }
    return this.statement.isWrapperFor(iface);
  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    context.getListener().onBeforeExecuteQuery(this, sql);

    Profiler p = new Profiler();
    try {
      return this.statement.executeQuery(sql);
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecuteQuery(this, sql, elapsed, context.getSqlError());
    }
  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    context.getListener().onBeforeExecuteUpdate(this, sql);

    Profiler p = new Profiler();
    int rowCount = 0;
    try {
      rowCount = this.statement.executeUpdate(sql);
      return rowCount;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterExecuteUpdate(this, sql, rowCount, elapsed, context.getSqlError());
    }
  }

  @Override
  public void close() throws SQLException {
    Profiler p = new Profiler();
    try {
      this.statement.close();
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterStatementClose(this, elapsed, context.getSqlError());
    }
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    return this.statement.getMaxFieldSize();
  }

  @Override
  public void setMaxFieldSize(int max) throws SQLException {
    this.statement.setMaxFieldSize(max);
  }

  @Override
  public int getMaxRows() throws SQLException {
    return this.statement.getMaxRows();
  }

  @Override
  public void setMaxRows(int max) throws SQLException {
    this.statement.setMaxRows(max);
  }

  @Override
  public void setEscapeProcessing(boolean enable) throws SQLException {
    this.statement.setEscapeProcessing(enable);
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    return this.statement.getQueryTimeout();
  }

  @Override
  public void setQueryTimeout(int seconds) throws SQLException {
    this.statement.setQueryTimeout(seconds);
  }

  @Override
  public void cancel() throws SQLException {
    this.statement.cancel();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return this.statement.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    this.statement.clearWarnings();
  }

  @Override
  public void setCursorName(String name) throws SQLException {
    this.statement.setCursorName(name);
  }

  @Override
  public boolean execute(String sql) throws SQLException {
    context.getListener().onBeforeExecute(this, sql);

    Profiler p = new Profiler();
    try {
      return this.statement.execute(sql);
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecute(this, sql, elapsed, context.getSqlError());
    }
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    Profiler p = new Profiler();
    try {
      ResultSet resultSet = this.statement.getResultSet();
      return new SaberResultSet(resultSet, this);
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterGetResultSet(this, elapsed, context.getSqlError());
    }
  }

  @Override
  public int getUpdateCount() throws SQLException {
    return this.statement.getUpdateCount();
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    return this.statement.getMoreResults();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    this.statement.setFetchDirection(direction);
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return this.statement.getFetchDirection();
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    this.statement.setFetchSize(rows);
  }

  @Override
  public int getFetchSize() throws SQLException {
    return this.statement.getFetchSize();
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    return this.statement.getResultSetConcurrency();
  }

  @Override
  public int getResultSetType() throws SQLException {
    return this.statement.getResultSetType();
  }

  @Override
  public void addBatch(String sql) throws SQLException {
    this.statement.addBatch(sql);
  }

  @Override
  public void clearBatch() throws SQLException {
    this.statement.clearBatch();
  }

  @Override
  public int[] executeBatch() throws SQLException {
    context.getListener().onBeforeExecuteBatch(this);

    Profiler p = new Profiler();
    int[] rowCounts = null;
    try {
      rowCounts = this.statement.executeBatch();
      return rowCounts;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecuteBatch(this, rowCounts, elapsed, context.getSqlError());
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    return this.statement.getConnection();
  }

  @Override
  public boolean getMoreResults(int current) throws SQLException {
    return this.statement.getMoreResults(current);
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    return this.statement.getGeneratedKeys();
  }

  @Override
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    context.getListener().onBeforeExecuteUpdate(this, sql);

    Profiler p = new Profiler();
    int rowCount = 0;
    try {
      rowCount = this.statement.executeUpdate(sql, autoGeneratedKeys);
      return rowCount;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterExecuteUpdate(this, sql, rowCount, elapsed, context.getSqlError());
    }
  }

  @Override
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    context.getListener().onBeforeExecuteUpdate(this, sql);

    Profiler p = new Profiler();
    int rowCount = 0;
    try {
      rowCount = this.statement.executeUpdate(sql, columnIndexes);
      return rowCount;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterExecuteUpdate(this, sql, rowCount, elapsed, context.getSqlError());
    }
  }

  @Override
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    context.getListener().onBeforeExecuteUpdate(this, sql);

    Profiler p = new Profiler();
    int rowCount = 0;
    try {
      rowCount = this.statement.executeUpdate(sql, columnNames);
      return rowCount;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterExecuteUpdate(this, sql, rowCount, elapsed, context.getSqlError());
    }
  }

  @Override
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    context.getListener().onBeforeExecute(this, sql);

    Profiler p = new Profiler();
    try {
      return this.statement.execute(sql, autoGeneratedKeys);
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecute(this, sql, elapsed, context.getSqlError());
    }
  }

  @Override
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    context.getListener().onBeforeExecute(this, sql);

    Profiler p = new Profiler();
    try {
      return this.statement.execute(sql, columnIndexes);
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecute(this, sql, elapsed, context.getSqlError());
    }
  }

  @Override
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    context.getListener().onBeforeExecute(this, sql);

    Profiler p = new Profiler();
    try {
      return this.statement.execute(sql, columnNames);
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecute(this, sql, elapsed, context.getSqlError());
    }
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    return this.statement.getResultSetHoldability();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return this.statement.isClosed();
  }

  @Override
  public void setPoolable(boolean poolable) throws SQLException {
    this.statement.setPoolable(poolable);
  }

  @Override
  public boolean isPoolable() throws SQLException {
    return this.statement.isPoolable();
  }

  @Override
  public void closeOnCompletion() throws SQLException {
    this.statement.closeOnCompletion();
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return this.statement.isCloseOnCompletion();
  }
}
