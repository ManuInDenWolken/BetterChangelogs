package net.navrix.betterchangelogs.api.inventory;

import org.bukkit.inventory.ItemStack;

public interface PageableInventory extends PageableInventoryHolder {

    void addItem(ItemStack item);

}
