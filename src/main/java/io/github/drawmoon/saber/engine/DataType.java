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

import java.sql.Types;

public final class DataType<T> {

  public static final DataType<Integer> INTEGER = new DataType<>(Integer.class, Types.INTEGER);

  // -----------------------------------------------------------------------
  final Class<T> clazz;
  final int type;

  private DataType(Class<T> clazz, int type) {
    this.clazz = checkNotNull(clazz);
    this.type = type;
  }
}
