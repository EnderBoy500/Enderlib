package net.enderboy500.enderlib;

import net.minecraft.util.Identifier;

public final class ELib {
    public static String currentModId = "";

    public static Identifier customId(String path) {
        return Identifier.of(currentModId, path);
    }

    public static void addModId(String modId) {
        ELib.currentModId = modId;
    }
}
