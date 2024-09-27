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

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public final class Preconditions {

  private Preconditions() {}

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param <T> the type of the reference
   * @param t the object reference to check for nullity
   * @author drash
   * @version 1.0
   * @since 2024
   */
  @Nonnull
  public static <T> T checkNotNull(T t) {
    if (t == null) throw new NullPointerException();

    return t;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param <T> the type of the reference
   * @param t the object reference to check for nullity
   * @param errorMessage the exception message to use if the check fails
   */
  @Nonnull
  public static <T> T checkNotNull(T t, String errorMessage) {
    if (t == null) throw new NullPointerException(errorMessage);

    return t;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param <T> the type of the reference
   * @param t1 the object reference to check for nullity
   * @param t2 the object reference to check for nullity
   */
  public static <T> void checkNotNull(T t1, T t2) {
    if (t1 == null || t2 == null) throw new NullPointerException();
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param <T> the type of the reference
   * @param t1 the object reference to check for nullity
   * @param t2 the object reference to check for nullity
   * @param errorMessage the exception message to use if the check fails
   */
  public static <T> void checkNotNull(T t1, T t2, String errorMessage) {
    if (t1 == null || t2 == null) throw new NullPointerException(errorMessage);
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param <T> the type of the reference
   * @param t1 the object reference to check for nullity
   * @param t2 the object reference to check for nullity
   * @param t3 the object reference to check for nullity
   */
  public static <T> void checkNotNull(T t1, T t2, T t3) {
    if (t1 == null || t2 == null) throw new NullPointerException();
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param <T> the type of the reference
   * @param t1 the object reference to check for nullity
   * @param t2 the object reference to check for nullity
   * @param t3 the object reference to check for nullity
   * @param errorMessage the exception message to use if the check fails
   */
  public static <T> void checkNotNull(T t1, T t2, T t3, String errorMessage) {
    if (t1 == null || t2 == null || t3 == null) throw new NullPointerException(errorMessage);
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is null.
   *
   * @param <T> the type of the reference
   * @param obj the object reference to check for nullity
   */
  public static <T> void ensureNull(T obj) {
    if (obj != null) throw new UnsupportedOperationException();
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is null.
   *
   * @param <T> the type of the reference
   * @param obj the object reference to check for nullity
   * @param errorMessage the exception message to use if the check fails
   */
  public static <T> void ensureNull(T obj, String errorMessage) {
    if (obj != null) throw new UnsupportedOperationException(errorMessage);
  }

  /**
   * Ensures that an collection passed as a parameter to the calling method is null or empty.
   *
   * @param <T> the type of the reference
   * @param coll the collection to check for nullity
   */
  public static <T> void ensureCollectionEmpty(Collection<? extends T> coll) {
    if (coll != null && !coll.isEmpty()) throw new UnsupportedOperationException();
  }

  /**
   * Ensures that an collection passed as a parameter to the calling method is null or empty.
   *
   * @param <T> the type of the reference
   * @param coll the collection to check for nullity
   * @param errorMessage the exception message to use if the check fails
   */
  public static <T> void ensureCollectionEmpty(Collection<? extends T> coll, String errorMessage) {
    if (coll != null && !coll.isEmpty()) throw new UnsupportedOperationException(errorMessage);
  }

  /**
   * Ensure that the string is not null and is not an empty string.
   *
   * @param text the string to check
   * @throws NullPointerException if the string is null or empty
   * @return the text
   */
  @CanIgnoreReturnValue
  @Nonnull
  public static String checkNotWhiteSpace(String text) {
    return checkNotWhiteSpace(text, null);
  }

  /**
   * Ensure that the string is not null and is not an empty string.
   *
   * @param text the string to check
   * @param errorMessage the exception message to use if the check fails
   * @throws NullPointerException if the string is null or empty
   * @return the text
   */
  @CanIgnoreReturnValue
  @Nonnull
  public static String checkNotWhiteSpace(String text, String errorMessage) {
    if (text == null) throw new NullPointerException(errorMessage);

    boolean foundNonWhitespace = false;
    for (int i = 0; i < text.length(); i++) {
      try {
        checkNotWhiteSpace(text.charAt(i));
        foundNonWhitespace = true;
        break;
      } catch (NullPointerException expected) {
        // expected, just continue
      }
    }

    if (!foundNonWhitespace) throw new NullPointerException();
    return text;
  }

  /**
   * Ensures that the character is not a whitespace character.
   *
   * @param c the character to check
   * @throws NullPointerException if the character is a whitespace character
   * @return the character
   */
  @CanIgnoreReturnValue
  public static char checkNotWhiteSpace(char c) {
    if (Character.isWhitespace(c)
        || Character.isSpaceChar(c)
        || c == '\ufeff'
        || c == '\u202a'
        || c == '\u0000'
        || c == '\u3164'
        || c == '\u2800'
        || c == '\u180e') throw new NullPointerException();

    return c;
  }

  /**
   * Ensures that the collection is not null and that the collection elements are not null.
   *
   * @param <T> the type of the reference
   * @param coll the collection to check
   */
  public static <T> void checkCollectionNotNull(Collection<? extends T> coll) {
    if (coll == null) throw new NullPointerException();

    for (T e : coll) {
      if (e == null) throw new NullPointerException();
    }
  }

  /**
   * Ensures that the collection is not null and that the collection elements are not null.
   *
   * @param <T> the type of the reference
   * @param coll the collection to check
   * @return the cleaned collection
   */
  @Nonnull
  public static <T> List<T> collectionNullClean(Collection<? extends T> coll) {
    return collectionNullClean(coll, null);
  }

  /**
   * Ensures that the collection is not null and that the collection elements are not null.
   *
   * @param <T> the type of the reference
   * @param coll the collection to check
   * @param errorMessage the exception message to use if the check fails
   * @return the cleaned collection
   */
  @Nonnull
  public static <T> ArrayList<T> collectionNullClean(
      Collection<? extends T> coll, String errorMessage) {
    if (coll == null) throw new NullPointerException(errorMessage);

    ArrayList<T> list = new ArrayList<>();
    int index = 0;
    for (T e : coll) {
      if (e == null) throw new NullPointerException("at " + index + ": " + errorMessage);
      list.add(e);
      index++;
    }

    return list;
  }

  /**
   * Returns the object if it is not null, otherwise returns the default value.
   *
   * @param <T> the type of the reference
   * @param t the object to check
   * @param defaultValue the default value, not null
   * @return the object, or the default value if the object is null
   */
  @Nonnull
  public static <T> T nullSafe(T t, @CheckForNull T defaultValue) {
    if (defaultValue == null) throw new NullPointerException();

    return t == null ? defaultValue : t;
  }

  /**
   * Execute the function and return the result, or null if the function throws an exception.
   *
   * @param <T> the type of the reference
   * @param <R> the type of the reference
   * @param execute a function to be executed and produces a result
   * @return the result of the function, or null if the function throws an exception
   */
  @CheckForNull
  public static <T, R> R executeSafe(Supplier<R> execute) {
    try {
      return execute.get();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Execute the function and return the result, or null if the function throws an exception.
   *
   * @param <T> the type of the reference
   * @param <R> the type of the reference
   * @param execute a function to be executed that takes one argument and produces a result
   * @param t input arguments to the function
   * @return the result of the function, or null if the function throws an exception
   */
  @CheckForNull
  public static <T, R> R executeSafe(Function<T, R> execute, T t) {
    try {
      return execute.apply(t);
    } catch (Exception e) {
      return null;
    }
  }
}
