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

import io.github.drawmoon.saber.DataTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class RequestContext {

  private final Request request;
  private final SaberOptions options;
  private final SaberEventListener listener;

  @Nullable private DataTable data;
  @Nullable private SQLException sqlError;
  @Nullable private Response response;

  @Nullable private transient SaberWrapper connection;
  @Nullable private transient SaberWrapper statement;
  @Nullable private transient SaberWrapper resultSet;

  public RequestContext(Request request, SaberOptions options, SaberEventListener listener) {
    this.request = checkNotNull(request);
    this.options = checkNotNull(options);
    this.listener = checkNotNull(listener);
  }

  public Request getRequest() {
    return request;
  }

  public int getRequestId() {
    return request.getRequestId();
  }

  public String getConnectionUri() {
    return request.getConnectionUri();
  }

  public String getQuery() {
    return request.getQuery();
  }

  public SaberOptions getOptions() {
    return options;
  }

  public SaberEventListener getListener() {
    return listener;
  }

  public DataTable getData() {
    return data;
  }

  public void setData(DataTable data) {
    this.data = data;
  }

  public SQLException getSqlError() {
    return sqlError;
  }

  public void setSqlError(SQLException sqlError) {
    this.sqlError = sqlError;
  }

  public Response getResponse() {
    if (response != null) {
      return response;
    }

    response = new Response(request);
    if (sqlError != null) {
      List<String> errList = new ArrayList<>();

      assert sqlError != null; // block compiler warning
      Throwable cause = sqlError.getCause();
      while (cause != null) {
        errList.add(cause.getMessage());
        cause = cause.getCause();
      }

      assert response != null; // block compiler warning
      response.setErrors(errList.toArray(new String[0]));
    } else {
      assert response != null; // block compiler warning
      response.setData(data);
    }

    return response;
  }

  public SaberWrapper getConnection() {
    return connection;
  }

  public void setConnection(SaberWrapper connection) {
    this.connection = connection;
  }

  public SaberWrapper getStatement() {
    return statement;
  }

  public void setStatement(SaberWrapper statement) {
    this.statement = statement;
  }

  public SaberWrapper getResultSet() {
    return resultSet;
  }

  public void setResultSet(SaberWrapper resultSet) {
    this.resultSet = resultSet;
  }
}
