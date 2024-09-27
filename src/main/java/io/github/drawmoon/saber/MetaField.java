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

import static com.google.common.base.Preconditions.checkState;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serial;
import java.util.Iterator;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaField implements Field {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private String id;
  private String name;
  private String tableId;

  @Nullable private RubikCube rubikCube;
  @Nullable private Field expression;
  private boolean prepared = false;

  public void prepare(RubikCube rubikCube) {
    if (prepared) {
      return;
    }

    this.rubikCube = rubikCube;
    expression = Expressions.field(this);
    prepared = true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getAlias() {
    checkState(prepared, "MetaField not prepared");

    assert expression != null;
    return expression.getAlias();
  }

  @Override
  @Nonnull
  public Field as(String alias) {
    checkState(prepared, "MetaField not prepared");

    assert expression != null;
    return expression.as(alias);
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTableId() {
    return tableId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }

  @Override
  @Nullable public Table getTable() {
    checkState(prepared, "MetaField not prepared");

    assert rubikCube != null;
    return rubikCube.getTableByField(this);
  }

  @Nonnull
  @Override
  public Expression replace(Function<Expression, Expression> replaceFn) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    checkState(prepared, "MetaField not prepared");

    assert expression != null;
    return expression.accept(visitor);
  }

  @Override
  public Iterator<Expression> iterator() {
    checkState(prepared, "MetaField not prepared");

    assert expression != null;
    return expression.iterator();
  }
}
