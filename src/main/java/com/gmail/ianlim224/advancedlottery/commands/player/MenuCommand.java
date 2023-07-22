package com.gmail.ianlim224.advancedlottery.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.gui.LotteryGUI;

public class MenuCommand implements Executable {

	@Override
	public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
		if (args.length != 1) {
			return CommandResponse.INCORRECT_ARGS;
		}
		
		LotteryGUI.getInstance().openFirstPage((Player) sender);
		return CommandResponse.SUCCESS;
	}

	@Override
	public String getLabel() {
		return "menu";
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
