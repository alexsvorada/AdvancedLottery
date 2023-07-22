package com.gmail.ianlim224.advancedlottery.hooks;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class VaultEcon {
	private final AdvancedLottery plugin;
	private Economy econ;

	public VaultEcon(AdvancedLottery plugin) {
		this.plugin = plugin;
		this.econ = null;
	}
	
	public boolean setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		
		if (rsp == null) {
			return false;
		}
		
		econ = rsp.getProvider();
		return econ != null;
	}

	public boolean payMoney(double amount, OfflinePlayer player) {
		EconomyResponse localEconomyResponse = econ.depositPlayer(player, amount);
		return localEconomyResponse.transactionSuccess();
	}

	public boolean takeMoney(double amount, OfflinePlayer player) {
		EconomyResponse localEconomyResponse = econ.withdrawPlayer(player, amount);
		return localEconomyResponse.transactionSuccess();
	}

	public double getBalance(OfflinePlayer player) {
		return econ.getBalance(player);
	}
}
