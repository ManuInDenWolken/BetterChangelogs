package net.navrix.betterchangelogs.core.inventory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.navrix.betterchangelogs.api.inventory.Pageable;
import net.navrix.betterchangelogs.api.inventory.PageableInventory;
import net.navrix.betterchangelogs.core.inventory.appearance.DefaultInventoryAppearance;
import net.navrix.betterchangelogs.api.inventory.InventoryAppearance;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultPageableInventory implements PageableInventory {

    private DefaultPageableInventory previous;
    private DefaultPageableInventory next;
    @Getter private int index;
    @Getter private Inventory inventory;

    public static DefaultPageableInventory create(int inventorySize, String inventoryTitle, InventoryAppearance appearance) {
        DefaultPageableInventory inventory = new DefaultPageableInventory();
        inventory.inventory = Bukkit.createInventory(null, inventorySize, inventoryTitle);
        appearance.apply(inventory.inventory);
        return inventory;
    }

    public static DefaultPageableInventory create(int inventorySize, String inventoryTitle) {
        return create(inventorySize, inventoryTitle, new DefaultInventoryAppearance());
    }

    @Override
    public void addItem(ItemStack item) {
        // inventory.getItem(inventory.getSize()-2) != null
        if (inventory.getContents().length == inventory.getSize()) {
            next = create(inventory.getSize(), inventory.getTitle());
            next.index++;
            next.previous = this;
            next.inventory.addItem(item);
            return;
        }
        inventory.addItem(item);
    }

    @Override
    public Pageable previousPage() {
        return previous;
    }

    @Override
    public Pageable nextPage() {
        return next;
    }

}
