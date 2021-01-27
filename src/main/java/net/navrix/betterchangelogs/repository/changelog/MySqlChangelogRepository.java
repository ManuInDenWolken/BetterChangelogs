package net.navrix.betterchangelogs.repository.changelog;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.navrix.betterchangelogs.api.changelog.Changelog;
import net.navrix.betterchangelogs.core.changelog.DefaultChangelog;
import net.navrix.betterchangelogs.util.ReflectionUtil;
import net.navrix.betterchangelogs.util.StringSerializableLocation;
import org.bukkit.Location;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.List;

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
                return Optional.of(resultSetToChangelog(resultSet));
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

    @Override
    public List<Changelog> findAll() {
        Statement statement = null;
        try {
            statement = connection().createStatement();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        List<Changelog> changelogList = Lists.newArrayList();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM changelogs");
            while (resultSet.next()) {
                if (resultSet.isLast()) break;
                changelogList.add(resultSetToChangelog(resultSet));
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        return changelogList;
    }

    private Changelog resultSetToChangelog(ResultSet resultSet) throws SQLException, NoSuchFieldException, IllegalAccessException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String optionalName = resultSet.getString("optionalName");
        Location location = StringSerializableLocation.fromString(resultSet.getString("location")).getLocation();
        Date date = resultSet.getTimestamp("dateOfCreation");
        Changelog changelog = optionalName.equals("null") ?
            DefaultChangelog.create(id, name, location) : DefaultChangelog.create(id, name, optionalName, location);
        ReflectionUtil.setAttributeValue(changelog, "dateOfCreation", date);
        return changelog;
    }

    @SneakyThrows
    private Connection connection() {
        return source.getConnection();
    }



}
