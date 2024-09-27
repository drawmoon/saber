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

import io.github.drawmoon.saber.common.Enumerable;
import io.github.drawmoon.saber.common.Sequence;
import java.util.ArrayList;
import java.util.function.Function;
import javax.annotation.Nonnull;

/** The common base type for all operate objects. */
public interface Expression extends Enumerable<Expression>, Cloneable {

  /**
   * Accepts a visitor and returns a result of type T.
   *
   * @param <T> the type of the result
   * @param visitor the visitor to accept
   * @return the result of accepting the visitor
   */
  @Nonnull
  <T> T accept(ExpressionVisitor<T> visitor);

  @Override
  @Nonnull
  default ArrayList<Expression> toList() {
    return Sequence.it(this.iterator()).toList();
  }

  @Override
  @Nonnull
  default <R> Enumerable<R> collect(Function<? super Expression, ? extends R> function) {
    return Sequence.it(this.iterator()).collect(function);
  }
}
