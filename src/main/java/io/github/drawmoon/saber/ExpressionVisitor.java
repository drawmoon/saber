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

/** A expression visitor. */
public interface ExpressionVisitor<T> {

  @Nonnull
  T visitExplain(ExplainExpression explain);

  @Nonnull
  T visitSelect(SelectExpression select);

  @Nonnull
  T visitDistinct(DistinctExpression distinct);

  @Nonnull
  T visitAsterisk(AsteriskExpression asterisk);

  @Nonnull
  T visitMember(MemberExpression member);

  @Nonnull
  T visitTableField(TableFieldExpression tableField);

  @Nonnull
  T visitTable(TableExpression table);

  @Nonnull
  T visitJoin(JoinExpression join);

  @Nonnull
  T visitComparison(ComparisonExpression comparison);

  @Nonnull
  T visitLogical(LogicalExpression logical);

  @Nonnull
  <V> T visitVariable(VariableExpression<V> variable);

  @Nonnull
  T visitAggregate(AggregateExpression aggregate);
}
