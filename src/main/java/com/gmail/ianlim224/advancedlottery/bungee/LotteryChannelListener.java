package com.gmail.ianlim224.advancedlottery.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class LotteryChannelListener implements PluginMessageListener {
    public static final String LOTTERY_CHANNEL = "AdvancedLottery";

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        String subchannel = in.readUTF();
        if (LOTTERY_CHANNEL.equals(subchannel)) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
        }

    }
}
