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

import io.github.drawmoon.saber.engine.ExpressionIterator;
import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A placeholder for all fields in a table.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class AsteriskExpression implements Field {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private final Table table;

  /**
   * Returns an instance of {@link AsteriskExpression} with the {@link Table} and {@link Field}
   * collections.
   *
   * @param table the table, not null
   */
  public AsteriskExpression(@CheckForNull Table table) {
    this.table = checkNotNull(table);
  }

  // -----------------------------------------------------------------------
  @CheckForNull
  public List<Field> getFields() {
    return this.table.getFields();
  }

  // -----------------------------------------------------------------------
  @Override
  public String getAlias() {
    return null;
  }

  @Nullable @Override
  public Table getTable() {
    return this.table;
  }

  @Override
  @Nonnull
  public Field as(String alias) {
    return this;
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitAsterisk(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.empty();
  }
}
