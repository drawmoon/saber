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

import javax.annotation.Nonnull;

/**
 * A select statement.
 *
 * <p>Example usages:
 *
 * <pre>{@code
 * DSL.create(SqlDialect.MY_SQL)
 *    .select(fields)
 *    .from(table)
 *    .where(condition)
 *    .orderBy(field1)
 *    .groupBy(field2);
 * }</pre>
 */
public interface Select extends Expression, SqlGenerator {

  /**
   * Gets the field of the select statement.
   *
   * @return the field, not null
   */
  @Nonnull
  Field getField();

  /**
   * Gets the table of the select statement.
   *
   * @return the table, not null
   */
  Table getTable();

  /**
   * Sets the alias for the select statement.
   *
   * @param alias the alias, not null
   * @return the select, not null
   */
  @Nonnull
  Select as(String alias);

  /**
   * Sets the table to be queried.
   *
   * @param t the table, not null
   * @return the select, not null
   */
  @Nonnull
  Select from(Table t);

  /**
   * Sets the where clause.
   *
   * @param c the condition, not null
   * @return the select, not null
   */
  @Nonnull
  Select where(Condition c);

  /**
   * Sets the having clause.
   *
   * @param c the condition, not null
   * @return the select, not null
   */
  @Nonnull
  Select having(Condition c);

  /**
   * Sets the order by clause.
   *
   * @param f the order fields, not null
   * @return the select, not null
   */
  @Nonnull
  Select orderBy(Field... f);

  /**
   * Sets the group by clause.
   *
   * @param f the group fields, not null
   * @return the select, not null
   */
  @Nonnull
  Select groupBy(Field... f);
}
