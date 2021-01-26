package net.navrix.betterchangelogs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterChangelogsPlugin extends JavaPlugin {

    private Injector injector;

    @Override
    public void onEnable() {

        setupAndStartDependencyInjection();

        sendToConsole("&7Plugin enabled successfully.");

    }

    @Override
    public void onDisable() {

        sendToConsole("&7Plugin disabled successfully.");

    }

    private void setupAndStartDependencyInjection() {
        injector = Guice.createInjector(BetterChangelogsModule.withPlugin(this));
        injector.injectMembers(this);
    }

    private void sendToConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(colorText(message));
    }

    private String colorText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
