package net.navrix.betterchangelogs;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import net.navrix.betterchangelogs.api.centity.ChangelogEntityService;
import net.navrix.betterchangelogs.api.inventory.PageableInventory;
import net.navrix.betterchangelogs.core.cache.changelog.ChangelogCache;
import net.navrix.betterchangelogs.core.cache.changelog.DefaultChangelogCache;
import net.navrix.betterchangelogs.api.changelog.ChangelogService;
import net.navrix.betterchangelogs.core.centity.DefaultChangelogEntityService;
import net.navrix.betterchangelogs.core.changelog.DefaultChangelogService;
import net.navrix.betterchangelogs.repository.changelog.ChangelogRepository;
import net.navrix.betterchangelogs.repository.changelog.MySqlChangelogRepository;
import net.navrix.betterchangelogs.repository.entity.ChangelogEntityRepository;
import net.navrix.betterchangelogs.repository.entity.DefaultChangelogEntityRepository;
import net.navrix.betterchangelogs.util.config.Config;
import net.navrix.betterchangelogs.util.config.YamlConfig;
import org.bukkit.configuration.file.FileConfiguration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@AllArgsConstructor
public final class BetterChangelogsModule extends AbstractModule {

    private BetterChangelogsPlugin plugin;

    static BetterChangelogsModule withPlugin(BetterChangelogsPlugin plugin) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null");
        return new BetterChangelogsModule(plugin);
    }

    @Override
    protected void configure() {

        bind(BetterChangelogsPlugin.class).toInstance(plugin);

    }

    @Singleton
    @Provides
    public ChangelogService provideChangelogService() {
        ChangelogRepository repository = MySqlChangelogRepository.create(generateDataSource());
        ChangelogCache cache = new DefaultChangelogCache();
        return DefaultChangelogService.createAndStart(cache, repository);
    }

    @Singleton
    @Provides
    public ChangelogEntityService provideChangelogEntityService() {
        ChangelogEntityRepository repository = DefaultChangelogEntityRepository.create(getProvider(Config.class).get(),
            getProvider(PageableInventory.class).get());
        return DefaultChangelogEntityService.createAndStart(getProvider(PageableInventory.class).get(), repository);
    }

    @Singleton
    @Provides
    public PageableInventory providePageableInventory() {
        return null;
    }

    @Singleton
    @Provides
    public Config provideChangelogEntityConfig() {
        Config config = null;
        File file = new File("plugins/BetterChangelogs/entities.yml");
        try {
            config = YamlConfig.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    private DataSource generateDataSource() {
        FileConfiguration config = this.plugin.getConfig();
        Properties properties = new Properties();
        properties.setProperty("dataSourceClassName", "com.mysql.jdbc.MysqlConnectionPoolDataSource");
        properties.setProperty("dataSource.serverName", config.getString("database.address"));
        properties.setProperty("dataSource.portNumber", config.getString("database.port"));
        properties.setProperty("dataSource.user", config.getString("database.username"));
        properties.setProperty("dataSource.password", config.getString("database.password"));
        properties.setProperty("dataSource.databaseName", config.getString("database.name"));
        HikariConfig hikariConfig = new HikariConfig(properties);
        return new HikariDataSource(hikariConfig);
    }

}
