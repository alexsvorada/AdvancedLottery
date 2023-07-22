package com.gmail.ianlim224.advancedlottery;

import com.gmail.ianlim224.advancedlottery.object.LotteryTicket;
import com.gmail.ianlim224.advancedlottery.object.Purchase;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class PersistenceManager {
    private final AdvancedLottery plugin;
    private final File temp;

    public PersistenceManager(AdvancedLottery plugin) {
        this.plugin = plugin;
        this.temp = new File(plugin.getDataFolder() + "/data", "lottery.tmp");
    }

    public boolean loadPreviousLotterySnapshot() {
        if (!temp.exists())
            return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(temp))) {
            long timeLeft = Long.parseLong(reader.readLine());
            plugin.getLotteryTimer().setDuration(timeLeft);

            String s;
            while ((s = reader.readLine()) != null) {
                String[] tokens = s.split("#");
                UUID uuid = UUID.fromString(tokens[0]);
                int tickets = Integer.parseInt(tokens[1]);

                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                Purchase purchase = new Purchase(player, tickets, plugin);
                purchase.executePurchase(false, false);
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to read previous lottery data!", e);
            return false;
        }

        if (!temp.delete()) {
            plugin.getLogger().log(Level.WARNING, "Failed to delete temp snapshot!");
            return false;
        }

        return true;
    }

    public void saveCurrentLotterySnapshot() {
        temp.getParentFile().mkdirs();

        try {
            temp.createNewFile();

            FileWriter writer = new FileWriter(temp, false);
            writer.write(plugin.getLotteryTimer().timeLeft() + "\n");

            for (Map.Entry<UUID, Integer> entry : LotteryTicket.getInstance(plugin).getWhoBought().entrySet()) {
                writer.write(entry.getKey().toString() + "#" + entry.getValue() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to create tmp file!", e);
        }
    }
}
