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

import io.github.drawmoon.saber.engine.ExpressionIterator;
import java.io.Serial;
import java.util.Iterator;
import javax.annotation.Nonnull;

/**
 * A logical expression.
 *
 * <p>Combine this condition with another condition using the specified operator.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class LogicalExpression implements Condition {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private final Condition lhs;
  private final Condition rhs;
  private final Operator operator;

  /**
   * Create a new logical expression.
   *
   * @param lhs the lhs expression, not null
   * @param rhs the rhs expression, not null
   * @param operator the operator, not null
   */
  public LogicalExpression(Condition lhs, Condition rhs, Operator operator) {
    this.lhs = checkNotNull(lhs);
    this.rhs = checkNotNull(rhs);
    this.operator = checkNotNull(operator);
  }

  // -----------------------------------------------------------------------
  @Override
  @Nonnull
  public Condition and(Expression expr) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Condition or(Expression expr) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitLogical(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.sameAsExpression(this);
  }
}
