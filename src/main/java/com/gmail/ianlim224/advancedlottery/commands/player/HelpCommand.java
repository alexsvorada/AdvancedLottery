package com.gmail.ianlim224.advancedlottery.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.gui.HelpGUI;

public class HelpCommand implements Executable {

    @Override
    public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
        if (args.length != 1) {
            return CommandResponse.INCORRECT_ARGS;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(new String[]{
                    AdvancedLottery.f("&aList of commands:"),
                    AdvancedLottery.f("Default:"),
                    AdvancedLottery.f("/lottery - main command"),
                    AdvancedLottery.f("/lottery menu - opens the menu gui"),
                    AdvancedLottery.f("/lottery help - opens the help gui"),
                    AdvancedLottery.f("/lottery time - tells the time left until draw"),
                    AdvancedLottery.f("/lottery buy - buy a lottery ticket"),
                    AdvancedLottery.f("/lottery stats <player> - show stats (Player optional)"),
                    AdvancedLottery.f(""),
                    AdvancedLottery.f("Admin:"),
                    AdvancedLottery.f("/lottery reload - reloads the plugin"),
                    AdvancedLottery.f("/lottery end - ends the lottery and select a winner"),
                    AdvancedLottery.f("/lottery settime <time> - sets the time for the lottery"),
                    AdvancedLottery.f("/lottery reminder list - lists all reminders"),
                    AdvancedLottery.f("/lottery reminder add <time> - adds a reminder that broadcasts time left until lottery draw"),
                    AdvancedLottery.f("/lottery reminder remove <time> - removes a reminder that was created"),
                    AdvancedLottery.f("/lottery addtickets <player> <amount> - Gives <player> free tickets")
            });
            return CommandResponse.SUCCESS;
        }

       HelpGUI.getInstance(plugin).show((Player) sender);
        return CommandResponse.SUCCESS;
    }

    @Override
    public String getLabel() {
        return "help";
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
