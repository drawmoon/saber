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

/**
 * Represents a schema that defines the structure of a database or data model. A schema typically
 * contains a collection of tables and fields, and it can be associated with a catalog that
 * organizes these tables and fields into a hierarchical structure.
 *
 * @param <TTable> The type of table in the schema.
 * @param <TField> The type of field in the schema.
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface Schema<TTable extends Table, TField extends Field> {

  /**
   * Retrieves the catalog associated with this schema. The catalog provides a higher-level
   * organization for the tables and fields defined in the schema. It can be used to manage and
   * access the schema's components in a structured manner.
   *
   * @return The catalog object that organizes the tables and fields of this schema.
   */
  Catalog<TTable, TField> getCatalog();
}
