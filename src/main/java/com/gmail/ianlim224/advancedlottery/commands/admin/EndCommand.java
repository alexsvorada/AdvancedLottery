package com.gmail.ianlim224.advancedlottery.commands.admin;

import org.bukkit.command.CommandSender;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;

public class EndCommand implements Executable {

	@Override
	public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
		if(args.length != 1)  {
			return CommandResponse.INCORRECT_ARGS;
		}
		
		plugin.getLotteryTimer().end();
		return CommandResponse.SUCCESS;
	}

	@Override
	public String getLabel() {
		return "end";
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
