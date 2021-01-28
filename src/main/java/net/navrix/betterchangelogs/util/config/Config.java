package net.navrix.betterchangelogs.util.config;

import java.io.IOException;
import java.util.List;

public interface Config {

    <T> void set(String key, T value);

    <T> T get(String key, Class clazz);

    List<String> getKeys();

    void remove(String key);

    void save() throws IOException;

}
