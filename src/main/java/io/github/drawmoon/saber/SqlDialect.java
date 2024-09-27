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

/**
 * Enum for the supported SQL dialects. This enum represents various SQL dialects and their
 * relationships. Each dialect can have a root dialect, which indicates that it is a variant or
 * extension of another dialect. The enum provides methods to retrieve the root dialect, branches
 * (derived dialects), and check if a dialect is a root.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public enum SqlDialect {
  /** The MySQL dialect. */
  MYSQL("MySql", null),

  /** The MariaDB dialect, which is a variant of MySQL. */
  MARIA_DB("MariaDB", MYSQL),

  // -----------------------------------------------------------------------
  /** The PostgreSQL dialect. */
  POSTGRES("Postgres", null),

  /** The Greenplum dialect, which is a variant of PostgreSQL. */
  GREEN_PLUM("GreenPlum", POSTGRES),

  /** The GaussDB dialect, which is a variant of PostgreSQL. */
  GAUSS_DB("GaussDB", POSTGRES),

  // -----------------------------------------------------------------------
  /** The Oracle dialect. */
  ORACLE("Oracle", null),

  /** The Dameng (达梦) dialect, which is a variant of Oracle. */
  DAMENG("DM", ORACLE),

  // -----------------------------------------------------------------------
  /** The Microsoft SQL Server dialect. */
  MSSQL("SqlServer", null);

  // -----------------------------------------------------------------------
  /** A map of dialect names to their corresponding {@code SqlDialect} instances. */
  public static final Map<String, SqlDialect> DICT = new HashMap<>();

  /** An array of root dialects. Root dialects are those that do not have a parent dialect. */
  public static final SqlDialect[] ROOT_DICT;

  /** The name of the SQL dialect. */
  private final String name;

  /** The root dialect of this dialect. If this dialect is a root, it points to itself. */
  private final SqlDialect root;

  /** Static block to initialize the {@code DICT} and {@code ROOT_DICT}. */
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

  /**
   * Constructor for the {@code SqlDialect} enum.
   *
   * @param name The name of the SQL dialect.
   * @param root The root dialect of this dialect. If this dialect is a root, it should be null.
   */
  SqlDialect(String name, SqlDialect root) {
    this.name = name;
    this.root = nullSafe(root, this);
  }

  /**
   * Retrieves the {@code SqlDialect} instance from a string value.
   *
   * @param v The string representation of the SQL dialect.
   * @return The corresponding {@code SqlDialect} instance.
   */
  public static SqlDialect fromValue(String v) {
    return DICT.computeIfAbsent(v.toLowerCase(), k -> valueOf(k));
  }

  /**
   * Retrieves the root dialect of this dialect.
   *
   * @return The root dialect.
   */
  public SqlDialect getRoot() {
    return root == this ? this : root.getRoot();
  }

  /**
   * Retrieves all dialects that are derived from this dialect.
   *
   * @return An array of derived dialects.
   */
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

  /**
   * Checks if this dialect is a root dialect.
   *
   * @return {@code true} if this dialect is a root, otherwise {@code false}.
   */
  public boolean isRoot() {
    return root == this;
  }
}
