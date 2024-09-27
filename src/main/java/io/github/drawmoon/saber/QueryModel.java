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

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryModel implements Serializable, Cloneable {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private Field field;
  private Filter filter;
  private Sort sort;

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public Sort getSort() {
    return sort;
  }

  public void setSort(Sort sort) {
    this.sort = sort;
  }

  // -----------------------------------------------------------------------
  public void drill() {}

  public void pivot() {}

  public void slice() {}

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  // -----------------------------------------------------------------------
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Field implements Serializable, Cloneable {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Filter implements Serializable, Cloneable {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Sort implements Serializable, Cloneable {
    @Serial private static final long serialVersionUID = -8492251942206794476L;

    private String expression;
    private SortOrder order;

    public String getExpression() {
      return expression;
    }

    public void setExpression(String expression) {
      this.expression = expression;
    }

    public SortOrder getOrder() {
      return order;
    }

    public void setOrder(SortOrder order) {
      this.order = order;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }

  static class StatementBuildResolver {}
}
