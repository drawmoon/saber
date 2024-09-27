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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class EventBus implements SaberEventListener {

  private static final List<SaberEventListener> listeners = new ArrayList<>();

  public static void addListener(SaberEventListener listener) {
    listeners.add(checkNotNull(listener));
  }

  public static void addListener(int index, SaberEventListener listener) {
    listeners.add(index, checkNotNull(listener));
  }

  public static void removeListener(SaberEventListener listener) {
    listeners.remove(listener);
  }

  public static void clearListeners() {
    listeners.clear();
  }

  @Override
  public void onBeforeGetDataSource(
      DataSourceManager store, SaberOptions options, RequestContext context) {
    listeners.forEach(listener -> listener.onBeforeGetDataSource(store, options, context));
  }

  @Override
  public void onAfterGetDataSource(
      DataSourceManager store,
      SaberOptions options,
      RequestContext context,
      SaberDataSource dataSource,
      long elapsedMillis,
      Exception e) {
    listeners.forEach(
        listener ->
            listener.onAfterGetDataSource(store, options, context, dataSource, elapsedMillis, e));
  }

  @Override
  public void onBeforeQuery(RequestManager queryMgr, Request request) {
    listeners.forEach(listener -> listener.onBeforeQuery(queryMgr, request));
  }

  @Override
  public void onAfterQuery(
      RequestManager queryMgr,
      RequestContext context,
      Response response,
      long elapsedMillis,
      SQLException e) {
    listeners.forEach(
        listener -> listener.onAfterQuery(queryMgr, context, response, elapsedMillis, e));
  }

  @Override
  public void onAfterQuery(
      RequestManager queryMgr, JdbcRecordCursor cursor, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterQuery(queryMgr, cursor, elapsedMillis, e));
  }

  @Override
  public void onAfterRecordCursorClose(
      JdbcRecordCursor cursor, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterRecordCursorClose(cursor, elapsedMillis, e));
  }

  @Override
  public void onBeforeGetConnection(SaberWrapper dataSource) {
    listeners.forEach(listener -> listener.onBeforeGetConnection(dataSource));
  }

  @Override
  public void onAfterGetConnection(SaberWrapper connection, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterGetConnection(connection, elapsedMillis, e));
  }

  @Override
  public void onAfterConnectionClose(SaberWrapper connection, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterConnectionClose(connection, elapsedMillis, e));
  }

  @Override
  public void onBeforeExecute(SaberWrapper statement) {
    listeners.forEach(listener -> listener.onBeforeExecute(statement));
  }

  @Override
  public void onAfterExecute(SaberWrapper statement, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterExecute(statement, elapsedMillis, e));
  }

  @Override
  public void onBeforeExecute(SaberWrapper statement, String sql) {
    listeners.forEach(listener -> listener.onBeforeExecute(statement, sql));
  }

  @Override
  public void onAfterExecute(
      SaberWrapper statement, String sql, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterExecute(statement, sql, elapsedMillis, e));
  }

  @Override
  public void onBeforeExecuteBatch(SaberWrapper statement) {
    listeners.forEach(listener -> listener.onBeforeExecuteBatch(statement));
  }

  @Override
  public void onAfterExecuteBatch(
      SaberWrapper statement, int[] rowCounts, long elapsedMillis, SQLException e) {
    listeners.forEach(
        listener -> listener.onAfterExecuteBatch(statement, rowCounts, elapsedMillis, e));
  }

  @Override
  public void onBeforeExecuteQuery(SaberWrapper statement) {
    listeners.forEach(listener -> listener.onBeforeExecuteQuery(statement));
  }

  @Override
  public void onAfterExecuteQuery(SaberWrapper statement, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterExecuteQuery(statement, elapsedMillis, e));
  }

  @Override
  public void onBeforeExecuteQuery(SaberWrapper statement, String sql) {
    listeners.forEach(listener -> listener.onBeforeExecuteQuery(statement, sql));
  }

  @Override
  public void onAfterExecuteQuery(
      SaberWrapper statement, String sql, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterExecuteQuery(statement, sql, elapsedMillis, e));
  }

  @Override
  public void onBeforeExecuteUpdate(SaberWrapper statement) {
    listeners.forEach(listener -> listener.onBeforeExecuteUpdate(statement));
  }

  @Override
  public void onAfterExecuteUpdate(
      SaberWrapper statement, int rowCount, long elapsedMillis, SQLException e) {
    listeners.forEach(
        listener -> listener.onAfterExecuteUpdate(statement, rowCount, elapsedMillis, e));
  }

  @Override
  public void onBeforeExecuteUpdate(SaberWrapper statement, String sql) {
    listeners.forEach(listener -> listener.onBeforeExecuteUpdate(statement, sql));
  }

  @Override
  public void onAfterExecuteUpdate(
      SaberWrapper statement, String sql, int rowCount, long elapsedMillis, SQLException e) {
    listeners.forEach(
        listener -> listener.onAfterExecuteUpdate(statement, sql, rowCount, elapsedMillis, e));
  }

  @Override
  public void onAfterGetResultSet(SaberWrapper statement, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterGetResultSet(statement, elapsedMillis, e));
  }

  @Override
  public void onAfterStatementClose(SaberWrapper statement, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterStatementClose(statement, elapsedMillis, e));
  }

  @Override
  public void onBeforeResultSetNext(SaberWrapper resultSet) {
    listeners.forEach(listener -> listener.onBeforeResultSetNext(resultSet));
  }

  @Override
  public void onAfterResultSetNext(
      SaberWrapper resultSet, boolean hasNext, long elapsedMillis, SQLException e) {
    listeners.forEach(
        listener -> listener.onAfterResultSetNext(resultSet, hasNext, elapsedMillis, e));
  }

  @Override
  public void onAfterResultSetGet(
      SaberWrapper resultSet,
      String columnLabel,
      Object value,
      long elapsedMillis,
      SQLException e) {
    listeners.forEach(
        listener -> listener.onAfterResultSetGet(resultSet, columnLabel, value, elapsedMillis, e));
  }

  @Override
  public void onAfterResultSetGet(
      SaberWrapper resultSet, int columnIndex, Object value, long elapsedMillis, SQLException e) {
    listeners.forEach(
        listener -> listener.onAfterResultSetGet(resultSet, columnIndex, value, elapsedMillis, e));
  }

  @Override
  public void onAfterResultSetClose(SaberWrapper resultSet, long elapsedMillis, SQLException e) {
    listeners.forEach(listener -> listener.onAfterResultSetClose(resultSet, elapsedMillis, e));
  }
}
