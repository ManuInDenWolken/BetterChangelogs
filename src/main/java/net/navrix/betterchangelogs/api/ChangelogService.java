package net.navrix.betterchangelogs.api;

import com.google.common.base.Optional;
import net.navrix.betterchangelogs.api.Changelog;

import java.util.List;

public interface ChangelogService {

    void createOrUpdateChangelog(Changelog changelog);

    Optional<Changelog> getChangelog(int id);

    void deleteChangelog(Changelog changelog);

    List<Changelog> getAllChangelogs();

    void shutdown();

}
