package net.navrix.betterchangelogs.core.changelog;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.navrix.betterchangelogs.core.cache.changelog.ChangelogCache;
import net.navrix.betterchangelogs.api.changelog.Changelog;
import net.navrix.betterchangelogs.api.changelog.ChangelogService;
import net.navrix.betterchangelogs.repository.changelog.ChangelogRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultChangelogService implements ChangelogService {

    private ChangelogCache cache;
    private ChangelogRepository repository;

    public static ChangelogService createAndStart(ChangelogCache cache, ChangelogRepository repository) {
        Preconditions.checkNotNull(cache, "Cache may not be null");
        Preconditions.checkNotNull(repository, "Repository may not be null");
        DefaultChangelogService service = new DefaultChangelogService(cache, repository);
        service.start();
        return service;
    }

    private void start() {
        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("BetterChangelogs"), () -> {
            repository.findAll().forEach(changelog -> cache.put(changelog.getKey(), changelog));
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "All changelogs loaded!");
        });
    }

    @Override
    public Changelog createChangelog(String name, String optionalName, Location location) {
        Changelog changelog = DefaultChangelog.create((int) (cache.size()+1), name, optionalName, location);
        cache.put(changelog.getKey(), changelog);
        return changelog;
    }

    @Override
    public Changelog createChangelog(String name, Location location) {
        Changelog changelog = DefaultChangelog.create((int) (cache.size()+1), name, location);
        cache.put(changelog.getKey(), changelog);
        return changelog;
    }

    @Override
    public Optional<Changelog> getChangelog(int id) {
        Changelog changelog = null;
        if (cache.isPresent(id))
            changelog = cache.getIfPresent(id);
        return Optional.fromNullable(changelog);
    }

    @Override
    public void updateChangelog(Changelog changelog) {
        cache.put(changelog.getKey(), changelog);
    }

    @Override
    public void deleteChangelog(Changelog changelog) {
        cache.invalidate(changelog.getKey());
        repository.delete(changelog);
    }

    @Override
    public List<Changelog> getAllChangelogs() {
        return StreamSupport.stream(cache.getAllPresent().spliterator(), true).collect(Collectors.toList());
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
