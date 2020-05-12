package com.bekvon.bukkit.legacy_residence.commands;

import com.bekvon.bukkit.cmiLib.ConfigReader;
import com.bekvon.bukkit.legacy_residence.Residence;
import com.bekvon.bukkit.legacy_residence.containers.CommandAnnotation;
import com.bekvon.bukkit.legacy_residence.containers.ResidencePlayer;
import com.bekvon.bukkit.legacy_residence.containers.cmd;
import com.bekvon.bukkit.legacy_residence.containers.lm;
import com.bekvon.bukkit.legacy_residence.permissions.PermissionGroup;
import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class unstuck implements cmd {

    @Override
    @CommandAnnotation(simple = true, priority = 4000)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;

        ResidencePlayer rPlayer = plugin.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();

        if (!group.hasUnstuckAccess()) {
            plugin.msg(player, lm.General_NoPermission);
            return true;
        }
        ClaimedResidence res = plugin.getResidenceManager().getByLoc(player.getLocation());
        if (res == null) {
            plugin.msg(player, lm.Residence_NotIn);
        } else {
            plugin.msg(player, lm.General_Moved);
            player.teleport(res.getOutsideFreeLoc(player.getLocation(), player));
        }
        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
        c.get("Description", "Teleports outside of residence");
        c.get("Info", Collections.singletonList("&eUsage: &6/res unstuck"));
    }
}
