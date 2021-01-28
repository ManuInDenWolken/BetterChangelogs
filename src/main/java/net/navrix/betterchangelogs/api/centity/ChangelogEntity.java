package net.navrix.betterchangelogs.api.centity;

import net.navrix.betterchangelogs.core.inventory.DefaultPageableInventory;
import org.bukkit.Location;

public interface ChangelogEntity {

    int getId();

    Location getLocation();

    DefaultPageableInventory getInventory();

}
