package net.enderboy500.enderlib;

import com.mojang.authlib.GameProfile;
import net.fabricmc.loader.api.FabricLoader;

public final class EnderApi {
    public static void addModId(String modId) {
        EnderLib.currentModId = modId;
    }

    public static boolean getCompat(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
