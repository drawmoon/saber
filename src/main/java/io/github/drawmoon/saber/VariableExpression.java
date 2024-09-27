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

import io.github.drawmoon.saber.engine.DataType;
import io.github.drawmoon.saber.engine.ExpressionIterator;
import java.io.Serial;
import java.util.Iterator;
import javax.annotation.Nonnull;

/**
 * Represents a variable expression that holds a value of a specific type.
 *
 * <p>A {@code VariableExpression} encapsulates a value and its associated data type. It can be used
 * within expressions and queries to represent variables or constants.
 *
 * @param <V> The type of the value held by this expression.
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class VariableExpression<V> implements Field {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  /** The value held by this variable expression. */
  private final V val;

  /** The data type of the value. */
  private final DataType<V> type;

  /** An optional alias for this variable expression. */
  private String alias;

  /**
   * Constructs a new {@code VariableExpression} with the specified value and data type.
   *
   * @param val The value of the expression, must not be null.
   * @param type The data type of the value, must not be null.
   * @throws NullPointerException if either {@code val} or {@code type} is null.
   */
  public VariableExpression(V val, DataType<V> type) {
    this.val = checkNotNull(val);
    this.type = checkNotNull(type);
  }

  /**
   * Retrieves the value held by this variable expression.
   *
   * @return The value of the expression.
   */
  public V getValue() {
    return this.val;
  }

  /**
   * Retrieves the data type of the value held by this variable expression.
   *
   * @return The data type of the value.
   */
  public DataType<V> getType() {
    return this.type;
  }

  /**
   * Retrieves the alias assigned to this variable expression.
   *
   * @return The alias, or null if no alias has been set.
   */
  @Override
  public String getAlias() {
    return this.alias;
  }

  /**
   * Assigns an alias to this variable expression.
   *
   * @param alias The alias to assign, must not be null.
   * @return This variable expression instance.
   * @throws NullPointerException if {@code alias} is null.
   */
  @Override
  @Nonnull
  public Field as(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Accepts a visitor to visit this variable expression.
   *
   * @param visitor The visitor to accept, must not be null.
   * @return The result of visiting this expression.
   * @throws NullPointerException if {@code visitor} is null.
   */
  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitVariable(this);
  }

  /**
   * Returns an empty iterator since a variable expression does not contain sub-expressions.
   *
   * @return An empty iterator.
   */
  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.empty();
  }
}
