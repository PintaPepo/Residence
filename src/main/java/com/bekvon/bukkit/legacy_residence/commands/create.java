package com.bekvon.bukkit.legacy_residence.commands;

import com.bekvon.bukkit.cmiLib.ConfigReader;
import com.bekvon.bukkit.legacy_residence.Residence;
import com.bekvon.bukkit.legacy_residence.containers.CommandAnnotation;
import com.bekvon.bukkit.legacy_residence.containers.cmd;
import com.bekvon.bukkit.legacy_residence.containers.lm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class create implements cmd {

    @Override
    @CommandAnnotation(simple = true, priority = 100)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        if (args.length != 1) {
            return false;
        }

        if (plugin.getWorldEdit() != null) {
            if (plugin.getWorldEditTool() == plugin.getConfigManager().getSelectionTool()) {
                plugin.getSelectionManager().worldEdit(player);
            }
        }
        if (plugin.getSelectionManager().hasPlacedBoth(player)) {
            plugin.getResidenceManager().addResidence(player, args[0], plugin.getSelectionManager().getPlayerLoc1(player), plugin
                    .getSelectionManager().getPlayerLoc2(player), resadmin);
            return true;
        }
        plugin.msg(player, lm.Select_Points);
        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
        // Main command
        c.get("Description", "Create Residences");
        c.get("Info", Collections.singletonList("&eUsage: &6/res create <residence name>"));
    }
}
