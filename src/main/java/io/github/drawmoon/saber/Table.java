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

import java.util.List;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** A table. */
public interface Table extends Expression {

  /**
   * Returns the name of the table.
   *
   * @return the name of the table
   */
  String getName();

  /**
   * Returns the alias of the table.
   *
   * @return the alias of the table
   */
  String getAlias();

  /**
   * Gets the schema of this table.
   *
   * @return returns the schema, not null
   */
  @CheckForNull
  Schema<Table, Field> getSchema();

  /**
   * Returns the number of fields in this table.
   *
   * @return returns the number
   */
  int fieldCount();

  /**
   * Gets the fields of this table.
   *
   * @return returns the fields, not null
   */
  @CheckForNull
  List<Field> getFields();

  /**
   * Gets a field from this table.
   *
   * @param f the field name, not null
   * @return returns the field, not null
   */
  @CheckForNull
  Field getField(String f);

  /**
   * Sets the alias for the table.
   *
   * @param alias the alias, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table as(String alias);

  /**
   * Create a qualified asterisk expression from this table, table.* for use with SELECT.
   *
   * @return returns the asterisk instance, not null
   */
  @Nonnull
  Field asterisk();

  /**
   * Join a table to this table.
   *
   * @param t the table to join, not null
   * @param jt the join type, not null
   * @param jh the join hint, not null
   * @return returns the join table, not null
   */
  @Nonnull
  Table join(Table t, JoinType jt, @Nullable JoinHint jh);

  // -----------------------------------------------------------------------
  /**
   * Use the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.UseIndex("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table useIndex(String... i);

  /**
   * Use the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.UseIndexForJoin("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table useIndexForJoin(String... i);

  /**
   * Use the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.UseIndexForOrderBy("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table useIndexForOrderBy(String... i);

  /**
   * Use the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.UseIndexForGroupBy("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table useIndexForGroupBy(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.IgnoreIndex("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table ignoreIndex(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.IgnoreIndexForJoin("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table ignoreIndexForJoin(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.IgnoreIndexForOrderBy("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table ignoreIndexForOrderBy(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.IgnoreIndexForGroupBy("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table ignoreIndexForGroupBy(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.ForceIndex("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table forceIndex(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.ForceIndexForJoin("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table forceIndexForJoin(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.ForceIndexForOrderBy("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table forceIndexForOrderBy(String... i);

  /**
   * Ignore the index hints for this table.
   *
   * <p>Example usages:
   *
   * <pre>
   *   table.ForceIndexForGroupBy("index1", "index2")</pre>
   *
   * @param i the index hints, not null
   * @return returns the table, not null
   */
  @Nonnull
  Table forceIndexForGroupBy(String... i);
}
