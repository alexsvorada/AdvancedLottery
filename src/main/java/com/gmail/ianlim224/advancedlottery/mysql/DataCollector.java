package com.gmail.ianlim224.advancedlottery.mysql;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public class DataCollector extends BukkitRunnable {
	private final UUID uuid;
	private final LotterySql sql;
	private final AdvancedLottery plugin;
	private final DataHandler dataHandler;
	private final CommandSender sender;

	public DataCollector(CommandSender sender, UUID uuid, LotterySql sql, DataHandler dataHandler, AdvancedLottery plugin) {
		this.uuid = uuid;
		this.sql = sql;
		this.plugin = plugin;
		this.dataHandler = dataHandler;
		this.sender = sender;
	}

	@Override
	public void run() {
		final PlayerData data = sql.getStats(uuid);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				dataHandler.onDataReceive(sender, data, uuid, plugin);
			}
		}.runTask(plugin);
	}
}
