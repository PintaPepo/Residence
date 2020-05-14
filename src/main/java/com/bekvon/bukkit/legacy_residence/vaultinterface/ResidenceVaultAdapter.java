package com.bekvon.bukkit.legacy_residence.vaultinterface;

import com.bekvon.bukkit.legacy_residence.economy.EconomyInterface;
import com.bekvon.bukkit.legacy_residence.permissions.PermissionsInterface;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class ResidenceVaultAdapter implements EconomyInterface, PermissionsInterface {

    public static Permission permissions = null;
    public static Economy economy = null;
    public static Chat chat = null;

    public ResidenceVaultAdapter(Server s) {
        setupPermissions(s);
        setupEconomy(s);
        setupChat(s);
    }

    private static void setupPermissions(Server s) {
        RegisteredServiceProvider<Permission> permissionProvider = s.getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permissions = permissionProvider.getProvider();
        }
    }

    private static void setupChat(Server s) {
        RegisteredServiceProvider<Chat> chatProvider = s.getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
    }

    private static void setupEconomy(Server s) {
        RegisteredServiceProvider<Economy> economyProvider = s.getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
    }

    public static boolean hasPermission(OfflinePlayer player, String perm, String world) {
        if (permissions == null)
            return false;
        try {
            return permissions.playerHas(world, player, perm);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean permissionsOK() {
        return permissions != null;
    }

    public boolean economyOK() {
        return economy != null;
    }

    public boolean chatOK() {
        return chat != null;
    }

    @Override
    public String getPlayerGroup(Player player) {
        String group = permissions.getPrimaryGroup(player).toLowerCase();
        if (group == null) {
            return group;
        }
        return group.toLowerCase();
    }

    @Override
    public String getPlayerGroup(String player, String world) {

        String group = permissions.getPrimaryGroup(world, player);
        if (group == null) {
            return group;
        }
        return group.toLowerCase();
    }

    @Override
    public double getBalance(Player player) {
        return economy.getBalance(player);
    }


    @Override
    public double getBalance(String playerName) {
        return economy.getBalance(playerName);
    }


    @Override
    public boolean canAfford(String playerName, double amount) {
        return economy.has(playerName, amount);
    }


    @Override
    public boolean add(String playerName, double amount) {
        return economy.depositPlayer(playerName, amount).transactionSuccess();
    }


    @Override
    public boolean subtract(String playerName, double amount) {
        return economy.withdrawPlayer(playerName, amount).transactionSuccess();
    }


    @Override
    public boolean transfer(String playerFrom, String playerTo, double amount) {
        if (economy.withdrawPlayer(playerFrom, amount).transactionSuccess()) {
            if (economy.depositPlayer(playerTo, amount).transactionSuccess()) {
                return true;
            }
            economy.depositPlayer(playerFrom, amount);
            return false;
        }
        return false;
    }

    public String getEconomyName() {
        if (economy != null) {
            return economy.getName();
        }
        return "";
    }

    public String getPermissionsName() {
        if (permissions != null) {
            return permissions.getName();
        }
        return "";
    }

    public String getChatName() {
        if (chat != null) {
            return chat.getName();
        }
        return "";
    }

    @Override
    public String getName() {
        return "Vault";
    }

    @Override
    public String format(double amount) {
        return economy.format(amount);
    }

}