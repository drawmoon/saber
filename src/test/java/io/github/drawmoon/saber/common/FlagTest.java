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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.drawmoon.saber.common.Flag.Flags;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class FlagTest {

  enum TestFlag implements Flag {
    OPTIONS_1(1),
    OPTIONS_2(2),
    OPTIONS_3(4);

    final int value;

    TestFlag(int value) {
      this.value = value;
    }

    @Override
    public int value() {
      return value;
    }
  }

  @Test
  public void hasFlagTest() {
    boolean b = Flags.hasFlag(3, TestFlag.OPTIONS_1);
    assertTrue(b);
  }

  @Test
  public void hasValueTest() {
    boolean b = Flags.hasValue(TestFlag.OPTIONS_1, 3);
    assertTrue(b);
  }

  @Test
  public void valueTest() {
    int value = Flags.value(Arrays.asList(TestFlag.OPTIONS_1, TestFlag.OPTIONS_2));

    assertEquals(3, value);
  }

  @Test
  public void flagsTest() {
    List<TestFlag> flags = Flags.flags(3, TestFlag.class);

    assertEquals(2, flags.size());
    assertThat(flags, equalTo(ImmutableList.of(TestFlag.OPTIONS_1, TestFlag.OPTIONS_2)));
  }

  @Test
  public void mapsTest() {
    Map<Integer, TestFlag> flags = Flags.maps(TestFlag.class);

    assertEquals(3, flags.size());
    assertThat(
        flags,
        equalTo(
            ImmutableMap.of(1, TestFlag.OPTIONS_1, 2, TestFlag.OPTIONS_2, 4, TestFlag.OPTIONS_3)));
  }
}
