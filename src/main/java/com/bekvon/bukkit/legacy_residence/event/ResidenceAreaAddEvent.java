package com.bekvon.bukkit.legacy_residence.event;

import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;
import com.bekvon.bukkit.legacy_residence.protection._CuboidArea;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ResidenceAreaAddEvent extends CancellableResidencePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    protected String resname;
    _CuboidArea area;

    public ResidenceAreaAddEvent(Player player, String newname, ClaimedResidence resref, _CuboidArea resarea) {
        super("RESIDENCE_AREA_ADD", resref, player);
        resname = newname;
        area = resarea;
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

//    public void setResidenceName(String name) {
//	resname = name;
//    }

    public _CuboidArea getPhysicalArea() {
        return area;
    }

//    public void setPhysicalArea(CuboidArea newarea) {
//	area = newarea;
//    }
}
