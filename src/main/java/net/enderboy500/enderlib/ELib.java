package net.enderboy500.enderlib;

import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Function;

public final class ELib {
    public static void addModId(String modId) {
        EnderLib.currentModId = modId;
    }

    public static boolean getCompat(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
    public static boolean isMidnightPresent() {
        return FabricLoader.getInstance().isModLoaded("midnightlib");
    }
    public static boolean isDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
