# BetterChangelogs

## What does BetterChangelogs?

BetterChangelogs is a plug-in for the popular minecraft server software
[Spigot](https://github.com/SpigotMC). The plug-in allows you to create, edit
(WIP) and view changelogs across your (BungeeCord based) minecraft server.
You're able to view this collection of created changelogs in a fancy graphical
interface in game.

## How to use the API in my Code?
### Basic Usage
```java
BetterChangelogsApiProvider apiProvider = BetterChangelogsApiProvider.get();
ChangelogService changelogService = apiProvider.getChangelogService();

// Do something...
```

### Manage Changelogs
```java
ChangelogService changelogService = apiProvider.getChangelogService();

Changelog myChangelog = Changelog.create(1, "My Changelog", ChatColor.RED +
  "Your optional name", location);
changelogService.createOrUpdateChangelog(myChangelog); // Create (or update) a changelog

Optional<Changelog> changelog = changelogService.getChangelog(1);
if (changelog.orNull() == myChangelog) { // true
    changelogService.deleteChangelog(myChangelog);
}

System.out.println(changelogService.getChangelog(myChangelog.getKey()).isPresent());
```

Output:
```shell
false
```

## Can I contribute to this awesome Project?
Surely. If you want to contribute, you have to fork the repository. Also you
have to adhere to some conventions:
- Every commit is marked in beginning with a symbol (https://gitmoji.dev/)
- All the code has to follow the appropriate conventions (https://www.oracle.com/technetwork/java/codeconventions-150003.pdf)

<br><b>The owner of the project reserves the right to change or remove all conventions!</b>

## Maintainers
- [ManuInDenWolken](https://github.com/ManuInDenWolken)
