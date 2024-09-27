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
package io.github.drawmoon.saber.engine;

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import io.github.drawmoon.saber.AggregateExpression;
import io.github.drawmoon.saber.AsteriskExpression;
import io.github.drawmoon.saber.ComparisonExpression;
import io.github.drawmoon.saber.DistinctExpression;
import io.github.drawmoon.saber.ExplainExpression;
import io.github.drawmoon.saber.Expression;
import io.github.drawmoon.saber.ExpressionVisitor;
import io.github.drawmoon.saber.Expressions;
import io.github.drawmoon.saber.Field;
import io.github.drawmoon.saber.JoinExpression;
import io.github.drawmoon.saber.LogicalExpression;
import io.github.drawmoon.saber.MemberExpression;
import io.github.drawmoon.saber.SelectExpression;
import io.github.drawmoon.saber.SqlDialect;
import io.github.drawmoon.saber.Table;
import io.github.drawmoon.saber.TableExpression;
import io.github.drawmoon.saber.TableFieldExpression;
import io.github.drawmoon.saber.VariableExpression;
import javax.annotation.Nonnull;

public class BasicSqlBuilder implements ExpressionVisitor<BasicSqlBuilder> {

  protected final ExpressionContext ctx;
  protected final SaberEventListener listener;

  public BasicSqlBuilder(ExpressionContext ctx) {
    this.ctx = checkNotNull(ctx);
    this.listener = ctx.getListener();
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitExplain(ExplainExpression explain) {
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitSelect(SelectExpression select) {
    SqlDialect dialect = ctx.getDialect().getRoot();

    ctx.writeKeyword(Expressions.keyword("SELECT")).writeSql(" ");

    Field field = select.getField();
    field.accept(this);

    Table table = select.getTable();
    if (table != null) {
      ctx.writeSql(" ").writeKeyword(Expressions.keyword("FROM")).writeSql(" ");
      table.accept(this);
    } else if (dialect == SqlDialect.ORACLE) {
      ctx.writeSql(" ").writeKeyword(Expressions.keyword("FROM")).writeSql(" ").writeSql("DUAL");
    }

    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitDistinct(DistinctExpression distinct) {
    ctx.writeKeyword(Expressions.keyword("DISTINCT")).writeSql("(");
    for (Expression e : distinct) {
      e.accept(this);
    }
    ctx.writeSql(")");
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitAsterisk(AsteriskExpression asterisk) {
    ctx.writeSql("*");
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitMember(MemberExpression member) {
    int i = 0;
    for (Expression e : member) {
      if (i > 0) ctx.writeSql(", ");
      e.accept(this);
      i++;
    }
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitTableField(TableFieldExpression tableField) {
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitTable(TableExpression table) {
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitJoin(JoinExpression join) {
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitComparison(ComparisonExpression comparison) {
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitLogical(LogicalExpression logical) {
    return this;
  }

  @Override
  @Nonnull
  public <V> BasicSqlBuilder visitVariable(VariableExpression<V> variable) {
    DataType<V> type = variable.getType();
    if (type == DataType.INTEGER) {
      ctx.writeSql(String.valueOf(variable.getValue()));
    }
    return this;
  }

  @Override
  @Nonnull
  public BasicSqlBuilder visitAggregate(AggregateExpression aggregate) {
    return this;
  }

  @Override
  public String toString() {
    return ctx.render();
  }
}
