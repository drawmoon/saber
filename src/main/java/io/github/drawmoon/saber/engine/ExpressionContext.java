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
package io.github.drawmoon.saber.engine;

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.github.drawmoon.saber.Catalog;
import io.github.drawmoon.saber.Field;
import io.github.drawmoon.saber.Keyword;
import io.github.drawmoon.saber.QueryModel;
import io.github.drawmoon.saber.SqlDialect;
import io.github.drawmoon.saber.Table;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ExpressionContext {

  private final StringBuilder sqlBuilder = new StringBuilder();
  private final Catalog<? extends Table, ? extends Field> catalog;
  private final SaberOptions options;

  @Nullable private final QueryModel model;
  @Nullable private ExpressionContext superCtx;
  @Nullable private ExpressionContext subCtx;

  public ExpressionContext(
      Catalog<? extends Table, ? extends Field> catalog, SaberOptions options) {
    this.catalog = checkNotNull(catalog);
    this.options = checkNotNull(options);
    this.model = null;
  }

  public ExpressionContext(
      Catalog<? extends Table, ? extends Field> catalog, SaberOptions options, QueryModel model) {
    this.catalog = checkNotNull(catalog);
    this.options = checkNotNull(options);
    this.model = checkNotNull(model);
  }

  // -----------------------------------------------------------------------
  public SqlDialect getDialect() {
    return catalog.getDialect();
  }

  public SaberEventListener getListener() {
    return options.getListener();
  }

  public QueryModel getModel() {
    return model;
  }

  public ExpressionContext getSuperCtx() {
    return superCtx;
  }

  public ExpressionContext getSubCtx() {
    return subCtx;
  }

  public ExpressionContext createSubCtx() {
    ExpressionContext subCtx = new ExpressionContext(catalog, options);
    subCtx.superCtx = this;

    this.subCtx = subCtx;
    return subCtx;
  }

  public BasicSqlBuilder createSqlBuilder() {
    return new BasicSqlBuilder(this); // TODO: 根据 SqlDialect 创建对应的 SqlBuilder
  }

  @Nonnull
  @CanIgnoreReturnValue
  public ExpressionContext writeKeyword(Keyword keyword) {
    this.writeSql(keyword.upper()); // TODO: 根据 DSLOptions 确定使用大写还是小写
    return this;
  }

  @Nonnull
  @CanIgnoreReturnValue
  public ExpressionContext writeSql(String sql) {
    return this.autoFormatAppend(sql);
  }

  @Nonnull
  public String render() {
    return this.sqlBuilder.toString();
  }

  // -----------------------------------------------------------------------
  private ExpressionContext autoFormatAppend(String sql) {
    int len = this.sqlBuilder.length();
    if (len != 0 && this.sqlBuilder.charAt(len - 1) != ' ' && !sql.startsWith(" ")) {
      this.sqlBuilder.append(" ");
    }
    this.sqlBuilder.append(sql);
    return this;
  }
}
