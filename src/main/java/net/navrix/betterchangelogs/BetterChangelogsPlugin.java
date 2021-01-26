package net.navrix.betterchangelogs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.navrix.betterchangelogs.api.ChangelogService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterChangelogsPlugin extends JavaPlugin {

    private Injector injector;
    private ChangelogService service;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        setupAndStartDependencyInjection();

        sendToConsole("&7Plugin enabled successfully.");

    }

    @Override
    public void onDisable() {

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
