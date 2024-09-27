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
 *
 * @author drash
 * @version 1.0
 * @since 2024
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
   * Retrieves the alias set for the SELECT statement.
   *
   * @return The alias name as a String.
   */
  String getAlias();

  /**
   * Sets an alias for the SELECT statement.
   *
   * @param alias The alias name.
   * @return The Select object itself for method chaining.
   */
  Select as(String alias);

  /**
   * Specifies whether the SELECT statement should include the DISTINCT keyword.
   *
   * @param distinct {@code true} to include the DISTINCT keyword, {@code false} otherwise.
   * @return This Select object for method chaining.
   */
  Select distinct(boolean distinct);

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

  /**
   * Sets the OFFSET and LIMIT values for pagination.
   *
   * @param limit the maximum number of rows to return
   * @param skip the number of rows to skip
   * @return this Select object for method chaining
   */
  Select offset(int limit, int skip);

  /**
   * Sets the page number and page size for pagination.
   *
   * @param page The page number (starting from 1).
   * @param pageSize The number of items per page.
   * @return This Select object for method chaining.
   */
  default Select pageBy(int page, int pageSize) {
    if (pageSize <= 0) return this;

    int defaultPageNumber = 1;
    if (page <= 0) {
      page = defaultPageNumber;
    }

    int skip = (page - 1) * pageSize;
    int limit = pageSize;
    return this.offset(limit, skip);
  }

  /**
   * Combines this SELECT statement with another SELECT statement using the UNION operator.
   *
   * @param s the other SELECT statement
   * @return this Select object for method chaining
   */
  Select union(Select s);

  /**
   * Combines this SELECT statement with another SELECT statement using the INTERSECT operator.
   *
   * @param s the other SELECT statement
   * @return this Select object for method chaining
   */
  Select intersect(Select s);
}
