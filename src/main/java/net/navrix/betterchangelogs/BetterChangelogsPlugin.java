package net.navrix.betterchangelogs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import net.navrix.betterchangelogs.api.centity.ChangelogEntityService;
import net.navrix.betterchangelogs.api.changelog.ChangelogService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterChangelogsPlugin extends JavaPlugin implements BetterChangelogsApiProvider {

    private Injector injector;
    @Getter private ChangelogService changelogService;
    @Getter private ChangelogEntityService changelogEntityService;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        setupAndStartDependencyInjection();

        sendToConsole("&7Plugin enabled successfully.");

    }

    @Override
    public void onDisable() {

        changelogService.shutdown();

        sendToConsole("&7Plugin disabled successfully.");

    }

    private void setupAndStartDependencyInjection() {
        injector = Guice.createInjector(BetterChangelogsModule.withPlugin(this));
        injectMembers(this);
    }

    private <T> void injectMembers(T t) {
        injector.injectMembers(t);
    }

    private void sendToConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(colorText(message));
    }

    private String colorText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
