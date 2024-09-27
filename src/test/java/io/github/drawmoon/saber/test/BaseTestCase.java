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
package io.github.drawmoon.saber.test;

import com.google.common.collect.Lists;
import io.github.drawmoon.saber.ExpressionRender;
import io.github.drawmoon.saber.RubikCube;
import io.github.drawmoon.saber.SqlDialect;
import java.util.List;
import java.util.function.Function;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class BaseTestCase {

  public <T> void assertExpr(
      Function<RubikCube, ExpressionRender<String>> func,
      Matcher<? super T> matcher,
      SqlDialect[] roots) {
    for (SqlDialect dialect : roots) {
      assertExpr(func, matcher, dialect);
    }
  }

  public <T> void assertExpr(
      Function<RubikCube, ExpressionRender<String>> func,
      Matcher<? super T> matcher,
      SqlDialect root) {
    if (!root.isRoot()) throw new IllegalArgumentException("dialect is not a root");

    List<SqlDialect> branches = Lists.asList(root, root.getBranch());
    for (SqlDialect dialect : branches) {
      RubikCube rubikCube = new RubikCube(dialect);
      String actual = func.apply(rubikCube).render();

      if (!matcher.matches(actual)) {
        Description description = new StringDescription();
        description
            .appendText(System.lineSeparator())
            .appendText(" Dialect: \"" + dialect + "\"")
            .appendText(System.lineSeparator())
            .appendText("Expected: ")
            .appendDescriptionOf(matcher)
            .appendText(System.lineSeparator())
            .appendText("     but: ");
        matcher.describeMismatch(actual, description);

        throw new AssertionError(description.toString());
      }
    }
  }

  public Matcher<String> equalToSqlWithBranch(String expected) {
    return new BaseMatcher<String>() {
      @Override
      public boolean matches(Object actual) {
        if (actual == null) {
          return false;
        }

        return expected.equals(actual.toString());
      }

      @Override
      public void describeTo(Description description) {
        description.appendValue(expected);
      }
    };
  }
}
