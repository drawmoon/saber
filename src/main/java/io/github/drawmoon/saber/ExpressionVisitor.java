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

import io.github.drawmoon.saber.common.Visitor;
import javax.annotation.Nonnull;

/** A visitor interface for visiting expressions. */
public interface ExpressionVisitor<T> extends Visitor<T> {

  /**
   * Visit explain expression.
   *
   * @param explain the explain expression
   * @return the result, not null
   */
  @Nonnull
  T visitExplain(ExplainExpression explain);

  /**
   * Visit select expression.
   *
   * @param select the select expression
   * @return the result, not null
   */
  @Nonnull
  T visitSelect(SelectExpression select);

  /**
   * Visit distinct expression.
   *
   * @param distinct the distinct expression
   * @return the result, not null
   */
  @Nonnull
  T visitDistinct(DistinctExpression distinct);

  /**
   * Visit asterisk expression.
   *
   * @param asterisk the asterisk expression
   * @return the result, not null
   */
  @Nonnull
  T visitAsterisk(AsteriskExpression asterisk);

  /**
   * Visit member expression.
   *
   * @param member the member expression
   * @return the result, not null
   */
  @Nonnull
  T visitMember(MemberExpression member);

  /**
   * Visit table field expression.
   *
   * @param tableField the table field expression
   * @return the result, not null
   */
  @Nonnull
  T visitTableField(TableFieldExpression tableField);

  /**
   * Visit table expression.
   *
   * @param table the table expression
   * @return the result, not null
   */
  @Nonnull
  T visitTable(TableExpression table);

  /**
   * Visit join expression.
   *
   * @param join the join expression
   * @return the result, not null
   */
  @Nonnull
  T visitJoin(JoinExpression join);

  /**
   * Visit comparison expression.
   *
   * @param comparison the comparison expression
   * @return the result, not null
   */
  @Nonnull
  T visitComparison(ComparisonExpression comparison);

  /**
   * Visit logical expression.
   *
   * @param logical the logical expression
   * @return the result, not null
   */
  @Nonnull
  T visitLogical(LogicalExpression logical);

  /**
   * Visit variable expression.
   *
   * @param variable the variable expression
   * @return the result, not null
   * @param <V> the type of the variable
   */
  @Nonnull
  <V> T visitVariable(VariableExpression<V> variable);

  /**
   * Visit aggregate expression.
   *
   * @param aggregate the aggregate expression
   * @return the result, not null
   */
  @Nonnull
  T visitAggregate(AggregateExpression aggregate);
}
