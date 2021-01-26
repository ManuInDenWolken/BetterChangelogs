package net.navrix.betterchangelogs;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BetterChangelogsModule extends AbstractModule {

    private BetterChangelogsPlugin plugin;

    static BetterChangelogsModule withPlugin(BetterChangelogsPlugin plugin) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null");
        return new BetterChangelogsModule(plugin);
    }

    @Override
    protected void configure() {



    }

}
