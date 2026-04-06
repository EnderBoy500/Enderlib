package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.HudEvents;
import net.enderboy500.enderlib.misc.CustomHeart;
import net.enderboy500.enderlib.misc.CustomHunger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Shadow @Final private Random random;

    @Shadow private int ticks;

    @Inject(method = "renderMiscOverlays", at = @At(value = "HEAD"), cancellable = true)
    private void enderlib$renderEffectOverLay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Identifier overlayTexture = HudEvents.SCREEN_OVERLAY.invoker().getOverlay(getCameraPlayer());
        if (overlayTexture != null) renderOverlay(context, overlayTexture, 1);
    }

    @Inject(method = "drawHeart", at = @At(value = "HEAD"), cancellable = true)
    private void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
        if (!(type.equals(InGameHud.HeartType.NORMAL) || type.equals(InGameHud.HeartType.CONTAINER))) return;
        boolean container = type.equals(InGameHud.HeartType.CONTAINER);
        PlayerEntity player = this.client.player;
        if (player == null) return;
        CustomHeart heartName = HudEvents.HEART.invoker().getHeart(getCameraPlayer());
        if (heartName != null) render(ci, context, x, y, half, blinking, container, heartName, true);
    }

    @Unique
    private static void render(CallbackInfo ci, DrawContext context, int x, int y, boolean half, boolean blinking, boolean container, CustomHeart heart, boolean renderContainer) {
        Identifier texture = Identifier.of(heart.getId(),"hud/heart/" + heart.getHeartFull());
        if (half) texture = Identifier.of(heart.getId(),"hud/heart/" + heart.getHeartHalf());
        if (container) {
            if (!renderContainer) return;
            texture = Identifier.of(heart.getId(),"hud/heart/" + heart.getContainer());
            if (blinking) texture = Identifier.of(heart.getId(),"hud/heart/" + heart.getContainerBlinking());
        }

        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, texture, x, y, 9, 9);

        ci.cancel();
    }

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void renderFood(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        HungerManager hungerManager = player.getHungerManager();
        int i = hungerManager.getFoodLevel();
        CustomHunger hunger = HudEvents.HUNGER.invoker().getHunger(getCameraPlayer());
        if (hunger != null) {
            for (int j = 0; j < 10; ++j) {
                int k = top;
                Identifier identifier = Identifier.of(hunger.getId(), "hud/" + hunger.getEmpty());
                Identifier identifier2 = Identifier.of(hunger.getId(), "hud/" + hunger.getHungerHalf());
                Identifier identifier3 = Identifier.of(hunger.getId(), "hud/" + hunger.getHungerFull());

                if (player.getHungerManager().getSaturationLevel() <= 0.0F && this.ticks % (i * 3 + 1) == 0) {
                    k = top + (this.random.nextInt(3) - 1);
                }

                int l = right - j * 8 - 9;
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier, l, k, 9, 9);
                if (j * 2 + 1 < i) {
                    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier3, l, k, 9, 9);
                }

                if (j * 2 + 1 == i) {
                    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier2, l, k, 9, 9);
                }
            }
            ci.cancel();
        }
    }
}
