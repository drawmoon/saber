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

import io.github.drawmoon.saber.common.Profiler;
import io.github.drawmoon.saber.common.PruneTimer;
import io.github.drawmoon.saber.exceptions.EngineException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DataSourceManager implements AutoCloseable {

  private final Map<Integer, StoreItem> store;
  private final PruneTimer pruneTimer;
  private final SaberOptions options;
  private final SaberEventListener listener;

  public DataSourceManager(SaberOptions options, SaberEventListener listener) {
    this.store = new ConcurrentHashMap<>();
    this.pruneTimer = new PruneTimer();
    this.options = checkNotNull(options);
    this.listener = checkNotNull(listener);

    // prune every hour
    pruneTimer.schedule(() -> this.prune(false), TimeUnit.HOURS.toMillis(1));
  }

  public SaberDataSource get(RequestContext context) {
    listener.onBeforeGetDataSource(this, options, checkNotNull(context));

    String connectionUri = checkNotNull(context.getConnectionUri());
    SaberDataSource dataSource = null;

    Profiler p = new Profiler();
    Exception exception = null;
    try {
      StoreItem item = store.get(connectionUri.hashCode());
      if (item != null) {
        dataSource = item.get();
        dataSource.setContext(context);
      } else {
        dataSource = new SaberDataSource(context);

        long ttl = TimeUnit.DAYS.toMillis(1); // TODO: configurable
        item = new StoreItem(dataSource, ttl);
        store.put(connectionUri.hashCode(), item);
      }
    } catch (Exception e) {
      exception = e;
      throw e;
    } finally {
      long elapsed = p.getMillis();
      listener.onAfterGetDataSource(this, options, context, dataSource, elapsed, exception);
    }

    return dataSource;
  }

  public void remove(String connectionUri) {
    int hash = checkNotNull(connectionUri).hashCode();
    StoreItem item = store.remove(hash);
    if (item != null) {
      try {
        item.close();
      } catch (Exception e) {
        throw new EngineException("Attempt to remove datasource fails", e);
      }
    }
  }

  public void prune(boolean force) {
    store.forEach(
        (hash, item) -> {
          if (force || item.isExpired()) {
            try {
              item.close();
            } catch (Exception expected) {
              // expected, just continue
            }
          }
        });
  }

  @Override
  public void close() throws Exception {
    try {
      pruneTimer.shutdown();
    } catch (Exception expected) {
      // safely close
    }
    try {
      prune(true);
    } catch (Exception expected) {
      // safely close
    }
  }

  static class StoreItem implements AutoCloseable {
    private final SaberDataSource item;
    private final long ttl;
    private volatile long lastAccessTime;

    public StoreItem(SaberDataSource item, long ttl) {
      this.item = checkNotNull(item);
      this.ttl = ttl;
      this.lastAccessTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
      return System.currentTimeMillis() - lastAccessTime > ttl;
    }

    public SaberDataSource get() {
      lastAccessTime = System.currentTimeMillis();
      return item;
    }

    @Override
    public void close() throws Exception {
      try {
        item.close();
      } catch (Exception expected) {
        // safely close
      }
    }
  }
}
