package net.navrix.betterchangelogs.core.cache.changelog;

import com.google.common.cache.Cache;
import net.navrix.betterchangelogs.api.changelog.Changelog;

import java.util.function.Consumer;

public interface ChangelogCache extends Cache<Integer, Changelog> {

    Iterable<Changelog> getAllPresent();

    default boolean isPresent(Integer id) {
        return getIfPresent(id) != null;
    }

    default void forEach(Consumer<Changelog> consumer) {
        getAllPresent().forEach(consumer);
    }

}
