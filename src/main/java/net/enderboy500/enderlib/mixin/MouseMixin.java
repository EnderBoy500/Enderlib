package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.DisablePlayerKeyInputEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
    public void enderlib$killAllButtonInputs(long l, MouseButtonInfo mouseButtonInfo, int i, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        boolean is = DisablePlayerKeyInputEvent.MOUSE_KEYS.invoker().getB(player);
        if (is) {
            KeyMapping.releaseAll();
            ci.cancel();
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    public void enderlib$killAllScroll(long l, double d, double e, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        boolean is = DisablePlayerKeyInputEvent.SCROLL.invoker().getB(player);
        if (is) {
            KeyMapping.releaseAll();
            ci.cancel();
        }
    }
}
