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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serial;
import java.io.Serializable;
import javax.swing.SortOrder;

/**
 * QueryModel represents a model for handling query-related operations such as filtering, sorting,
 * and field selection. It also includes methods for advanced data manipulation like drill-down,
 * pivot, and slice operations.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryModel implements Serializable, Cloneable {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  /** The field to be queried. */
  private Field field;

  /** The filter criteria for the query. */
  private Filter filter;

  /** The sort order for the query results. */
  private Sort sort;

  /** The RubikCube object for complex data manipulation. */
  private RubikCube rubikCube;

  /**
   * Gets the field to be queried.
   *
   * @return the field
   */
  public Field getField() {
    return field;
  }

  /**
   * Sets the field to be queried.
   *
   * @param field the field to set
   */
  public void setField(Field field) {
    this.field = field;
  }

  /**
   * Gets the filter criteria for the query.
   *
   * @return the filter
   */
  public Filter getFilter() {
    return filter;
  }

  /**
   * Sets the filter criteria for the query.
   *
   * @param filter the filter to set
   */
  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  /**
   * Gets the sort order for the query results.
   *
   * @return the sort
   */
  public Sort getSort() {
    return sort;
  }

  /**
   * Sets the sort order for the query results.
   *
   * @param sort the sort to set
   */
  public void setSort(Sort sort) {
    this.sort = sort;
  }

  /**
   * Gets the RubikCube object for complex data manipulation.
   *
   * @return the rubikCube
   */
  public RubikCube getRubikCube() {
    return rubikCube;
  }

  /**
   * Sets the RubikCube object for complex data manipulation.
   *
   * @param rubikCube the rubikCube to set
   */
  public void setRubikCube(RubikCube rubikCube) {
    this.rubikCube = rubikCube;
  }

  // -----------------------------------------------------------------------
  /** Performs a drill-down operation on the query. */
  public void drill() {}

  /** Performs a pivot operation on the query. */
  public void pivot() {}

  /** Performs a slice operation on the query. */
  public void slice() {}

  /**
   * Creates and returns a copy of this object.
   *
   * @return a clone of this instance
   * @throws CloneNotSupportedException if the object's class does not support the {@code Cloneable}
   *     interface
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  // -----------------------------------------------------------------------
  /**
   * Represents a field in the query.
   *
   * @author drash
   * @version 1.0
   * @since 2024
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Field implements Serializable, Cloneable {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    /**
     * Creates and returns a copy of this object.
     *
     * @return a clone of this instance
     * @throws CloneNotSupportedException if the object's class does not support the {@code
     *     Cloneable} interface
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  /**
   * Represents filter criteria for the query.
   *
   * @author drash
   * @version 1.0
   * @since 2024
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Filter implements Serializable, Cloneable {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    /**
     * Creates and returns a copy of this object.
     *
     * @return a clone of this instance
     * @throws CloneNotSupportedException if the object's class does not support the {@code
     *     Cloneable} interface
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  /**
   * Represents the sort order for the query results.
   *
   * @author drash
   * @version 1.0
   * @since 2024
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Sort implements Serializable, Cloneable {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    /** The expression used for sorting. */
    private String expression;

    /** The sort order (ascending or descending). */
    private SortOrder order;

    /**
     * Gets the expression used for sorting.
     *
     * @return the expression
     */
    public String getExpression() {
      return expression;
    }

    /**
     * Sets the expression used for sorting.
     *
     * @param expression the expression to set
     */
    public void setExpression(String expression) {
      this.expression = expression;
    }

    /**
     * Gets the sort order.
     *
     * @return the order
     */
    public SortOrder getOrder() {
      return order;
    }

    /**
     * Sets the sort order.
     *
     * @param order the order to set
     */
    public void setOrder(SortOrder order) {
      this.order = order;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return a clone of this instance
     * @throws CloneNotSupportedException if the object's class does not support the {@code
     *     Cloneable} interface
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  /**
   * A helper class for building SQL statements.
   *
   * @author drash
   * @version 1.0
   * @since 2024
   */
  static class StatementBuildResolver {}
}
