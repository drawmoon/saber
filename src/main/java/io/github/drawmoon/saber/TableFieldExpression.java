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
import javax.annotation.Nonnull;

/**
 * A table field expression.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class TableFieldExpression implements Field {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private final String name;
  private final Table table;
  private String alias;

  /**
   * Create a new table field expression.
   *
   * @param name the name, not null
   * @param table the table, not null
   */
  public TableFieldExpression(String name, Table table) {
    this.name = checkNotNull(name);
    this.table = checkNotNull(table);
  }

  // -----------------------------------------------------------------------
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getAlias() {
    return this.alias;
  }

  @Override
  @Nonnull
  public Table getTable() {
    return this.table;
  }

  @Override
  @Nonnull
  public Field as(String alias) {
    this.alias = alias;
    return this;
  }

  @Override
  @Nonnull
  public <T> T accept(ExpressionVisitor<T> visitor) {
    return visitor.visitTableField(this);
  }

  @Override
  @Nonnull
  public Iterator<Expression> iterator() {
    return ExpressionIterator.empty();
  }
}
