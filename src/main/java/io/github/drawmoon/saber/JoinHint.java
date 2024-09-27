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
 * Enum representing different types of join hints used in SQL queries.
 *
 * <p>A join hint provides guidance to the query optimizer on how to perform joins between tables.
 * This can be particularly useful for optimizing performance in complex queries.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public enum JoinHint {

  /** Specifies a hash join hint. */
  HASH("hash"),

  /** Specifies a loop join hint. */
  LOOP("loop"),

  /** Specifies a merge join hint. */
  MERGE("merge"),

  /** Specifies a remote join hint. */
  REMOTE("remote");

  private final String keyword;

  /**
   * Constructs a new {@code JoinHint} instance with the specified keyword.
   *
   * @param keyword the keyword associated with this join hint, not null
   */
  JoinHint(String keyword) {
    this.keyword = keyword;
  }

  /**
   * Accepts an {@link ExpressionContext} object to render a SQL string or bind variables.
   *
   * @param ctx the {@link ExpressionContext} object to accept, not null
   */
  public void accept(ExpressionContext ctx) {
    ctx.writeKeyword(Keyword.of(this.keyword));
  }
}
