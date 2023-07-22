package com.gmail.ianlim224.advancedlottery.commands.admin;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.ItemGrabber;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.gui.HelpGUI;
import com.gmail.ianlim224.advancedlottery.gui.LotteryGUI;
import com.gmail.ianlim224.advancedlottery.items.MenuItems;
import com.gmail.ianlim224.advancedlottery.messages.Messages;
import com.gmail.ianlim224.advancedlottery.object.LotteryTicket;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements Executable {

    @Override
    public CommandResponse onExecute(final CommandSender sender, String[] args, final AdvancedLottery plugin) {
        if (args.length != 1) {
            return CommandResponse.INCORRECT_ARGS;
        }

        plugin.reloadConfig();
        plugin.getLotteryConfig().loadGrabber();

        plugin.getMessagesManager().reloadConfig();
        Messages.setFc(plugin.getMessagesManager().getConfig());

        plugin.getItemsManager().reloadConfig();
        MenuItems.setFc(plugin.getItemsManager().getConfig());
        MenuItems.loadConfigValues();
        ItemGrabber.reload(plugin);

        HelpGUI.getInstance(plugin).reload();
        LotteryGUI.getInstance().reset(plugin);
        LotteryTicket.getInstance(plugin).getPlayers().forEach(uuid -> {
            LotteryGUI.getInstance().addPlayer(Bukkit.getOfflinePlayer(uuid));
        });
        LotteryTicket.getInstance(plugin).clearCache();

        plugin.getLogger().info("Configuration files reloaded");
        sender.sendMessage(AdvancedLottery.f("&aSuccessfully reloaded config files!"));
        return CommandResponse.SUCCESS;
    }

    @Override
    public String getLabel() {
        return "reload";
    }

    @Override
    public Permissions getPermission() {
        return Permissions.ADMIN;
    }

    @Override
    public boolean isCmdPlayerOnly() {
        return false;
    }

}
