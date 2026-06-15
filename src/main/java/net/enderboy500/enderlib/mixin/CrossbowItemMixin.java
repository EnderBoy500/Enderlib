package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.util.EnderlibTags;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @Unique
    private static Predicate<ItemStack> heldPredicate() {
        return ProjectileWeaponItem.ARROW_OR_FIREWORK.or(itemStack -> itemStack.is(EnderlibTags.CROSSBOW_AMMO));
    }

    @Inject(method = "getSupportedHeldProjectiles", at = @At("HEAD"), cancellable = true)
    public void enderlib$ammo(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(heldPredicate());
    }
}
