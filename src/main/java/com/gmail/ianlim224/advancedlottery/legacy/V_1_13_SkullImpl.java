package com.gmail.ianlim224.advancedlottery.legacy;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class V_1_13_SkullImpl implements SkullManager {

    @Override
    public ItemStack getSkull(OfflinePlayer player) {
        ItemStack skull = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial());
        skull = setNameAndLore(skull, player);

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        Objects.requireNonNull(meta);
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        skull.setItemMeta(meta);
        return skull;
    }

    @Override
    public OfflinePlayer getPlayer(ItemStack skull) {
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        return meta.getOwningPlayer();
    }
}
