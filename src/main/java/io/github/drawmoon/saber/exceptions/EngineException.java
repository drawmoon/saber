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
package io.github.drawmoon.saber.exceptions;

import com.google.common.base.Strings;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class EngineException extends SaberException {

  private static final long serialVersionUID = -8492251942206794476L;

  private SqlStateType sqlState;
  private int maxCauseLookups = 256;

  public EngineException(String message) {
    super(message);
  }

  public EngineException(Throwable cause) {
    super(cause);
  }

  public EngineException(String message, Throwable cause) {
    super(message, cause);
  }

  // The following code is derived from Project JOOQ (https://github.com/jOOQ/jOOQ)
  // Licensed under the Apache License, Version 2.0 (the "License");
  // you may not use this file except in compliance with the License.
  // You may obtain a copy of the License at
  //
  //  https://www.apache.org/licenses/LICENSE-2.0
  //
  // Unless required by applicable law or agreed to in writing, software
  // distributed under the License is distributed on an "AS IS" BASIS,
  // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  // See the License for the specific language governing permissions and
  // limitations under the License.

  public SQLException getSqlException() {
    return getCause(SQLException.class);
  }

  /**
   * Decode the {@link SQLException#getSQLState()} into {@link SqlStateType#getSqlState()}.
   *
   * @return the SQL state
   */
  @Nonnull
  public String getSqlState() {
    String sqlState = null;
    SQLException s = getCause(SQLException.class);
    if (s != null) sqlState = s.getSQLState();

    if (sqlState == null) sqlState = "00000";
    return sqlState;
  }

  /**
   * Decode the {@link SQLException#getSQLState()} into {@link SqlStateType}.
   *
   * @return the {@link SqlStateType} object
   */
  public SqlStateType getSqlStateType() {
    if (sqlState == null) sqlState = getSqlStateType(getSqlException());
    return sqlState;
  }

  /**
   * Decode the {@link SQLException#getSQLState()} into {@link SqlStateType}.
   *
   * @param e the exception to be examined
   * @return the {@link SqlStateType} object
   */
  @Nonnull
  public SqlStateType getSqlStateType(SQLException e) {
    if (e.getSQLState() != null) return SqlStateType.fromCode(e.getSQLState());
    else return SqlStateType.UNKNOWN;
  }

  /**
   * Find a root cause of a given type, or <code>null</code> if no root cause of that type was
   * found.
   *
   * @param <T> the type to be searched for
   * @param type the type to be searched for
   * @return the root cause of the given type, or <code>null</code> if no root cause of that type
   */
  @CheckForNull
  public <T extends Throwable> T getCause(Class<? extends T> type) {
    return getCause(this, type);
  }

  /**
   * Find a root cause of a given type, or <code>null</code> if no root cause of that type was
   * found.
   *
   * @param <T> the type to be searched for
   * @param t the throwable to be examined
   * @param type the type to be searched for
   * @return the root cause of the given type, or <code>null</code> if no root cause of that type
   */
  @SuppressWarnings("unchecked")
  @CheckForNull
  public <T extends Throwable> T getCause(Throwable t, Class<? extends T> type) {
    Throwable next = t.getCause();
    Throwable prev;

    for (int i = 0; i < maxCauseLookups; i++) {
      if (next == null) return null;

      if (type.isInstance(next)) return (T) next;

      prev = next;
      next = next.getCause();

      // Don't trust exceptions to respect the default behaviour of Throwable.getCause()
      if (prev == next) return null;
    }

    return null;
  }

  public void setMaxCauseLookups(int maxCauseLookups) {
    this.maxCauseLookups = maxCauseLookups;
  }

  /**
   * The type of the SQL state as specified by the SQL:2011 standard, or by individual vendors.
   *
   * <ul>
   *   <li>00: Successful completion
   *   <li>01: Warning
   *   <li>02: No data
   *   <li>07: Dynamic SQL Error
   *   <li>08: Connection exception
   *   <li>09: Triggered action exception
   *   <li>0A: Feature not supported
   *   <li>0D: Invalid target type specification
   *   <li>0E: Invalid schema name list specification
   *   <li>0F: Locator exception
   *   <li>0L: Invalid grantor
   *   <li>0M: Invalid SQL-invoked procedure reference
   *   <li>0P: Invalid role specification
   *   <li>0S: Invalid transform group name specification
   *   <li>0T: Target table disagrees with cursor specification
   *   <li>0U: Attempt to assign to non-updatable column
   *   <li>0V: Attempt to assign to ordering column
   *   <li>0W: Prohibited statement encountered during trigger execution
   *   <li>0Z: Diagnostics exception
   *   <li>21: Cardinality violation
   *   <li>22: Data exception
   *   <li>23: Integrity constraint violation
   *   <li>24: Invalid cursor state
   *   <li>25: Invalid transaction state
   *   <li>26: Invalid SQL statement name
   *   <li>27: Triggered data change violation
   *   <li>28: Invalid authorization specification
   *   <li>2B: Dependent privilege descriptors still exist
   *   <li>2C: Invalid character set name
   *   <li>2D: Invalid transaction termination
   *   <li>2E: Invalid connection name
   *   <li>2F: SQL routine exception
   *   <li>2H: Invalid collation name
   *   <li>30: Invalid SQL statement identifier
   *   <li>33: Invalid SQL descriptor name
   *   <li>34: Invalid cursor name
   *   <li>35: Invalid condition number
   *   <li>36: Cursor sensitivity exception
   *   <li>38: External routine exception
   *   <li>39: External routine invocation exception
   *   <li>3B: Savepoint exception
   *   <li>3C: Ambiguous cursor name
   *   <li>3D: Invalid catalog name
   *   <li>3F: Invalid schema name
   *   <li>40: Transaction rollback
   *   <li>42: Syntax error or access rule violation
   *   <li>44: With check option violation
   *   <li>HZ: Remote database access
   * </ul>
   */
  public enum SqlStateType {
    C00_SUCCESSFUL_COMPLETION("00"),
    C01_WARNING("01"),
    C02_NO_DATA("02"),
    C07_DYNAMIC_SQL_ERROR("07"),
    C08_CONNECTION_EXCEPTION("08"),
    C09_TRIGGERED_ACTION_EXCEPTION("09"),
    C0A_FEATURE_NOT_SUPPORTED("0A"),
    C0D_INVALID_TARGET_TYPE_SPECIFICATION("0D"),
    C0E_INVALID_SCHEMA_NAME_LIST_SPECIFICATION("0E"),
    C0F_LOCATOR_EXCEPTION("0F"),
    C0L_INVALID_GRANTOR("0L"),
    C0M_INVALID_SQL_INVOKED_PROCEDURE_REFERENCE("0M"),
    C0P_INVALID_ROLE_SPECIFICATION("0P"),
    C0S_INVALID_TRANSFORM_GROUP_NAME_SPECIFICATION("0S"),
    C0T_TARGET_TABLE_DISAGREES_WITH_CURSOR_SPECIFICATION("0T"),
    C0U_ATTEMPT_TO_ASSIGN_TO_NON_UPDATABLE_COLUMN("0U"),
    C0V_ATTEMPT_TO_ASSIGN_TO_ORDERING_COLUMN("0V"),
    C0W_PROHIBITED_STATEMENT_ENCOUNTERED_DURING_TRIGGER_EXECUTION("0W"),
    C0Z_DIAGNOSTICS_EXCEPTION("0Z"),
    C21_CARDINALITY_VIOLATION("21"),
    C22_DATA_EXCEPTION("22"),
    C23_INTEGRITY_CONSTRAINT_VIOLATION("23"),
    C24_INVALID_CURSOR_STATE("24"),
    C25_INVALID_TRANSACTION_STATE("25"),
    C26_INVALID_SQL_STATEMENT_NAME("26"),
    C27_TRIGGERED_DATA_CHANGE_VIOLATION("27"),
    C28_INVALID_AUTHORIZATION_SPECIFICATION("28"),
    C2B_DEPENDENT_PRIVILEGE_DESCRIPTORS_STILL_EXIST("2B"),
    C2C_INVALID_CHARACTER_SET_NAME("2C"),
    C2D_INVALID_TRANSACTION_TERMINATION("2D"),
    C2E_INVALID_CONNECTION_NAME("2E"),
    C2F_SQL_ROUTINE_EXCEPTION("2F"),
    C2H_INVALID_COLLATION_NAME("2H"),
    C30_INVALID_SQL_STATEMENT_IDENTIFIER("30"),
    C33_INVALID_SQL_DESCRIPTOR_NAME("33"),
    C34_INVALID_CURSOR_NAME("34"),
    C35_INVALID_CONDITION_NUMBER("35"),
    C36_CURSOR_SENSITIVITY_EXCEPTION("36"),
    C38_EXTERNAL_ROUTINE_EXCEPTION("38"),
    C39_EXTERNAL_ROUTINE_INVOCATION_EXCEPTION("39"),
    C3B_SAVEPOINT_EXCEPTION("3B"),
    C3C_AMBIGUOUS_CURSOR_NAME("3C"),
    C3D_INVALID_CATALOG_NAME("3D"),
    C3F_INVALID_SCHEMA_NAME("3F"),
    C40_TRANSACTION_ROLLBACK("40"),
    C42_SYNTAX_ERROR_OR_ACCESS_RULE_VIOLATION("42"),
    CHZ_REMOTE_DATABASE_ACCESS("HZ"),

    OTHER(""),
    UNKNOWN("");

    private final String sqlState;

    SqlStateType(String sqlState) {
      this.sqlState = sqlState;
    }

    public String getSqlState() {
      return sqlState;
    }

    @Nonnull
    public static SqlStateType fromCode(String code) {
      if (Strings.isNullOrEmpty(code) || code.length() < 2) return SqlStateType.OTHER;

      Optional<SqlStateType> first =
          Arrays.stream(values()).filter(x -> x.sqlState.equals(code.substring(0, 2))).findFirst();
      return first.orElse(SqlStateType.OTHER);
    }
  }
}
