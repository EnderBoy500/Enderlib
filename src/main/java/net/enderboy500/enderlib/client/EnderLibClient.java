package net.enderboy500.enderlib.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourceType;

public class EnderLibClient implements ClientModInitializer {
    private final ReloadableResourceManagerImpl resourceManager = new ReloadableResourceManagerImpl(ResourceType.CLIENT_RESOURCES);
    @Override
    public void onInitializeClient() {

    }
}
