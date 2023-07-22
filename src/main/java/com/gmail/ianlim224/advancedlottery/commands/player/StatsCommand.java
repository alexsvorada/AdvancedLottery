package com.gmail.ianlim224.advancedlottery.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.commands.CommandResponse;
import com.gmail.ianlim224.advancedlottery.commands.Executable;
import com.gmail.ianlim224.advancedlottery.commands.Permissions;
import com.gmail.ianlim224.advancedlottery.messages.Messages;
import com.gmail.ianlim224.advancedlottery.mysql.DataCollector;
import com.gmail.ianlim224.advancedlottery.mysql.DataPrinter;
import com.gmail.ianlim224.advancedlottery.mysql.LotterySql;

public class StatsCommand implements Executable {

	@Override
	public CommandResponse onExecute(CommandSender sender, String[] args, AdvancedLottery plugin) {
		if(args.length > 2) {
			return CommandResponse.INCORRECT_ARGS;
		}
		
		LotterySql lotterySql = LotterySql.getInstance(plugin);
		
		if(!lotterySql.isEnabled()) {
			sender.sendMessage(Messages.MYSQL_NOT_SUPPORTED.getConfigValue(null));
			return CommandResponse.SUCCESS;
		}
		
		if(args.length == 2) {
			@SuppressWarnings("deprecation")
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
			
			
			if(!offlinePlayer.hasPlayedBefore()) {
				Player p = null;
				if (sender instanceof Player) p = (Player) sender;
				sender.sendMessage(Messages.PLAYER_NOT_FOUND.getConfigValue(p));
				return CommandResponse.SUCCESS;
			}

			Player p = null;
			if (sender instanceof Player) p = (Player) sender;
			sender.sendMessage(Messages.RETRIEVING_DATA.getConfigValue(p).replaceAll("%player%", offlinePlayer.getName()));
			new DataCollector(sender, offlinePlayer.getUniqueId(), lotterySql, new DataPrinter(), plugin).runTaskAsynchronously(plugin);
		}
		
		if(args.length == 1) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(AdvancedLottery.f("&cCannot find stats for console"));
				return CommandResponse.SUCCESS;
			}
			
			Player player = (Player) sender;
			sender.sendMessage(Messages.RETRIEVING_DATA.getConfigValue(player).replaceAll("%player%", player.getName()));
			new DataCollector(player, player.getUniqueId(), lotterySql, new DataPrinter(), plugin).runTaskAsynchronously(plugin);
		}
		
		return CommandResponse.SUCCESS;
	}

	@Override
	public String getLabel() {
		return "stats";
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
