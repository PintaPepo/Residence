package com.bekvon.bukkit.legacy_residence.containers;

import com.bekvon.bukkit.legacy_residence.Residence;
import org.bukkit.command.CommandSender;

public interface cmd {

//    public boolean perform(Residence plugin, String[] args, boolean resadmin, Command command, CommandSender sender);

    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin);

    public void getLocale();

}
