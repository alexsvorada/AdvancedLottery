package com.gmail.ianlim224.advancedlottery.utils;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.UUID;
import java.util.logging.Level;

public class WinnerRegistry {
    private final File winnersFile;
    private final AdvancedLottery plugin;
    private OfflinePlayer winner;

    public WinnerRegistry(AdvancedLottery plugin) {
        this.winnersFile = new File(plugin.getDataFolder() + "/data", "winner.dat");
        this.plugin = plugin;
        this.winner = null;
    }

    public void read() {
        checkIfWinnersFileExists();
        try (BufferedReader reader = new BufferedReader(new FileReader(winnersFile))) {
            String uid = reader.readLine();
            if (uid == null) return;
            this.winner = Bukkit.getOfflinePlayer(UUID.fromString(uid));
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to read from winner.dat!", e);
        }
    }

    private void checkIfWinnersFileExists() {
        winnersFile.getParentFile().mkdirs();

        if (!winnersFile.exists()) {
            try {
                winnersFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create winner.dat!", e);
            }
        }
    }

    public void save() {
        checkIfWinnersFileExists();

        if (winner == null) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(winnersFile, false))) {
            writer.write(winner.getUniqueId().toString() + "\n");
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to write to winner.dat!", e);
        }
    }

    public OfflinePlayer getWinner() {
        return winner;
    }

    public void setWinner(OfflinePlayer winner) {
        this.winner = winner;
    }
}
