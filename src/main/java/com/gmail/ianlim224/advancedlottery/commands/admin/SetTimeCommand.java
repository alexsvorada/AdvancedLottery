package com.gmail.ianlim224.advancedlottery.commands.admin;

import org.bukkit.command.CommandSender;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.time.TimeParser;
import com.gmail.ianlim224.advancedlottery.utils.SpigotCommons;

public class SetTimeCommand implements Executable {

	@Override
	public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
		if(args.length == 0)
			return CommandResponse.INCORRECT_ARGS;
		
		StringBuilder builder = new StringBuilder(args[0]);
		for(int i = 1; i < args.length; i++) {
			builder.append(args[i]);
		}
		
		TimeParser parser = new TimeParser(builder.toString(), plugin);
		
		if(!parser.isValid()) {
			sender.sendMessage(SpigotCommons.f("&cInvalid time format!"));
			return CommandResponse.SUCCESS;
		}
		
		plugin.getLotteryTimer().setDuration(parser.getTime().toMilis());
		sender.sendMessage(SpigotCommons.f("&aSuccessfully set lottery time!"));
		return CommandResponse.SUCCESS;
	}

	@Override
	public String getLabel() {
		return "settime";
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
