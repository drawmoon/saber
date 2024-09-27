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

/**
 * A utility class that provides methods to schedule periodic tasks with a fixed delay. This class
 * uses a single-threaded scheduled executor service to manage the execution of tasks.
 *
 * @author drash
 * @version 1.0
 * @since 2024
 */
public class PruneTimer {

  /** The scheduled executor service used to execute periodic tasks. */
  private final ScheduledExecutorService executor;

  /** Constructs a new PruneTimer instance and initializes the scheduled executor service. */
  public PruneTimer() {
    executor = Executors.newSingleThreadScheduledExecutor();
  }

  /**
   * Schedules a periodic task to run with a fixed delay between executions.
   *
   * @param runnable The task to be executed periodically.
   * @param delay The delay between successive executions, in milliseconds.
   * @return A {@link ScheduledFuture} representing pending completion of the task.
   * @throws NullPointerException if the specified runnable is null.
   */
  @CanIgnoreReturnValue
  public ScheduledFuture<?> schedule(Runnable runnable, long delay) {
    return executor.scheduleAtFixedRate(
        checkNotNull(runnable), delay, delay, TimeUnit.MILLISECONDS);
  }

  /**
   * Checks if the executor has been shut down.
   *
   * @return {@code true} if the executor has been shut down; {@code false} otherwise.
   */
  public boolean isShutdown() {
    return executor.isShutdown();
  }

  /**
   * Initiates an orderly shutdown of the executor, allowing running tasks to complete. If the
   * executor does not terminate within 60 seconds, it will be forcefully shut down.
   */
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

  /**
   * Attempts to stop all actively executing tasks and halts the processing of waiting tasks.
   *
   * @return A list of the tasks that were awaiting execution.
   */
  public List<Runnable> shutdownNow() {
    return executor.shutdownNow();
  }
}
