package net.navrix.betterchangelogs.core.centity;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.navrix.betterchangelogs.api.centity.ChangelogEntity;
import net.navrix.betterchangelogs.api.inventory.PageableInventory;
import net.navrix.betterchangelogs.core.inventory.DefaultPageableInventory;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultChangelogEntity implements ChangelogEntity {

    private static final EntityType DEFAULT_TYPE = EntityType.VILLAGER;
    private static final String ITEMIZER_TAG = "changelogNpc";

    @Getter private int id;
    @Setter @Getter private String customName;
    @Setter @Getter private boolean customNameVisibility;
    @Getter private final PageableInventory inventory;
    private final LivingEntity entity;

    static DefaultChangelogEntity createAndSpawn(int id, EntityType type, Location location, String customName,
                                                 boolean customNameVisibility, PageableInventory inventory) {
        Preconditions.checkNotNull(type, "EntityType may not be null");
        Preconditions.checkNotNull(location, "Location may not be null");
        Preconditions.checkNotNull(customName, "Name may not be null");
        Preconditions.checkNotNull(inventory, "Inventory may not be null");

        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        DefaultChangelogEntity itemizer = new DefaultChangelogEntity(id, customName, customNameVisibility, inventory, entity);
        itemizer.setupEntity();
        return itemizer;
    }

    public static DefaultChangelogEntity createAndSpawnVillager(int id, Location location, PageableInventory inventory) {
        return createAndSpawn(id, DEFAULT_TYPE, location, "", false, inventory);
    }

    protected final void setupEntity() {
        PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, Integer.MAX_VALUE,
            false, false);
        entity.addPotionEffect(effect);
        entity.setMetadata(ITEMIZER_TAG, null);
        entity.setCustomName(ChatColor.translateAlternateColorCodes('&', customName));
        entity.setCustomNameVisible(customNameVisibility);
    }

    @Override
    public Location getLocation() {
        return entity.getLocation();
    }

    void remove() {
        entity.remove();
    }

    public static boolean isChangelogItemizer(LivingEntity entity) {
        return entity.hasMetadata(ITEMIZER_TAG);
    }

}
