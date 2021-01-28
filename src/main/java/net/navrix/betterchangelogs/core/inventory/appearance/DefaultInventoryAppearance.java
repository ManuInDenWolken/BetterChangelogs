package net.navrix.betterchangelogs.core.inventory.appearance;

import lombok.Setter;
import net.navrix.betterchangelogs.api.inventory.InventoryAppearance;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class DefaultInventoryAppearance implements InventoryAppearance {

    @SuppressWarnings("deprecation")
    @Setter private static ItemStack GLASS_PANE_ITEM = new ItemStack(160, 1, (short) 0, (byte) 7);
    @Setter private static ItemStack PREVIOUS_PAGE_ITEM = new ItemStack(Material.LEVER);
    @Setter private static ItemStack NEXT_PAGE_ITEM = new ItemStack(Material.LEVER);

    @Override
    public void apply(Inventory inventory) {
        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("BetterChangelogs"), () -> {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (i % 8 == 0 || i % 9 == 0 || i < 9 || i > inventory.getSize() - 8) {
                    inventory.setItem(i, GLASS_PANE_ITEM);
                }
            }
        });
        inventory.setItem(inventory.getSize() - 2, NEXT_PAGE_ITEM);
        inventory.setItem(inventory.getSize() - 9, NEXT_PAGE_ITEM);
    }

}
