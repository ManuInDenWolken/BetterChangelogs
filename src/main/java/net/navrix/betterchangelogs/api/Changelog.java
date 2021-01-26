package net.navrix.betterchangelogs.api;

import com.google.common.base.Optional;
import net.navrix.betterchangelogs.core.cache.KeyedCacheable;
import org.bukkit.Location;

import java.util.Date;

public interface Changelog extends KeyedCacheable<Integer> {

    String getName();
    Optional<String> getOptionalName();
    Date getDateOfCreation();
    Location getLocation();

}
