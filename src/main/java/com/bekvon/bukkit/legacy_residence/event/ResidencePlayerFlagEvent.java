package com.bekvon.bukkit.legacy_residence.event;

import com.bekvon.bukkit.legacy_residence.Residence;
import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;

public class ResidencePlayerFlagEvent extends ResidenceFlagEvent implements ResidencePlayerEventInterface {
    Player p;

    public ResidencePlayerFlagEvent(String eventName, ClaimedResidence resref, Player player, String flag, FlagType type, String target) {
        super(eventName, resref, flag, type, target);
        p = player;
    }

    @Override
    public boolean isPlayer() {
        return p != null;
    }

    @Override
    public boolean isAdmin() {
        if (isPlayer()) {
            return Residence.getInstance().getPermissionManager().isResidenceAdmin(p);
        }
        return true;
    }

    @Override
    public Player getPlayer() {
        return p;
    }
}
