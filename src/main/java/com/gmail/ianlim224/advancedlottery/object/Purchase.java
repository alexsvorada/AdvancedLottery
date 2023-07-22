package com.gmail.ianlim224.advancedlottery.object;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.clickablechat.ClickableText;
import com.gmail.ianlim224.advancedlottery.gui.LotteryGUI;
import com.gmail.ianlim224.advancedlottery.messages.Messages;
import com.gmail.ianlim224.advancedlottery.mysql.LotterySql;
import com.gmail.ianlim224.advancedlottery.utils.SpigotCommons;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Purchase {
    private final OfflinePlayer player;
    private final TicketTransaction ticket;
    private final AdvancedLottery plugin;

    public Purchase(OfflinePlayer player, int ticketAmount, AdvancedLottery plugin) {
        this.player = player;
        this.ticket = new TicketTransaction(ticketAmount, plugin);
        this.plugin = plugin;
    }

    public Purchase(OfflinePlayer player, TicketTransaction ticket, AdvancedLottery plugin) {
        this.player = player;
        this.ticket = ticket;
        this.plugin = plugin;
    }

    public void executePurchase(boolean saveToDatabase, boolean deductMoney) {
        if (deductMoney)
            executeMoneyTransaction();
        registerTicket();
        if (plugin.getConfig().getBoolean("allow_broadcast"))
            executeBroadcasts();
        sendConfirmationMessage();
        addPlayerSkull();
        if (saveToDatabase)
            savePurchaseToSqlDatabase();
    }

    private void sendConfirmationMessage() {
        Player p = (Player) player;
        p.sendMessage(
                Messages.BUY_SUCCESS.getConfigValue(p).replaceAll("%balance%", SpigotCommons.formatMoney(plugin.getVaultEcon().getBalance(player)))
                        .replaceAll("%money%", SpigotCommons.formatMoney(ticket.getTotalPrice()))
                        .replaceAll("%time%", plugin.getLotteryTimer().time(false))
                        .replaceAll("%time_short%", plugin.getLotteryTimer().time(true))
                        .replaceAll("%ticket%", Integer.toString(ticket.getAmount())));
    }

    private void registerTicket() {
        plugin.getFileLogging().debug(String.format("%s bought %d tickets for %f", player.getName(), ticket.getAmount(), ticket.getTotalPrice()));
        LotteryTicket.getInstance(plugin).addPlayer(player, ticket.getAmount());
        LotteryPot.getInstance(plugin).addMoneyInPot(ticket.getTotalPrice());
    }

    private void executeMoneyTransaction() {
        plugin.getVaultEcon().takeMoney(ticket.getTotalPrice(), player);
    }

    private void addPlayerSkull() {
        LotteryGUI gui = LotteryGUI.getInstance();
        gui.addPlayer(player);
    }

    private void executeBroadcasts() {
        if (!player.isOnline())
            return;

        Player p = (Player) player;
        ClickableText chat = new ClickableText();
        chat.sendToPlayer(p, ticket.getAmount());
    }

    private void savePurchaseToSqlDatabase() {
        LotterySql.getInstance(plugin)
                .addMoney(player, ticket.getTotalPrice());
    }
}
