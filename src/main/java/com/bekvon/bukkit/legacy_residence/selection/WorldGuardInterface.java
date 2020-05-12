package com.bekvon.bukkit.legacy_residence.selection;

import com.bekvon.bukkit.legacy_residence.protection._CuboidArea;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

public interface WorldGuardInterface {

    ProtectedRegion getRegion(Player player, _CuboidArea area);

    boolean isSelectionInArea(Player player);

}
