package com.bekvon.bukkit.legacy_residence.selection;

import com.bekvon.bukkit.legacy_residence.protection.ClaimedResidence;

public interface WESchematicManager {

    boolean save(ClaimedResidence res);

    boolean load(ClaimedResidence res);

    boolean rename(ClaimedResidence res, String newName);

    boolean delete(ClaimedResidence res);

}
