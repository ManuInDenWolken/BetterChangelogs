package net.navrix.betterchangelogs.repository.changelog;

import net.navrix.betterchangelogs.api.Changelog;
import net.navrix.betterchangelogs.repository.Repository;

import java.util.List;

public interface ChangelogRepository extends Repository<Integer, Changelog> {

    List<Changelog> findAll();

}
