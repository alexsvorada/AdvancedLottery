package com.gmail.ianlim224.advancedlottery.commands;

import org.bukkit.command.CommandSender;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public interface Executable {
	public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin);

	public String getLabel();

	public Permissions getPermission();

	public boolean isCmdPlayerOnly();
}
