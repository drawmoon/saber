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

import com.zaxxer.hikari.HikariConfig;
import io.github.drawmoon.saber.common.PropertyElf;
import io.github.drawmoon.saber.exceptions.EngineException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SaberOptions {

  private static final String PROPERTY_FILE_NAME = "saber.properties";
  private static final long DEFAULT_DATA_SOURCE_LIFETIME = TimeUnit.DAYS.toMillis(1);
  private static final int DEFAULT_DATA_SOURCE_LIMIT = 10;
  private static final String DEFAULT_DATA_SOURCE_LIMIT_POLICIES = "StopCreate";
  private static final int DEFAULT_MAXIMUM_POOL_SIZE_LIMIT = 0;
  private static final long DEFAULT_QUERY_TIMEOUT = 30;

  private volatile long dataSourceLifetime;
  private volatile int dataSourceLimit;
  private volatile String dataSourceLimitPolicies;
  private volatile int maximumPoolSizeLimit;
  private volatile long queryTimeout;
  private HikariConfig hikariConfig;
  private SaberEventListener listener;

  public SaberOptions() {
    dataSourceLifetime = DEFAULT_DATA_SOURCE_LIFETIME;
    dataSourceLimit = DEFAULT_DATA_SOURCE_LIMIT;
    dataSourceLimitPolicies = DEFAULT_DATA_SOURCE_LIMIT_POLICIES;
    maximumPoolSizeLimit = DEFAULT_MAXIMUM_POOL_SIZE_LIMIT;
    queryTimeout = DEFAULT_QUERY_TIMEOUT;

    Properties properties = null;
    ClassLoader loader = this.getClass().getClassLoader();
    try (InputStream stream = loader.getResourceAsStream(PROPERTY_FILE_NAME)) {
      if (stream != null) {
        properties = new Properties();
        properties.load(stream);

        PropertyElf.setTargetFromProperties(this, properties);
      }
    } catch (Exception e) {
      throw new EngineException(e);
    }
    if (properties != null) {
      hikariConfig = new HikariConfig(PropertyElf.getHikariProperties(properties));
    } else {
      hikariConfig = new HikariConfig();
    }

    listener = new EventBus();
  }

  /**
   * Get the lifetime of the datasource. If fetched within the lifetime cycle, the current fetch
   * moment is used as the new start point and continues for a lifetime cycle that will last until
   * the next check.
   *
   * @return the lifetime of the datasource
   */
  public long getDataSourceLifetime() {
    return dataSourceLifetime;
  }

  /**
   * Sets the lifetime of the datasource. If fetched within the lifetime cycle, the current fetch
   * moment is used as the new start point and continues for a lifetime cycle that will last until
   * the next check.
   *
   * @param dataSourceLifetime the lifetime of the datasource
   */
  public void setDataSourceLifetime(long dataSourceLifetime) {
    this.dataSourceLifetime = dataSourceLifetime;
  }

  /**
   * Gets the upper limit of the total number of datasource. When the total number of datasource
   * reaches the upper limit, it will be processed according to the predefined limiting policy.
   *
   * @return the upper limit of the total number of datasource
   */
  public int getDataSourceLimit() {
    return dataSourceLimit;
  }

  /**
   * Sets the upper limit of the total number of datasource. When the total number of datasource
   * reaches the upper limit, it will be processed according to the predefined limiting policy.
   *
   * @param dataSourceLimit the upper limit of the total number of datasource
   */
  public void setDataSourceLimit(int dataSourceLimit) {
    this.dataSourceLimit = dataSourceLimit;
  }

  /**
   * Get the upper limit of the total number of datasource and the corresponding limiting policy.
   * Two strategies are supported: <code>StopCreate</code> and <code>Recreate</code>.
   *
   * <ul>
   *   <li><b>StopCreate</b>: throws an SQLException when the total number is reached.
   *   <li><b>Recreate</b>: When the total number of instances reaches the upper limit, destroy the
   *       datasource that has been idle for the longest time and create a new instance.
   * </ul>
   *
   * @return the upper limit of the total number of datasource and the corresponding limiting policy
   */
  public String getDataSourceLimitPolicies() {
    return dataSourceLimitPolicies;
  }

  /**
   * Set the upper limit of the total number of datasource and the corresponding limiting policy.
   * Two strategies are supported: <code>StopCreate</code> and <code>Recreate</code>.
   *
   * <ul>
   *   <li><b>StopCreate</b>: throws an SQLException when the total number is reached.
   *   <li><b>Recreate</b>: When the total number of instances reaches the upper limit, destroy the
   *       datasource that has been idle for the longest time and create a new instance.
   * </ul>
   *
   * @param dataSourceLimitPolicies the upper limit of the total number of datasource and the
   *     corresponding limiting policy
   */
  public void setDataSourceLimitPolicies(String dataSourceLimitPolicies) {
    this.dataSourceLimitPolicies = dataSourceLimitPolicies;
  }

  /**
   * Gets the upper limit of the maximum number of connections for the connection pool.
   * Automatically adjusts the maximum number of connections in the connection pool based on the
   * upper limit of the total number of datasource and the maximum number of connections in the
   * connection pool.
   *
   * @return the upper limit of the maximum number of connections for the connection pool
   */
  public int getMaximumPoolSizeLimit() {
    return maximumPoolSizeLimit;
  }

  /**
   * Sets the upper limit of the maximum number of connections for the connection pool.
   * Automatically adjusts the maximum number of connections in the connection pool based on the
   * upper limit of the total number of datasource and the maximum number of connections in the
   * connection pool.
   *
   * @param maximumPoolSizeLimit the upper limit of the maximum number of connections for the
   *     connection pool
   */
  public void setMaximumPoolSizeLimit(int maximumPoolSizeLimit) {
    this.maximumPoolSizeLimit = maximumPoolSizeLimit;
  }

  /**
   * Gets the default query wait timeout for datasource. This default value is used when no query
   * wait timeout is specified, and overrides the default value if a query wait timeout is
   * specified.
   *
   * @return the default query wait timeout for datasource
   */
  public long getQueryTimeout() {
    return queryTimeout;
  }

  /**
   * Sets the default query wait timeout for datasource. This default value is used when no query
   * wait timeout is specified, and overrides the default value if a query wait timeout is
   * specified.
   *
   * @param queryTimeout the default query wait timeout for datasource
   */
  public void setQueryTimeout(long queryTimeout) {
    this.queryTimeout = queryTimeout;
  }

  /**
   * Gets the HikariCP configuration.
   *
   * @return the {@link HikariConfig} object
   */
  public HikariConfig getHikariConfig() {
    return hikariConfig;
  }

  /**
   * Sets the HikariCP configuration.
   *
   * @param hikariConfig the {@link HikariConfig} object
   */
  public void setHikariConfig(HikariConfig hikariConfig) {
    this.hikariConfig = hikariConfig;
  }

  /**
   * Gets the {@link SaberEventListener}.
   *
   * @return the {@link SaberEventListener} object
   */
  public SaberEventListener getListener() {
    return listener;
  }

  /**
   * Sets the {@link SaberEventListener}.
   *
   * @param listener the {@link SaberEventListener} object
   */
  public void setListener(SaberEventListener listener) {
    this.listener = listener;
  }
}
