package net.navrix.betterchangelogs;

import net.navrix.betterchangelogs.api.changelog.ChangelogService;
import org.bukkit.Bukkit;

public interface BetterChangelogsApiProvider {

    ChangelogService getChangelogService();

    static BetterChangelogsApiProvider get() {
        return (BetterChangelogsApiProvider) Bukkit.getPluginManager().getPlugin("BetterChangelogs");
    }

}
