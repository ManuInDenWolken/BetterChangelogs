package net.navrix.betterchangelogs.repository.entity;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.navrix.betterchangelogs.api.centity.ChangelogEntity;
import net.navrix.betterchangelogs.api.inventory.PageableInventory;
import net.navrix.betterchangelogs.core.centity.DefaultChangelogEntity;
import net.navrix.betterchangelogs.util.config.Config;
import org.bukkit.Location;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultChangelogEntityRepository implements ChangelogEntityRepository {

    private Config config;
    private PageableInventory inventory;

    public static DefaultChangelogEntityRepository create(Config config, PageableInventory inventory) {
        Preconditions.checkNotNull(config, "Config may not be null");
        Preconditions.checkNotNull(inventory, "Inventory may not be null");
        return new DefaultChangelogEntityRepository(config, inventory);
    }

    @Override
    public void create(ChangelogEntity entity) {
        config.set(String.valueOf(entity.getId()), entity.getLocation());
    }

    @Override
    public Optional<ChangelogEntity> read(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(ChangelogEntity entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(ChangelogEntity entity) {
        config.remove(String.valueOf(entity.getId()));
    }

    @Override
    public List<ChangelogEntity> findAll() {
        List<ChangelogEntity> entities = Lists.newArrayList();
        config.getKeys().forEach(key -> {
            int keyAsInt = Integer.parseInt(key);
            entities.add(DefaultChangelogEntity.createAndSpawnVillager(keyAsInt,  config.get(key, Location.class), inventory));
        });
        return entities;
    }

}
