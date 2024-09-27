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
import javax.swing.SortOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Qm {

  private QmQuery q;
  private QmFilter f;
  private QmSort s;

  public QmQuery getQ() {
    return q;
  }

  public void setQ(QmQuery q) {
    this.q = q;
  }

  public QmFilter getF() {
    return f;
  }

  public void setF(QmFilter f) {
    this.f = f;
  }

  public QmSort getS() {
    return s;
  }

  public void setS(QmSort s) {
    this.s = s;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class QmQuery {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class QmFilter {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class QmSort {
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
  }

  static class QmStatementBuildResolver {}
}
