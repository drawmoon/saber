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

import static io.github.drawmoon.saber.SqlDialect.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SqlDialectTest {

  @Test
  public void rootDialectTest() {
    assertThat(ROOT_DICT, equalTo(new SqlDialect[] {MYSQL, POSTGRES, ORACLE, MSSQL}));

    assertTrue(POSTGRES.isRoot());
    assertEquals(POSTGRES, POSTGRES.getRoot());
    assertThat(POSTGRES.getBranch(), equalTo(new SqlDialect[] {GREEN_PLUM, GAUSS_DB}));
    assertEquals(POSTGRES, GREEN_PLUM.getRoot());
  }
}
