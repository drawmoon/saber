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
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;
import javax.annotation.Nonnull;

/**
 * The common base type for all operate objects.
 *
 * <p>This interface represents an expression that can be enumerated, serialized, cloned, and
 * visited. It provides methods to replace expressions, accept visitors, serialize, deserialize,
 * collect elements, convert to a list, and perform deep copies of the expression.
 *
 * <p>Key features of this interface include:
 *
 * <ul>
 *   <li>Defining common behaviors for expressions
 *   <li>Supporting traversal and transformation of expression trees
 *   <li>Providing serialization and deserialization capabilities
 *   <li>Enabling deep copying to ensure independent instances of expressions
 * </ul>
 *
 * @param <Expression> The type of the expression
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface Expression extends Enumerable<Expression>, Serializable, Cloneable {

  /**
   * Replaces the current expression with another expression using the provided replacement
   * function.
   *
   * <p>This method applies the replacement logic through the visitor pattern, allowing structured
   * modifications to the expression tree.
   *
   * @param replaceFn The function used to generate a new expression instance
   * @return A new expression instance, not null
   */
  @Nonnull
  default Expression replace(Function<Expression, Expression> replaceFn) {
    return accept(new ReplacingVisitor(replaceFn));
  }

  /**
   * Accepts a visitor and returns a result of type T.
   *
   * <p>This method implements the visitor pattern, allowing external logic (such as interpreters or
   * transformers) to traverse the expression tree and perform specific operations.
   *
   * @param <T> The type of the result
   * @param visitor The visitor object to accept
   * @return The result of accepting the visitor
   */
  @Nonnull
  <T> T accept(ExpressionVisitor<T> visitor);

  /**
   * Serializes the expression into a string representation.
   *
   * @return The serialized string
   * @throws UnsupportedOperationException If serialization is not supported by the implementing
   *     class
   */
  @Nonnull
  default String serialize() {
    throw new UnsupportedOperationException();
  }

  /**
   * Deserializes the expression from a string representation.
   *
   * @param serialStr The serialized string
   * @return The deserialized expression
   * @throws UnsupportedOperationException If deserialization is not supported by the implementing
   *     class
   */
  @Nonnull
  default Expression deserialize(String serialStr) {
    throw new UnsupportedOperationException();
  }

  /**
   * Collects elements from the expression using the provided function.
   *
   * @param <R> The type of elements to collect
   * @param function The function to apply to each element
   * @return An enumerable collection of the collected elements
   */
  @Override
  @Nonnull
  default <R> Enumerable<R> collect(Function<? super Expression, ? extends R> function) {
    return Sequence.it(this.iterator()).collect(function);
  }

  /**
   * Converts the expression to a list.
   *
   * @return A list of expressions
   */
  @Override
  @Nonnull
  default ArrayList<Expression> toList() {
    return Sequence.it(this.iterator()).toList();
  }

  /**
   * Creates a deep copy of the expression.
   *
   * <p>This method attempts to clone the expression using reflection. If cloning is not supported
   * by the implementing class, it throws an {@code UnsupportedOperationException}.
   *
   * @return A deep copy of the expression
   * @throws UnsupportedOperationException If cloning is not supported
   */
  default Object deepCopy() {
    try {
      Method clone = getClass().getMethod("clone");
      return clone.invoke(this);
    } catch (Exception e) {
      throw new UnsupportedOperationException(e);
    }
  }
}
