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

/** A SQL keyword. */
public final class Keyword {

  private final String keyword;

  private Keyword(@CheckForNull String keyword) {
    checkNotWhiteSpace(keyword);
    this.keyword = keyword;
  }

  /**
   * Create a {@link Keyword} object.
   *
   * @param keyword The keyword string, not null
   * @return The {@link Keyword} object, not null
   */
  @Nonnull
  public static Keyword of(@CheckForNull String keyword) {
    checkNotWhiteSpace(keyword);
    return new Keyword(keyword);
  }

  // -----------------------------------------------------------------------
  @Nonnull
  public String asis() {
    return this.keyword;
  }

  @Nonnull
  public String lower() {
    return this.keyword.toLowerCase();
  }

  @Nonnull
  public String upper() {
    return this.keyword.toUpperCase();
  }
}
