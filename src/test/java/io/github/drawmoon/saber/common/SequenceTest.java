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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class SequenceTest {

  public void itTest() {}

  @Test
  public void emptyTest() {
    Sequence<Object> seq = Sequence.empty();
    assertThat(seq.size(), is(equalTo(0L)));

    List<Object> list = seq.toList();
    assertThat(list.size(), is(equalTo(0)));
  }

  @Test
  public void rangeTest() {
    List<Integer> list = Sequence.range(1, 10).toList();
    assertThat(list, is(equalTo(ImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))));

    // test is consistent with the results of Stream.range
    List<Integer> streamNumList = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
    assertThat(list, is(equalTo(streamNumList)));
  }

  @Test
  public void filterTest() {
    Sequence<Integer> seq = Sequence.range(1, 10);
    List<Integer> list = Sequence.it(seq).filter(i -> i % 2 == 0).toList();
    assertThat(list, is(equalTo(ImmutableList.of(2, 4, 6, 8, 10))));
  }

  @Test
  public void mapTest() {
    Sequence<Integer> seq = Sequence.range(1, 5);
    List<String> list = Sequence.it(seq).map(i -> String.valueOf(i)).toList();
    assertThat(list, is(equalTo(ImmutableList.of("1", "2", "3", "4", "5"))));
  }

  @Test
  public void sizeTest() {
    Sequence<Integer> seq = Sequence.range(1, 10);
    long size = Sequence.it(seq).filter(i -> i % 2 == 0).size();
    assertEquals(5, size);
  }
}
