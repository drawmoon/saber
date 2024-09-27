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

/** A catalog. */
public interface Catalog<TTable extends Table, TField extends Field> {

  /**
   * Gets the SQL dialect.
   *
   * @return the SQL dialect
   */
  SqlDialect getDialect();

  /**
   * Gets the version.
   *
   * @return the version
   */
  DialectVersion getVersion();

  /**
   * Gets the table count.
   *
   * @return the table count
   */
  int tableCount();

  /**
   * Gets the tables.
   *
   * @return the tables
   */
  List<TTable> getTables();

  /**
   * Gets the table by id.
   *
   * @param id the id
   * @return the table
   */
  TTable getTableById(String id);

  /**
   * Gets the table by name.
   *
   * @param name the name
   * @return the table
   */
  TTable getTableByName(String name);

  /**
   * Gets the table by field.
   *
   * @param field the field
   * @return the table
   */
  TTable getTableByField(TField field);

  /**
   * Gets the field count.
   *
   * @return the field count
   */
  int fieldCount();

  /**
   * Gets the fields.
   *
   * @return the field collection
   */
  List<TField> getFields();

  /**
   * Gets the field by id.
   *
   * @param id the id
   * @return the field
   */
  TField getFieldById(String id);

  /**
   * Gets the field by name.
   *
   * @param name the name
   * @return the field
   */
  TField getFieldByName(String name);

  /**
   * Gets the field by table.
   *
   * @param table the table
   * @return the field collection
   */
  List<TField> getFieldByTable(TTable table);
}
