package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLibComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity{
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getStack();

    @Shadow @Nullable public abstract Entity getOwner();

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void tick(CallbackInfo ci) {
        ItemStack stack = this.getStack();
        Entity owner = this.getOwner();
        if (stack.contains(EnderLibComponents.UNDROPPABlE)) {
            if (!this.getWorld().isClient && owner instanceof PlayerEntity player) {
                player.giveItemStack(stack);
                this.kill(this.getServer().getWorld(this.getWorld().getRegistryKey()));
            }
        }
    }
}
