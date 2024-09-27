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
import io.github.drawmoon.saber.common.Sequence;
import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaTable implements Table {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private String id;
  private String name;

  @Nullable private RubikCube rubikCube;
  @Nullable private Table expression;
  private boolean prepared = false;

  public void prepare(RubikCube rubikCube) {
    if (prepared) {
      return;
    }

    this.rubikCube = rubikCube;
    expression = Expressions.table(this);
    prepared = true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getAlias() {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.getAlias();
  }

  @Override
  public Schema<Table, Field> getSchema() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int fieldCount() {
    return getFields().size();
  }

  @Override
  public List<Field> getFields() {
    checkState(prepared, "MetaTable not prepared");

    assert rubikCube != null;
    List<MetaField> fields = rubikCube.getFieldByTable(this);
    return Sequence.it(fields).map(f -> (Field) f).toList();
  }

  @Override
  public Field getField(String f) {
    checkState(prepared, "MetaTable not prepared");

    assert rubikCube != null;
    List<MetaField> fields = rubikCube.getFieldByTable(this);
    return Sequence.it(fields).filter(o -> f.equals(o.getName())).firstOrNull();
  }

  @Override
  @Nonnull
  public Table as(String alias) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.as(alias);
  }

  @Override
  @Nonnull
  public Field asterisk() {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.asterisk();
  }

  @Override
  @Nonnull
  public Table join(Table t, Condition c, JoinType jt, @Nullable JoinHint jh) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.join(t, c, jt, jh);
  }

  @Override
  @Nonnull
  public Table useIndex(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.useIndex(i);
  }

  @Override
  @Nonnull
  public Table useIndexForJoin(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.useIndexForJoin(i);
  }

  @Override
  @Nonnull
  public Table useIndexForOrderBy(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.useIndexForOrderBy(i);
  }

  @Override
  @Nonnull
  public Table useIndexForGroupBy(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.useIndexForGroupBy(i);
  }

  @Override
  @Nonnull
  public Table ignoreIndex(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.ignoreIndex(i);
  }

  @Override
  @Nonnull
  public Table ignoreIndexForJoin(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.ignoreIndexForJoin(i);
  }

  @Override
  @Nonnull
  public Table ignoreIndexForOrderBy(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.ignoreIndexForOrderBy(i);
  }

  @Override
  @Nonnull
  public Table ignoreIndexForGroupBy(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.ignoreIndexForGroupBy(i);
  }

  @Override
  @Nonnull
  public Table forceIndex(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.forceIndex(i);
  }

  @Override
  @Nonnull
  public Table forceIndexForJoin(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.forceIndexForJoin(i);
  }

  @Override
  @Nonnull
  public Table forceIndexForOrderBy(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.forceIndexForOrderBy(i);
  }

  @Override
  @Nonnull
  public Table forceIndexForGroupBy(String... i) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.forceIndexForGroupBy(i);
  }

  @Nonnull
  @Override
  public Expression replace(Function<Expression, Expression> replaceFn) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.accept(visitor);
  }

  public void forEachField(Consumer<? super MetaField> action) {
    checkState(prepared, "MetaTable not prepared");

    assert rubikCube != null;
    List<MetaField> fields = rubikCube.getFieldByTable(this);
    if (fields == null || fields.isEmpty()) return;

    fields.forEach(action);
  }

  @Override
  public Iterator<Expression> iterator() {
    checkState(prepared, "MetaTable not prepared");

    assert expression != null;
    return expression.iterator();
  }
}
