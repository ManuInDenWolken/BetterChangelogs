package net.navrix.betterchangelogs;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.AllArgsConstructor;
import net.navrix.betterchangelogs.core.cache.changelog.ChangelogCache;
import net.navrix.betterchangelogs.core.changelog.ChangelogService;
import net.navrix.betterchangelogs.core.changelog.impl.DefaultChangelogService;
import net.navrix.betterchangelogs.repository.changelog.ChangelogRepository;

@AllArgsConstructor
public class BetterChangelogsModule extends AbstractModule {

    private BetterChangelogsPlugin plugin;

    static BetterChangelogsModule withPlugin(BetterChangelogsPlugin plugin) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null");
        return new BetterChangelogsModule(plugin);
    }

    @Override
    protected void configure() { }

    @Provides
    public ChangelogService provideChangelogService() {
        ChangelogRepository repository = null;
        ChangelogCache cache = null;
        return DefaultChangelogService.create(cache, repository);
    }

}
