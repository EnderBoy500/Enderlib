package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.EntityTickingEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Level level();

    @Inject(method = "tick", at = @At("HEAD"))
    public void preTick(CallbackInfo ci) {
        EntityTickingEvent.PRE_TICKING.invoker().ticking((Entity) (Object) this, this.level());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void postTick(CallbackInfo ci) {
        EntityTickingEvent.POST_TICKING.invoker().ticking((Entity) (Object) this, this.level());
    }
}
