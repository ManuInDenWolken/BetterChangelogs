package net.navrix.betterchangelogs.core.changelog;

import com.google.common.base.Optional;

public interface ChangelogService {

    void createOrUpdateChangelog(Changelog changelog);

    Optional<Changelog> getChangelog(int id);

    void deleteChangelog(Changelog changelog);

    void shutdown();

}
