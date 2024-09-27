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
import static io.github.drawmoon.saber.common.Preconditions.collectionNullClean;

import io.github.drawmoon.saber.engine.DataType;
import io.github.drawmoon.saber.engine.ExpressionContext;
import io.github.drawmoon.saber.engine.SaberOptions;
import java.util.Arrays;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public final class Expressions implements SqlGenerator {

  private final ExpressionContext ctx;

  /**
   * Constructor.
   *
   * @param ctx the context, not null
   */
  private Expressions(ExpressionContext ctx) {
    this.ctx = checkNotNull(ctx);
  }

  /**
   * Create a new {@link Expressions}.
   *
   * @param catalog the catalog, not null
   * @return the new {@link Expressions}
   */
  public static Expressions create(Catalog<? extends Table, ? extends Field> catalog) {
    return create(catalog, new SaberOptions());
  }

  /**
   * Create a new {@link Expressions}.
   *
   * @param catalog the catalog, not null
   * @param options the options, not null
   * @return the new {@link Expressions}
   */
  public static Expressions create(
      Catalog<? extends Table, ? extends Field> catalog, SaberOptions options) {
    ExpressionContext ctx = new ExpressionContext(catalog, options);
    return new Expressions(ctx);
  }

  // -----------------------------------------------------------------------
  /**
   * Gets a keyword.
   *
   * @param keyword The keyword string, not null
   * @return the {@link Keyword} object, not null
   */
  @Nonnull
  public static Keyword keyword(@CheckForNull String keyword) {
    return Keyword.of(keyword);
  }

  /**
   * Gets a variable.
   *
   * @param i the value
   * @return the new expression
   */
  @Nonnull
  public static VariableExpression<Integer> intVal(Integer i) {
    return val(i);
  }

  /**
   * Gets a variable.
   *
   * @param s the value
   * @return the new expression
   */
  @Nonnull
  public static VariableExpression<String> strVal(String s) {
    return val(s);
  }

  /**
   * Gets a variable.
   *
   * @param b the value
   * @return the new expression
   */
  @Nonnull
  public static VariableExpression<Boolean> boolVal(Boolean b) {
    return val(b);
  }

  /**
   * Gets a variable.
   *
   * @param <T> the type of the value.
   * @param val the value.
   * @return the new VariableExpression.
   */
  @SuppressWarnings("unchecked")
  public static <T> VariableExpression<T> val(T val) {
    if (val instanceof Integer)
      return new VariableExpression<>(val, (DataType<T>) DataType.INTEGER);
    throw new UnsupportedOperationException();
  }

  /**
   * Gets a field.
   *
   * @param field the meta field
   * @return the new field
   */
  public static Field field(MetaField field) {
    checkNotNull(field);
    return new TableFieldExpression(field.getName(), field.getTable());
  }

  /**
   * Gets a table.
   *
   * @param table the meta table
   * @return the new table
   */
  public static Table table(MetaTable table) {
    checkNotNull(table);
    return new TableExpression(table.getName(), null, null);
  }

  // -----------------------------------------------------------------------
  /**
   * Create a new select statement.
   *
   * @param f the field, not null
   * @return the new select
   */
  @Nonnull
  public Select select(@CheckForNull Object... f) {
    checkNotNull(f);
    collectionNullClean(Arrays.asList(f));

    MemberExpression member = new MemberExpression();
    for (Object e : f) {
      Field field;
      if (e instanceof Field) field = (Field) e;
      else field = val(e);
      member.append(field);
    }

    return SelectExpression.of(this.ctx, member);
  }

  @Override
  @Nonnull
  public String render() {
    return this.ctx.render();
  }
}
