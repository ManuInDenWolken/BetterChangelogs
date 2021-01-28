package net.navrix.betterchangelogs.core.centity;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.navrix.betterchangelogs.api.centity.ChangelogEntity;
import net.navrix.betterchangelogs.api.centity.ChangelogEntityService;
import net.navrix.betterchangelogs.api.inventory.PageableInventory;
import net.navrix.betterchangelogs.repository.entity.ChangelogEntityRepository;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Map;

public final class DefaultChangelogEntityService implements ChangelogEntityService {

    private Map<Integer, ChangelogEntity> entityMap = Maps.newHashMap();

    private PageableInventory inventory;
    private ChangelogEntityRepository repository;

    private DefaultChangelogEntityService(PageableInventory inventory, ChangelogEntityRepository repository) {
        this.inventory = inventory;
        this.repository = repository;
    }

    public static DefaultChangelogEntityService createAndStart(PageableInventory inventory,
                                                               ChangelogEntityRepository repository) {
        Preconditions.checkNotNull(inventory, "Inventory may not be null");
        Preconditions.checkNotNull(repository, "Repository may not be null");
        DefaultChangelogEntityService service = new DefaultChangelogEntityService(inventory, repository);
        service.start();
        return service;
    }

    @Override
    public void start() {
        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("BetterChangelogs"), () -> {
            repository.findAll().forEach(entity -> entityMap.put(entity.getId(), entity));
        });
    }

    @Override
    public ChangelogEntity createEntity(EntityType type, Location location) {
        throw new NotImplementedException();
    }

    @Override
    public ChangelogEntity createEntity(Location location) {
        ChangelogEntity entity = DefaultChangelogEntity.createAndSpawnVillager(entityMap.size()+1, location, inventory);
        repository.create(entity);
        return entity;
    }

    @Override
    public void deleteEntity(ChangelogEntity entity) {
        entityMap.remove(entity.getId());
        repository.delete(entity);
    }

}
