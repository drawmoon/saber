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

/** A variable expression. */
public class VariableExpression<V> implements Field {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private final V val;
  private final DataType<V> type;
  private String alias;

  /**
   * Create a new variable expression.
   *
   * @param val the value, not null
   * @param type the type, not null
   */
  public VariableExpression(V val, DataType<V> type) {
    this.val = checkNotNull(val);
    this.type = checkNotNull(type);
  }

  public V getValue() {
    return this.val;
  }

  public DataType<V> getType() {
    return this.type;
  }

  @Override
  public String getAlias() {
    return this.alias;
  }

  @Override
  @Nonnull
  public Field as(String alias) {
    this.alias = alias;
    return this;
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitVariable(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.empty();
  }
}
