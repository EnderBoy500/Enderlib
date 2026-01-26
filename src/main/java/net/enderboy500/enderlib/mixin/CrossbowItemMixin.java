package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
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
        return RangedWeaponItem.CROSSBOW_HELD_PROJECTILES.or(itemStack -> itemStack.isIn(EnderLib.CROSSBOW_AMMO));
    }

    @Inject(method = "getHeldProjectiles", at = @At("HEAD"), cancellable = true)
    public void enderlib$ammo(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(heldPredicate());
    }
}
