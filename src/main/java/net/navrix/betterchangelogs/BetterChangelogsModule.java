package net.navrix.betterchangelogs;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import net.navrix.betterchangelogs.core.cache.changelog.ChangelogCache;
import net.navrix.betterchangelogs.core.cache.changelog.DefaultChangelogCache;
import net.navrix.betterchangelogs.core.changelog.ChangelogService;
import net.navrix.betterchangelogs.core.changelog.impl.DefaultChangelogService;
import net.navrix.betterchangelogs.repository.changelog.ChangelogRepository;
import net.navrix.betterchangelogs.repository.changelog.MySqlChangelogRepository;
import org.bukkit.configuration.file.FileConfiguration;

import javax.sql.DataSource;
import java.util.Properties;

@AllArgsConstructor
public class BetterChangelogsModule extends AbstractModule {

    private BetterChangelogsPlugin plugin;

    static BetterChangelogsModule withPlugin(BetterChangelogsPlugin plugin) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null");
        return new BetterChangelogsModule(plugin);
    }

    @Override
    protected void configure() { }

    @Singleton
    @Provides
    public ChangelogService provideChangelogService() {
        ChangelogRepository repository = MySqlChangelogRepository.create(generateDataSource());
        ChangelogCache cache = new DefaultChangelogCache();
        return DefaultChangelogService.create(cache, repository);
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
