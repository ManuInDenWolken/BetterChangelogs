package net.navrix.betterchangelogs.core.changelog.impl;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.navrix.betterchangelogs.core.cache.changelog.ChangelogCache;
import net.navrix.betterchangelogs.core.changelog.Changelog;
import net.navrix.betterchangelogs.core.changelog.ChangelogService;
import net.navrix.betterchangelogs.repository.changelog.ChangelogRepository;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultChangelogService implements ChangelogService {

    private ChangelogCache cache;
    private ChangelogRepository repository;

    public static ChangelogService create(ChangelogCache cache, ChangelogRepository repository) {
        Preconditions.checkNotNull(cache, "Cache may not be null");
        Preconditions.checkNotNull(repository, "Repository may not be null");
        return new DefaultChangelogService(cache, repository);
    }

    @Override
    public void createOrUpdateChangelog(Changelog changelog) {
        cache.put(changelog.getKey(), changelog);
    }

    @Override
    public Optional<Changelog> getChangelog(int id) {
        Changelog changelog;
        if (cache.isPresent(id))
            changelog = cache.getIfPresent(id);
        else
            changelog = repository.read(id).orNull();
        return Optional.fromNullable(changelog);
    }

    @Override
    public void deleteChangelog(Changelog changelog) {
        cache.invalidate(changelog.getKey());
        repository.delete(changelog);
    }

    @Override
    public void shutdown() {
        cache.forEach(changelog -> {
            if (repository.read(changelog.getKey()).isPresent()) {
                repository.update(changelog);
            } else {
                repository.create(changelog);
            }
        });
    }
}
