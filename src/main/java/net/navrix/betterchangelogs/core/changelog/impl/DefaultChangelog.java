package net.navrix.betterchangelogs.core.changelog.impl;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.navrix.betterchangelogs.core.changelog.Changelog;
import org.bukkit.Location;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DefaultChangelog implements Changelog {

    private int key;
    private String name;
    private Optional<String> optionalName;
    private Date dateOfCreation;
    private Location location;

    public static DefaultChangelog create(int id, String name, Location location) {
        Preconditions.checkNotNull(name, "Name may not be null");
        Preconditions.checkNotNull(location, "Location may not be null");
        return new DefaultChangelog(id, name, Optional.absent(), new Date(), location);
    }

    public static DefaultChangelog create(int id, String name, String optionalName, Location location) {
        Preconditions.checkNotNull(name, "Name may not be null");
        Preconditions.checkNotNull(optionalName);
        Preconditions.checkNotNull(location, "Location may not be null");
        return new DefaultChangelog(id, name, Optional.of(optionalName), new Date(), location);
    }

}
