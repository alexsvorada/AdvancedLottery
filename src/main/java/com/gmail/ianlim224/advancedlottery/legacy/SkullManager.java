package com.gmail.ianlim224.advancedlottery.legacy;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;
import com.gmail.ianlim224.advancedlottery.items.MenuItems;
import com.gmail.ianlim224.advancedlottery.object.LotteryTicket;
import com.gmail.ianlim224.advancedlottery.utils.ItemBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public interface SkullManager {
    ItemStack getSkull(OfflinePlayer player);

    OfflinePlayer getPlayer(ItemStack skull);

    default ItemStack setNameAndLore(ItemStack skull, OfflinePlayer player) {
        return new ItemBuilder(skull)
                .setName(MenuItems.PLAYER_HEAD_NAME.getStringValue().replaceAll("%player%", Objects.requireNonNull(player.getName())))
                .setLore(AdvancedLottery.replace(MenuItems.PLAYER_HEAD_LORE.getListValue(), "%tickets%",
                        LotteryTicket.getInstance(AdvancedLottery.getInstance()).getTicketsBought(player.getUniqueId()) + "")).toItemStack();
    }
}
