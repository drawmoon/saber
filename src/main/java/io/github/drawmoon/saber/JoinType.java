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
 * Enum representing different types of SQL JOIN operations.
 *
 * <p>This enum defines constants for various SQL JOIN types, each with its specific behavior in
 * terms of how it combines records from two tables. The join types include INNER JOIN, LEFT JOIN
 * (LEFT OUTER JOIN), RIGHT JOIN (RIGHT OUTER JOIN), and FULL JOIN (FULL OUTER JOIN).
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public enum JoinType {

  /**
   * Represents the INNER JOIN keyword.
   *
   * <p>The INNER JOIN selects records that have matching values in both tables. Only rows with
   * matching keys in both tables are returned.
   */
  INNER_JOIN("inner join"),

  /**
   * Represents the LEFT JOIN keyword.
   *
   * <p>The LEFT JOIN returns all records from the left table (table1), and the matching records
   * from the right table (table2). If there is no match, the result is NULL on the side of the
   * right table.
   */
  LEFT_JOIN("left join"),

  /**
   * Represents the LEFT OUTER JOIN keyword.
   *
   * <p>The LEFT OUTER JOIN behaves similarly to LEFT JOIN. It returns all records from the left
   * table (table1), and the matching records from the right table (table2). If there is no match,
   * the result is NULL on the side of the right table.
   */
  LEFT_OUTER_JOIN("left outer join"),

  /**
   * Represents the RIGHT JOIN keyword.
   *
   * <p>The RIGHT JOIN returns all records from the right table (table2), and the matching records
   * from the left table (table1). If there is no match, the result is NULL on the side of the left
   * table.
   */
  RIGHT_JOIN("right join"),

  /**
   * Represents the RIGHT OUTER JOIN keyword.
   *
   * <p>The RIGHT OUTER JOIN behaves similarly to RIGHT JOIN. It returns all records from the right
   * table (table2), and the matching records from the left table (table1). If there is no match,
   * the result is NULL on the side of the left table.
   */
  RIGHT_OUTER_JOIN("right outer join"),

  /**
   * Represents the FULL JOIN keyword.
   *
   * <p>The FULL JOIN returns all records when there is a match in either left (table1) or right
   * (table2) table records. If there is no match, the result is NULL on the side where there is no
   * match.
   */
  FULL_JOIN("full join"),

  /**
   * Represents the FULL OUTER JOIN keyword.
   *
   * <p>The FULL OUTER JOIN behaves similarly to FULL JOIN. It returns all records when there is a
   * match in either left (table1) or right (table2) table records. If there is no match, the result
   * is NULL on the side where there is no match.
   */
  FULL_OUTER_JOIN("full outer join");

  private final String keyword;

  /**
   * Constructor for JoinType enum.
   *
   * @param keyword The SQL keyword associated with this join type, not null.
   */
  JoinType(String keyword) {
    this.keyword = keyword;
  }

  /**
   * Accepts an {@link ExpressionContext} object to render a SQL string or bind variables.
   *
   * @param ctx The {@link ExpressionContext} object to accept, not null.
   */
  public void accept(ExpressionContext ctx) {
    ctx.writeKeyword(Keyword.of(this.keyword));
  }
}
