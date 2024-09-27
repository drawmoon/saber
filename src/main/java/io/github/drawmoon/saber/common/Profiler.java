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
 * A simple profiler class to measure elapsed time in milliseconds and nanoseconds. This class
 * provides methods to get the elapsed time since the creation of the profiler instance.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class Profiler {

  /** The start time in milliseconds. */
  private long millis;

  /** The start time in nanoseconds. */
  private long nanos;

  /** Constructs a new Profiler instance and initializes the start time. */
  public Profiler() {
    this.millis = System.currentTimeMillis();
    this.nanos = System.nanoTime();
  }

  /**
   * Gets the elapsed time in milliseconds since the creation of this profiler instance.
   *
   * @return The elapsed time in milliseconds.
   */
  public long getMillis() {
    return System.currentTimeMillis() - this.millis;
  }

  /**
   * Gets the elapsed time in nanoseconds since the creation of this profiler instance.
   *
   * @return The elapsed time in nanoseconds.
   */
  public long getNanos() {
    return System.nanoTime() - this.nanos;
  }
}
