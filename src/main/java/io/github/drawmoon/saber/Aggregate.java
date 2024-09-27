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
package io.github.drawmoon.saber;

import io.github.drawmoon.saber.engine.ExpressionContext;

/**
 * Enum representing different types of SQL aggregate functions.
 *
 * <p>This enum encapsulates various SQL aggregate functions such as COUNT, MAX, MIN, SUM, and AVG.
 * Each enum constant holds a keyword that corresponds to the respective SQL aggregate function. The
 * enum provides a method to accept an {@link ExpressionContext} object for rendering SQL strings or
 * binding variables.
 *
 * @see ExpressionContext
 * @author drash
 * @version 1.0
 * @since 2024
 */
public enum Aggregate {
  /** Represents the COUNT aggregate function. */
  COUNT("count"),

  /** Represents the MAX aggregate function. */
  MAX("max"),

  /** Represents the MIN aggregate function. */
  MIN("min"),

  /** Represents the SUM aggregate function. */
  SUM("sum"),

  /** Represents the AVG aggregate function. */
  AVG("avg"),

  /** Represents the ROUND aggregate function. */
  ROUND("round"),

  /** Represents the MEDIAN aggregate function. */
  MEDIAN("median");

  private final String keyword;

  /**
   * Constructs an instance of the {@code Aggregate} enum with the specified keyword.
   *
   * @param keyword the keyword corresponding to the SQL aggregate function, not null
   */
  Aggregate(String keyword) {
    this.keyword = keyword;
  }

  /**
   * Accepts an {@link ExpressionContext} object to render a SQL string or bind its variables.
   *
   * <p>This method writes the appropriate SQL keyword into the provided {@link ExpressionContext}.
   *
   * @param ctx the {@link ExpressionContext} object to accept, not null
   */
  public void accept(ExpressionContext ctx) {
    ctx.writeKeyword(Keyword.of(this.keyword));
  }
}
