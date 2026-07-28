package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.item.CustomCrossbowItemModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
    @Shadow
    @Final
    private Function<Identifier, ItemModel> modelGetter;

    @Inject(
            method = "appendItemLayers",
            at = @At("HEAD"), cancellable = true
    )
    private void enderlib$chargedModel(ItemStackRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Level world, ItemOwner heldItemContext, int seed, CallbackInfo ci) {
        Identifier chargedModel = getChargedModel(stack);
        if (chargedModel != null) {
            ItemModel var10000 = this.modelGetter.apply(chargedModel);
            ClientLevel var10005;
            if (world instanceof ClientLevel) {
                ClientLevel clientLevel = (ClientLevel)world;
                var10005 = clientLevel;
            } else {
                var10005 = null;
            }
            var10000.update(renderState, stack, (ItemModelResolver) (Object) this, displayContext, var10005, heldItemContext, seed);
            ci.cancel();
        }
    }

    @Unique
    @Nullable
    private static Identifier getChargedModel(ItemStack stack) {
        for (ItemStack projectile : stack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY.EMPTY).getItems()) {
            @Nullable Identifier chargedModel = getChargedModel(stack, projectile.getItem());
            if (chargedModel != null) return chargedModel;
        }
        return null;
    }

    @Unique
    @Nullable
    private static Identifier getChargedModel(ItemStack stack, Item item) {
        if (CustomCrossbowItemModel.getModelApplier(item))
            return CustomCrossbowItemModel.getId(item);
        return null;
    }
}
