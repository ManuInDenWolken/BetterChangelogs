package net.navrix.betterchangelogs.util.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class YamlConfig implements Config {

    private YamlConfiguration config;
    private File file;

    public static YamlConfig create(File file) throws IOException {
        Preconditions.checkNotNull(file, "File may not be null");
        if (!file.exists()) {
            file.mkdirs();
            file.createNewFile();
        }
        return new YamlConfig(YamlConfiguration.loadConfiguration(file), file);
    }

    @Override
    public <T> void set(String key, T value) {
        config.set(key, value);
    }

    @Override
    public <T> T get(String key, Class clazz) {
        return (T) config.get(key);
    }

    @Override
    public List<String> getKeys() {
        ConfigurationSection section = config.getConfigurationSection("entities");
        List<String> keys = Lists.newArrayList(section.getKeys(false));
        return null;
    }

    @Override
    public void remove(String key) {
        set(key, null);
    }

    @Override
    public void save() throws IOException {
        config.save(file);
    }
}
