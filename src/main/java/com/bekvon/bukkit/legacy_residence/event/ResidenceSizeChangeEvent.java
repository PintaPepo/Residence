package com.bekvon.bukkit.legacy_residence.event;

import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;
import com.bekvon.bukkit.legacy_residence.protection._CuboidArea;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceSizeChangeEvent extends CancellableResidencePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    protected String resname;
    _CuboidArea oldarea;
    _CuboidArea newarea;
    ClaimedResidence res;
    public ResidenceSizeChangeEvent(Player player, ClaimedResidence res, _CuboidArea oldarea, _CuboidArea newarea) {
        super("RESIDENCE_SIZE_CHANGE", res, player);
        resname = res.getName();
        this.res = res;
        this.oldarea = oldarea;
        this.newarea = newarea;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getResidenceName() {
        return resname;
    }

    @Override
    public ClaimedResidence getResidence() {
        return res;
    }

    public _CuboidArea getOldArea() {
        return oldarea;
    }

    public _CuboidArea getNewArea() {
        return newarea;
    }
}
