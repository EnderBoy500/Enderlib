package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.HudEvents;
import net.enderboy500.enderlib.misc.CustomHeart;
import net.enderboy500.enderlib.misc.CustomHunger;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Nullable protected abstract Player getCameraPlayer();

    @Shadow protected abstract void renderTextureOverlay(GuiGraphics context, Identifier texture, float opacity);

    @Shadow @Final private RandomSource random;

    @Shadow private int tickCount;

    @Inject(method = "renderCameraOverlays", at = @At(value = "HEAD"), cancellable = true)
    private void enderlib$renderEffectOverLay(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        Identifier overlayTexture = HudEvents.SCREEN_OVERLAY.invoker().getOverlay(getCameraPlayer());
        if (overlayTexture != null) renderTextureOverlay(context, overlayTexture, 1);
    }

    @Inject(method = "renderHeart", at = @At(value = "HEAD"), cancellable = true)
    private void drawHeart(GuiGraphics context, Gui.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
        if (!(type.equals(Gui.HeartType.NORMAL) || type.equals(Gui.HeartType.CONTAINER))) return;
        boolean container = type.equals(Gui.HeartType.CONTAINER);
        Player player = this.minecraft.player;
        if (player == null) return;
        CustomHeart heartName = HudEvents.HEART.invoker().getHeart(getCameraPlayer());
        if (heartName != null) render(ci, context, x, y, half, blinking, container, heartName, true);
    }

    @Unique
    private static void render(CallbackInfo ci, GuiGraphics context, int x, int y, boolean half, boolean blinking, boolean container, CustomHeart heart, boolean renderContainer) {
        Identifier texture = Identifier.fromNamespaceAndPath(heart.getId(),"hud/heart/" + heart.getHeartFull());
        if (half) texture = Identifier.fromNamespaceAndPath(heart.getId(),"hud/heart/" + heart.getHeartHalf());
        if (container) {
            if (!renderContainer) return;
            texture = Identifier.fromNamespaceAndPath(heart.getId(),"hud/heart/" + heart.getContainer());
            if (blinking) texture = Identifier.fromNamespaceAndPath(heart.getId(),"hud/heart/" + heart.getContainerBlinking());
        }

        context.blitSprite(RenderPipelines.GUI_TEXTURED, texture, x, y, 9, 9);

        ci.cancel();
    }

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void renderFood(GuiGraphics context, Player player, int top, int right, CallbackInfo ci) {
        FoodData hungerManager = player.getFoodData();
        int i = hungerManager.getFoodLevel();
        CustomHunger hunger = HudEvents.HUNGER.invoker().getHunger(getCameraPlayer());
        if (hunger != null) {
            for (int j = 0; j < 10; ++j) {
                int k = top;
                Identifier identifier = Identifier.fromNamespaceAndPath(hunger.getId(), "hud/" + hunger.getEmpty());
                Identifier identifier2 = Identifier.fromNamespaceAndPath(hunger.getId(), "hud/" + hunger.getHungerHalf());
                Identifier identifier3 = Identifier.fromNamespaceAndPath(hunger.getId(), "hud/" + hunger.getHungerFull());

                if (player.getFoodData().getSaturationLevel() <= 0.0F && this.tickCount % (i * 3 + 1) == 0) {
                    k = top + (this.random.nextInt(3) - 1);
                }

                int l = right - j * 8 - 9;
                context.blitSprite(RenderPipelines.GUI_TEXTURED, identifier, l, k, 9, 9);
                if (j * 2 + 1 < i) {
                    context.blitSprite(RenderPipelines.GUI_TEXTURED, identifier3, l, k, 9, 9);
                }

                if (j * 2 + 1 == i) {
                    context.blitSprite(RenderPipelines.GUI_TEXTURED, identifier2, l, k, 9, 9);
                }
            }
            ci.cancel();
        }
    }
}
