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
 * operators like {@link EnumSet}, except that {@link Flag} is represented as a value rather than a
 * set
 */
public interface Flag {

  /**
   * Gets a value that represents the flag.
   *
   * @return return the value of the flag
   */
  int value();

  /** Static utility methods pertaining to {@link Flag} instances. */
  public static final class Flags {

    private Flags() {}

    /**
     * Checks if the value contains a flag enum.
     *
     * @param <E> the type of the flag
     * @param a the value
     * @param b the flag
     * @return true if the value contains the flag
     */
    public static <E extends Enum<E> & Flag> boolean hasFlag(int a, @CheckForNull E b) {
      checkNotNull(b);

      return (a & b.value()) == b.value();
    }

    /**
     * Checks if the flag enum contains the specified value.
     *
     * @param <E> the type of the flag
     * @param a the flag
     * @param b the value
     * @return true if the flag contains the value
     */
    public static <E extends Enum<E> & Flag> boolean hasValue(@CheckForNull E a, int b) {
      checkNotNull(a);

      return (a.value() | b) == b;
    }

    /**
     * Gets the value of the flags.
     *
     * @param <E> the type of the flag
     * @param flags the flags, not null
     * @return the value of the flags
     */
    public static <E extends Enum<E> & Flag> int value(List<E> flags) {
      int value = 0;
      for (E flag : flags) {
        value |= flag.value();
      }

      return value;
    }

    /**
     * Gets the flags.
     *
     * @param <E> the type of the flag
     * @param value the value, not null
     * @param c the flag enum class, not null
     * @return the flags, not null
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
          throw new IndexOutOfBoundsException();
        }
        int m = o.get();
        flags.add(0, map.get(m));
        n -= m;
      }

      return flags;
    }

    /**
     * Gets the maps.
     *
     * @param <E> the type of the flag
     * @param c the flag enum class, not null
     * @return the maps, not null
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
