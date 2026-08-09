package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.DisablePlayerKeyInputEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    public void enderlib$killAllInputs(long l, int i, KeyEvent keyEvent, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        boolean is = DisablePlayerKeyInputEvent.ALL_KEYS.invoker().getB(player);
        if (is) {
            KeyMapping.releaseAll();
            ci.cancel();
        }
    }
}
