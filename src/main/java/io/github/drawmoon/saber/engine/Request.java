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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a request object that holds information about a specific request. This class manages
 * request details such as connection URI, query, and timeout. It also assigns a unique request ID
 * to each instance.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class Request {

  /** A static counter used to generate unique request IDs. */
  private static final AtomicInteger counter = new AtomicInteger(0);

  /** The unique identifier for this request. */
  private final int requestId = counter.getAndIncrement();

  /** The URI of the connection for this request. */
  private String connectionUri;

  /** The query string associated with this request. */
  private String query;

  /** The timeout value for this request. */
  private Integer timeout;

  /**
   * Gets the unique identifier for this request.
   *
   * @return the unique request ID
   */
  public int getRequestId() {
    return requestId;
  }

  /**
   * Gets the connection URI for this request.
   *
   * @return the connection URI
   */
  public String getConnectionUri() {
    return connectionUri;
  }

  /**
   * Sets the connection URI for this request.
   *
   * @param connectionUri the connection URI to set
   */
  public void setConnectionUri(String connectionUri) {
    this.connectionUri = connectionUri;
  }

  /**
   * Gets the query string for this request.
   *
   * @return the query string
   */
  public String getQuery() {
    return query;
  }

  /**
   * Sets the query string for this request.
   *
   * @param query the query string to set
   */
  public void setQuery(String query) {
    this.query = query;
  }

  /**
   * Gets the timeout value for this request.
   *
   * @return the timeout value
   */
  public Integer getTimeout() {
    return timeout;
  }

  /**
   * Sets the timeout value for this request.
   *
   * @param timeout the timeout value to set
   */
  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }
}
