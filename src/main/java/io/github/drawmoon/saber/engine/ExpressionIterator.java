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

import com.google.common.collect.AbstractIterator;
import io.github.drawmoon.saber.Expression;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;

public class ExpressionIterator extends AbstractIterator<Expression> {

  protected Iterator<Expression> itr;

  private ExpressionIterator(Iterator<Expression> itr) {
    this.itr = itr;
  }

  public static ExpressionIterator empty() {
    return new Empty();
  }

  public static ExpressionIterator sameAsExpression(@CheckForNull Object... o) {
    List<Expression> list = new ArrayList<>();
    for (Object i : checkNotNull(o)) {
      if (i instanceof Expression) list.add((Expression) i);
      if (i instanceof List) {
        for (Object e : (List<?>) i) {
          if (e instanceof Expression) list.add((Expression) e);
        }
      }
    }

    return list.isEmpty() ? empty() : new ExpressionIterator(list.iterator());
  }

  @CheckForNull
  @Override
  protected Expression computeNext() {
    if (!itr.hasNext()) return endOfData();
    return itr.next();
  }

  static final class Empty extends ExpressionIterator {
    private Empty() {
      super(
          new Iterator<Expression>() {
            @Override
            public boolean hasNext() {
              return false;
            }

            @Override
            public Expression next() {
              throw new NoSuchElementException();
            }
          });
    }
  }
}
