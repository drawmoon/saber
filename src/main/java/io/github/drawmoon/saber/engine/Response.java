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

/**
 * Represents a response object that holds the result of a request processing. It contains the
 * original request, data returned from the processing, and any errors encountered.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class Response {

  private final Request request;
  private DataTable data;
  private String[] errors;

  /**
   * Constructs a new Response object with the given request.
   *
   * @param request The original request associated with this response. Must not be null.
   * @throws NullPointerException if the request is null.
   */
  public Response(Request request) {
    this.request = checkNotNull(request);
  }

  /**
   * Gets the original request associated with this response.
   *
   * @return The original request.
   */
  public Request getRequest() {
    return request;
  }

  /**
   * Gets the unique identifier of the original request.
   *
   * @return The request ID.
   */
  public int getRequestId() {
    return request.getRequestId();
  }

  /**
   * Gets the data returned from the request processing.
   *
   * @return The data table containing the result of the request.
   */
  public DataTable getData() {
    return data;
  }

  /**
   * Sets the data returned from the request processing.
   *
   * @param data The data table containing the result of the request.
   */
  public void setData(DataTable data) {
    this.data = data;
  }

  /**
   * Gets the array of error messages encountered during request processing.
   *
   * @return An array of error messages, or null if no errors occurred.
   */
  public String[] getErrors() {
    return errors;
  }

  /**
   * Sets the array of error messages encountered during request processing.
   *
   * @param errors An array of error messages.
   */
  public void setErrors(String[] errors) {
    this.errors = errors;
  }
}
