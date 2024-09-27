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

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import java.util.List;
import java.util.function.Function;
import javax.annotation.Nonnull;

/**
 * An expression visitor that replaces one expression with another in given expression tree.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public final class ReplacingVisitor implements ExpressionVisitor<Expression> {

  private final Function<Expression, Expression> replaceFn;

  /**
   * Create a new replacing visitor.
   *
   * @param replaceFn the function to replace the expression
   */
  public ReplacingVisitor(Function<Expression, Expression> replaceFn) {
    this.replaceFn = checkNotNull(replaceFn);
  }

  /**
   * Visit explain expression.
   *
   * @param explain the explain expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitExplain(ExplainExpression explain) {
    return null;
  }

  /**
   * Visit select expression.
   *
   * @param select the select expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitSelect(SelectExpression select) {
    return null;
  }

  /**
   * Visit distinct expression.
   *
   * @param distinct the distinct expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitDistinct(DistinctExpression distinct) {
    return null;
  }

  /**
   * Visit asterisk expression.
   *
   * @param asterisk the asterisk expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitAsterisk(AsteriskExpression asterisk) {
    return null;
  }

  /**
   * Visit member expression.
   *
   * @param member the member expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitMember(MemberExpression member) {
    return null;
  }

  /**
   * Visit table field expression.
   *
   * @param tableField the table field expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitTableField(TableFieldExpression tableField) {
    return null;
  }

  /**
   * Visit table expression.
   *
   * @param table the table expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitTable(TableExpression table) {
    return null;
  }

  /**
   * Visit join expression.
   *
   * @param join the join expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitJoin(JoinExpression join) {
    return null;
  }

  /**
   * Visit comparison expression.
   *
   * @param comparison the comparison expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitComparison(ComparisonExpression comparison) {
    return null;
  }

  /**
   * Visit logical expression.
   *
   * @param logical the logical expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitLogical(LogicalExpression logical) {
    return null;
  }

  /**
   * Visit variable expression.
   *
   * @param variable the variable expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public <V> Expression visitVariable(VariableExpression<V> variable) {
    return null;
  }

  /**
   * Visit aggregate expression.
   *
   * @param aggregate the aggregate expression
   * @return the result, not null
   */
  @Nonnull
  @Override
  public Expression visitAggregate(AggregateExpression aggregate) {
    Expression newExpr = replaceFn.apply(aggregate);
    if (newExpr != aggregate) {
      return newExpr;
    }

    List<Expression> args = aggregate.getArguments();
    for (int i = 0, argsSize = args.size(); i < argsSize; i++) {
      Expression oldArgExpr = args.get(i);
      Expression newArgExpr = oldArgExpr.accept(this);
      if (newArgExpr != oldArgExpr) {
        args.set(i, newArgExpr);
      }
    }

    AggregateExpression newAggregateExpr = new AggregateExpression(args, aggregate.getOperator());
    if (aggregate.isDistinct()) {
      newAggregateExpr.distinct();
    }
    newAggregateExpr.as(aggregate.getAlias());
    return newAggregateExpr;
  }
}
