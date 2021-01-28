package net.navrix.betterchangelogs.api.changelog;

import com.google.common.base.Optional;
import org.bukkit.Location;

import java.util.List;

public interface ChangelogService {

    Changelog createChangelog(String name, String optionalName, Location location);

    Changelog createChangelog(String name, Location location);

    Optional<Changelog> getChangelog(int id);

    void updateChangelog(Changelog changelog);

    void deleteChangelog(Changelog changelog);

    List<Changelog> getAllChangelogs();

    void shutdown();

}
