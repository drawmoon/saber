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
import java.io.Serializable;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaRelation implements Serializable {

  @Serial private static final long serialVersionUID = -8492251942206794476L;

  private String id;
  private String lhsId;
  private String rhsId;

  @Nullable private RubikCube rubikCube;
  private boolean prepared = false;

  public void prepare(RubikCube rubikCube) {
    if (prepared) {
      return;
    }

    this.rubikCube = rubikCube;
    prepared = true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLhsId() {
    return lhsId;
  }

  public void setLhsId(String lhsId) {
    this.lhsId = lhsId;
  }

  public String getRhsId() {
    return rhsId;
  }

  public void setRhsId(String rhsId) {
    this.rhsId = rhsId;
  }

  public MetaTable getLhs() {
    checkState(prepared, "MetaRelation not prepared");

    assert rubikCube != null;
    return rubikCube.getTableById(lhsId);
  }

  public MetaTable getRhs() {
    checkState(prepared, "MetaRelation not prepared");

    assert rubikCube != null;
    return rubikCube.getTableById(rhsId);
  }
}
