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
 * Represents logical operators used for combining conditions in expressions.
 *
 * <p>This enum provides two logical operators: {@link #AND} and {@link #OR}. These operators are
 * used to combine multiple conditions in a logical expression, typically in the context of building
 * SQL queries or other types of conditional expressions.
 *
 * <p>Each operator has an associated keyword that is used when rendering the expression as a
 * string, such as "and" for the AND operator and "or" for the OR operator.
 *
 * @see ExpressionContext
 * @author drash
 * @version 1.0
 * @since 2024
 */
public enum Operator {

  /**
   * Logical AND operator.
   *
   * <p>The AND operator combines two conditions and returns true if both conditions are true.
   * Otherwise, it returns false.
   */
  AND("and"),

  /**
   * Logical OR operator.
   *
   * <p>The OR operator combines two conditions and returns true if at least one of the conditions
   * is true. It returns false only if both conditions are false.
   */
  OR("or");

  private final String keyword;

  /**
   * Constructs an Operator with the specified keyword.
   *
   * @param keyword the keyword representing this operator, must not be null
   */
  Operator(String keyword) {
    this.keyword = keyword;
  }

  /**
   * Accepts an {@link ExpressionContext} object to render the operator as part of a larger
   * expression.
   *
   * <p>This method writes the operator's keyword into the provided context, which can then be used
   * to generate a SQL string or bind variables.
   *
   * @param ctx the {@link ExpressionContext} object to accept, must not be null
   */
  public void accept(ExpressionContext ctx) {
    ctx.writeKeyword(Keyword.of(this.keyword));
  }
}
