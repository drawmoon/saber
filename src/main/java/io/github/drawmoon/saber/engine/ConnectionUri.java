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
package io.github.drawmoon.saber.engine;

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import io.github.drawmoon.saber.SqlDialect;

public class ConnectionUri {

  private final String jdbcUrl;
  private final String username;
  private final String password;

  public ConnectionUri(
      SqlDialect dialect,
      String host,
      Integer port,
      String database,
      String username,
      String password) {
    checkNotNull(dialect);
    checkNotNull(host);
    if (dialect == SqlDialect.POSTGRES) {
    } else if (dialect == SqlDialect.MYSQL) {
    } else if (dialect == SqlDialect.ORACLE) {
    } else if (dialect == SqlDialect.MSSQL) {
    } else if (dialect == SqlDialect.DAMENG) {
    } else {
      throw new UnsupportedOperationException();
    }
    this.jdbcUrl = "";
    this.username = checkNotNull(username);
    this.password = checkNotNull(password);
  }

  public ConnectionUri(String jdbcUrl, String username, String password) {
    this.jdbcUrl = checkNotNull(jdbcUrl);
    this.username = checkNotNull(username);
    this.password = checkNotNull(password);
  }

  public static ConnectionUri fromStr(String connectionUri) {
    return null;
  }

  public SqlDialect getDialect() {
    if (jdbcUrl.contains(":postgresql:") || jdbcUrl.contains(":pgsql:")) {
      return SqlDialect.POSTGRES;
    } else if (jdbcUrl.contains(":mysql:")) {
      return SqlDialect.MYSQL;
    } else if (jdbcUrl.contains(":oracle:")) {
      return SqlDialect.ORACLE;
    } else if (jdbcUrl.contains(":sqlserver:")) {
      return SqlDialect.MSSQL;
    } else if (jdbcUrl.contains(":dm:")) {
      return SqlDialect.DAMENG;
    } else {
      return null;
    }
  }

  public String getDriver() {
    SqlDialect dialect = getDialect();
    if (dialect == null) {
      return "java.sql.Driver";
    }

    switch (dialect) {
      case POSTGRES:
        return "org.postgresql.Driver";
      case MYSQL:
        return "com.mysql.cj.jdbc.Driver";
      case ORACLE:
        return "oracle.jdbc.OracleDriver";
      case MSSQL:
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
      case DAMENG:
        return "dm.jdbc.driver.DmDriver";
      default:
        return "java.sql.Driver";
    }
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  static class JdbcUrlTemplate {
    public static final String POSTGRES = "jdbc:postgresql://{host}[:{port}][/{database}]";
    public static final String MYSQL = "jdbc:mysql://{host}[:{port}][/{database}]";
    public static final String ORACLE = "jdbc:oracle:thin:@//{host}[:{port}]/{database}";
    public static final String MSSQL = "jdbc:sqlserver://{host}[:{port}][;databaseName={database}]";
    public static final String DAMENG = "jdbc:dm://{host}[:{port}]";
  }
}
