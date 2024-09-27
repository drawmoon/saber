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

/**
 * A visitor interface for traversing and processing various types of expressions. This interface
 * extends the generic {@link Visitor} interface and provides methods for visiting different kinds
 * of expressions used in query processing.
 *
 * @param <T> The type of result returned by each visit method.
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface ExpressionVisitor<T> extends Visitor<T> {

  /**
   * Visits an explain expression.
   *
   * @param explain The explain expression to be visited.
   * @return The result of visiting the explain expression, never null.
   */
  @Nonnull
  T visitExplain(ExplainExpression explain);

  /**
   * Visits a select expression.
   *
   * @param select The select expression to be visited.
   * @return The result of visiting the select expression, never null.
   */
  @Nonnull
  T visitSelect(SelectExpression select);

  /**
   * Visits a distinct expression.
   *
   * @param distinct The distinct expression to be visited.
   * @return The result of visiting the distinct expression, never null.
   */
  @Nonnull
  T visitDistinct(DistinctExpression distinct);

  /**
   * Visits an asterisk expression.
   *
   * @param asterisk The asterisk expression to be visited.
   * @return The result of visiting the asterisk expression, never null.
   */
  @Nonnull
  T visitAsterisk(AsteriskExpression asterisk);

  /**
   * Visits a member expression.
   *
   * @param member The member expression to be visited.
   * @return The result of visiting the member expression, never null.
   */
  @Nonnull
  T visitMember(MemberExpression member);

  /**
   * Visits a table field expression.
   *
   * @param tableField The table field expression to be visited.
   * @return The result of visiting the table field expression, never null.
   */
  @Nonnull
  T visitTableField(TableFieldExpression tableField);

  /**
   * Visits a table expression.
   *
   * @param table The table expression to be visited.
   * @return The result of visiting the table expression, never null.
   */
  @Nonnull
  T visitTable(TableExpression table);

  /**
   * Visits a join expression.
   *
   * @param join The join expression to be visited.
   * @return The result of visiting the join expression, never null.
   */
  @Nonnull
  T visitJoin(JoinExpression join);

  /**
   * Visits a comparison expression.
   *
   * @param comparison The comparison expression to be visited.
   * @return The result of visiting the comparison expression, never null.
   */
  @Nonnull
  T visitComparison(ComparisonExpression comparison);

  /**
   * Visits a logical expression.
   *
   * @param logical The logical expression to be visited.
   * @return The result of visiting the logical expression, never null.
   */
  @Nonnull
  T visitLogical(LogicalExpression logical);

  /**
   * Visits a variable expression.
   *
   * @param variable The variable expression to be visited.
   * @param <V> The type of the variable.
   * @return The result of visiting the variable expression, never null.
   */
  @Nonnull
  <V> T visitVariable(VariableExpression<V> variable);

  /**
   * Visits an aggregate expression.
   *
   * @param aggregate The aggregate expression to be visited.
   * @return The result of visiting the aggregate expression, never null.
   */
  @Nonnull
  T visitAggregate(AggregateExpression aggregate);
}
