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

/**
 * A utility class for creating various types of expressions used in SQL-like queries.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public final class Expressions implements ExpressionRender<String> {

  private final ExpressionContext ctx;

  /**
   * Private constructor to prevent instantiation outside the class.
   *
   * @param ctx The expression context, must not be null
   */
  private Expressions(ExpressionContext ctx) {
    this.ctx = checkNotNull(ctx);
  }

  /**
   * Creates a new {@link Expressions} instance with default options.
   *
   * @param catalog The catalog containing tables and fields, must not be null
   * @return A new {@link Expressions} instance
   */
  public static Expressions create(Catalog<? extends Table, ? extends Field> catalog) {
    return create(catalog, new SaberOptions());
  }

  /**
   * Creates a new {@link Expressions} instance with custom options.
   *
   * @param catalog The catalog containing tables and fields, must not be null
   * @param options Custom options for the expression context, must not be null
   * @return A new {@link Expressions} instance
   */
  public static Expressions create(
      Catalog<? extends Table, ? extends Field> catalog, SaberOptions options) {
    ExpressionContext ctx = new ExpressionContext(catalog, options);
    return new Expressions(ctx);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a keyword expression.
   *
   * @param keyword The keyword string, can be null
   * @return A new {@link Keyword} object, never null
   */
  @Nonnull
  public static Keyword keyword(@CheckForNull String keyword) {
    return Keyword.of(keyword);
  }

  /**
   * Returns an integer variable expression.
   *
   * @param i The integer value
   * @return A new integer variable expression, never null
   */
  @Nonnull
  public static VariableExpression<Integer> intVal(Integer i) {
    return val(i);
  }

  /**
   * Returns a string variable expression.
   *
   * @param s The string value
   * @return A new string variable expression, never null
   */
  @Nonnull
  public static VariableExpression<String> strVal(String s) {
    return val(s);
  }

  /**
   * Returns a boolean variable expression.
   *
   * @param b The boolean value
   * @return A new boolean variable expression, never null
   */
  @Nonnull
  public static VariableExpression<Boolean> boolVal(Boolean b) {
    return val(b);
  }

  /**
   * Returns a generic variable expression.
   *
   * @param <T> The type of the value
   * @param val The value to wrap in a variable expression
   * @return A new variable expression, never null
   * @throws UnsupportedOperationException If the value type is not supported
   */
  @SuppressWarnings("unchecked")
  public static <T> VariableExpression<T> val(T val) {
    if (val instanceof Integer)
      return new VariableExpression<>(val, (DataType<T>) DataType.INTEGER);
    throw new UnsupportedOperationException();
  }

  /**
   * Returns a field expression.
   *
   * @param field The meta field, must not be null
   * @return A new field expression, never null
   */
  public static Field field(MetaField field) {
    checkNotNull(field);
    return new TableFieldExpression(field.getName(), field.getTable());
  }

  /**
   * Returns a table expression.
   *
   * @param table The meta table, must not be null
   * @return A new table expression, never null
   */
  public static Table table(MetaTable table) {
    checkNotNull(table);
    return new TableExpression(table.getName(), null, null);
  }

  // -----------------------------------------------------------------------
  /**
   * Creates a new select statement.
   *
   * @param f The fields or values to select, can be null
   * @return A new select statement, never null
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
