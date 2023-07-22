package com.gmail.ianlim224.advancedlottery.commands.admin;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.time.Reminder;
import com.gmail.ianlim224.advancedlottery.time.Time;
import com.gmail.ianlim224.advancedlottery.time.TimeParser;
import com.gmail.ianlim224.advancedlottery.utils.SpigotCommons;
import org.bukkit.command.CommandSender;

public class ReminderCommand implements Executable {

    @Override
    public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
        if ((args.length == 2 && args[1].equalsIgnoreCase("list")) || args.length == 1) {
            sender.sendMessage(SpigotCommons.f("&aReminders:"));
            for (Reminder reminder : plugin.getReminderManager().getReminders()) {
                sender.sendMessage("- " + reminder.toString());
            }
            return CommandResponse.SUCCESS;
        }

        if (args.length == 3) {
            StringBuilder builder = new StringBuilder(args[0]);
            for (int i = 1; i < args.length; i++) {
                builder.append(args[i]);
            }

            TimeParser parser = new TimeParser(builder.toString(), plugin);

            if (!parser.isValid()) {
                return CommandResponse.INCORRECT_ARGS;
            }

            final Time time = parser.getTime();

            if (args[1].equalsIgnoreCase("add")) {
                plugin.getReminderManager().addReminder(new Reminder(time, plugin));
                sender.sendMessage(SpigotCommons.f("&aSuccessfully added lottery reminder!"));
                return CommandResponse.SUCCESS;

            } else if (args[1].equalsIgnoreCase("remove")) {
                Reminder reminder = new Reminder(time, plugin);
                if (!plugin.getReminderManager().hasReminder(reminder)) {
                    sender.sendMessage(SpigotCommons.f("&cLottery reminder does not exist! Do /lottery reminder list to check the list of reminders!"));
                    return CommandResponse.SUCCESS;
                }

                plugin.getReminderManager().removeReminder(reminder);
                sender.sendMessage(SpigotCommons.f("&aSuccessfully removed lottery reminder!"));
                return CommandResponse.SUCCESS;
            }
        }
        return CommandResponse.INCORRECT_ARGS;
    }

    @Override
    public String getLabel() {
        return "reminder";
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
