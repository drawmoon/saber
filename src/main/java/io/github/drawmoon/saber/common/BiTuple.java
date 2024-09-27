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

/** Represents an 2-tuple, or pair. */
public final class BiTuple<T1, T2> {

  private final T1 item1;
  private final T2 item2;

  /**
   * Constructs.
   *
   * @param item1 the value of the current tuple object's first component
   * @param item2 the value of the current tuple object's second component
   */
  public BiTuple(T1 item1, T2 item2) {
    this.item1 = item1;
    this.item2 = item2;
  }

  /**
   * Gets the value of the current tuple object's first component.
   *
   * @return the value of the tuple
   */
  public T1 item1() {
    return item1;
  }

  /**
   * Gets the value of the current tuple object's second component.
   *
   * @return the value of the tuple
   */
  public T2 item2() {
    return item2;
  }
}
