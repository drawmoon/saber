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

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * Represents an enumeration containing a value.
 *
 * <p>Implementations of the {@link Flag} interface indicate that they can be combined with bitwise
 * operators like {@link EnumSet}, except that {@link Flag} is represented as a single integer value
 * rather than a set. This allows for efficient flag combination and checking using bitwise
 * operations.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface Flag {

  /**
   * Gets the integer value that represents this flag.
   *
   * @return the integer value of the flag
   */
  int value();

  /** Utility methods for working with {@link Flag} instances. */
  public static final class Flags {

    private Flags() {}

    /**
     * Checks if the specified integer value contains the given flag.
     *
     * @param <E> the type of the flag enum
     * @param a the integer value to check
     * @param b the flag to check for
     * @return {@code true} if the integer value contains the flag, otherwise {@code false}
     * @throws NullPointerException if the flag is null
     */
    public static <E extends Enum<E> & Flag> boolean hasFlag(int a, @CheckForNull E b) {
      checkNotNull(b);

      return (a & b.value()) == b.value();
    }

    /**
     * Checks if the specified flag contains the given integer value.
     *
     * @param <E> the type of the flag enum
     * @param a the flag to check
     * @param b the integer value to check for
     * @return {@code true} if the flag contains the integer value, otherwise {@code false}
     * @throws NullPointerException if the flag is null
     */
    public static <E extends Enum<E> & Flag> boolean hasValue(@CheckForNull E a, int b) {
      checkNotNull(a);

      return (a.value() | b) == b;
    }

    /**
     * Combines multiple flags into a single integer value using bitwise OR operations.
     *
     * @param <E> the type of the flag enum
     * @param flags the list of flags to combine
     * @return the combined integer value of the flags
     * @throws NullPointerException if the list of flags is null
     */
    public static <E extends Enum<E> & Flag> int value(List<E> flags) {
      int value = 0;
      for (E flag : flags) {
        value |= flag.value();
      }

      return value;
    }

    /**
     * Converts an integer value into a list of flags based on the provided enum class.
     *
     * @param <E> the type of the flag enum
     * @param value the integer value to convert
     * @param c the class object of the flag enum
     * @return a list of flags corresponding to the integer value
     * @throws NullPointerException if the enum class is null
     * @throws IndexOutOfBoundsException if the integer value cannot be represented by any
     *     combination of flags
     */
    @Nonnull
    public static <E extends Enum<E> & Flag> List<E> flags(int value, @CheckForNull Class<E> c) {
      checkNotNull(c);

      LinkedHashMap<Integer, E> map = maps(c);

      List<E> flags = new ArrayList<>();
      if (map.containsKey(value)) {
        flags.add(map.get(value));
        return flags;
      }

      int n = value;
      while (n > 0) {
        final int t = n;
        Optional<Integer> o = map.keySet().stream().filter(x -> x <= t).max(Integer::compare);
        if (!o.isPresent()) {
          throw new IndexOutOfBoundsException(
              "Value " + value + " cannot be represented by the provided flags.");
        }
        int m = o.get();
        flags.add(0, map.get(m));
        n -= m;
      }

      return flags;
    }

    /**
     * Creates a mapping from flag values to their corresponding enum constants.
     *
     * @param <E> the type of the flag enum
     * @param c the class object of the flag enum
     * @return a linked hash map where keys are flag values and values are enum constants
     * @throws NullPointerException if the enum class is null
     */
    @Nonnull
    public static <E extends Enum<E> & Flag> LinkedHashMap<Integer, E> maps(
        @CheckForNull Class<E> c) {
      checkNotNull(c);

      LinkedHashMap<Integer, E> map = new LinkedHashMap<>();
      for (E e : c.getEnumConstants()) {
        map.put(e.value(), e);
      }

      return map;
    }
  }
}
