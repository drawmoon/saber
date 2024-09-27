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

/**
 * Interface for rendering expressions into a specific type.
 *
 * <p>This interface defines a generic rendering method {@link #render()}, which allows implementing
 * classes to convert expressions into the target type based on specific business logic.
 *
 * @param <T> The type of the rendered result. Implementing classes can specify the concrete return
 *     type.
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface ExpressionRender<T> {

  /**
   * Renders the expression and returns the result.
   *
   * <p>This method is implemented by concrete classes to convert the expression into the specified
   * type.
   *
   * @return The rendered result, with the type specified by the generic parameter T.
   */
  T render();
}
