package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.item.CustomCrossbowItemModel;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ItemModelManager.class)
public class ItemModelMixin {
    @Shadow
    @Final
    private Function<Identifier, ItemModel> modelGetter;

    @Inject(
            method = "update",
            at = @At("HEAD"), cancellable = true
    )
    private void enderlib$chargedModel(ItemRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, World world, LivingEntity entity, int seed, CallbackInfo ci) {
        Identifier chargedModel = getChargedModel(stack);
        if (chargedModel != null) {
            ItemModel var10000 = this.modelGetter.apply(chargedModel);
            ClientWorld var10005;
            if (world instanceof ClientWorld) {
                ClientWorld clientLevel = (ClientWorld)world;
                var10005 = clientLevel;
            } else {
                var10005 = null;
            }
            var10000.update(renderState, stack, (ItemModelManager) (Object) this, displayContext, var10005, entity, seed);
            ci.cancel();
        }
    }

    @Unique
    @Nullable
    private static Identifier getChargedModel(ItemStack stack) {
        for (ItemStack projectile : stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT.DEFAULT).getProjectiles()) {
            @Nullable Identifier chargedModel = getChargedModel(stack, projectile.getItem());
            if (chargedModel != null) return chargedModel;
        }
        return null;
    }

    @Unique
    @Nullable
    private static Identifier getChargedModel(ItemStack stack, Item item) {
        if (CustomCrossbowItemModel.getAmmo(item))
            return CustomCrossbowItemModel.getId(item);
        return null;
    }
}
