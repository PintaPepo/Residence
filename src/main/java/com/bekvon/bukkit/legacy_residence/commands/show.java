package com.bekvon.bukkit.legacy_residence.commands;

import com.bekvon.bukkit.cmiLib.ConfigReader;
import com.bekvon.bukkit.legacy_residence.Residence;
import com.bekvon.bukkit.legacy_residence.containers.CommandAnnotation;
import com.bekvon.bukkit.legacy_residence.containers.Visualizer;
import com.bekvon.bukkit.legacy_residence.containers.cmd;
import com.bekvon.bukkit.legacy_residence.containers.lm;
import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class show implements cmd {

    @Override
    @CommandAnnotation(simple = true, priority = 3300)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;

        ClaimedResidence res = null;

        if (args.length == 1) {
            res = plugin.getResidenceManager().getByName(args[0]);
        } else {
            res = plugin.getResidenceManager().getByLoc(player.getLocation());
        }

        if (res == null) {
            plugin.msg(sender, lm.Invalid_Residence);
            return true;
        }

        Visualizer v = new Visualizer(player);
        v.setAreas(res.getAreaArray());
        plugin.getSelectionManager().showBounds(player, v);

        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
        c.get("Description", "Show residence boundaries");
        c.get("Info", Collections.singletonList("&eUsage: &6/res show <residence>"));
        Residence.getInstance().getLocaleManager().CommandTab.put(Collections.singletonList(this.getClass().getSimpleName()), Collections.singletonList("[residence]"));
    }
}
