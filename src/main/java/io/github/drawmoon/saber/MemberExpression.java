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

import static com.google.common.base.Preconditions.checkElementIndex;
import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;
import static io.github.drawmoon.saber.common.Preconditions.collectionNullClean;

import io.github.drawmoon.saber.common.Sequence;
import io.github.drawmoon.saber.engine.ExpressionIterator;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * Member expressions consisting of multiple fields.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class MemberExpression implements Field {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private final List<Field> fields;

  /** Create a new empty field expression. */
  public MemberExpression() {
    this(new ArrayList<>());
  }

  /**
   * Create a new field expression.
   *
   * @param fields the fields, not null
   */
  public MemberExpression(List<Field> fields) {
    this.fields = collectionNullClean(fields);
  }

  // -----------------------------------------------------------------------
  /**
   * Gets a field from this set.
   *
   * @param f the field name, not null
   * @return returns the field
   */
  @CheckForNull
  public Field getField(String f) {
    return (Field)
        Sequence.it(this)
            .filter(o -> o instanceof Field)
            .find(o -> f.equals(((Field) o).getName()))
            .orElse(null);
  }

  /**
   * Appends the table.
   *
   * @param f the field, not null
   * @return the new fields, not null
   */
  @Nonnull
  public Field append(@CheckForNull Field f) {
    this.fields.add(checkNotNull(f));
    return this;
  }

  /**
   * Inserts the table.
   *
   * @param index the index
   * @param f the field, not null
   * @throws IndexOutOfBoundsException if index is negative or greater than {@code size()}, or
   *     offset or len are negative
   * @return the new fields, not null
   */
  @Nonnull
  public Field insert(int index, @CheckForNull Field f) {
    checkElementIndex(index, this.fields.size());

    this.fields.add(index, checkNotNull(f));
    return this;
  }

  /**
   * Perform the given action for each {@link Field} until all elements have been processed.
   *
   * @param action the action
   */
  public void forEachField(Consumer<Field> action) {
    checkNotNull(action);
    for (Field f : this.fields) action.accept(f);
  }

  // -----------------------------------------------------------------------
  @Override
  public String getAlias() {
    return null;
  }

  @Override
  @Nonnull
  public Field as(String alias) {
    return this;
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitMember(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.sameAsExpression(fields.toArray());
  }
}
