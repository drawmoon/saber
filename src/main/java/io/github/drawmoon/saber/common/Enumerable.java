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
package io.github.drawmoon.saber.common;

import java.util.ArrayList;
import java.util.function.Function;
import javax.annotation.Nonnull;

/**
 * An interface for inherently recursive, multi-valued data structures. The order of elements is
 * determined by {@link Iterable#iterator()}, which may vary each time it is called.
 */
public interface Enumerable<T> extends Iterable<T> {

  /**
   * Collects all elements that are in the domain of the given <code>action</code> by mapping the
   * elements to type <code>R</code>.
   *
   * @param <R> the type of the result
   * @param function a function that is not necessarily defined of all elements of this traversable
   * @return a new <code>Traversable</code> instance containing elements of type <code>R</code>
   */
  @Nonnull
  <R> Enumerable<R> collect(Function<? super T, ? extends R> function);

  /**
   * Returns a list containing all elements of this collection.
   *
   * @return the new list
   */
  @Nonnull
  ArrayList<T> toList();
}
