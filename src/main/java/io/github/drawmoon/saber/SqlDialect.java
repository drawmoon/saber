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

import static io.github.drawmoon.saber.common.Preconditions.nullSafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Enum for the supported SQL dialect. */
public enum SqlDialect {
  /** The MySQL dialect. */
  MYSQL("MySql", null),

  /** The MariaDB dialect. */
  MARIA_DB("MariaDB", MYSQL),

  // -----------------------------------------------------------------------
  /** The Postgres dialect. */
  POSTGRES("Postgres", null),

  /** The GreenPlum dialect. */
  GREEN_PLUM("GreenPlum", POSTGRES),

  /** The GaussDB (高斯) dialect. */
  GAUSS_DB("GaussDB", POSTGRES),

  // -----------------------------------------------------------------------
  /** The Oracle dialect. */
  ORACLE("Oracle", null),

  /** The Dameng (达梦) dialect. */
  DAMENG("DM", ORACLE),

  // -----------------------------------------------------------------------
  /** The Microsoft SQL Server dialect. */
  MSSQL("SqlServer", null);

  // -----------------------------------------------------------------------
  public static final Map<String, SqlDialect> DICT = new HashMap<>();
  public static final SqlDialect[] ROOT_DICT;
  private final String name;
  private final SqlDialect root;

  static {
    List<SqlDialect> rootList = new ArrayList<>();

    for (SqlDialect dialect : values()) {
      DICT.put(dialect.name.toLowerCase(), dialect);
      if (dialect.isRoot()) {
        rootList.add(dialect);
      }
    }

    ROOT_DICT = rootList.toArray(new SqlDialect[0]);
  }

  SqlDialect(String name, SqlDialect root) {
    this.name = name;
    this.root = nullSafe(root, this);
  }

  public static SqlDialect fromValue(String v) {
    return DICT.computeIfAbsent(v.toLowerCase(), k -> valueOf(k));
  }

  public SqlDialect getRoot() {
    return root == this ? this : root.getRoot();
  }

  public SqlDialect[] getBranch() {
    Set<SqlDialect> dialectList = new HashSet<>();
    for (SqlDialect dialect : values()) {
      if (dialect == this) continue;

      if (dialect.getRoot() == this) {
        dialectList.add(dialect);
      }
    }

    return dialectList.toArray(new SqlDialect[0]);
  }

  public boolean isRoot() {
    return root == this;
  }
}
