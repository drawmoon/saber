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

/**
 * Represents a connection URI for database connections. This class provides methods to construct
 * and parse connection URIs, determine the SQL dialect, and retrieve the JDBC URL, driver class
 * name, username, and password.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class ConnectionUri {

  /** The JDBC URL used to connect to the database. */
  private final String jdbcUrl;

  /** The username for database authentication. */
  private final String username;

  /** The password for database authentication. */
  private final String password;

  /**
   * Constructs a new {@code ConnectionUri} instance using the specified SQL dialect, host, port,
   * database, username, and password.
   *
   * @param dialect The SQL dialect (e.g., POSTGRES, MYSQL)
   * @param host The hostname or IP address of the database server
   * @param port The port number of the database server
   * @param database The name of the database
   * @param username The username for database authentication
   * @param password The password for database authentication
   * @throws UnsupportedOperationException if the provided SQL dialect is not supported
   */
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
      // PostgreSQL-specific initialization can be added here
    } else if (dialect == SqlDialect.MYSQL) {
      // MySQL-specific initialization can be added here
    } else if (dialect == SqlDialect.ORACLE) {
      // Oracle-specific initialization can be added here
    } else if (dialect == SqlDialect.MSSQL) {
      // Microsoft SQL Server-specific initialization can be added here
    } else if (dialect == SqlDialect.DAMENG) {
      // Dameng-specific initialization can be added here
    } else {
      throw new UnsupportedOperationException("Unsupported SQL dialect: " + dialect);
    }
    this.jdbcUrl = ""; // TODO: Construct the JDBC URL based on the provided parameters
    this.username = checkNotNull(username);
    this.password = checkNotNull(password);
  }

  /**
   * Constructs a new {@code ConnectionUri} instance using the provided JDBC URL, username, and
   * password.
   *
   * @param jdbcUrl The JDBC URL used to connect to the database
   * @param username The username for database authentication
   * @param password The password for database authentication
   */
  public ConnectionUri(String jdbcUrl, String username, String password) {
    this.jdbcUrl = checkNotNull(jdbcUrl);
    this.username = checkNotNull(username);
    this.password = checkNotNull(password);
  }

  /**
   * Parses a string representation of a connection URI and returns a new {@code ConnectionUri}
   * instance.
   *
   * @param connectionUri The string representation of the connection URI
   * @return A new {@code ConnectionUri} instance
   */
  public static ConnectionUri fromStr(String connectionUri) {
    return null; // TODO: Implement parsing logic
  }

  /**
   * Determines the SQL dialect based on the JDBC URL.
   *
   * @return The SQL dialect, or {@code null} if the dialect cannot be determined
   */
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

  /**
   * Retrieves the fully qualified name of the JDBC driver class for the current SQL dialect.
   *
   * @return The JDBC driver class name
   */
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

  /**
   * Retrieves the JDBC URL used to connect to the database.
   *
   * @return The JDBC URL
   */
  public String getJdbcUrl() {
    return jdbcUrl;
  }

  /**
   * Retrieves the username for database authentication.
   *
   * @return The username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Retrieves the password for database authentication.
   *
   * @return The password
   */
  public String getPassword() {
    return password;
  }

  /** A static inner class containing template strings for constructing JDBC URLs. */
  static class JdbcUrlTemplate {
    /** Template string for constructing PostgreSQL JDBC URLs. */
    public static final String POSTGRES = "jdbc:postgresql://{host}[:{port}][/{database}]";

    /** Template string for constructing MySQL JDBC URLs. */
    public static final String MYSQL = "jdbc:mysql://{host}[:{port}][/{database}]";

    /** Template string for constructing Oracle JDBC URLs. */
    public static final String ORACLE = "jdbc:oracle:thin:@//{host}[:{port}]/{database}";

    /** Template string for constructing Microsoft SQL Server JDBC URLs. */
    public static final String MSSQL = "jdbc:sqlserver://{host}[:{port}][;databaseName={database}]";

    /** Template string for constructing Dameng JDBC URLs. */
    public static final String DAMENG = "jdbc:dm://{host}[:{port}]";
  }
}
