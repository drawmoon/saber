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
import static io.github.drawmoon.saber.common.Preconditions.checkNotWhiteSpace;

import com.google.common.base.CharMatcher;
import io.github.drawmoon.saber.exceptions.DateException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

/**
 * Represents a date and time.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
final class DateTime implements Serializable {

  private static final long serialVersionUID = -8492251942206794476L;

  final ZonedDateTime dateTime;

  private DateTime(ZonedDateTime dateTime) {
    this.dateTime = dateTime;
  }

  /**
   * Obtains the current date-time from the system clock in the default time-zone.
   *
   * @return the current date-time using the system clock, not null
   */
  @Nonnull
  public static DateTime now() {
    return new DateTime(ZonedDateTime.now(ZoneId.systemDefault()));
  }

  /**
   * Obtains the current date-time from the system clock in the UTC time-zone.
   *
   * @return the current date-time using the system clock, not null
   */
  @Nonnull
  public static DateTime utcNow() {
    return new DateTime(ZonedDateTime.now(ZoneId.of("UTC")));
  }

  /**
   * Obtains the current date-time from the system clock in the specified time-zone.
   *
   * @param zone the zone ID to use, not null
   * @return the current date-time using the system clock, not null
   */
  @Nonnull
  public static DateTime of(ZoneId zone) {
    return new DateTime(ZonedDateTime.now(zone));
  }

  /**
   * Trying to parse the text.
   *
   * <p>The incoming date formats are tried one by one until parsing is successful and a {@link
   * DateTime} object is returned, otherwise a {@link DateException} exception is thrown.
   *
   * @param text the text to parse, not null
   * @return the parsed date-time, not null
   */
  @Nonnull
  public static DateTime parse(String text) {
    checkNotWhiteSpace(text);

    try {
      return parseUtc(text);
    } catch (DateException e) {
      // ignore
    }
    try {
      return parseNorm(text);
    } catch (DateException e) {
      // ignore
    }

    throw new DateException("No format fit for date string: " + text);
  }

  /**
   * Trying to parse the text, such as:
   *
   * <ul>
   *   <li>yyyy-MM-dd HH:mm:ss
   *   <li>yyyy/MM/dd HH:mm:ss
   *   <li>yyyy-MM-dd
   *   <li>yyyy/MM/dd
   * </ul>
   *
   * <p>The incoming date formats are tried one by one until parsing is successful and a {@link
   * DateTime} object is returned, otherwise a {@link DateException} exception is thrown.
   *
   * @param text the text to parse, not null
   * @return the parsed date-time, not null
   */
  @Nonnull
  public static DateTime parseNorm(String text) {
    checkNotWhiteSpace(text);

    if (Pattern.matches(
        "\\d{4}[-/]\\d{1,2}[-/]\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?)?(.\\d{1,6})?", text)) {
      int colonCount = CharMatcher.is(':').countIn(text);
      switch (colonCount) {
        case 0:
          DateTimeFormatter[] normDateFormatters = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"), DateTimeFormatter.ofPattern("yyyy/MM/dd")
          };
          for (DateTimeFormatter fmt : normDateFormatters) {
            try {
              LocalDate d = LocalDate.parse(text, fmt);
              return new DateTime(ZonedDateTime.of(d, LocalTime.MIN, ZoneId.systemDefault()));
            } catch (Exception e) {
              // ignore
            }
          }
          break;
        case 2:
          DateTimeFormatter[] normDateTimeFormatters = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
          };
          for (DateTimeFormatter fmt : normDateTimeFormatters) {
            try {
              LocalDateTime t = LocalDateTime.parse(text, fmt);
              return new DateTime(ZonedDateTime.of(t, ZoneId.systemDefault()));
            } catch (Exception e) {
              // ignore
            }
          }
          break;
      }
    }

    throw new DateException("No format fit for date string: " + text);
  }

  /**
   * Trying to parse UTC format date-time, such as:
   *
   * <ul>
   *   <li>2011-12-03T10:15:30
   *   <li>2011-12-03T10:15:30Z
   *   <li>2011-12-03T10:15:30+01:00
   *   <li>2011-12-03T10:15:30-01:00
   *   <li>2011-12-03T10:15:30+01:00[Europe/Paris]
   * </ul>
   *
   * <p>The incoming date formats are tried one by one until parsing is successful and a {@link
   * DateTime} object is returned, otherwise a {@link DateException} exception is thrown.
   *
   * @param text the text to parse, not null
   * @return the parsed date-time, not null
   */
  @Nonnull
  public static DateTime parseUtc(String text) {
    checkNotWhiteSpace(text);

    DateTimeFormatter[] isoFormatters = {
      DateTimeFormatter.ISO_INSTANT, DateTimeFormatter.ISO_DATE_TIME
    };
    for (DateTimeFormatter fmt : isoFormatters) {
      try {
        // with offset or Z
        if (text.contains("Z") || text.contains("+") || Pattern.matches(text, "-\\d{2}:?00")) {
          ZonedDateTime t = ZonedDateTime.parse(text, fmt);
          return new DateTime(t);
        } else if (text.contains("T")) {
          LocalDateTime t = LocalDateTime.parse(text, fmt);
          return new DateTime(ZonedDateTime.of(t, ZoneId.of("UTC")));
        }
      } catch (Exception e) {
        // ignore
      }
    }

    throw new DateException("No format fit for date string: " + text);
  }

  /**
   * Returns the kind of this date-time.
   *
   * @return the kind, not null
   */
  @Nonnull
  public DateTimeKind kind() {
    ZoneId dataTimeZone = this.dateTime.getZone();
    if (dataTimeZone.getId().equals("UTC")) {
      return DateTimeKind.UTC;
    } else if (dataTimeZone.equals(ZoneId.systemDefault())) {
      return DateTimeKind.LOCAL;
    } else {
      return DateTimeKind.UNSPECIFIED;
    }
  }

  /**
   * Gets the {@link ZonedDateTime} part of this date-time.
   *
   * @return the date-time with zone of this date-time, not null
   */
  @Nonnull
  public ZonedDateTime toZonedDateTime() {
    return this.dateTime;
  }

  /**
   * Gets the {@link LocalDateTime} part of this date-time.
   *
   * <p>This returns a LocalDateTime with the same year, month, day and time as this date-time.
   *
   * @return the local date-time part of this date-time, not null
   */
  @Nonnull
  public LocalDateTime toLocalDateTime() {
    return this.dateTime.toLocalDateTime();
  }

  /**
   * Converts this date-time to an {@link Instant}.
   *
   * @return an {@link Instant} representing the same instant, not null
   */
  @Nonnull
  public Instant toInstant() {
    return this.dateTime.toInstant();
  }

  /** Outputs this date-time as a {@code String}, such as {@code 2007-12-03 10:15:30}. */
  @Override
  @Nonnull
  public String toString() {
    ZonedDateTime zonedDateTime = this.dateTime.withZoneSameInstant(ZoneId.systemDefault());
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    return zonedDateTime.format(fmt);
  }

  /**
   * Outputs this date-time as a specified time zone format {@code String}.
   *
   * @param zone the zone ID to use, not null
   * @return the formatted date-time, not null
   */
  @Nonnull
  public String toString(ZoneId zone) {
    checkNotNull(zone);

    ZonedDateTime zonedDateTime = this.dateTime.withZoneSameInstant(zone);
    DateTimeFormatter fmt = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    return zonedDateTime.format(fmt);
  }

  /**
   * Outputs this date-time as a UTC format {@code String}, such as {@code
   * 2007-12-03T10:15:30.001Z}.
   *
   * @return the formatted date-time, not null
   */
  @Nonnull
  public String toUtcString() {
    DateTimeFormatter fmt = DateTimeFormatter.ISO_INSTANT;
    return this.dateTime.format(fmt);
  }

  /**
   * This enum is used to indentify DateTime instances in cases when they are known to be in local
   * time, UTC time or if this information has not been specified or is not applicable.
   */
  public enum DateTimeKind {
    UNSPECIFIED,
    UTC,
    LOCAL
  }
}
