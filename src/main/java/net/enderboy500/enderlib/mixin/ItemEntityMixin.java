package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity{
    public ItemEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getItem();

    @Shadow @Nullable public abstract Entity getOwner();

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void tick(CallbackInfo ci) {
        ItemStack stack = this.getItem();
        Entity owner = this.getOwner();
        if (stack.has(EnderLibComponents.UNDROPPABlE)) {
            if (!this.level().isClientSide() && owner instanceof Player player) {
                player.addItem(stack);
                this.kill(this.level().getServer().getLevel(this.level().dimension()));
            }
        }
    }
}
