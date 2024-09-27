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
import static io.github.drawmoon.saber.common.Preconditions.checkNotWhiteSpace;
import static io.github.drawmoon.saber.common.Preconditions.ensureNull;

import io.github.drawmoon.saber.common.Sequence;
import io.github.drawmoon.saber.engine.ExpressionIterator;
import java.io.Serial;
import java.util.*;
import java.util.function.Consumer;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A join expression.
 *
 * <p>A table consisting of two joined tables and possibly a join condition.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class JoinExpression implements Table {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private final Table lhs;
  private final Table rhs;
  private JoinType type;
  private JoinHint hint;
  private Condition condition;
  private String alias;

  /**
   * Create a new join expression.
   *
   * @param lhs the left hand side table
   * @param rhs the right hand side table
   * @param type the join type
   */
  public JoinExpression(Table lhs, Table rhs, JoinType type) {
    this(lhs, rhs, type, JoinHint.HASH);
  }

  /**
   * Create a new join expression.
   *
   * @param lhs the left hand side table
   * @param rhs the right hand side table
   * @param type the join type
   * @param hint the join hint
   */
  public JoinExpression(Table lhs, Table rhs, JoinType type, JoinHint hint) {
    this(lhs, rhs, type, hint, null);
  }

  /**
   * Create a new join expression.
   *
   * @param lhs the left hand side table
   * @param rhs the right hand side table
   * @param type the join type
   * @param hint the join hint
   * @param condition the join condition
   */
  public JoinExpression(Table lhs, Table rhs, JoinType type, JoinHint hint, Condition condition) {
    this.lhs = lhs;
    this.rhs = rhs;
    this.type = type;
    this.hint = hint;
    this.condition = condition;
  }

  // -----------------------------------------------------------------------
  /**
   * Perform the given action for each {@link Table} until all elements have been processed.
   *
   * @param action the action
   */
  public void forEachTable(Consumer<Table> action) {
    checkNotNull(action);
    Consumer<Table> forEachSide =
        (t) -> {
          if (t instanceof JoinExpression) {
            ((JoinExpression) t).forEachTable(action);
          } else {
            action.accept(t);
          }
        };

    forEachSide.accept(this.lhs);
    forEachSide.accept(this.rhs);
  }

  // -----------------------------------------------------------------------
  @Override
  public String getName() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getAlias() {
    return this.alias;
  }

  @Override
  @CheckForNull
  public Schema<Table, Field> getSchema() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int fieldCount() {
    return this.lhs.fieldCount() + this.rhs.fieldCount();
  }

  @Override
  @CheckForNull
  public List<Field> getFields() {
    HashSet<Field> fields = new HashSet<>(this.fieldCount());
    Optional.ofNullable(lhs.getFields()).ifPresent(fields::addAll);
    Optional.ofNullable(rhs.getFields()).ifPresent(fields::addAll);

    return new ArrayList<>(fields);
  }

  @Override
  @CheckForNull
  public Field getField(String f) {
    checkNotWhiteSpace(f);

    return (Field)
        Sequence.it(this.lhs)
            .filter(o -> o instanceof Field)
            .find(o -> f.equals(((Field) o).getName()))
            .orElseGet(
                () ->
                    Sequence.it(this.rhs)
                        .filter(o -> o instanceof Field)
                        .find(o -> f.equals(((Field) o).getName()))
                        .orElse(null));
  }

  @Override
  @Nonnull
  public Table as(String alias) {
    ensureNull(this.alias);
    checkNotWhiteSpace(alias, "alias cannot be null");

    this.alias = alias;
    return this;
  }

  @Override
  @Nonnull
  public Field asterisk() {
    return new AsteriskExpression(this);
  }

  @Override
  @Nonnull
  public JoinExpression join(Table t, Condition c, JoinType jt, @Nullable JoinHint jh) {
    checkNotNull(t, jt);

    throw new UnsupportedOperationException();
  }

  // -----------------------------------------------------------------------
  @Override
  @Nonnull
  public Table useIndex(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table useIndexForJoin(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table useIndexForOrderBy(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table useIndexForGroupBy(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table ignoreIndex(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table ignoreIndexForJoin(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table ignoreIndexForOrderBy(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table ignoreIndexForGroupBy(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table forceIndex(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table forceIndexForJoin(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table forceIndexForOrderBy(String... i) {
    throw new UnsupportedOperationException();
  }

  @Override
  @Nonnull
  public Table forceIndexForGroupBy(String... i) {
    throw new UnsupportedOperationException();
  }

  // -----------------------------------------------------------------------
  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitJoin(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.sameAsExpression(this);
  }
}
