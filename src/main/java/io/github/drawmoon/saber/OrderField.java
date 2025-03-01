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
 * Represents a field that can be used for ordering query results.
 *
 * <p>This interface extends the {@link Field} interface and is specifically designed to define
 * fields that can be utilized in the ORDER BY clause of SQL queries. Implementations of this
 * interface should provide methods or properties that allow specifying the sort direction
 * (ascending or descending) and any other relevant sorting criteria.
 *
 * @see Field
 * @author drash
 * @version 1.0
 * @since 2024
 */
public interface OrderField extends Field {}
