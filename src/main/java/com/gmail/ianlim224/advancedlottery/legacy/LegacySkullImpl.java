package com.gmail.ianlim224.advancedlottery.legacy;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class LegacySkullImpl implements SkullManager {

    @Override
    @Deprecated
    public ItemStack getSkull(OfflinePlayer player) {
        ItemStack skull = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
        skull = setNameAndLore(skull, player);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        Objects.requireNonNull(meta);
        meta.setOwner(player.getName());
        skull.setItemMeta(meta);
        return skull;
    }

    @Override
    @Deprecated
    public OfflinePlayer getPlayer(ItemStack skull) {
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        return Bukkit.getOfflinePlayer(meta.getOwner());
    }
}
