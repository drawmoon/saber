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

/**
 * Represents a field in a database table or query.
 *
 * <p>A field can be part of a table, and it supports various operations such as comparisons and
 * aggregations. It also allows setting an alias for the field to use in queries.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface Field extends Expression {

  /**
   * Returns the alias of the field.
   *
   * @return the alias of the field, or an empty string if no alias is set
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
   * Gets the table that this field belongs to.
   *
   * @return the table object, or {@code null} if the table is not specified
   */
  @Nullable default Table getTable() {
    return null;
  }

  /**
   * Sets an alias for the field.
   *
   * @param alias the alias to set, must not be null
   * @return the current field instance with the new alias applied
   */
  @Nonnull
  Field as(String alias);

  // -----------------------------------------------------------------------
  /**
   * Creates an equality condition comparing this field to a given value.
   *
   * @param value the value to compare against
   * @param <T> the type of the value
   * @return a new condition representing the equality comparison
   */
  @Nonnull
  default <T> Condition eq(T value) {
    return eq(Expressions.val(value));
  }

  /**
   * Creates an equality condition comparing this field to another field.
   *
   * @param field the field to compare against
   * @return a new condition representing the equality comparison
   */
  @Nonnull
  default Condition eq(Field field) {
    return new ComparisonExpression(this, field, Comparator.EQ);
  }

  /**
   * Creates a not-equal condition comparing this field to a given value.
   *
   * @param value the value to compare against
   * @param <T> the type of the value
   * @return a new condition representing the not-equal comparison
   */
  @Nonnull
  default <T> Condition ne(T value) {
    return ne(Expressions.val(value));
  }

  /**
   * Creates a not-equal condition comparing this field to another field.
   *
   * @param field the field to compare against
   * @return a new condition representing the not-equal comparison
   */
  @Nonnull
  default Condition ne(Field field) {
    return new ComparisonExpression(this, field, Comparator.NE);
  }

  /**
   * Creates a less-than condition comparing this field to a given value.
   *
   * @param value the value to compare against
   * @param <T> the type of the value
   * @return a new condition representing the less-than comparison
   */
  @Nonnull
  default <T> Condition lt(T value) {
    return lt(Expressions.val(value));
  }

  /**
   * Creates a less-than condition comparing this field to another field.
   *
   * @param field the field to compare against
   * @return a new condition representing the less-than comparison
   */
  @Nonnull
  default Condition lt(Field field) {
    return new ComparisonExpression(this, field, Comparator.LT);
  }

  /**
   * Creates a greater-than condition comparing this field to a given value.
   *
   * @param value the value to compare against
   * @param <T> the type of the value
   * @return a new condition representing the greater-than comparison
   */
  @Nonnull
  default <T> Condition gt(T value) {
    return gt(Expressions.val(value));
  }

  /**
   * Creates a greater-than condition comparing this field to another field.
   *
   * @param field the field to compare against
   * @return a new condition representing the greater-than comparison
   */
  @Nonnull
  default Condition gt(Field field) {
    return new ComparisonExpression(this, field, Comparator.GT);
  }

  /**
   * Creates a less-than-or-equal condition comparing this field to a given value.
   *
   * @param value the value to compare against
   * @param <T> the type of the value
   * @return a new condition representing the less-than-or-equal comparison
   */
  @Nonnull
  default <T> Condition le(T value) {
    return le(Expressions.val(value));
  }

  /**
   * Creates a less-than-or-equal condition comparing this field to another field.
   *
   * @param field the field to compare against
   * @return a new condition representing the less-than-or-equal comparison
   */
  @Nonnull
  default Condition le(Field field) {
    return new ComparisonExpression(this, field, Comparator.LE);
  }

  /**
   * Creates a greater-than-or-equal condition comparing this field to a given value.
   *
   * @param value the value to compare against
   * @param <T> the type of the value
   * @return a new condition representing the greater-than-or-equal comparison
   */
  @Nonnull
  default <T> Condition ge(T value) {
    return ge(Expressions.val(value));
  }

  /**
   * Creates a greater-than-or-equal condition comparing this field to another field.
   *
   * @param field the field to compare against
   * @return a new condition representing the greater-than-or-equal comparison
   */
  @Nonnull
  default Condition ge(Field field) {
    return new ComparisonExpression(this, field, Comparator.GE);
  }

  // -----------------------------------------------------------------------
  /**
   * Creates a count aggregation expression for this field.
   *
   * @return a new aggregate expression for counting the occurrences of this field
   */
  @Nonnull
  default AggregateExpression count() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.COUNT);
  }

  /**
   * Creates a max aggregation expression for this field.
   *
   * @return a new aggregate expression for finding the maximum value of this field
   */
  @Nonnull
  default AggregateExpression max() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.MAX);
  }

  /**
   * Creates a min aggregation expression for this field.
   *
   * @return a new aggregate expression for finding the minimum value of this field
   */
  @Nonnull
  default AggregateExpression min() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.MIN);
  }

  /**
   * Creates a sum aggregation expression for this field.
   *
   * @return a new aggregate expression for calculating the sum of this field
   */
  @Nonnull
  default AggregateExpression sum() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.SUM);
  }

  /**
   * Creates an average aggregation expression for this field.
   *
   * @return a new aggregate expression for calculating the average value of this field
   */
  @Nonnull
  default AggregateExpression avg() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.AVG);
  }

  /**
   * Creates a round aggregation expression for this field.
   *
   * @return a new aggregate expression for rounding the values of this field
   */
  @Nonnull
  default AggregateExpression round() {
    return new AggregateExpression(Lists.newArrayList(this), Aggregate.ROUND);
  }

  /**
   * Creates a round aggregation expression for this field.
   *
   * @param decimalPlaces the number of decimal places to round to
   * @return a new aggregate expression for rounding the values of this field
   */
  @Nonnull
  default AggregateExpression round(int decimalPlaces) {
    throw new UnsupportedOperationException("Round is not supported for this field");
  }

  /**
   * Creates a median aggregation expression for this field.
   *
   * @return a new aggregate expression for calculating the median value of this field
   */
  @Nonnull
  default AggregateExpression median() {
    throw new UnsupportedOperationException("Median is not supported for this field");
  }
}
