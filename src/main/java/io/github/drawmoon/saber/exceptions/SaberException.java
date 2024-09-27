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
package io.github.drawmoon.saber.exceptions;

/**
 * A custom exception class used within the Saber framework to handle runtime errors. This exception
 * extends {@link RuntimeException} and can be thrown when unexpected conditions occur during the
 * execution of the application.
 *
 * <p>The {@code SaberException} class provides constructors that allow for the creation of
 * exceptions with messages, causes, or both.
 *
 * @see RuntimeException
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class SaberException extends RuntimeException {

  private static final long serialVersionUID = -8492251942206794476L;

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *     #getMessage()} method).
   */
  public SaberException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified cause.
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   *     (A <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or
   *     unknown.)
   */
  public SaberException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *     #getMessage()} method).
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   *     (A <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or
   *     unknown.)
   */
  public SaberException(String message, Throwable cause) {
    super(message);
    if (cause != null) {
      super.initCause(cause);
    }
  }
}
