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
 * An enumeration representing various comparison operators used in SQL queries. This enum provides
 * a set of predefined comparators that can be utilized to construct comparison predicates within
 * conditions. Each comparator has a corresponding SQL keyword that it represents.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public enum Comparator {
  /** Represents the equality operator "=". */
  EQ("="),

  /** Represents the inequality operator "<>". */
  NE("<>"),

  /** Represents the less than operator "<". */
  LT("<"),

  /** Represents the greater than operator ">". */
  GT(">"),

  /** Represents the less than or equal to operator "<=". */
  LE("<="),

  /** Represents the greater than or equal to operator ">=". */
  GE(">="),

  /** Represents the "IN" operator which checks if a value exists within a list. */
  IN("in"),

  /** Represents the "NOT IN" operator which checks if a value does not exist within a list. */
  NOT_IN("not in"),

  /** Represents the "LIKE" operator which matches a pattern with wildcards. */
  LIKE("like"),

  /** Represents the "NOT LIKE" operator which ensures a pattern does not match. */
  NOT_LIKE("not like"),

  /** Represents the case-insensitive "ILIKE" operator which matches a pattern with wildcards. */
  LIKE_IGNORE_CASE("ilike"),

  /**
   * Represents the case-insensitive "NOT ILIKE" operator which ensures a pattern does not match.
   */
  NOT_LIKE_IGNORE_CASE("not ilike"),

  /** Represents the "BETWEEN" operator which checks if a value lies within a specified range. */
  BETWEEN("between"),

  /**
   * Represents the "NOT BETWEEN" operator which checks if a value does not lie within a specified
   * range.
   */
  NOT_BETWEEN("not between"),

  /** Represents the "IS NULL" operator which checks if a value is null. */
  IS_NULL("is null"),

  /** Represents the "IS NOT NULL" operator which checks if a value is not null. */
  IS_NOT_NULL("is not null");

  private final String keyword;

  /**
   * Constructs a new Comparator instance with the specified SQL keyword.
   *
   * @param keyword The SQL keyword represented by this comparator, must not be null.
   */
  Comparator(String keyword) {
    this.keyword = keyword;
  }

  /**
   * Accepts an {@link ExpressionContext} object to render a SQL string or bind variables. This
   * method writes the corresponding SQL keyword into the context.
   *
   * @param ctx The {@link ExpressionContext} object to accept, must not be null.
   */
  public void accept(ExpressionContext ctx) {
    ctx.writeKeyword(Keyword.of(this.keyword));
  }
}
