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

import static com.google.common.base.Preconditions.checkArgument;
import static io.github.drawmoon.saber.common.Preconditions.checkNotWhiteSpace;
import static io.github.drawmoon.saber.common.Preconditions.collectionNullClean;
import static io.github.drawmoon.saber.common.Preconditions.ensureNull;

import io.github.drawmoon.saber.engine.ExpressionContext;
import io.github.drawmoon.saber.engine.ExpressionIterator;
import java.io.Serial;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * A select expression.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class SelectExpression implements Select {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private final ExpressionContext ctx;
  private final Field field;
  private Table table;
  private Condition where;
  private Condition having;
  private List<Field> orders;
  private List<Field> groups;
  private String alias;

  /**
   * Constructor.
   *
   * @param ctx the context
   * @param field the field
   */
  private SelectExpression(ExpressionContext ctx, Field field) {
    this.ctx = ctx;
    this.field = field;
  }

  /**
   * Create a new {@link SelectExpression}.
   *
   * @param ctx the context
   * @param field the field
   * @return a new {@link SelectExpression}
   */
  public static SelectExpression of(ExpressionContext ctx, Field field) {
    return new SelectExpression(ctx, field);
  }

  // -----------------------------------------------------------------------
  @Override
  @Nonnull
  public Field getField() {
    return this.field;
  }

  @Override
  public Table getTable() {
    return this.table;
  }

  @Override
  public String getAlias() {
    return this.alias;
  }

  @Override
  @Nonnull
  public Select as(String alias) {
    ensureNull(this.alias);
    checkNotWhiteSpace(alias, "alias cannot be null");

    this.alias = alias;
    return this;
  }

  @Override
  @Nonnull
  public Select from(Table t) {
    ensureNull(this.table);
    checkArgument(t != null, "table cannot be null");

    this.table = t;
    return this;
  }

  @Override
  @Nonnull
  public Select where(Condition c) {
    checkArgument(c != null, "condition cannot be null");

    if (this.where == null) {
      this.where = c;
    } else {
      this.where = this.where.and(c);
    }
    return this;
  }

  @Override
  @Nonnull
  public Select having(Condition c) {
    checkArgument(c != null, "condition cannot be null");

    if (this.having == null) {
      this.having = c;
    } else {
      this.having = this.having.and(c);
    }
    return this;
  }

  @Override
  @Nonnull
  public Select orderBy(Field... f) {
    List<Field> fields = collectionNullClean(Arrays.asList(f), "order fields cannot be null");

    if (this.orders == null) {
      this.orders = fields;
    } else {
      this.orders.addAll(fields);
    }
    return this;
  }

  @Override
  @Nonnull
  public Select groupBy(Field... f) {
    List<Field> fields = collectionNullClean(Arrays.asList(f), "group fields cannot be null");

    if (this.groups == null) {
      this.groups = fields;
    } else {
      this.groups.addAll(fields);
    }
    return this;
  }

  @Override
  public Select distinct(boolean distinct) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Select offset(int limit, int skip) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Select union(Select s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Select intersect(Select s) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public String render() {
    return this.accept(this.ctx.createSqlBuilder()).toString();
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitSelect(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.sameAsExpression(this);
  }
}
