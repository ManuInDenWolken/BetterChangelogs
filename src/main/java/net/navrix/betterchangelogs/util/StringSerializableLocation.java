package net.navrix.betterchangelogs.util;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringSerializableLocation {

    private static final Gson GSON = new Gson();

    private Location location;

    public static StringSerializableLocation fromLocation(Location location) {
        Preconditions.checkNotNull(location, "Location may not be null");
        return new StringSerializableLocation(location);
    }

    public static StringSerializableLocation fromString(String string) {
        Preconditions.checkNotNull(string, "String may not be null");
        return new StringSerializableLocation(GSON.fromJson(string, Location.class));
    }

    @Override
    public String toString() {
        return GSON.toJson(location);
    }

}
