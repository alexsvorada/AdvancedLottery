package com.gmail.ianlim224.advancedlottery.object;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.mysql.LotterySql;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class LotteryTicket {
    private static final Pattern DONOR_PERMISSION_PATTERN = Pattern.compile("advancedlottery\\.maxtickets\\.[0-9]+");
    private static LotteryTicket instance;
    private final AdvancedLottery plugin;
    private final HashMap<UUID, Integer> ticketCounts;
    private final HashMap<UUID, Integer> maxTicketCache;
    private LotterySql sql;

    private LotteryTicket(AdvancedLottery plugin) {
        this.plugin = plugin;
        this.ticketCounts = new HashMap<>();
        this.maxTicketCache = new HashMap<>();
        sql = LotterySql.getInstance(plugin);
    }

    public static LotteryTicket getInstance(AdvancedLottery plugin) {
        if (instance != null) {
            return instance;
        } else {
            instance = new LotteryTicket(plugin);
        }
        return instance;
    }

    public boolean isMaxTickets(Player player) {
        return getMaxTicketsCanBeBought(player) == 0;
    }

    public int getMaxTicketsCanBeBought(Player player) {
        if(maxTicketCache.containsKey(player.getUniqueId())) {
            return maxTicketCache.get(player.getUniqueId()) - getTicketsBought(player.getUniqueId());
        }

        PermissionAttachmentInfo permission = player.getEffectivePermissions().stream()
                .filter(p -> DONOR_PERMISSION_PATTERN.matcher(p.getPermission()).matches())
                .findFirst()
                .orElse(null);

        if(permission == null || !permission.getValue()) {
            int maxTickets = AdvancedLottery.getLotteryGrabber().getMaxTicketDefault();
            if(player.hasPermission("advancedlottery.donor"))
                maxTickets = AdvancedLottery.getLotteryGrabber().getMaxTicketDonor();

            maxTicketCache.put(player.getUniqueId(), maxTickets);
            return maxTickets;
        }

        String[] tokens = permission.getPermission().split("\\.");
        int maxTickets = Integer.parseInt(tokens[2]);
        maxTicketCache.put(player.getUniqueId(), maxTickets);
        return maxTickets;
    }

    public UUID selectWinner() {
        int uppBound = 0;
        for (Entry<UUID, Integer> entry : ticketCounts.entrySet()) {
            uppBound += entry.getValue();
        }

        int winningIndex = plugin.getRandom().nextInt(uppBound);

        int counter = 0;
        for (Entry<UUID, Integer> entry : ticketCounts.entrySet()) {
            counter += entry.getValue();
            if (counter > winningIndex) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Invalid winner decision state");
    }

    void addPlayer(OfflinePlayer player, int tickets) {
        if (ticketCounts.containsKey(player.getUniqueId())) {
            ticketCounts.put(player.getUniqueId(), ticketCounts.get(player.getUniqueId()) + tickets);
        } else {
            ticketCounts.put(player.getUniqueId(), tickets);
        }
        sql.addTicketsBought(player, tickets);
    }

    public int getTicketsBought(UUID player) {
        return ticketCounts.getOrDefault(player, 0);
    }

    public Map<UUID, Integer> getWhoBought() {
        return ticketCounts;
    }

    public Set<UUID> getPlayers() {
        return ticketCounts.keySet();
    }

    public double getTicketCost() {
        return plugin.getConfig().getDouble("buy_price");
    }

    public void clear() {
        ticketCounts.clear();
    }

    public boolean isEmpty() {
        return ticketCounts.isEmpty();
    }

    public void clearCache() {
        maxTicketCache.clear();
    }
}
