package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.OverrideHeartSpriteEvent;
import net.enderboy500.enderlib.events.OverrideHungerSpriteEvent;
import net.enderboy500.enderlib.events.ScreenOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffects;
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
        Identifier overlayTexture = ScreenOverlayEvent.EVENT.invoker().getOverlay(getCameraPlayer());
        if (overlayTexture != null) renderOverlay(context, overlayTexture, 1);
    }

    @Inject(method = "drawHeart", at = @At(value = "HEAD"), cancellable = true)
    private void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
        if (!(type.equals(InGameHud.HeartType.NORMAL) || type.equals(InGameHud.HeartType.CONTAINER))) return;
        boolean container = type.equals(InGameHud.HeartType.CONTAINER);
        PlayerEntity player = this.client.player;
        if (player == null) return;
        Identifier heartName = OverrideHeartSpriteEvent.EVENT.invoker().getHeart(getCameraPlayer());
        if (heartName != null) render(ci, context, x, y, half, blinking, container, heartName.getNamespace(), heartName.getPath(), true);
    }

    @Unique
    private static void render(CallbackInfo ci, DrawContext context, int x, int y, boolean half, boolean blinking, boolean container,String id, String name, boolean renderContainer) {
        Identifier texture = Identifier.of("hud/heart/" + name + "_full");
        if (half) texture = Identifier.of("hud/heart/" + name + "_half");
        if (container) {
            if (!renderContainer) return;
            texture = Identifier.of(id ,"hud/heart/" + name + "_container");
            if (blinking) texture = Identifier.of(id ,"hud/heart/" + name + "_container_blinking");
        }

        context.drawGuiTexture(RenderLayer::getGuiTextured, texture, x, y, 9, 9);

        ci.cancel();
    }

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void renderFood(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        HungerManager hungerManager = player.getHungerManager();
        int i = hungerManager.getFoodLevel();
        Identifier hunger = OverrideHungerSpriteEvent.EVENT.invoker().getHunger(getCameraPlayer());
        if (hunger != null) {
            for (int j = 0; j < 10; ++j) {
                int k = top;
                Identifier identifier = Identifier.of(hunger.getNamespace(), "hud/food_empty_" + hunger.getPath());
                Identifier identifier2 = Identifier.of(hunger.getNamespace(), "hud/food_half_" + hunger.getPath());
                Identifier identifier3 = Identifier.of(hunger.getNamespace(), "hud/food_full_" + hunger.getPath());

                if (player.getHungerManager().getSaturationLevel() <= 0.0F && this.ticks % (i * 3 + 1) == 0) {
                    k = top + (this.random.nextInt(3) - 1);
                }

                int l = right - j * 8 - 9;
                context.drawGuiTexture(RenderLayer::getGuiTextured, identifier, l, k, 9, 9);
                if (j * 2 + 1 < i) {
                    context.drawGuiTexture(RenderLayer::getGuiTextured, identifier3, l, k, 9, 9);
                }

                if (j * 2 + 1 == i) {
                    context.drawGuiTexture(RenderLayer::getGuiTextured, identifier2, l, k, 9, 9);
                }
            }
            ci.cancel();
        }
    }
}
