package com.gmail.ianlim224.advancedlottery.commands.player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.messages.Messages;
import org.bukkit.command.CommandSender;

public class TimeCommand implements Executable {

    @Override
    public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
        if (args.length != 1) {
            return CommandResponse.INCORRECT_ARGS;
        }

        sender.sendMessage(
                Messages.TIME_TO_DRAW.getConfigValue(null)
                        .replaceAll("%time%", plugin.getLotteryTimer().time(false))
                        .replaceAll("%time_short%", plugin.getLotteryTimer().time(true)));
        return CommandResponse.SUCCESS;
    }

    @Override
    public String getLabel() {
        return "time";
    }

    @Override
    public Permissions getPermission() {
        return Permissions.DEFAULT;
    }

    @Override
    public boolean isCmdPlayerOnly() {
        return false;
    }

}
