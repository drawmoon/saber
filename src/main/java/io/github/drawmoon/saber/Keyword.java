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

import static io.github.drawmoon.saber.common.Preconditions.checkNotWhiteSpace;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * Represents a SQL keyword.
 *
 * <p>This class provides utility methods to handle SQL keywords, ensuring they are not null or
 * whitespace. It also offers methods to retrieve the keyword in its original form, lowercase, and
 * uppercase.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public final class Keyword {

  private final String keyword;

  /**
   * Constructs a new {@code Keyword} object.
   *
   * @param keyword The keyword string, must not be null or whitespace
   * @throws IllegalArgumentException if the keyword is null or whitespace
   */
  private Keyword(@CheckForNull String keyword) {
    checkNotWhiteSpace(keyword);
    this.keyword = keyword;
  }

  /**
   * Creates a new {@code Keyword} object.
   *
   * @param keyword The keyword string, must not be null or whitespace
   * @return A new {@code Keyword} object
   * @throws IllegalArgumentException if the keyword is null or whitespace
   */
  @Nonnull
  public static Keyword of(@CheckForNull String keyword) {
    checkNotWhiteSpace(keyword);
    return new Keyword(keyword);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns the keyword in its original form.
   *
   * @return The original keyword string
   */
  @Nonnull
  public String asis() {
    return this.keyword;
  }

  /**
   * Returns the keyword in lowercase.
   *
   * @return The lowercase keyword string
   */
  @Nonnull
  public String lower() {
    return this.keyword.toLowerCase();
  }

  /**
   * Returns the keyword in uppercase.
   *
   * @return The uppercase keyword string
   */
  @Nonnull
  public String upper() {
    return this.keyword.toUpperCase();
  }
}
