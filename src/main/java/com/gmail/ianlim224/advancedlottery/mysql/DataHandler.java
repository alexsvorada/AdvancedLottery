package com.gmail.ianlim224.advancedlottery.mysql;

import java.util.UUID;

import org.bukkit.command.CommandSender;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public abstract class DataHandler {

	public void handleData(CommandSender target, final PlayerData data, final UUID uuid, AdvancedLottery plugin) {
		onDataReceive(target, data, uuid, plugin);
	}

	protected abstract void onDataReceive(CommandSender target, PlayerData data, UUID uuid, AdvancedLottery plugin);
}
