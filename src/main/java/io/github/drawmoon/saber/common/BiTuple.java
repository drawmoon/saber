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

import java.util.Objects;

/**
 * Represents a 2-tuple, or pair, which is an immutable data structure holding two components. This
 * class is useful for returning multiple values from a method or grouping related data together.
 *
 * @param <T1> the type of the first component in the tuple
 * @param <T2> the type of the second component in the tuple
 * @author drash
 * @version 1.0
 * @since 2024
 */
public final class BiTuple<T1, T2> {

  private final T1 item1;
  private final T2 item2;

  /**
   * Constructs a new {@code BiTuple} with the specified components.
   *
   * @param item1 the value of the current tuple object's first component
   * @param item2 the value of the current tuple object's second component
   * @throws NullPointerException if either {@code item1} or {@code item2} is null (depending on use
   *     case)
   */
  public BiTuple(T1 item1, T2 item2) {
    this.item1 = item1;
    this.item2 = item2;
  }

  /**
   * Gets the value of the current tuple object's first component.
   *
   * @return the value of the first component
   */
  public T1 item1() {
    return item1;
  }

  /**
   * Gets the value of the current tuple object's second component.
   *
   * @return the value of the second component
   */
  public T2 item2() {
    return item2;
  }

  /**
   * Returns a string representation of this tuple in the form "BiTuple[item1=item1, item2=item2]".
   *
   * @return a string representation of this tuple
   */
  @Override
  public String toString() {
    return "BiTuple[item1=" + item1 + ", item2=" + item2 + "]";
  }

  /**
   * Indicates whether some other object is "equal to" this tuple. Two tuples are considered equal
   * if their corresponding components are equal.
   *
   * @param o the reference object with which to compare
   * @return {@code true} if this tuple is the same as the argument; {@code false} otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BiTuple<?, ?> biTuple = (BiTuple<?, ?>) o;
    return item1.equals(biTuple.item1) && item2.equals(biTuple.item2);
  }

  /**
   * Returns a hash code value for the tuple. The hash code is computed using the hash codes of the
   * tuple's components.
   *
   * @return a hash code value for this tuple
   */
  @Override
  public int hashCode() {
    return Objects.hash(item1, item2);
  }
}
