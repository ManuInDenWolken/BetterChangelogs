package net.navrix.betterchangelogs;

import net.navrix.betterchangelogs.api.centity.ChangelogEntityService;
import net.navrix.betterchangelogs.api.changelog.ChangelogService;
import org.bukkit.Bukkit;

public interface BetterChangelogsApiProvider {

    ChangelogService getChangelogService();

    ChangelogEntityService getChangelogEntityService();

    static BetterChangelogsApiProvider get() {
        return (BetterChangelogsApiProvider) Bukkit.getPluginManager().getPlugin("BetterChangelogs");
    }

}
