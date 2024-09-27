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
import com.google.common.collect.Iterables;
import io.github.drawmoon.saber.common.Sequence;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Represents a RubikCube object which models a collection of metadata tables, fields, and
 * relations. It provides methods to manage SQL dialects, retrieve metadata information, and perform
 * graph-based operations.
 *
 * <p>The RubikCube class supports hierarchical structures by allowing the creation of child cubes
 * that inherit properties from a parent cube.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class RubikCube implements Catalog<MetaTable, MetaField>, Serializable, Cloneable {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private RubikCube parent;
  private SqlDialect dialect;
  private DialectVersion version;
  private List<MetaTable> tables;
  private List<MetaField> fields;
  private List<MetaRelation> relations;

  @JsonIgnore private boolean prepared;

  @JsonIgnore
  private final Graph<MetaTable, DefaultEdge> tbGraph =
      new DefaultDirectedGraph<>(DefaultEdge.class);

  /**
   * Constructs a new Rubik's Cube with default settings.
   *
   * <p>This constructor initializes a new RubikCube object with default properties. It can be used
   * to create a basic Rubik's Cube without any specific configurations.
   */
  public RubikCube() {}

  /**
   * Creates a new instance of {@link RubikCube} with the specified SQL dialect.
   *
   * @param dialect the SQL dialect to use for this RubikCube instance
   * @throws NullPointerException if the provided dialect is null
   */
  public RubikCube(SqlDialect dialect) {
    this.dialect = checkNotNull(dialect);
    this.parent = null;
  }

  /**
   * Creates a new instance of {@link RubikCube} as a child of the specified parent RubikCube.
   *
   * @param parent the parent RubikCube instance
   * @throws NullPointerException if the provided parent is null
   */
  public RubikCube(RubikCube parent) {
    this.parent = checkNotNull(parent);
    this.dialect = parent.getDialect();
  }

  /**
   * Prepares the RubikCube instance by initializing internal structures such as the table graph.
   */
  public void prepare() {
    if (prepared) {
      return;
    }

    if (parent != null) {
      parent.prepare();
    }
    initGraph();

    prepared = true;
  }

  /**
   * Gets the SQL dialect used by this RubikCube instance.
   *
   * @return the SQL dialect
   */
  @Override
  public SqlDialect getDialect() {
    return dialect;
  }

  /**
   * Sets the SQL dialect for this RubikCube instance.
   *
   * @param dialect the SQL dialect to set
   */
  public void setDialect(SqlDialect dialect) {
    this.dialect = dialect;
  }

  /**
   * Gets the version of the SQL dialect used by this RubikCube instance.
   *
   * @return the dialect version
   */
  @Override
  public DialectVersion getVersion() {
    return version;
  }

  /**
   * Sets the version of the SQL dialect for this RubikCube instance.
   *
   * @param version the dialect version to set
   */
  public void setVersion(DialectVersion version) {
    this.version = version;
  }

  /**
   * Gets the parent RubikCube instance if this instance is a child.
   *
   * @return the parent RubikCube or null if this is a root instance
   */
  public RubikCube getParent() {
    return parent;
  }

  /**
   * Sets the parent Rubik's Cube for this cube.
   *
   * <p>This method assigns the provided RubikCube object as the parent of the current cube. The
   * parent-child relationship is used to establish the hierarchical structure of nested cubes.
   *
   * @param parent The RubikCube instance that will be set as the parent of this cube. Can be null
   *     if this cube does not have a parent.
   */
  public void setParent(RubikCube parent) {
    this.parent = parent;
  }

  /**
   * Returns the count of metadata tables in this RubikCube.
   *
   * @return the number of tables
   */
  @Override
  public int tableCount() {
    return tables == null ? 0 : tables.size();
  }

  /**
   * Retrieves a list of all metadata tables in this RubikCube, including inherited tables from the
   * parent.
   *
   * @return a list of metadata tables
   */
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

  /**
   * Retrieves a metadata table by its unique identifier.
   *
   * @param id the unique identifier of the table
   * @return the metadata table or null if not found
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  @Override
  public MetaTable getTableById(String id) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a metadata table by its name.
   *
   * @param name the name of the table
   * @return the metadata table or null if not found
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  @Override
  public MetaTable getTableByName(String name) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a metadata table by one of its fields.
   *
   * @param field the metadata field
   * @return the metadata table or null if not found
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  @Override
  public MetaTable getTableByField(MetaField field) {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns the count of metadata fields in this RubikCube.
   *
   * @return the number of fields
   */
  @Override
  public int fieldCount() {
    return fields == null ? 0 : fields.size();
  }

  /**
   * Retrieves a list of all metadata fields in this RubikCube, including inherited fields from the
   * parent.
   *
   * @return a list of metadata fields
   */
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

  /**
   * Retrieves a metadata field by its unique identifier.
   *
   * @param id the unique identifier of the field
   * @return the metadata field or null if not found
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  @Override
  public MetaField getFieldById(String id) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a metadata field by its name.
   *
   * @param name the name of the field
   * @return the metadata field or null if not found
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  @Override
  public MetaField getFieldByName(String name) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a list of metadata fields associated with a specific table.
   *
   * @param table the metadata table
   * @return a list of metadata fields
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  @Override
  public List<MetaField> getFieldByTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns the count of metadata relations in this RubikCube.
   *
   * @return the number of relations
   */
  public int relationCount() {
    return relations == null ? 0 : relations.size();
  }

  /**
   * Retrieves a list of all metadata relations in this RubikCube, including inherited relations
   * from the parent.
   *
   * @return a list of metadata relations
   */
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

  /**
   * Retrieves a metadata relation by its unique identifier.
   *
   * @param id the unique identifier of the relation
   * @return the metadata relation or null if not found
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  public MetaRelation getRelationById(String id) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a list of metadata relations associated with a specific table.
   *
   * @param table the metadata table
   * @return a list of metadata relations
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  public List<MetaRelation> getRelationByTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a list of metadata relations where the specified table is on the left-hand side.
   *
   * @param table the metadata table
   * @return a list of metadata relations
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  public List<MetaRelation> getRelationByLeftTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a list of metadata relations where the specified table is on the right-hand side.
   *
   * @param table the metadata table
   * @return a list of metadata relations
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  public List<MetaRelation> getRelationByRightTable(MetaTable table) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves a metadata relation between two specified tables.
   *
   * @param lhd the left-hand side metadata table
   * @param rhd the right-hand side metadata table
   * @return the metadata relation or null if not found
   * @throws UnsupportedOperationException always thrown as this method is not implemented
   */
  public MetaRelation getRelationByLeftAndRight(MetaTable lhd, MetaTable rhd) {
    throw new UnsupportedOperationException();
  }

  /**
   * Sets the list of metadata tables for this RubikCube instance.
   *
   * @param tables the list of metadata tables to set
   */
  public void setTables(List<MetaTable> tables) {
    this.tables = tables;
  }

  /**
   * Sets the list of metadata fields for this RubikCube instance.
   *
   * @param fields the list of metadata fields to set
   */
  public void setFields(List<MetaField> fields) {
    this.fields = fields;
  }

  /**
   * Sets the list of metadata relations for this RubikCube instance.
   *
   * @param relations the list of metadata relations to set
   */
  public void setRelations(List<MetaRelation> relations) {
    this.relations = relations;
  }

  /**
   * Computes the shortest path between two metadata tables using Dijkstra's algorithm.
   *
   * @param source the source metadata table
   * @param target the target metadata table
   * @return a list of metadata tables representing the shortest path from source to target
   */
  public List<MetaTable> getShortestPath(MetaTable source, MetaTable target) {
    DijkstraShortestPath<MetaTable, DefaultEdge> dijkstraShortestPath =
        new DijkstraShortestPath<>(tbGraph);

    GraphPath<MetaTable, DefaultEdge> path = dijkstraShortestPath.getPath(source, target);
    return path.getVertexList();
  }

  /**
   * Initializes the internal directed graph representing the relationships between metadata tables.
   */
  private void initGraph() {
    Map<String, MetaTable> tableMap = new HashMap<>();
    for (MetaTable table : tables) {
      tbGraph.addVertex(table);
      tableMap.put(table.getId(), table);
    }

    @SuppressWarnings("null")
    MetaRelation first = Iterables.getFirst(relations, null);
    if (first == null) return;

    MetaTable source = tableMap.get(first.getLhsId());
    for (MetaRelation rel : relations) {
      MetaTable target = tableMap.get(rel.getRhsId());

      tbGraph.addEdge(source, target);
      source = target;
    }
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
