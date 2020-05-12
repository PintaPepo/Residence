package com.bekvon.bukkit.legacy_residence.api;

import com.bekvon.bukkit.legacy_residence.chat.ChatChannel;
import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;

public interface ChatInterface {
    public boolean setChannel(String player, String resName);

    public boolean setChannel(String player, ClaimedResidence res);

    public boolean removeFromChannel(String player);

    public ChatChannel getChannel(String channel);

    public ChatChannel getPlayerChannel(String player);

}
