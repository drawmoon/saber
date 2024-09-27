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

public interface SaberWrapper extends Wrapper {

  /**
   * Get wrapper id.
   *
   * @return the wrapper id
   */
  int getWrapperId();

  /**
   * Get request id.
   *
   * @return the request id
   */
  int getRequestId();

  /**
   * Get query context.
   *
   * @return the query context
   */
  RequestContext getContext();
}
