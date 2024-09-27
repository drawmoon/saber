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

/**
 * Represents a 1-tuple, or singleton, which holds a single element. This class is immutable and
 * provides methods to access the contained item.
 *
 * @param <T> the type of the single element held by this tuple
 * @author drash
 * @version 1.0
 * @since 2024
 */
public final class Tuple<T> {

  /** The single element held by this tuple. */
  private final T item;

  /**
   * Constructs a new Tuple with the specified item.
   *
   * @param item the value of the current tuple object's single component
   * @throws NullPointerException if the item is null
   */
  public Tuple(T item) {
    if (item == null) {
      throw new NullPointerException("Item cannot be null");
    }
    this.item = item;
  }

  /**
   * Gets the value of the tuple object's single component.
   *
   * @return the value of the tuple
   */
  public T item() {
    return item;
  }

  /**
   * Checks if the specified object is equal to this tuple.
   *
   * @param o the object to compare with this tuple
   * @return true if the specified object is equal to this tuple, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tuple<?> tuple = (Tuple<?>) o;
    return item.equals(tuple.item);
  }

  /**
   * Returns the hash code value for this tuple.
   *
   * @return the hash code value for this tuple
   */
  @Override
  public int hashCode() {
    return item.hashCode();
  }

  /**
   * Returns a string representation of this tuple.
   *
   * @return a string representation of this tuple
   */
  @Override
  public String toString() {
    return "Tuple[item=" + item + "]";
  }
}
