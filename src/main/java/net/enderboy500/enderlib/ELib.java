package net.enderboy500.enderlib;

import net.fabricmc.loader.api.FabricLoader;

public final class ELib {
    public static void addModId(String modId) {
        EnderLib.currentModId = modId;
    }

    public static boolean getCompat(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
