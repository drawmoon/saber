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

import io.github.drawmoon.saber.engine.ExpressionIterator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;

/** A condition that is an aggregate expression. */
public class AggregateExpression implements Field {

  private final List<Expression> arguments;
  private final Aggregate operator;
  private boolean distinct;
  private String alias;

  public AggregateExpression(List<Expression> arguments, Aggregate operator) {
    this.arguments = collectionNullClean(arguments);
    this.operator = checkNotNull(operator);
  }

  // -----------------------------------------------------------------------
  public int argumentCount() {
    return arguments.size();
  }

  public List<Expression> getArguments() {
    return arguments;
  }

  public Expression getArgument(int index) {
    return arguments.get(index);
  }

  public boolean isDistinct() {
    return distinct;
  }

  public AggregateExpression distinct() {
    this.distinct = true;
    return this;
  }

  @Override
  public String getAlias() {
    return alias;
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
    return visitor.visitAggregate(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.sameAsExpression(this.arguments, this.operator);
  }
}
