package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.AllowPlayerKeyInputEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void enderlib$killInputs(CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        boolean is = AllowPlayerKeyInputEvent.EVENT.invoker().getB(player);
        if (is) ci.cancel();
    }
}
