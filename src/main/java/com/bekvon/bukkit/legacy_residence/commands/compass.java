package com.bekvon.bukkit.legacy_residence.commands;

import com.bekvon.bukkit.cmiLib.ConfigReader;
import com.bekvon.bukkit.legacy_residence.Residence;
import com.bekvon.bukkit.legacy_residence.containers.CommandAnnotation;
import com.bekvon.bukkit.legacy_residence.containers.cmd;
import com.bekvon.bukkit.legacy_residence.containers.lm;
import com.bekvon.bukkit.legacy_residence.permissions.PermissionManager.ResPerm;
import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;
import com.bekvon.bukkit.legacy_residence.protection._CuboidArea;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class compass implements cmd {

    @Override
    @CommandAnnotation(simple = true, priority = 3200, consoleVar = {666})
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {

        Player player = (Player) sender;

        if (args.length != 1) {
            player.setCompassTarget(player.getWorld().getSpawnLocation());
            plugin.msg(player, lm.General_CompassTargetReset);
            return true;
        }

        if (!ResPerm.command_$1.hasPermission(sender, this.getClass().getSimpleName()))
            return null;

        ClaimedResidence res = plugin.getResidenceManager().getByName(args[0]);

        if (res == null || !res.getWorld().equalsIgnoreCase(player.getWorld().getName())) {
            plugin.msg(player, lm.Invalid_Residence);
            return null;
        }

        _CuboidArea area = res.getMainArea();
        if (area == null)
            return false;
        Location loc = res.getTeleportLocation(player);
        if (loc == null)
            return false;
        player.setCompassTarget(loc);
        plugin.msg(player, lm.General_CompassTargetSet, args[0]);

        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
        c.get("Description", "Set compass pointer to residence location");
        c.get("Info", Collections.singletonList("&eUsage: &6/res compass <residence>"));
        Residence.getInstance().getLocaleManager().CommandTab.put(Collections.singletonList(this.getClass().getSimpleName()), Collections.singletonList("[residence]"));
    }
}