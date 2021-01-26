package net.navrix.betterchangelogs.repository.changelog;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.navrix.betterchangelogs.core.changelog.Changelog;
import net.navrix.betterchangelogs.core.changelog.impl.DefaultChangelog;
import net.navrix.betterchangelogs.util.ReflectionUtil;
import net.navrix.betterchangelogs.util.StringSerializableLocation;
import org.bukkit.Location;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class MySqlChangelogRepository implements ChangelogRepository {

    private DataSource source;

    public static MySqlChangelogRepository create(DataSource source) {
        Preconditions.checkNotNull(source, "DataSource may not be null");
        return new MySqlChangelogRepository(source);
    }

    @Override
    public void create(Changelog changelog) {
        try {
            PreparedStatement statement = connection().prepareStatement("INSERT INTO changelogs(id, name, " +
                "optionalName, dateOfCreation, location) VALUES (?, ?, ?, ?, ?);");
            statement.setInt(1, changelog.getKey());
            statement.setString(2, changelog.getName());
            statement.setString(3, changelog.getOptionalName().orNull());
            statement.setTimestamp(4, new Timestamp(changelog.getDateOfCreation().getTime()));
            statement.setString(5, StringSerializableLocation.fromLocation(changelog.getLocation()).toString());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Optional<Changelog> read(Integer id) {
        PreparedStatement statement = null;
        try {
            statement = connection().prepareStatement("SELECT * FROM changelogs WHERE id=?;");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String optionalName = resultSet.getString("optionalName");
                Location location = StringSerializableLocation.fromString(resultSet.getString("location")).getLocation();
                Date date = resultSet.getTimestamp("dateOfCreation");
                Changelog changelog = optionalName.equals("null") ?
                    DefaultChangelog.create(id, name, location) : DefaultChangelog.create(id, name, optionalName, location);
                ReflectionUtil.setAttributeValue(changelog, "dateOfCreation", date);
                return Optional.of(changelog);
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        return Optional.absent();
    }

    @Override
    public void update(Changelog changelog) {
        try {
            PreparedStatement statement = connection().prepareStatement("UPDATE changelogs SET id=?, name=?, " +
                "optionalName=?, dateOfCreation=?, location=?;");
            statement.setInt(1, changelog.getKey());
            statement.setString(2, changelog.getName());
            statement.setString(3, changelog.getOptionalName().orNull());
            statement.setTimestamp(4, new Timestamp(changelog.getDateOfCreation().getTime()));
            statement.setString(5, StringSerializableLocation.fromLocation(changelog.getLocation()).toString());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(Changelog changelog) {
        try {
            PreparedStatement statement = connection().prepareStatement("DELETE * FROM changelogs WHERE id=?;");
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @SneakyThrows
    private Connection connection() {
        return source.getConnection();
    }



}
