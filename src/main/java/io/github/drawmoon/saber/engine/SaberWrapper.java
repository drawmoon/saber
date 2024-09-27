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

import java.sql.Wrapper;

/**
 * A wrapper interface that extends the standard SQL Wrapper interface. This interface provides
 * additional methods for managing wrapper-specific information, such as wrapper ID, request ID, and
 * query context. It is designed to be implemented by classes that need to encapsulate and manage
 * these details in a consistent manner.
 *
 * <p>The {@code SaberWrapper} interface is part of the Saber engine and is intended to provide a
 * standardized way to handle wrapper-related operations within the system.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface SaberWrapper extends Wrapper {

  /**
   * Retrieves the unique identifier for this wrapper instance.
   *
   * @return an integer representing the wrapper ID
   */
  int getWrapperId();

  /**
   * Retrieves the unique identifier for the associated request.
   *
   * @return an integer representing the request ID
   */
  int getRequestId();

  /**
   * Retrieves the context in which the query is executed.
   *
   * @return a {@link RequestContext} object containing the query context information
   */
  RequestContext getContext();
}
