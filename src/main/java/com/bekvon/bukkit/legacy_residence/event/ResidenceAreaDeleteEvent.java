package com.bekvon.bukkit.legacy_residence.event;

import com.bekvon.bukkit.legacy_residence.event.ResidenceDeleteEvent.DeleteCause;
import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceAreaDeleteEvent extends CancellableResidencePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    DeleteCause cause;

    public ResidenceAreaDeleteEvent(Player player, ClaimedResidence resref, DeleteCause delcause) {
        super("RESIDENCE_AREA_DELETE", resref, player);
        cause = delcause;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public DeleteCause getCause() {
        return cause;
    }

}