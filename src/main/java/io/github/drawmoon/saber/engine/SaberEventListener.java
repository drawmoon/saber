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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public interface SaberEventListener {

  // ***********************************************************************
  //                          Query manager events
  // ***********************************************************************
  /**
   * This callback method is executed before any of the {@link DataSourceManager#get} methods are
   * invoked.
   *
   * @param store the store
   * @param options the options
   * @param context the query context
   */
  void onBeforeGetDataSource(DataSourceManager store, SaberOptions options, RequestContext context);

  /**
   * This callback method is executed after any of the {@link DataSourceManager#get} methods are
   * invoked.
   *
   * @param store the store
   * @param options the options
   * @param context the query context
   * @param dataSource the data source
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link Exception} which may be triggered by the call (<code>null</code> if there
   *     was no exception)
   */
  void onAfterGetDataSource(
      DataSourceManager store,
      SaberOptions options,
      RequestContext context,
      SaberDataSource dataSource,
      long elapsedMillis,
      Exception e);

  /**
   * This callback method is executed before any of the {@link RequestManager}.fetch*(Lazy) methods
   * are invoked.
   *
   * @param queryMgr the query manager
   * @param request the query request
   */
  void onBeforeQuery(RequestManager queryMgr, Request request);

  /**
   * This callback method is executed before any of the {@link RequestManager#fetch} methods are
   * invoked.
   *
   * @param queryMgr the query manager
   * @param context the query context
   * @param response the query response
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterQuery(
      RequestManager queryMgr,
      RequestContext context,
      Response response,
      long elapsedMillis,
      SQLException e);

  /**
   * This callback method is executed before any of the {@link RequestManager#fetchLazy} methods are
   * invoked.
   *
   * @param queryMgr the query manager
   * @param cursor the record cursor
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterQuery(
      RequestManager queryMgr, JdbcRecordCursor cursor, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed after any of the {@link JdbcRecordCursor#close} methods are
   * invoked.
   *
   * @param cursor the record cursor
   * @param elapsedMillis the execution time of the execute call
   * @param e yhe {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterRecordCursorClose(JdbcRecordCursor cursor, long elapsedMillis, SQLException e);

  // ***********************************************************************
  //                              JDBC events
  // ***********************************************************************

  // The following code is derived from Project P6spy (https://github.com/p6spy/p6spy)
  // Copyright (C) 2002 P6Spy
  //
  // Licensed under the Apache License, Version 2.0 (the "License");
  // you may not use this file except in compliance with the License.
  // You may obtain a copy of the License at
  //
  //      http://www.apache.org/licenses/LICENSE-2.0
  //
  // Unless required by applicable law or agreed to in writing, software
  // distributed under the License is distributed on an "AS IS" BASIS,
  // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  // See the License for the specific language governing permissions and
  // limitations under the License.

  /**
   * This callback method is executed before a {@link Connection} obtained from a {@link DataSource}
   * or a {@link Driver}.
   *
   * @param dataSource the data source
   */
  void onBeforeGetConnection(SaberWrapper dataSource);

  /**
   * This callback method is executed after a {@link Connection} obtained from a {@link DataSource}
   * or a {@link Driver}.
   *
   * @param connection the connection
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterGetConnection(SaberWrapper connection, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed after the {@link Connection#close()} method is invoked.
   *
   * @param connection the connection
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterConnectionClose(SaberWrapper connection, long elapsedMillis, SQLException e);

  // -----------------------------------------------------------------------
  /**
   * This callback method is executed before any of the {@link PreparedStatement#execute()} methods
   * are invoked.
   *
   * @param statement the statement
   */
  void onBeforeExecute(SaberWrapper statement);

  /**
   * This callback method is executed after any the {@link PreparedStatement#execute()} methods are
   * invoked.
   *
   * @param statement the statement
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterExecute(SaberWrapper statement, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed before any of the {@link Statement#execute(String)} methods
   * are invoked.
   *
   * @param statement the statement
   * @param sql the SQL string provided to the execute method
   */
  void onBeforeExecute(SaberWrapper statement, String sql);

  /**
   * This callback method is executed after any the {@link Statement#execute(String)} methods are
   * invoked.
   *
   * @param statement the statement
   * @param sql the SQL string provided to the execute method
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterExecute(SaberWrapper statement, String sql, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed before the {@link Statement#executeBatch()} method is invoked.
   *
   * @param statement the statement
   */
  void onBeforeExecuteBatch(SaberWrapper statement);

  /**
   * This callback method is executed after the {@link Statement#executeBatch()} method is invoked.
   *
   * @param statement the statement
   * @param rowCounts an array of row counts or null if an exception was thrown
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterExecuteBatch(
      SaberWrapper statement, int[] rowCounts, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed before the {@link PreparedStatement#executeQuery()} method is
   * invoked.
   *
   * @param statement the statement
   */
  void onBeforeExecuteQuery(SaberWrapper statement);

  /**
   * This callback method is executed after the {@link PreparedStatement#executeQuery()} method is
   * invoked.
   *
   * @param statement the statement
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterExecuteQuery(SaberWrapper statement, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed before the {@link Statement#executeQuery(String)} method is
   * invoked.
   *
   * @param statement the statement
   * @param sql the SQL string provided to the execute method
   */
  void onBeforeExecuteQuery(SaberWrapper statement, String sql);

  /**
   * This callback method is executed after the {@link Statement#executeQuery(String)} method is
   * invoked.
   *
   * @param statement the statement
   * @param sql the SQL string provided to the execute method
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterExecuteQuery(SaberWrapper statement, String sql, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed before any of the {@link
   * PreparedStatement#executeUpdate(String)} methods are invoked.
   *
   * @param statement the statement
   */
  void onBeforeExecuteUpdate(SaberWrapper statement);

  /**
   * This callback method is executed after any of the {@link
   * PreparedStatement#executeUpdate(String)} methods are invoked.
   *
   * @param statement the statement
   * @param rowCount either the row count for SQL Data Manipulation Language (DML) statements or 0
   *     for SQL statements that return nothing or if an exception was thrown
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterExecuteUpdate(
      SaberWrapper statement, int rowCount, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed before any of the {@link Statement#executeUpdate(String)}
   * methods are invoked.
   *
   * @param statement the statement
   * @param sql the SQL string provided to the execute method
   */
  void onBeforeExecuteUpdate(SaberWrapper statement, String sql);

  /**
   * This callback method is executed after any of the {@link Statement#executeUpdate(String)}
   * methods are invoked.
   *
   * @param statement the statement
   * @param sql the SQL string provided to the execute method
   * @param rowCount either the row count for SQL Data Manipulation Language (DML) statements or 0
   *     for SQL statements that return nothing or if an exception was thrown
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterExecuteUpdate(
      SaberWrapper statement, String sql, int rowCount, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed after the {@link Statement#getResultSet()} method is invoked.
   *
   * @param statement the statement
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterGetResultSet(SaberWrapper statement, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed after the {@link Statement#close()} method is invoked.
   *
   * @param statement the statement
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterStatementClose(SaberWrapper statement, long elapsedMillis, SQLException e);

  // -----------------------------------------------------------------------
  /**
   * This callback method is executed before the {@link ResultSet#next()} method is invoked.
   *
   * @param resultSet the result set
   */
  void onBeforeResultSetNext(SaberWrapper resultSet);

  /**
   * This callback method is executed after the {@link ResultSet#next()} method is invoked.
   *
   * @param resultSet the result set
   * @param hasNext the return value of {@link ResultSet#next()}
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterResultSetNext(
      SaberWrapper resultSet, boolean hasNext, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed after any of the {@link ResultSet}.get*(String) methods are
   * invoked.
   *
   * @param resultSet the result set
   * @param columnLabel the label for the column specified with the SQL AS clause. If the SQL AS
   *     clause was not specified, then the label is the name of the column
   * @param value the column value; if the value is SQL NULL, the value returned is null
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterResultSetGet(
      SaberWrapper resultSet, String columnLabel, Object value, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed after any of the {@link ResultSet}.get*(String) methods are
   * invoked.
   *
   * @param resultSet the result set
   * @param columnIndex the first column is 1, the second is 2, ...
   * @param value the column value; if the value is SQL NULL, the value returned is null
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterResultSetGet(
      SaberWrapper resultSet, int columnIndex, Object value, long elapsedMillis, SQLException e);

  /**
   * This callback method is executed after the {@link ResultSet#close()} method is invoked.
   *
   * @param resultSet the result set
   * @param elapsedMillis the execution time of the execute call
   * @param e the {@link SQLException} which may be triggered by the call (<code>null</code> if
   *     there was no exception)
   */
  void onAfterResultSetClose(SaberWrapper resultSet, long elapsedMillis, SQLException e);
}
