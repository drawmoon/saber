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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;

/**
 * An iterator that iterates over {@link Expression} objects. This class extends {@link
 * AbstractIterator} to provide a fail-fast iterator for expressions. It can be used to iterate over
 * expressions from various sources such as lists or fields of an object.
 */
public class ExpressionIterator extends AbstractIterator<Expression> {

  /** The underlying iterator that provides the elements to be iterated over. */
  protected Iterator<Expression> itr;

  /**
   * Constructs an ExpressionIterator with the given iterator.
   *
   * @param itr The iterator to wrap.
   */
  private ExpressionIterator(Iterator<Expression> itr) {
    this.itr = itr;
  }

  /**
   * Returns an empty ExpressionIterator.
   *
   * @return An empty ExpressionIterator.
   */
  public static ExpressionIterator empty() {
    return new Empty();
  }

  /**
   * Creates an ExpressionIterator from the fields of the given object. This method checks all
   * declared fields of the provided object and collects any fields that are instances of {@link
   * Expression} or lists containing {@link Expression} objects.
   *
   * @param o The object whose fields will be inspected.
   * @return An ExpressionIterator containing the expressions found in the object's fields.
   * @throws NullPointerException if the provided object is null.
   */
  public static ExpressionIterator sameAsExpression(@CheckForNull Object o) {
    checkNotNull(o);
    List<Expression> list = new ArrayList<>();
    try {
      Class<?> clazz = o.getClass();
      for (Field f : clazz.getDeclaredFields()) {
        Class<?> fClazz = f.getType();
        if (Expression.class.isAssignableFrom(fClazz)) {
          list.add((Expression) f.get(o));
        }
        if (List.class.isAssignableFrom(fClazz)) {
          List<?> fl = (List<?>) f.get(o);
          for (Object e : fl) {
            if (e instanceof Expression) list.add((Expression) e);
          }
        }
      }
    } catch (IllegalArgumentException | IllegalAccessException e) {
      // Handle exceptions if necessary
    }

    return list.isEmpty() ? empty() : new ExpressionIterator(list.iterator());
  }

  /**
   * Computes the next element in the iteration.
   *
   * @return The next element in the iteration, or {@code endOfData()} if there are no more
   *     elements.
   */
  @CheckForNull
  @Override
  protected Expression computeNext() {
    if (!itr.hasNext()) return endOfData();
    return itr.next();
  }

  /** A static inner class representing an empty ExpressionIterator. */
  static final class Empty extends ExpressionIterator {
    /** Constructs an empty ExpressionIterator. */
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
