package net.enderboy500.enderlib;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.fabric.impl.resource.ResourceLoaderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

public final class ELib {
    public static String currentModId = "";

    public static Identifier customId(String path) {
        return Identifier.fromNamespaceAndPath(currentModId, path);
    }

    public static void addModId(String modId) {
        ELib.currentModId = modId;
    }

    public void addBuiltInDatapack(String name, String modId, PackActivationType packActivationType) {
        FabricLoader.getInstance().getModContainer(modId).ifPresent(modContainer -> ResourceLoaderImpl.registerBuiltinPack(Identifier.fromNamespaceAndPath(modId, name), "datapacks/" + Identifier.fromNamespaceAndPath(modId, name).getPath(), modContainer, packActivationType));
    }

    public void addBuiltInResourcepack(String name, String modId, PackActivationType packActivationType) {
        ResourceLoader.registerBuiltinPack(Identifier.fromNamespaceAndPath(name, modId), FabricLoader.getInstance().getModContainer(modId).get(), packActivationType);
    }
}
