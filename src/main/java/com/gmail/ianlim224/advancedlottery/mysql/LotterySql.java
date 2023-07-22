package com.gmail.ianlim224.advancedlottery.mysql;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class LotterySql {
    private static LotterySql instance;
    private final AdvancedLottery plugin;
    private final Map<UUID, PlayerData> cache;
    private HikariDataSource hikari;
    private boolean isEnabled;

    private LotterySql(AdvancedLottery plugin) {
        this.plugin = plugin;
        this.cache = new HashMap<>();
        isEnabled = plugin.getConfig().getBoolean("mysql.enabled");
    }

    public static LotterySql getInstance(AdvancedLottery plugin) {
        if (instance == null) {
            instance = new LotterySql(plugin);
        }
        return instance;
    }

    public void initDatabase() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isEnabled) {
                    plugin.getLogger().log(Level.INFO, "You are not using mysql, mysql functions will be disabled");
                    return;
                }

                FileConfiguration c = plugin.getConfig();

                plugin.getLogger().log(Level.INFO, "Connecting to database!");
                hikari = new HikariDataSource();

                String ip = c.getString("mysql.ip");
                String port = c.getString("mysql.port");
                String dbName = c.getString("mysql.database-name");
                hikari.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", ip, port, dbName));
//                hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
//                hikari.addDataSourceProperty("serverName", c.getString("mysql.ip"));
//                hikari.addDataSourceProperty("port", c.getString("mysql.port"));
//                hikari.addDataSourceProperty("databaseName", c.getString("mysql.database-name"));
                hikari.addDataSourceProperty("user", c.getString("mysql.username"));
                hikari.addDataSourceProperty("password", c.getString("mysql.password"));

                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT "
                             + "EXISTS Lottery(uuid VARCHAR(36), wins INT, tickets INT, money DOUBLE, money_won DOUBLE)")) {
                    statement.execute();
                    plugin.getLogger().log(Level.INFO, "Successfully connected!");
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "failed to connect to database", e);
                    isEnabled = false;
                }

                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection.prepareStatement("ALTER TABLE Lottery ADD COLUMN money_won DOUBLE NOT NULL DEFAULT 0")) {
                    statement.execute();
                } catch (SQLException e) {
                    if (e.getErrorCode() == 1060) { // column already exists
                        plugin.getLogger().log(Level.INFO, "New column money_won already exists");
                    } else {
                        plugin.getLogger().log(Level.SEVERE, "failed to connect to database", e);
                        isEnabled = false;
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void close() {
        hikari.close();
    }

    public void addPlayerIfNotExists(final Player player) {
        if (!isEnabled) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement("SELECT * FROM Lottery WHERE uuid = ?");) {
                    statement.setString(1, player.getUniqueId().toString());
                    ResultSet result = statement.executeQuery();
                    if (result.isBeforeFirst()) {
                        // player found, not adding again
                        return;
                    }
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "failed to execute find player task", e);
                }

                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection.prepareStatement(
                             "INSERT INTO Lottery(uuid, wins, tickets, money, money_won) values (?, ?, ?, ?, ?)")) {
                    statement.setString(1, player.getUniqueId().toString());

                    for (int i = 2; i < 6; i++) {
                        statement.setInt(i, 0);
                    }

                    statement.execute();
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "failed to execute sql tasks", e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addWins(final OfflinePlayer player) {
        if (!isEnabled) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement("UPDATE Lottery SET wins = wins + 1 WHERE uuid = ?");) {
                    statement.setString(1, player.getUniqueId().toString());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "failed to execute update wins task", e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addMoneyWon(final OfflinePlayer player, double amount) {
        if (!isEnabled) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement("UPDATE Lottery SET money_won = money_won + " + amount + " WHERE uuid = ?")) {
                    statement.setString(1, player.getUniqueId().toString());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "failed to execute update wins task", e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addTicketsBought(final OfflinePlayer player, final int amount) {
        if (!isEnabled) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement("UPDATE Lottery SET tickets = tickets + ? WHERE uuid = ?");) {
                    statement.setInt(1, amount);
                    statement.setString(2, player.getUniqueId().toString());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "failed to execute update tickets task", e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addMoney(final OfflinePlayer player, final double amount) {
        if (!isEnabled) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     PreparedStatement statement = connection
                             .prepareStatement("UPDATE Lottery SET money = money + ? WHERE uuid = ?");) {
                    statement.setDouble(1, amount);
                    statement.setString(2, player.getUniqueId().toString());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "failed to execute update money task", e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public PlayerData getStats(final UUID uuid) {
        if (!isEnabled) {
            return null;
        }

        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        PlayerData data = null;
        try (Connection connection = hikari.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from Lottery WHERE uuid = ?");) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int wins = resultSet.getInt("wins");
                int tickets = resultSet.getInt("tickets");
                double money = resultSet.getDouble("money");
                double money_won = resultSet.getDouble("money_won");
                data = new PlayerData(wins, tickets, money, money_won, uuid);
                cache.put(uuid, data);
            }
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "failed to get stats", e);
        }
        return data;
    }

    public void clearCache() {
        cache.clear();
    }

    public HikariDataSource getHikariCp() {
        return hikari;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
