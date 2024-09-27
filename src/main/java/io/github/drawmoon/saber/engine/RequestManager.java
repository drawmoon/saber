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

import com.google.common.util.concurrent.MoreExecutors;
import io.github.drawmoon.saber.DataTable;
import io.github.drawmoon.saber.DataTable.DataColumn;
import io.github.drawmoon.saber.DataTable.DataRow;
import io.github.drawmoon.saber.common.Profiler;
import io.github.drawmoon.saber.exceptions.EngineException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;

public class RequestManager implements AutoCloseable {

  private final DataSourceManager store;
  private final SaberOptions options;
  private final SaberEventListener listener;
  private final ExecutorService executor;

  public RequestManager(SaberOptions options, SaberEventListener listener) {
    this.store = new DataSourceManager(options, listener);
    this.options = checkNotNull(options);
    this.listener = checkNotNull(listener);
    this.executor = MoreExecutors.newDirectExecutorService();
  }

  public DataSourceManager getStore() {
    return store;
  }

  public Response fetch(Request request) {
    listener.onBeforeQuery(this, checkNotNull(request));

    Profiler p = new Profiler();
    RequestContext context = new RequestContext(request, options, listener);
    Response response;
    try {
      Connection connection = getConnectionOrThrow(context);
      try (JdbcRecordCursor cursor =
          new JdbcRecordCursor(
              connection, request.getQuery(), request.getTimeout(), executor, listener)) {

        boolean hasNext = cursor.advanceNextPosition();

        DataTable dataTable = new DataTable();
        LinkedHashMap<String, Integer> metadata = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> indexMap = new LinkedHashMap<>();

        ResultSetMetaData meta = cursor.getMetaData();
        for (int columnIndex = 1; columnIndex <= meta.getColumnCount(); columnIndex++) {
          String columnName = meta.getColumnName(columnIndex);
          int columnType = meta.getColumnType(columnIndex);

          metadata.put(columnName, columnType);
          indexMap.put(columnName, columnIndex);
          dataTable.addColumn(new DataColumn(columnName));
        }

        while (hasNext) {
          DataRow dataRow = dataTable.newRow();
          for (DataColumn dataColumn : dataTable.getColumns()) {
            Object value = cursor.getObject(indexMap.get(dataColumn.getName()));
            dataRow.setRowData(dataColumn.getName(), value);
            dataTable.addRow(dataRow);
          }

          hasNext = cursor.advanceNextPosition();
        }

        dataTable.setExecutionSql(request.getQuery());
        dataTable.setMetadata(metadata);

        context.setData(dataTable);
      } catch (Exception e) {
        if (e instanceof SQLException) {
          throw (SQLException) e;
        }
        throw new SQLException(e);
      }
    } catch (SQLException e) {
      context.setSqlError(e);
    } finally {
      response = context.getResponse();

      long elapsed = p.getMillis();
      listener.onAfterQuery(this, context, response, elapsed, context.getSqlError());
    }

    return response;
  }

  public JdbcRecordCursor fetchLazy(Request request) {
    listener.onBeforeQuery(this, checkNotNull(request));

    Profiler p = new Profiler();
    RequestContext context = new RequestContext(request, options, listener);
    JdbcRecordCursor cursor = null;
    SQLException exception = null;
    try {
      Connection connection = getConnectionOrThrow(context);
      cursor =
          new JdbcRecordCursor(
              connection, request.getQuery(), request.getTimeout(), executor, listener);
      return cursor;
    } catch (SQLException e) {
      exception = e;
      throw new EngineException(e);
    } finally {
      long elapsed = p.getMillis();
      listener.onAfterQuery(this, cursor, elapsed, exception);
    }
  }

  private Connection getConnectionOrThrow(RequestContext context) throws SQLException {
    SaberDataSource dataSource = store.get(context);
    return dataSource.getConnection();
  }

  @Override
  public void close() throws Exception {
    try {
      executor.shutdown();
    } catch (Exception expected) {
      // safely close
    }
    try {
      store.close();
    } catch (Exception expected) {
      // safely close
    }
  }
}
