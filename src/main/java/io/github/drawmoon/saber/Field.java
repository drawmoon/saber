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

import com.google.common.collect.Lists;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** A field. */
public interface Field extends Expression {

  /**
   * Returns the alias of the field.
   *
   * @return the alias of the field
   */
  String getAlias();

  /**
   * Returns the name of the field.
   *
   * @return the name of the field
   */
  default String getName() {
    return "";
  }

  /**
   * Get the table that this field belongs to.
   *
   * @return the table
   */
  @Nullable default Table getTable() {
    return null;
  }

  /**
   * Sets the alias for the field.
   *
   * @param alias the alias, not null
   * @return returns the field, not null
   */
  @Nonnull
  Field as(String alias);

  // -----------------------------------------------------------------------
  @Nonnull
  default <T> Condition eq(T value) {
    return eq(Expressions.val(value));
  }

  @Nonnull
  default Condition eq(Field field) {
    return new ComparisonExpression(this, field, Comparator.EQ);
  }

  @Nonnull
  default <T> Condition ne(T value) {
    return ne(Expressions.val(value));
  }

  @Nonnull
  default Condition ne(Field field) {
    return new ComparisonExpression(this, field, Comparator.NE);
  }

  @Nonnull
  default <T> Condition lt(T value) {
    return lt(Expressions.val(value));
  }

  @Nonnull
  default Condition lt(Field field) {
    return new ComparisonExpression(this, field, Comparator.LT);
  }

  @Nonnull
  default <T> Condition gt(T value) {
    return gt(Expressions.val(value));
  }

  @Nonnull
  default Condition gt(Field field) {
    return new ComparisonExpression(this, field, Comparator.GT);
  }

  @Nonnull
  default <T> Condition le(T value) {
    return le(Expressions.val(value));
  }

  @Nonnull
  default Condition le(Field field) {
    return new ComparisonExpression(this, field, Comparator.LE);
  }

  @Nonnull
  default <T> Condition ge(T value) {
    return ge(Expressions.val(value));
  }

  @Nonnull
  default Condition ge(Field field) {
    return new ComparisonExpression(this, field, Comparator.GE);
  }

  // -----------------------------------------------------------------------
  @Nonnull
  default AggregateExpression count() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.COUNT);
  }

  @Nonnull
  default AggregateExpression max() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.MAX);
  }

  @Nonnull
  default AggregateExpression min() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.MIN);
  }

  @Nonnull
  default AggregateExpression sum() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.SUM);
  }

  @Nonnull
  default AggregateExpression avg() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.AVG);
  }
}
