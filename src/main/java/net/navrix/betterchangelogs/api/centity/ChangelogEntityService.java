package net.navrix.betterchangelogs.api.centity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public interface ChangelogEntityService {

    void start();

    ChangelogEntity createEntity(EntityType type, Location location);

    ChangelogEntity createEntity(Location location);

    void deleteEntity(ChangelogEntity entity);

}
