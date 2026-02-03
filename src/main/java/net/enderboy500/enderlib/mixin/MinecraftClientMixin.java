package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.WorldConnectionEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "joinWorld", at=@At("HEAD"), cancellable = true)
    public void enderlib$join(ClientWorld world, CallbackInfo ci) throws IOException {
        WorldConnectionEvent.JOIN.invoker().join(world);
    }
}
