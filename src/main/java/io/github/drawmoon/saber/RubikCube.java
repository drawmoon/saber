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

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.drawmoon.saber.common.Sequence;
import java.util.ArrayList;
import java.util.List;

public class RubikCube implements Catalog<MetaTable, MetaField> {

  private SqlDialect dialect;
  private DialectVersion version;
  private List<MetaTable> tables;
  private List<MetaField> fields;
  private List<MetaRelation> relations;

  @JsonIgnore private final RubikCube parent;
  @JsonIgnore private boolean prepared;

  /**
   * Creates a new instance of {@link RubikCube}.
   *
   * @param dialect the SQL dialect to use
   */
  public RubikCube(SqlDialect dialect) {
    this.dialect = checkNotNull(dialect);
    this.parent = null;
  }

  /**
   * Creates a new instance of {@link RubikCube}.
   *
   * @param parent the parent RubikCube
   */
  public RubikCube(RubikCube parent) {
    this.parent = checkNotNull(parent);
    this.dialect = parent.getDialect();
  }

  public void prepare() {
    if (prepared) {
      return;
    }

    prepared = true;
  }

  @Override
  public SqlDialect getDialect() {
    return dialect;
  }

  public void setDialect(SqlDialect dialect) {
    this.dialect = dialect;
  }

  @Override
  public DialectVersion getVersion() {
    return version;
  }

  public void setVersion(DialectVersion version) {
    this.version = version;
  }

  public RubikCube getParent() {
    return parent;
  }

  @Override
  public int tableCount() {
    return tables == null ? 0 : tables.size();
  }

  @Override
  public List<MetaTable> getTables() {
    List<MetaTable> tableList = tables == null ? new ArrayList<>() : tables;

    if (parent != null) {
      parent
          .getTables()
          .forEach(
              t -> {
                if (!Sequence.it(tableList).any(o -> o.getId().equals(t.getId()))) {
                  tableList.add(t);
                }
              });
    }

    return tableList;
  }

  @Override
  public MetaTable getTableById(String id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public MetaTable getTableByName(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public MetaTable getTableByField(MetaField field) {
    throw new UnsupportedOperationException();
  }

  public void setTables(List<MetaTable> tables) {
    this.tables = tables;
  }

  @Override
  public int fieldCount() {
    return fields == null ? 0 : fields.size();
  }

  @Override
  public List<MetaField> getFields() {
    List<MetaField> fieldList = tables == null ? new ArrayList<>() : fields;

    if (parent != null) {
      parent
          .getFields()
          .forEach(
              f -> {
                if (!Sequence.it(fieldList).any(o -> o.getId().equals(f.getId()))) {
                  fieldList.add(f);
                }
              });
    }

    return fieldList;
  }

  @Override
  public MetaField getFieldById(String id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public MetaField getFieldByName(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<MetaField> getFieldByTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  public void setFields(List<MetaField> fields) {
    this.fields = fields;
  }

  public int relationCount() {
    return relations == null ? 0 : relations.size();
  }

  public List<MetaRelation> getRelations() {
    List<MetaRelation> relList = tables == null ? new ArrayList<>() : relations;

    if (parent != null) {
      parent
          .getRelations()
          .forEach(
              r -> {
                if (!Sequence.it(relList).any(o -> o.getId().equals(r.getId()))) {
                  relList.add(r);
                }
              });
    }

    return relList;
  }

  public MetaRelation getRelationById(String id) {
    throw new UnsupportedOperationException();
  }

  public List<MetaRelation> getRelationByTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  public List<MetaRelation> getRelationByLeftTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  public List<MetaRelation> getRelationByRightTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  public MetaRelation getRelationByLeftAndRight(MetaTable lhd, MetaTable rhd) {
    throw new UnsupportedOperationException();
  }

  public void setRelations(List<MetaRelation> relations) {
    this.relations = relations;
  }
}
