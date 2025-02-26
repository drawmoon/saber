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

/**
 * Represents a condition or predicate in SQL queries.
 *
 * <p>Conditions are used in various SQL clauses, primarily in the WHERE clause of a SELECT
 * statement. They can be combined using logical operators to form complex conditions.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface Condition extends Expression {

  /**
   * Performs a logical AND operation between this Condition and the specified expression.
   *
   * @param expr The expression to be logically ANDed with this Condition.
   * @return return a new Condition representing the logical AND operation.
   */
  Condition and(Expression expr);

  /**
   * Performs a logical OR operation between this Condition and the specified expression.
   *
   * @param expr The expression to be logically ORed with this Condition.
   * @return return a new Condition representing the logical OR operation.
   */
  Condition or(Expression expr);
}
