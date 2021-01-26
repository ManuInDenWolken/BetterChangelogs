package net.navrix.betterchangelogs.core.cache.changelog;

import com.google.common.base.Function;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.navrix.betterchangelogs.api.Changelog;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public class DefaultChangelogCache implements ChangelogCache {

    private Map<Integer, Changelog> changelogs = Maps.newHashMap();

    @Override
    public Iterable<Changelog> getAllPresent() {
        return changelogs.values();
    }

    @Override
    public Changelog getIfPresent(Object o) {
        return changelogs.get(o);
    }

    @Override
    public Changelog get(Integer integer, Callable<? extends Changelog> callable) throws ExecutionException {
        return getIfPresent(integer);
    }

    @Override
    public ImmutableMap<Integer, Changelog> getAllPresent(Iterable<?> iterable) {
        ImmutableMap map = Maps.toMap(iterable, (Function<Object, Object>) o -> changelogs.get(o));
        return map;
    }

    @Override
    public void put(Integer integer, Changelog changelog) {
        changelogs.put(integer, changelog);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Changelog> map) {
        changelogs.putAll(map);
    }

    @Override
    public void invalidate(Object o) {
        changelogs.remove(o);
    }

    @Override
    public void invalidateAll(Iterable<?> iterable) {
        iterable.forEach(id -> {
            invalidate(id);
        });
    }

    @Override
    public void invalidateAll() {
        changelogs.clear();
    }

    @Override
    public long size() {
        return changelogs.size();
    }

    @Override
    public CacheStats stats() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConcurrentMap<Integer, Changelog> asMap() {
        return new ConcurrentHashMap<>(changelogs);
    }

    @Override
    public void cleanUp() {
        changelogs.clear();
    }
}
