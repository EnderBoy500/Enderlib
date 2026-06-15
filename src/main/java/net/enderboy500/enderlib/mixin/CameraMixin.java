package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.util.interfaces.ScreenShake;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Shadow private float yRot;

    @Shadow
    private float xRot;

    @Inject(method = "setup", at = @At("TAIL"))
    private void enderlib$update(Level area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci){
        float y = this.yRot;
        float p = this.xRot;
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null){
            if (player instanceof ScreenShake shaker){
                float mult = 1;
                float swayFactor = 0.5F;
                float intensity = shaker.getScreenShakeIntensity();
                if(intensity > 0.01F){
                    float shakeSwayYaw = (float) ((Math.random() * 2 - 1) * mult * swayFactor * intensity);
                    float shakeSwayPitch = (float) ((Math.random() * 2 - 1) * mult * swayFactor * intensity);

                    this.setRotation(y + shakeSwayYaw, p + shakeSwayPitch);
                }
            }
        }
    }
}
