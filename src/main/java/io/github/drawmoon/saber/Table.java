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

/**
 * Represents a table in a database.
 *
 * <p>This interface provides methods to interact with and manipulate tables, including retrieving
 * metadata, setting aliases, joining tables, and managing index hints.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
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
   * Gets the schema associated with this table.
   *
   * @return the schema object, or null if not available
   */
  @CheckForNull
  Schema<Table, Field> getSchema();

  /**
   * Returns the number of fields in this table.
   *
   * @return the count of fields in this table
   */
  int fieldCount();

  /**
   * Retrieves the list of fields in this table.
   *
   * @return a list of fields, or null if not available
   */
  @CheckForNull
  List<Field> getFields();

  /**
   * Retrieves a specific field from this table by its name.
   *
   * @param fieldName the name of the field to retrieve
   * @return the field object, or null if not found
   */
  @CheckForNull
  Field getField(String fieldName);

  /**
   * Sets an alias for the table.
   *
   * @param alias the alias to set
   * @return the table instance with the new alias
   */
  @Nonnull
  Table as(String alias);

  /**
   * Creates a qualified asterisk expression from this table, suitable for use in SELECT statements.
   *
   * @return the asterisk field instance
   */
  @Nonnull
  Field asterisk();

  /**
   * Joins another table to this table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jt the type of join (e.g., INNER, LEFT OUTER)
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  Table join(Table t, Condition c, JoinType jt, @Nullable JoinHint jh);

  /**
   * Performs an inner join with another table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  default Table innerJoin(Table t, Condition c, @Nullable JoinHint jh) {
    return join(t, c, JoinType.INNER_JOIN, jh);
  }

  /**
   * Performs a left join with another table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  default Table leftJoin(Table t, Condition c, @Nullable JoinHint jh) {
    return join(t, c, JoinType.LEFT_JOIN, jh);
  }

  /**
   * Performs a left outer join with another table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  default Table leftOuterJoin(Table t, Condition c, @Nullable JoinHint jh) {
    return join(t, c, JoinType.LEFT_OUTER_JOIN, jh);
  }

  /**
   * Performs a right join with another table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  default Table rightJoin(Table t, Condition c, @Nullable JoinHint jh) {
    return join(t, c, JoinType.RIGHT_JOIN, jh);
  }

  /**
   * Performs a right outer join with another table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  default Table rightOuterJoin(Table t, Condition c, @Nullable JoinHint jh) {
    return join(t, c, JoinType.RIGHT_OUTER_JOIN, jh);
  }

  /**
   * Performs a full join with another table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  default Table fullJoin(Table t, Condition c, @Nullable JoinHint jh) {
    return join(t, c, JoinType.FULL_JOIN, jh);
  }

  /**
   * Performs a full outer join with another table.
   *
   * @param t the table to join with
   * @param c the join condition
   * @param jh an optional hint for the join operation
   * @return the resulting joined table
   */
  @Nonnull
  default Table fullOuterJoin(Table t, Condition c, @Nullable JoinHint jh) {
    return join(t, c, JoinType.FULL_OUTER_JOIN, jh);
  }

  // -----------------------------------------------------------------------
  /**
   * Applies index hints to this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.useIndex("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to apply
   * @return the table instance with applied index hints
   */
  @Nonnull
  Table useIndex(String... indexHints);

  /**
   * Applies index hints specifically for join operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.useIndexForJoin("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to apply for join operations
   * @return the table instance with applied index hints for joins
   */
  @Nonnull
  Table useIndexForJoin(String... indexHints);

  /**
   * Applies index hints specifically for ORDER BY operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.useIndexForOrderBy("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to apply for ORDER BY operations
   * @return the table instance with applied index hints for ORDER BY
   */
  @Nonnull
  Table useIndexForOrderBy(String... indexHints);

  /**
   * Applies index hints specifically for GROUP BY operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.useIndexForGroupBy("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to apply for GROUP BY operations
   * @return the table instance with applied index hints for GROUP BY
   */
  @Nonnull
  Table useIndexForGroupBy(String... indexHints);

  /**
   * Ignores specified index hints for this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.ignoreIndex("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to ignore
   * @return the table instance with ignored index hints
   */
  @Nonnull
  Table ignoreIndex(String... indexHints);

  /**
   * Ignores specified index hints specifically for join operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.ignoreIndexForJoin("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to ignore for join operations
   * @return the table instance with ignored index hints for joins
   */
  @Nonnull
  Table ignoreIndexForJoin(String... indexHints);

  /**
   * Ignores specified index hints specifically for ORDER BY operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.ignoreIndexForOrderBy("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to ignore for ORDER BY operations
   * @return the table instance with ignored index hints for ORDER BY
   */
  @Nonnull
  Table ignoreIndexForOrderBy(String... indexHints);

  /**
   * Ignores specified index hints specifically for GROUP BY operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.ignoreIndexForGroupBy("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to ignore for GROUP BY operations
   * @return the table instance with ignored index hints for GROUP BY
   */
  @Nonnull
  Table ignoreIndexForGroupBy(String... indexHints);

  /**
   * Forces the use of specified index hints for this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.forceIndex("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to force
   * @return the table instance with forced index hints
   */
  @Nonnull
  Table forceIndex(String... indexHints);

  /**
   * Forces the use of specified index hints specifically for join operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.forceIndexForJoin("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to force for join operations
   * @return the table instance with forced index hints for joins
   */
  @Nonnull
  Table forceIndexForJoin(String... indexHints);

  /**
   * Forces the use of specified index hints specifically for ORDER BY operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.forceIndexForOrderBy("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to force for ORDER BY operations
   * @return the table instance with forced index hints for ORDER BY
   */
  @Nonnull
  Table forceIndexForOrderBy(String... indexHints);

  /**
   * Forces the use of specified index hints specifically for GROUP BY operations on this table.
   *
   * <p>Example usage:
   *
   * <pre>
   *   table.forceIndexForGroupBy("index1", "index2");
   * </pre>
   *
   * @param indexHints the index hints to force for GROUP BY operations
   * @return the table instance with forced index hints for GROUP BY
   */
  @Nonnull
  Table forceIndexForGroupBy(String... indexHints);
}
