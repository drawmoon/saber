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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.drawmoon.saber.common.DateTime.DateTimeKind;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

public class DateTimeTest {

  @Test
  public void nowTest() {
    DateTime dateTime = DateTime.now();
    assertNotNull(dateTime);

    DateTime utcDateTime = DateTime.utcNow();
    assertNotNull(utcDateTime);
  }

  @Test
  public void ofZoneTest() {
    DateTime dateTime = DateTime.of(ZoneId.of("Asia/Shanghai"));
    assertNotNull(dateTime);
  }

  @Test
  public void utcDateTimeStringParseTest() {
    String withZ = "2024-05-23T01:59:04.146Z";
    DateTime dateTime = DateTime.parse(withZ);

    assertNotNull(dateTime);
    assertEquals(withZ, dateTime.toUtcString());

    String withoutZ = "2024-05-23T01:59:04.146";
    DateTime dateTime2 = DateTime.parse(withoutZ);

    assertNotNull(dateTime2);
    assertEquals(withoutZ + "Z", dateTime2.toUtcString());

    String withOffset = "2024-05-23T10:18:00.892+08:00";
    DateTime dateTime3 = DateTime.parse(withOffset);

    assertNotNull(dateTime3);

    String withOffset2 = "2024-05-23T10:18:00.892+08:00[Asia/Shanghai]";
    DateTime dateTime4 = DateTime.parse(withOffset2);

    assertNotNull(dateTime4);

    String withOffset3 = "2024-05-23T10:18:00.892-08:00";
    DateTime dateTime5 = DateTime.parse(withOffset3);

    assertNotNull(dateTime5);
  }

  // @Test
  // public void utcDateStringParseTest() {
  //   String withOffset = "2024-05-23+08:00";
  //   DateTime dateTime = DateTime.parse(withOffset);

  //   assertNotNull(dateTime);

  //   String withOffset2 = "2024-05-23-08:00";
  //   DateTime dateTime2 = DateTime.parse(withOffset2);

  //   assertNotNull(dateTime2);
  // }

  @Test
  public void dateTimeKindTest() {
    DateTime dateTime = DateTime.now();
    assertEquals(DateTimeKind.LOCAL, dateTime.kind());

    DateTime utcDateTime = DateTime.utcNow();
    assertEquals(DateTimeKind.UTC, utcDateTime.kind());

    DateTime shanghaiDateTime = DateTime.of(ZoneId.of("Asia/Shanghai"));
    assertEquals(DateTimeKind.LOCAL, shanghaiDateTime.kind());

    DateTime newYorkDateTime = DateTime.of(ZoneId.of("America/New_York"));
    assertEquals(DateTimeKind.UNSPECIFIED, newYorkDateTime.kind());
  }

  @Test
  public void toZonedDateTimeTest() {
    DateTime dateTime = DateTime.now();
    ZonedDateTime zonedDateTime = dateTime.toZonedDateTime();

    assertNotNull(zonedDateTime);
  }

  @Test
  public void toLocalDateTimeTest() {
    DateTime dateTime = DateTime.now();
    LocalDateTime localDateTime = dateTime.toLocalDateTime();

    assertNotNull(localDateTime);
  }

  @Test
  public void toInstantTest() {
    DateTime dateTime = DateTime.now();
    Instant instant = dateTime.toInstant();

    assertNotNull(instant);
  }

  @Test
  public void toStringTest() {
    DateTime dateTime = DateTime.parse("2024-05-23 12:08:11");
    assertEquals("2024-05-23 12:08:11", dateTime.toString());

    DateTime dateTime2 = DateTime.parse("2024-05-23T04:08:11.193Z");
    assertEquals("2024-05-23 12:08:11", dateTime2.toString());
  }

  @Test
  public void toUtcStringTest() {
    DateTime dateTime = DateTime.now();
    String dateTimeString = dateTime.toUtcString();

    assertNotNull(dateTimeString);

    DateTime dateTime2 = DateTime.of(ZoneId.of("America/New_York"));
    String dateTimeString2 = dateTime2.toUtcString();

    assertNotNull(dateTimeString2);
  }

  @Test
  public void toStringWithZoneTest() {
    DateTime dateTime = DateTime.of(ZoneId.of("Asia/Shanghai"));
    String dateTimeString = dateTime.toString(ZoneId.of("America/New_York"));

    assertNotNull(dateTimeString);
  }
}
