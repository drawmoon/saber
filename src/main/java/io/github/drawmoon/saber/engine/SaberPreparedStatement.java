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
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class SaberPreparedStatement extends SaberStatement implements PreparedStatement {

  public SaberPreparedStatement(PreparedStatement statement, SaberConnection connection) {
    super(statement, connection);
  }

  @Override
  public ResultSet executeQuery() throws SQLException {
    context.getListener().onBeforeExecuteQuery(this);

    Profiler p = new Profiler();
    SQLException exception = null;
    try {
      return getPreparedStatement().executeQuery();
    } catch (SQLException e) {
      exception = e;
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecuteQuery(this, elapsed, exception);
    }
  }

  @Override
  public int executeUpdate() throws SQLException {
    context.getListener().onBeforeExecuteUpdate(this);

    Profiler p = new Profiler();
    int rowCount = 0;
    SQLException exception = null;
    try {
      rowCount = getPreparedStatement().executeUpdate();
      return rowCount;
    } catch (SQLException e) {
      exception = e;
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecuteUpdate(this, rowCount, elapsed, exception);
    }
  }

  @Override
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    getPreparedStatement().setNull(parameterIndex, sqlType);
  }

  @Override
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    getPreparedStatement().setBoolean(parameterIndex, x);
  }

  @Override
  public void setByte(int parameterIndex, byte x) throws SQLException {
    getPreparedStatement().setByte(parameterIndex, x);
  }

  @Override
  public void setShort(int parameterIndex, short x) throws SQLException {
    getPreparedStatement().setShort(parameterIndex, x);
  }

  @Override
  public void setInt(int parameterIndex, int x) throws SQLException {
    getPreparedStatement().setInt(parameterIndex, x);
  }

  @Override
  public void setLong(int parameterIndex, long x) throws SQLException {
    getPreparedStatement().setLong(parameterIndex, x);
  }

  @Override
  public void setFloat(int parameterIndex, float x) throws SQLException {
    getPreparedStatement().setFloat(parameterIndex, x);
  }

  @Override
  public void setDouble(int parameterIndex, double x) throws SQLException {
    getPreparedStatement().setDouble(parameterIndex, x);
  }

  @Override
  public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
    getPreparedStatement().setBigDecimal(parameterIndex, x);
  }

  @Override
  public void setString(int parameterIndex, String x) throws SQLException {
    getPreparedStatement().setString(parameterIndex, x);
  }

  @Override
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    getPreparedStatement().setBytes(parameterIndex, x);
  }

  @Override
  public void setDate(int parameterIndex, Date x) throws SQLException {
    getPreparedStatement().setDate(parameterIndex, x);
  }

  @Override
  public void setTime(int parameterIndex, Time x) throws SQLException {
    getPreparedStatement().setTime(parameterIndex, x);
  }

  @Override
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    getPreparedStatement().setTimestamp(parameterIndex, x);
  }

  @Override
  public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
    getPreparedStatement().setAsciiStream(parameterIndex, x);
  }

  @SuppressWarnings("deprecation")
  @Override
  public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
    getPreparedStatement().setUnicodeStream(parameterIndex, x, length);
  }

  @Override
  public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
    getPreparedStatement().setBinaryStream(parameterIndex, x, length);
  }

  @Override
  public void clearParameters() throws SQLException {
    getPreparedStatement().clearParameters();
  }

  @Override
  public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
    getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
  }

  @Override
  public void setObject(int parameterIndex, Object x) throws SQLException {
    getPreparedStatement().setObject(parameterIndex, x);
  }

  @Override
  public boolean execute() throws SQLException {
    context.getListener().onBeforeExecute(this);

    Profiler p = new Profiler();
    SQLException exception = null;
    try {
      return getPreparedStatement().execute();
    } catch (SQLException e) {
      exception = e;
      throw e;
    } finally {
      long elapsed = p.getMillis();
      context.getListener().onAfterExecute(this, elapsed, exception);
    }
  }

  @Override
  public void addBatch() throws SQLException {
    getPreparedStatement().addBatch();
  }

  @Override
  public void setCharacterStream(int parameterIndex, Reader reader, int length)
      throws SQLException {
    getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
  }

  @Override
  public void setRef(int parameterIndex, Ref x) throws SQLException {
    getPreparedStatement().setRef(parameterIndex, x);
  }

  @Override
  public void setBlob(int parameterIndex, Blob x) throws SQLException {
    getPreparedStatement().setBlob(parameterIndex, x);
  }

  @Override
  public void setClob(int parameterIndex, Clob x) throws SQLException {
    getPreparedStatement().setClob(parameterIndex, x);
  }

  @Override
  public void setArray(int parameterIndex, Array x) throws SQLException {
    getPreparedStatement().setArray(parameterIndex, x);
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    return getPreparedStatement().getMetaData();
  }

  @Override
  public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
    getPreparedStatement().setDate(parameterIndex, x, cal);
  }

  @Override
  public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
    getPreparedStatement().setTime(parameterIndex, x, cal);
  }

  @Override
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
    getPreparedStatement().setTimestamp(parameterIndex, x, cal);
  }

  @Override
  public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
    getPreparedStatement().setNull(parameterIndex, sqlType, typeName);
  }

  @Override
  public void setURL(int parameterIndex, URL x) throws SQLException {
    getPreparedStatement().setURL(parameterIndex, x);
  }

  @Override
  public ParameterMetaData getParameterMetaData() throws SQLException {
    return getPreparedStatement().getParameterMetaData();
  }

  @Override
  public void setRowId(int parameterIndex, RowId x) throws SQLException {
    getPreparedStatement().setRowId(parameterIndex, x);
  }

  @Override
  public void setNString(int parameterIndex, String value) throws SQLException {
    getPreparedStatement().setNString(parameterIndex, value);
  }

  @Override
  public void setNCharacterStream(int parameterIndex, Reader value, long length)
      throws SQLException {
    getPreparedStatement().setNCharacterStream(parameterIndex, value, length);
  }

  @Override
  public void setNClob(int parameterIndex, NClob value) throws SQLException {
    getPreparedStatement().setNClob(parameterIndex, value);
  }

  @Override
  public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
    getPreparedStatement().setClob(parameterIndex, reader, length);
  }

  @Override
  public void setBlob(int parameterIndex, InputStream inputStream, long length)
      throws SQLException {
    getPreparedStatement().setBlob(parameterIndex, inputStream, length);
  }

  @Override
  public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
    getPreparedStatement().setNClob(parameterIndex, reader, length);
  }

  @Override
  public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
    getPreparedStatement().setSQLXML(parameterIndex, xmlObject);
  }

  @Override
  public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
      throws SQLException {
    getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
  }

  @Override
  public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
    getPreparedStatement().setAsciiStream(parameterIndex, x, length);
  }

  @Override
  public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
    getPreparedStatement().setBinaryStream(parameterIndex, x, length);
  }

  @Override
  public void setCharacterStream(int parameterIndex, Reader reader, long length)
      throws SQLException {
    getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
  }

  @Override
  public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
    getPreparedStatement().setAsciiStream(parameterIndex, x);
  }

  @Override
  public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
    getPreparedStatement().setBinaryStream(parameterIndex, x);
  }

  @Override
  public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
    getPreparedStatement().setCharacterStream(parameterIndex, reader);
  }

  @Override
  public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
    getPreparedStatement().setNCharacterStream(parameterIndex, value);
  }

  @Override
  public void setClob(int parameterIndex, Reader reader) throws SQLException {
    getPreparedStatement().setClob(parameterIndex, reader);
  }

  @Override
  public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
    getPreparedStatement().setBlob(parameterIndex, inputStream);
  }

  @Override
  public void setNClob(int parameterIndex, Reader reader) throws SQLException {
    getPreparedStatement().setNClob(parameterIndex, reader);
  }

  protected final PreparedStatement getPreparedStatement() {
    return (PreparedStatement) this.statement;
  }
}
