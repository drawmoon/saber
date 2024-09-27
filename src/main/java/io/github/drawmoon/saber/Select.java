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
 * The Select interface represents a SQL SELECT statement. It extends the Expression and
 * SqlGenerator interfaces.
 *
 * <p>This interface provides methods to build and customize a SELECT statement, including
 * specifying the fields, tables, conditions, and other SQL clauses. It supports method chaining to
 * allow for fluent API usage.
 */
public interface Select extends Expression, ExpressionRender<String> {

  /**
   * Gets the field associated with the SELECT statement.
   *
   * @return The Field object representing the selected field.
   */
  Field getField();

  /**
   * Gets the table associated with the SELECT statement.
   *
   * @return The Table object representing the selected table.
   */
  Table getTable();

  /**
   * Sets an alias for the SELECT statement.
   *
   * @param alias The alias name.
   * @return The Select object itself for method chaining.
   */
  Select as(String alias);

  /**
   * Specifies the source table of the SELECT statement.
   *
   * @param t The Table object representing the source table.
   * @return The Select object itself for method chaining.
   */
  Select from(Table t);

  /**
   * Adds a condition to the WHERE clause of the SELECT statement.
   *
   * @param c The Condition object representing the condition.
   * @return The Select object itself for method chaining.
   */
  Select where(Condition c);

  /**
   * Adds a condition to the HAVING clause of the SELECT statement.
   *
   * @param c The Condition object representing the condition.
   * @return The Select object itself for method chaining.
   */
  Select having(Condition c);

  /**
   * Adds sorting fields to the ORDER BY clause of the SELECT statement.
   *
   * @param f The Field objects representing the sorting fields.
   * @return The Select object itself for method chaining.
   */
  Select orderBy(Field... f);

  /**
   * Adds grouping fields to the GROUP BY clause of the SELECT statement.
   *
   * @param f The Field objects representing the grouping fields.
   * @return The Select object itself for method chaining.
   */
  Select groupBy(Field... f);
}
