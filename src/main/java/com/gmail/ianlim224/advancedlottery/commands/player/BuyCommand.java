package com.gmail.ianlim224.advancedlottery.commands.player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.gui.ConfirmGUI;
import com.gmail.ianlim224.advancedlottery.messages.Messages;
import com.gmail.ianlim224.advancedlottery.object.LotteryTicket;
import com.gmail.ianlim224.advancedlottery.object.Purchase;
import com.gmail.ianlim224.advancedlottery.object.PurchaseCooldown;
import com.gmail.ianlim224.advancedlottery.object.TicketTransaction;
import com.gmail.ianlim224.advancedlottery.sounds.CancelSound;
import com.gmail.ianlim224.advancedlottery.text.TextConfirmer;
import com.gmail.ianlim224.advancedlottery.utils.SpigotCommons;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;

public class BuyCommand implements Executable {

    @Override
    public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
        if (args.length > 2) {
            return CommandResponse.INCORRECT_ARGS;
        }

        Player player = (Player) sender;
        LotteryTicket ticket = LotteryTicket.getInstance(plugin);
        CancelSound sound = new CancelSound();

        PurchaseCooldown purchaseCooldown = PurchaseCooldown.getInstance(plugin);
        if (!purchaseCooldown.getCooldown().isReady(player) && !player.hasPermission("advancedlottery.cooldown.bypass")) {
            player.sendMessage(Messages.BUY_TICKET_COOLDOWN.getConfigValue(player).replaceAll("%time%", purchaseCooldown.getCooldown().getTimeLeft(player).getSeconds() + ""));
            sound.playSound(player, plugin);
            player.closeInventory();
            return CommandResponse.SUCCESS;
        }

        purchaseCooldown.getCooldown().addCooldown(player);

        if (args.length == 1) {
            if (plugin.getVaultEcon().getBalance(player) < LotteryTicket.getInstance(plugin).getTicketCost()) {
                player.sendMessage(Messages.NOT_ENOUGH_MONEY.getConfigValue(player));
                sound.playSound(player, plugin);
                return CommandResponse.SUCCESS;
            }

            if (!ticket.isMaxTickets(player)) {
                queryConfirmation(1, player, false, plugin);
            } else {
                player.sendMessage(Messages.ALREADY_BOUGHT.getConfigValue(player));
                sound.playSound(player, plugin);
            }
            return CommandResponse.SUCCESS;
        }

        if (args.length == 2) {
            if (!SpigotCommons.isInteger(args[1])) {
                if (!args[1].equalsIgnoreCase("confirm")) {
                    return CommandResponse.INCORRECT_ARGS;
                }

                TextConfirmer confirmer = TextConfirmer.getInstance();
                if (!confirmer.hasPendingConfirmation(player)) {
                    player.sendMessage(Messages.NO_PENDING_CONFIRMATIONS.getConfigValue(player));
                    return CommandResponse.SUCCESS;
                }

                TicketTransaction transaction = confirmer.completePendingConfirmation(player);
                Purchase purchase = new Purchase(player, transaction, plugin);
                purchase.executePurchase(true, true);
                return CommandResponse.SUCCESS;
            }

            int amount = Integer.parseInt(args[1]);

            if (amount <= 0) {
                return CommandResponse.INCORRECT_ARGS;
            }

            if (plugin.getVaultEcon().getBalance(player) < LotteryTicket.getInstance(plugin).getTicketCost() * amount) {
                player.sendMessage(Messages.NOT_ENOUGH_MONEY.getConfigValue(player));
                sound.playSound(player, plugin);
                return CommandResponse.SUCCESS;
            }

            if (ticket.isMaxTickets(player)) {
                player.sendMessage(Messages.ALREADY_BOUGHT.getConfigValue(player));
                sound.playSound(player, plugin);
                return CommandResponse.SUCCESS;
            } else if (ticket.getMaxTicketsCanBeBought(player) < amount) {
                player.sendMessage(Messages.TOO_MANY_TICKETS.getConfigValue(player));
                sound.playSound(player, plugin);
                return CommandResponse.SUCCESS;
            } else {
                queryConfirmation(amount, player, true, plugin);
                return CommandResponse.SUCCESS;
            }
        }

        return CommandResponse.SUCCESS;

    }

    private void queryConfirmation(int tickets, Player player, boolean hasAmountArgs, AdvancedLottery plugin) {
        boolean useText = false;
        boolean openConfirmMenu = false;
        if (hasAmountArgs) {
            useText = plugin.getConfig().getBoolean("use_text_confirmation_on_buy_amount");
            openConfirmMenu = plugin.getConfig().getBoolean("open_confirm_menu_on_buy_amount");
        } else {
            useText = plugin.getConfig().getBoolean("use_text_confirmation_on_buy");
            openConfirmMenu = plugin.getConfig().getBoolean("open_confirm_menu_on_buy");
        }


        TicketTransaction transaction = new TicketTransaction(tickets, plugin);
        if (useText) {
            TextConfirmer.getInstance().addPendingConfirmation(player, transaction);
            player.sendMessage(Messages.BUY_TEXT_CONFIRM.getConfigValue(player).replaceAll("%ticket%", tickets + "").replaceAll("%price%", SpigotCommons.formatMoney(transaction.getTotalPrice())));
        } else if (openConfirmMenu) {
            ConfirmGUI confirmGui = new ConfirmGUI(plugin);
            confirmGui.openGui(player);
            confirmGui.setCounter(player, tickets);
        } else {
            Purchase purchase = new Purchase(player, transaction, plugin);
            purchase.executePurchase(true, true);
        }
    }

    @Override
    public String getLabel() {
        return "buy";
    }

    @Override
    public Permissions getPermission() {
        return Permissions.DEFAULT;
    }

    @Override
    public boolean isCmdPlayerOnly() {
        return true;
    }

}
