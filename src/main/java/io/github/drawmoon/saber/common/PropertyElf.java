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
package io.github.drawmoon.saber.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyElf {

  private static final Logger log = LoggerFactory.getLogger(PropertyElf.class);

  private PropertyElf() {}

  public static void setTargetFromProperties(Object target, Properties properties) {
    if (target == null || properties == null) {
      return;
    }

    List<Method> methods = Arrays.asList(target.getClass().getMethods());
    properties.forEach(
        (key, value) -> {
          String propertyName = key.toString();
          if (propertyName.startsWith("hikariConfig.")) {
            return;
          }
          setProperty(target, propertyName, value, methods);
        });
  }

  public static Properties getHikariProperties(Properties properties) {
    if (properties == null) {
      return null;
    }

    Properties hikariProperties = new Properties();
    properties.forEach(
        (key, value) -> {
          String propertyName = key.toString();
          if (propertyName.startsWith("hikariConfig.")) {
            String hikariPropertyName = propertyName.substring(13);
            hikariProperties.put(hikariPropertyName, value);
          }
        });

    return hikariProperties;
  }

  // The following code is derived from Project HikariCP
  // (https://github.com/brettwooldridge/HikariCP)
  // Copyright (C) 2013, 2014 Brett Wooldridge
  //
  // Licensed under the Apache License, Version 2.0 (the "License");
  // you may not use this file except in compliance with the License.
  // You may obtain a copy of the License at
  //
  // http://www.apache.org/licenses/LICENSE-2.0
  //
  // Unless required by applicable law or agreed to in writing, software
  // distributed under the License is distributed on an "AS IS" BASIS,
  // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  // See the License for the specific language governing permissions and
  // limitations under the License.

  private static void setProperty(
      Object target, String propName, Object propValue, List<Method> methods) {
    // use the english locale to avoid the infamous turkish locale bug
    String methodName =
        "set" + propName.substring(0, 1).toUpperCase(Locale.ENGLISH) + propName.substring(1);
    Method writeMethod =
        methods.stream()
            .filter(m -> m.getName().equals(methodName) && m.getParameterCount() == 1)
            .findFirst()
            .orElse(null);

    if (writeMethod == null) {
      String methodName2 = "set" + propName.toUpperCase(Locale.ENGLISH);
      writeMethod =
          methods.stream()
              .filter(m -> m.getName().equals(methodName2) && m.getParameterCount() == 1)
              .findFirst()
              .orElse(null);
    }

    if (writeMethod == null) {
      log.error("Property {} does not exist on target {}", propName, target.getClass());
      throw new RuntimeException(
          String.format("Property %s does not exist on target %s", propName, target.getClass()));
    }

    try {
      Class<?> paramClass = writeMethod.getParameterTypes()[0];
      if (paramClass == int.class) {
        writeMethod.invoke(target, Integer.parseInt(propValue.toString()));
      } else if (paramClass == long.class) {
        writeMethod.invoke(target, Long.parseLong(propValue.toString()));
      } else if (paramClass == short.class) {
        writeMethod.invoke(target, Short.parseShort(propValue.toString()));
      } else if (paramClass == boolean.class || paramClass == Boolean.class) {
        writeMethod.invoke(target, Boolean.parseBoolean(propValue.toString()));
      } else if (paramClass == String.class) {
        writeMethod.invoke(target, propValue.toString());
      } else {
        try {
          log.debug("Try to create a new instance of \"{}\"", propValue);
          writeMethod.invoke(
              target, Class.forName(propValue.toString()).getDeclaredConstructor().newInstance());
        } catch (InstantiationException | ClassNotFoundException e) {
          log.debug(
              "Class \"{}\" not found or could not instantiate it (Default constructor)",
              propValue);
          writeMethod.invoke(target, propValue);
        }
      }
    } catch (Exception e) {
      log.error("Failed to set property {} on target {}", propName, target.getClass(), e);
      throw new RuntimeException(e);
    }
  }
}
