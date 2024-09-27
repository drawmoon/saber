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

import java.util.List;

/**
 * Represents a catalog that manages tables and fields within a database system. This interface
 * provides methods to interact with the catalog, including retrieving SQL dialect information,
 * accessing tables and fields, and querying metadata.
 *
 * @param <TTable> The type of table objects managed by this catalog.
 * @param <TField> The type of field objects managed by this catalog.
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface Catalog<TTable extends Table, TField extends Field> {

  /**
   * Retrieves the SQL dialect used by this catalog.
   *
   * @return The {@link SqlDialect} instance representing the SQL dialect.
   */
  SqlDialect getDialect();

  /**
   * Retrieves the version of the SQL dialect used by this catalog.
   *
   * @return The {@link DialectVersion} instance representing the dialect version.
   */
  DialectVersion getVersion();

  /**
   * Returns the total number of tables in the catalog.
   *
   * @return The count of tables.
   */
  int tableCount();

  /**
   * Retrieves a list of all tables in the catalog.
   *
   * @return A list of {@link TTable} objects.
   */
  List<TTable> getTables();

  /**
   * Retrieves a table by its unique identifier.
   *
   * @param id The unique identifier of the table.
   * @return The {@link TTable} object corresponding to the given ID, or null if not found.
   */
  TTable getTableById(String id);

  /**
   * Retrieves a table by its name.
   *
   * @param name The name of the table.
   * @return The {@link TTable} object corresponding to the given name, or null if not found.
   */
  TTable getTableByName(String name);

  /**
   * Retrieves a table by one of its fields.
   *
   * @param field The field associated with the table.
   * @return The {@link TTable} object containing the specified field, or null if not found.
   */
  TTable getTableByField(TField field);

  /**
   * Returns the total number of fields in the catalog.
   *
   * @return The count of fields.
   */
  int fieldCount();

  /**
   * Retrieves a list of all fields in the catalog.
   *
   * @return A list of {@link TField} objects.
   */
  List<TField> getFields();

  /**
   * Retrieves a field by its unique identifier.
   *
   * @param id The unique identifier of the field.
   * @return The {@link TField} object corresponding to the given ID, or null if not found.
   */
  TField getFieldById(String id);

  /**
   * Retrieves a field by its name.
   *
   * @param name The name of the field.
   * @return The {@link TField} object corresponding to the given name, or null if not found.
   */
  TField getFieldByName(String name);

  /**
   * Retrieves a list of fields associated with a specific table.
   *
   * @param table The table whose fields are to be retrieved.
   * @return A list of {@link TField} objects belonging to the specified table.
   */
  List<TField> getFieldByTable(TTable table);
}
