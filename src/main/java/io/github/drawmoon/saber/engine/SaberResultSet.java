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

import io.github.drawmoon.saber.common.Profiler;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SaberResultSet implements ResultSet, SaberWrapper {

  private static final AtomicInteger counter = new AtomicInteger(0);
  protected final int wrapperId;
  protected final ResultSet resultSet;
  protected final SaberStatement statement;
  protected final RequestContext context;

  public SaberResultSet(ResultSet resultSet, SaberStatement statement) {
    this.wrapperId = counter.getAndIncrement();
    this.resultSet = checkNotNull(resultSet);
    this.statement = checkNotNull(statement);
    this.context = statement.getContext();

    context.setResultSet(this);
  }

  public SaberStatement getSaberStatement() {
    return this.statement;
  }

  @Override
  public int getWrapperId() {
    return this.wrapperId;
  }

  @Override
  public int getRequestId() {
    return getContext().getRequestId();
  }

  @Override
  public RequestContext getContext() {
    return null;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (iface.isAssignableFrom(getClass())) {
      return iface.cast(this);
    }
    return this.resultSet.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (iface.isAssignableFrom(getClass())) {
      return true;
    }
    return this.resultSet.isWrapperFor(iface);
  }

  @Override
  public boolean next() throws SQLException {
    context.getListener().onBeforeResultSetNext(this);

    Profiler p = new Profiler();
    boolean hasNext = false;
    try {
      hasNext = this.resultSet.next();
      return hasNext;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterResultSetNext(this, hasNext, elapsed, context.getSqlError());
    }
  }

  @Override
  public void close() throws SQLException {
    Profiler p = new Profiler();
    try {
      this.resultSet.close();
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterResultSetClose(this, elapsed, context.getSqlError());
    }
  }

  @Override
  public boolean wasNull() throws SQLException {
    return this.resultSet.wasNull();
  }

  @Override
  public String getString(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    String value = null;
    try {
      value = this.resultSet.getString(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public boolean getBoolean(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Boolean value = null;
    try {
      value = this.resultSet.getBoolean(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public byte getByte(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Byte value = null;
    try {
      value = this.resultSet.getByte(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public short getShort(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Short value = null;
    try {
      value = this.resultSet.getShort(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public int getInt(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    int value = 0;
    try {
      value = this.resultSet.getInt(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public long getLong(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Long value = null;
    try {
      value = this.resultSet.getLong(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public float getFloat(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Float value = null;
    try {
      value = this.resultSet.getFloat(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public double getDouble(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Double value = null;
    try {
      value = this.resultSet.getDouble(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public BigDecimal getBigDecimal(int columnLabel, int scale) throws SQLException {
    Profiler p = new Profiler();
    BigDecimal value = null;
    try {
      value = this.resultSet.getBigDecimal(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public byte[] getBytes(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    byte[] value = null;
    try {
      value = this.resultSet.getBytes(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Date getDate(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Date value = null;
    try {
      value = this.resultSet.getDate(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Time getTime(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Time value = null;
    try {
      value = this.resultSet.getTime(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Timestamp getTimestamp(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Timestamp value = null;
    try {
      value = this.resultSet.getTimestamp(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public InputStream getAsciiStream(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    InputStream value = null;
    try {
      value = this.resultSet.getAsciiStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public InputStream getUnicodeStream(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    InputStream value = null;
    try {
      value = this.resultSet.getUnicodeStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public InputStream getBinaryStream(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    InputStream value = null;
    try {
      value = this.resultSet.getBinaryStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public String getString(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    String value = null;
    try {
      value = this.resultSet.getString(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public boolean getBoolean(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Boolean value = null;
    try {
      value = this.resultSet.getBoolean(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public byte getByte(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Byte value = null;
    try {
      value = this.resultSet.getByte(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public short getShort(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Short value = null;
    try {
      value = this.resultSet.getShort(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public int getInt(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    int value = 0;
    try {
      value = this.resultSet.getInt(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public long getLong(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Long value = null;
    try {
      value = this.resultSet.getLong(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public float getFloat(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Float value = null;
    try {
      value = this.resultSet.getFloat(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public double getDouble(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Double value = null;
    try {
      value = this.resultSet.getDouble(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
    Profiler p = new Profiler();
    BigDecimal value = null;
    try {
      value = this.resultSet.getBigDecimal(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public byte[] getBytes(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    byte[] value = null;
    try {
      value = this.resultSet.getBytes(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Date getDate(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Date value = null;
    try {
      value = this.resultSet.getDate(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Time getTime(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Time value = null;
    try {
      value = this.resultSet.getTime(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Timestamp value = null;
    try {
      value = this.resultSet.getTimestamp(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public InputStream getAsciiStream(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    InputStream value = null;
    try {
      value = this.resultSet.getAsciiStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public InputStream getUnicodeStream(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    InputStream value = null;
    try {
      value = this.resultSet.getUnicodeStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public InputStream getBinaryStream(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    InputStream value = null;
    try {
      value = this.resultSet.getBinaryStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return this.resultSet.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    this.resultSet.clearWarnings();
  }

  @Override
  public String getCursorName() throws SQLException {
    return this.resultSet.getCursorName();
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    return this.resultSet.getMetaData();
  }

  @Override
  public Object getObject(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Object value = null;
    try {
      value = this.resultSet.getObject(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Object getObject(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Object value = null;
    try {
      value = this.resultSet.getObject(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public int findColumn(String columnLabel) throws SQLException {
    return this.resultSet.findColumn(columnLabel);
  }

  @Override
  public Reader getCharacterStream(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Reader value = null;
    try {
      value = this.resultSet.getCharacterStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Reader getCharacterStream(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Reader value = null;
    try {
      value = this.resultSet.getCharacterStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public BigDecimal getBigDecimal(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    BigDecimal value = null;
    try {
      value = this.resultSet.getBigDecimal(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    BigDecimal value = null;
    try {
      value = this.resultSet.getBigDecimal(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public boolean isBeforeFirst() throws SQLException {
    return this.resultSet.isBeforeFirst();
  }

  @Override
  public boolean isAfterLast() throws SQLException {
    return this.resultSet.isAfterLast();
  }

  @Override
  public boolean isFirst() throws SQLException {
    return this.resultSet.isFirst();
  }

  @Override
  public boolean isLast() throws SQLException {
    return this.resultSet.isLast();
  }

  @Override
  public void beforeFirst() throws SQLException {
    this.resultSet.beforeFirst();
  }

  @Override
  public void afterLast() throws SQLException {
    this.resultSet.afterLast();
  }

  @Override
  public boolean first() throws SQLException {
    return this.resultSet.first();
  }

  @Override
  public boolean last() throws SQLException {
    return this.resultSet.last();
  }

  @Override
  public int getRow() throws SQLException {
    return this.resultSet.getRow();
  }

  @Override
  public boolean absolute(int row) throws SQLException {
    return this.resultSet.absolute(row);
  }

  @Override
  public boolean relative(int rows) throws SQLException {
    return this.resultSet.relative(rows);
  }

  @Override
  public boolean previous() throws SQLException {
    return this.resultSet.previous();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    this.resultSet.setFetchDirection(direction);
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return this.resultSet.getFetchDirection();
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    this.resultSet.setFetchSize(rows);
  }

  @Override
  public int getFetchSize() throws SQLException {
    return this.resultSet.getFetchSize();
  }

  @Override
  public int getType() throws SQLException {
    return this.resultSet.getType();
  }

  @Override
  public int getConcurrency() throws SQLException {
    return this.resultSet.getConcurrency();
  }

  @Override
  public boolean rowUpdated() throws SQLException {
    return this.resultSet.rowUpdated();
  }

  @Override
  public boolean rowInserted() throws SQLException {
    return this.resultSet.rowInserted();
  }

  @Override
  public boolean rowDeleted() throws SQLException {
    return this.resultSet.rowDeleted();
  }

  @Override
  public void updateNull(int columnLabel) throws SQLException {
    this.resultSet.updateNull(columnLabel);
  }

  @Override
  public void updateBoolean(int columnLabel, boolean x) throws SQLException {
    this.resultSet.updateBoolean(columnLabel, x);
  }

  @Override
  public void updateByte(int columnLabel, byte x) throws SQLException {
    this.resultSet.updateByte(columnLabel, x);
  }

  @Override
  public void updateShort(int columnLabel, short x) throws SQLException {
    this.resultSet.updateShort(columnLabel, x);
  }

  @Override
  public void updateInt(int columnLabel, int x) throws SQLException {
    this.resultSet.updateInt(columnLabel, x);
  }

  @Override
  public void updateLong(int columnLabel, long x) throws SQLException {
    this.resultSet.updateLong(columnLabel, x);
  }

  @Override
  public void updateFloat(int columnLabel, float x) throws SQLException {
    this.resultSet.updateFloat(columnLabel, x);
  }

  @Override
  public void updateDouble(int columnLabel, double x) throws SQLException {
    this.resultSet.updateDouble(columnLabel, x);
  }

  @Override
  public void updateBigDecimal(int columnLabel, BigDecimal x) throws SQLException {
    this.resultSet.updateBigDecimal(columnLabel, x);
  }

  @Override
  public void updateString(int columnLabel, String x) throws SQLException {
    this.resultSet.updateString(columnLabel, x);
  }

  @Override
  public void updateBytes(int columnLabel, byte[] x) throws SQLException {
    this.resultSet.updateBytes(columnLabel, x);
  }

  @Override
  public void updateDate(int columnLabel, Date x) throws SQLException {
    this.resultSet.updateDate(columnLabel, x);
  }

  @Override
  public void updateTime(int columnLabel, Time x) throws SQLException {
    this.resultSet.updateTime(columnLabel, x);
  }

  @Override
  public void updateTimestamp(int columnLabel, Timestamp x) throws SQLException {
    this.resultSet.updateTimestamp(columnLabel, x);
  }

  @Override
  public void updateAsciiStream(int columnLabel, InputStream x, int length) throws SQLException {
    this.resultSet.updateAsciiStream(columnLabel, x);
  }

  @Override
  public void updateBinaryStream(int columnLabel, InputStream x, int length) throws SQLException {
    this.resultSet.updateBinaryStream(columnLabel, x);
  }

  @Override
  public void updateCharacterStream(int columnLabel, Reader x, int length) throws SQLException {
    this.resultSet.updateCharacterStream(columnLabel, x);
  }

  @Override
  public void updateObject(int columnLabel, Object x, int scaleOrLength) throws SQLException {
    this.resultSet.updateObject(columnLabel, x);
  }

  @Override
  public void updateObject(int columnLabel, Object x) throws SQLException {
    this.resultSet.updateObject(columnLabel, x);
  }

  @Override
  public void updateNull(String columnLabel) throws SQLException {
    this.resultSet.updateNull(columnLabel);
  }

  @Override
  public void updateBoolean(String columnLabel, boolean x) throws SQLException {
    this.resultSet.updateBoolean(columnLabel, x);
  }

  @Override
  public void updateByte(String columnLabel, byte x) throws SQLException {
    this.resultSet.updateByte(columnLabel, x);
  }

  @Override
  public void updateShort(String columnLabel, short x) throws SQLException {
    this.resultSet.updateShort(columnLabel, x);
  }

  @Override
  public void updateInt(String columnLabel, int x) throws SQLException {
    this.resultSet.updateInt(columnLabel, x);
  }

  @Override
  public void updateLong(String columnLabel, long x) throws SQLException {
    this.resultSet.updateLong(columnLabel, x);
  }

  @Override
  public void updateFloat(String columnLabel, float x) throws SQLException {
    this.resultSet.updateFloat(columnLabel, x);
  }

  @Override
  public void updateDouble(String columnLabel, double x) throws SQLException {
    this.resultSet.updateDouble(columnLabel, x);
  }

  @Override
  public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
    this.resultSet.updateBigDecimal(columnLabel, x);
  }

  @Override
  public void updateString(String columnLabel, String x) throws SQLException {
    this.resultSet.updateString(columnLabel, x);
  }

  @Override
  public void updateBytes(String columnLabel, byte[] x) throws SQLException {
    this.resultSet.updateBytes(columnLabel, x);
  }

  @Override
  public void updateDate(String columnLabel, Date x) throws SQLException {
    this.resultSet.updateDate(columnLabel, x);
  }

  @Override
  public void updateTime(String columnLabel, Time x) throws SQLException {
    this.resultSet.updateTime(columnLabel, x);
  }

  @Override
  public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
    this.resultSet.updateTimestamp(columnLabel, x);
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
    this.resultSet.updateAsciiStream(columnLabel, x, length);
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, int length)
      throws SQLException {
    this.resultSet.updateBinaryStream(columnLabel, x, length);
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, int length)
      throws SQLException {
    this.resultSet.updateCharacterStream(columnLabel, reader, length);
  }

  @Override
  public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
    this.resultSet.updateObject(columnLabel, x, scaleOrLength);
  }

  @Override
  public void updateObject(String columnLabel, Object x) throws SQLException {
    this.resultSet.updateObject(columnLabel, x);
  }

  @Override
  public void insertRow() throws SQLException {
    this.resultSet.insertRow();
  }

  @Override
  public void updateRow() throws SQLException {
    this.resultSet.updateRow();
  }

  @Override
  public void deleteRow() throws SQLException {
    this.resultSet.deleteRow();
  }

  @Override
  public void refreshRow() throws SQLException {
    this.resultSet.refreshRow();
  }

  @Override
  public void cancelRowUpdates() throws SQLException {
    this.resultSet.cancelRowUpdates();
  }

  @Override
  public void moveToInsertRow() throws SQLException {
    this.resultSet.moveToInsertRow();
  }

  @Override
  public void moveToCurrentRow() throws SQLException {
    this.resultSet.moveToCurrentRow();
  }

  @Override
  public Statement getStatement() throws SQLException {
    return this.resultSet.getStatement();
  }

  @Override
  public Object getObject(int columnLabel, Map<String, Class<?>> map) throws SQLException {
    Profiler p = new Profiler();
    Object value = null;
    try {
      value = this.resultSet.getObject(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Ref getRef(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Ref value = null;
    try {
      value = this.resultSet.getRef(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Blob getBlob(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Blob value = null;
    try {
      value = this.resultSet.getBlob(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Clob getClob(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Clob value = null;
    try {
      value = this.resultSet.getClob(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Array getArray(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Array value = null;
    try {
      value = this.resultSet.getArray(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
    Profiler p = new Profiler();
    Object value = null;
    try {
      value = this.resultSet.getObject(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Ref getRef(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Ref value = null;
    try {
      value = this.resultSet.getRef(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Blob getBlob(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Blob value = null;
    try {
      value = this.resultSet.getBlob(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Clob getClob(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Clob value = null;
    try {
      value = this.resultSet.getClob(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Array getArray(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Array value = null;
    try {
      value = this.resultSet.getArray(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Date getDate(int columnLabel, Calendar cal) throws SQLException {
    Profiler p = new Profiler();
    Date value = null;
    try {
      value = this.resultSet.getDate(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Date getDate(String columnLabel, Calendar cal) throws SQLException {
    Profiler p = new Profiler();
    Date value = null;
    try {
      value = this.resultSet.getDate(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Time getTime(int columnLabel, Calendar cal) throws SQLException {
    Profiler p = new Profiler();
    Time value = null;
    try {
      value = this.resultSet.getTime(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Time getTime(String columnLabel, Calendar cal) throws SQLException {
    Profiler p = new Profiler();
    Time value = null;
    try {
      value = this.resultSet.getTime(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Timestamp getTimestamp(int columnLabel, Calendar cal) throws SQLException {
    Profiler p = new Profiler();
    Timestamp value = null;
    try {
      value = this.resultSet.getTimestamp(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
    Profiler p = new Profiler();
    Timestamp value = null;
    try {
      value = this.resultSet.getTimestamp(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public URL getURL(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    URL value = null;
    try {
      value = this.resultSet.getURL(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public URL getURL(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    URL value = null;
    try {
      value = this.resultSet.getURL(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public void updateRef(int columnLabel, Ref x) throws SQLException {
    this.resultSet.updateRef(columnLabel, x);
  }

  @Override
  public void updateRef(String columnLabel, Ref x) throws SQLException {
    this.resultSet.updateRef(columnLabel, x);
  }

  @Override
  public void updateBlob(int columnLabel, Blob x) throws SQLException {
    this.resultSet.updateBlob(columnLabel, x);
  }

  @Override
  public void updateBlob(String columnLabel, Blob x) throws SQLException {
    this.resultSet.updateBlob(columnLabel, x);
  }

  @Override
  public void updateClob(int columnLabel, Clob x) throws SQLException {
    this.resultSet.updateClob(columnLabel, x);
  }

  @Override
  public void updateClob(String columnLabel, Clob x) throws SQLException {
    this.resultSet.updateClob(columnLabel, x);
  }

  @Override
  public void updateArray(int columnLabel, Array x) throws SQLException {
    this.resultSet.updateArray(columnLabel, x);
  }

  @Override
  public void updateArray(String columnLabel, Array x) throws SQLException {
    this.resultSet.updateArray(columnLabel, x);
  }

  @Override
  public RowId getRowId(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    RowId value = null;
    try {
      value = this.resultSet.getRowId(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public RowId getRowId(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    RowId value = null;
    try {
      value = this.resultSet.getRowId(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public void updateRowId(int columnLabel, RowId x) throws SQLException {
    this.resultSet.updateRowId(columnLabel, x);
  }

  @Override
  public void updateRowId(String columnLabel, RowId x) throws SQLException {
    this.resultSet.updateRowId(columnLabel, x);
  }

  @Override
  public int getHoldability() throws SQLException {
    return this.resultSet.getHoldability();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return this.resultSet.isClosed();
  }

  @Override
  public void updateNString(int columnLabel, String nString) throws SQLException {
    this.resultSet.updateNString(columnLabel, nString);
  }

  @Override
  public void updateNString(String columnLabel, String nString) throws SQLException {
    this.resultSet.updateNString(columnLabel, nString);
  }

  @Override
  public void updateNClob(int columnLabel, NClob nClob) throws SQLException {
    this.resultSet.updateNClob(columnLabel, nClob);
  }

  @Override
  public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
    this.resultSet.updateNClob(columnLabel, nClob);
  }

  @Override
  public NClob getNClob(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    NClob value = null;
    try {
      value = this.resultSet.getNClob(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public NClob getNClob(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    NClob value = null;
    try {
      value = this.resultSet.getNClob(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public SQLXML getSQLXML(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    SQLXML value = null;
    try {
      value = this.resultSet.getSQLXML(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public SQLXML getSQLXML(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    SQLXML value = null;
    try {
      value = this.resultSet.getSQLXML(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public void updateSQLXML(int columnLabel, SQLXML xmlObject) throws SQLException {
    this.resultSet.updateSQLXML(columnLabel, xmlObject);
  }

  @Override
  public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
    this.resultSet.updateSQLXML(columnLabel, xmlObject);
  }

  @Override
  public String getNString(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    String value = null;
    try {
      value = this.resultSet.getNString(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public String getNString(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    String value = null;
    try {
      value = this.resultSet.getNString(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Reader getNCharacterStream(int columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Reader value = null;
    try {
      value = this.resultSet.getNCharacterStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public Reader getNCharacterStream(String columnLabel) throws SQLException {
    Profiler p = new Profiler();
    Reader value = null;
    try {
      value = this.resultSet.getNCharacterStream(columnLabel);
      return value;
    } catch (SQLException e) {
      context.setSqlError(e);
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context
          .getListener()
          .onAfterResultSetGet(this, columnLabel, value, elapsed, context.getSqlError());
    }
  }

  @Override
  public void updateNCharacterStream(int columnLabel, Reader x, long length) throws SQLException {
    this.resultSet.updateNCharacterStream(columnLabel, x, length);
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader, long length)
      throws SQLException {
    this.resultSet.updateNCharacterStream(columnLabel, reader, length);
  }

  @Override
  public void updateAsciiStream(int columnLabel, InputStream x, long length) throws SQLException {
    this.resultSet.updateAsciiStream(columnLabel, x, length);
  }

  @Override
  public void updateBinaryStream(int columnLabel, InputStream x, long length) throws SQLException {
    this.resultSet.updateBinaryStream(columnLabel, x, length);
  }

  @Override
  public void updateCharacterStream(int columnLabel, Reader x, long length) throws SQLException {
    this.resultSet.updateCharacterStream(columnLabel, x, length);
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, long length)
      throws SQLException {
    this.resultSet.updateAsciiStream(columnLabel, x, length);
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, long length)
      throws SQLException {
    this.resultSet.updateBinaryStream(columnLabel, x, length);
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, long length)
      throws SQLException {
    this.resultSet.updateCharacterStream(columnLabel, reader, length);
  }

  @Override
  public void updateBlob(int columnLabel, InputStream inputStream, long length)
      throws SQLException {
    this.resultSet.updateBlob(columnLabel, inputStream, length);
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream, long length)
      throws SQLException {
    this.resultSet.updateBlob(columnLabel, inputStream, length);
  }

  @Override
  public void updateClob(int columnLabel, Reader reader, long length) throws SQLException {
    this.resultSet.updateClob(columnLabel, reader, length);
  }

  @Override
  public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
    this.resultSet.updateClob(columnLabel, reader, length);
  }

  @Override
  public void updateNClob(int columnLabel, Reader reader, long length) throws SQLException {
    this.resultSet.updateNClob(columnLabel, reader, length);
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
    this.resultSet.updateNClob(columnLabel, reader, length);
  }

  @Override
  public void updateNCharacterStream(int columnLabel, Reader x) throws SQLException {
    this.resultSet.updateNCharacterStream(columnLabel, x);
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
    this.resultSet.updateNCharacterStream(columnLabel, reader);
  }

  @Override
  public void updateAsciiStream(int columnLabel, InputStream x) throws SQLException {
    this.resultSet.updateAsciiStream(columnLabel, x);
  }

  @Override
  public void updateBinaryStream(int columnLabel, InputStream x) throws SQLException {
    this.resultSet.updateBinaryStream(columnLabel, x);
  }

  @Override
  public void updateCharacterStream(int columnLabel, Reader x) throws SQLException {
    this.resultSet.updateCharacterStream(columnLabel, x);
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
    this.resultSet.updateAsciiStream(columnLabel, x);
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
    this.resultSet.updateBinaryStream(columnLabel, x);
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
    this.resultSet.updateCharacterStream(columnLabel, reader);
  }

  @Override
  public void updateBlob(int columnLabel, InputStream inputStream) throws SQLException {
    this.resultSet.updateBlob(columnLabel, inputStream);
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
    this.resultSet.updateBlob(columnLabel, inputStream);
  }

  @Override
  public void updateClob(int columnLabel, Reader reader) throws SQLException {
    this.resultSet.updateClob(columnLabel, reader);
  }

  @Override
  public void updateClob(String columnLabel, Reader reader) throws SQLException {
    this.resultSet.updateClob(columnLabel, reader);
  }

  @Override
  public void updateNClob(int columnLabel, Reader reader) throws SQLException {
    this.resultSet.updateNClob(columnLabel, reader);
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader) throws SQLException {
    this.resultSet.updateNClob(columnLabel, reader);
  }

  @Override
  public <T> T getObject(int columnLabel, Class<T> type) throws SQLException {
    return this.resultSet.getObject(columnLabel, type);
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return this.resultSet.getObject(columnLabel, type);
  }
}
