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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import io.github.drawmoon.saber.common.Profiler;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.sql.DataSource;

public class SaberDataSource implements DataSource, SaberWrapper, AutoCloseable {

  private static final AtomicInteger counter = new AtomicInteger(0);
  protected final int wrapperId;
  protected final ConnectionUri connectionUri;

  @Nullable protected transient RequestContext context;
  @Nullable protected transient HikariDataSource dataSource;

  public SaberDataSource(RequestContext context) {
    this.wrapperId = counter.getAndIncrement();
    this.context = checkNotNull(context);
    this.connectionUri = ConnectionUri.fromStr(context.getConnectionUri());
  }

  public int getActiveConnections() {
    if (dataSource == null) return 0;

    HikariPoolMXBean bean = dataSource.getHikariPoolMXBean();
    return bean.getActiveConnections();
  }

  public int getIdleConnections() {
    if (dataSource == null) return 0;

    HikariPoolMXBean bean = dataSource.getHikariPoolMXBean();
    return bean.getIdleConnections();
  }

  public int getTotalConnections() {
    if (dataSource == null) return 0;

    HikariPoolMXBean bean = dataSource.getHikariPoolMXBean();
    return bean.getTotalConnections();
  }

  protected synchronized void bindDataSource() {
    checkNotNull(context);
    if (dataSource != null) return;

    HikariConfig hikariConfig = context.getOptions().getHikariConfig();
    hikariConfig.setJdbcUrl(connectionUri.getJdbcUrl());
    hikariConfig.setUsername(connectionUri.getUsername());
    hikariConfig.setPassword(connectionUri.getPassword());

    // This is a specific optimization done for DB.
    switch (connectionUri.getDialect()) {
      case MYSQL:
        // This sets the number of prepared statements that the MySQL driver will cache per
        // connection. The default is a conservative 25. We recommend setting this to between
        // 250-500.
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        // This is the maximum length of a prepared SQL statement that the driver will cache. The
        // MySQL default is 256. In our experience, especially with ORM frameworks like Hibernate,
        // this default is well below the threshold of generated statement lengths. Our recommended
        // setting is 2048.
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // Neither of the above parameters have any effect if the cache is in fact disabled, as it
        // is by default. You must set this parameter to true.
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        // Newer versions of MySQL support server-side prepared statements, this can provide a
        // substantial performance boost. Set this property to true.
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        break;
      case POSTGRES:
      default:
        break;
    }

    hikariConfig.validate();
    dataSource = new HikariDataSource(hikariConfig);
  }

  @Override
  public int getWrapperId() {
    return wrapperId;
  }

  @Override
  public int getRequestId() {
    return checkNotNull(context).getRequestId();
  }

  @Override
  public RequestContext getContext() {
    return context;
  }

  public void setContext(RequestContext context) {
    this.context = checkNotNull(context);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    if (dataSource == null) this.bindDataSource();

    return dataSource.getLogWriter();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    if (dataSource == null) this.bindDataSource();

    dataSource.setLogWriter(out);
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    if (dataSource == null) this.bindDataSource();

    return dataSource.getLoginTimeout();
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    if (dataSource == null) this.bindDataSource();

    dataSource.setLoginTimeout(seconds);
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    if (dataSource == null) this.bindDataSource();

    return dataSource.getParentLogger();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (iface.isAssignableFrom(getClass())) {
      return iface.cast(this);
    }

    if (dataSource == null) this.bindDataSource();
    return dataSource.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (iface.isAssignableFrom(getClass())) {
      return true;
    }

    if (dataSource == null) this.bindDataSource();
    return dataSource.isWrapperFor(iface);
  }

  @Override
  public Connection getConnection() throws SQLException {
    checkNotNull(context);
    if (dataSource == null) this.bindDataSource();

    context.getListener().onBeforeGetConnection(this);

    Profiler p = new Profiler();
    SaberConnection connection = null;
    try {
      connection = new SaberConnection(dataSource.getConnection(), context);
      return connection;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterGetConnection(connection, elapsed, context.getSqlError());
    }
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    checkNotNull(context);
    if (dataSource == null) this.bindDataSource();

    context.getListener().onBeforeGetConnection(this);

    Profiler p = new Profiler();
    SaberConnection connection = null;
    try {
      connection = new SaberConnection(dataSource.getConnection(username, password), context);
      return connection;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterGetConnection(connection, elapsed, context.getSqlError());
    }
  }

  @Override
  public void close() throws Exception {
    try {
      if (dataSource != null) {
        dataSource.close();
      }
    } catch (Exception expected) {
      // safely close
    }
  }
}
