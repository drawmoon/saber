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

import static io.github.drawmoon.saber.common.Preconditions.checkNotNull;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PruneTimer {

  private final ScheduledExecutorService executor;

  public PruneTimer() {
    executor = Executors.newSingleThreadScheduledExecutor();
  }

  @CanIgnoreReturnValue
  public ScheduledFuture<?> schedule(Runnable runnable, long delay) {
    return executor.scheduleAtFixedRate(
        checkNotNull(runnable), delay, delay, TimeUnit.MILLISECONDS);
  }

  public boolean isShutdown() {
    return executor.isShutdown();
  }

  public void shutdown() {
    executor.shutdown();
    try {
      if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  public List<Runnable> shutdownNow() {
    return executor.shutdownNow();
  }
}
