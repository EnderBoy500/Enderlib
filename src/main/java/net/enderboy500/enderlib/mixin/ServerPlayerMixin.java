package net.enderboy500.enderlib.mixin;

import com.mojang.datafixers.util.Either;
import net.enderboy500.enderlib.events.BedInteractionEvent;
import net.enderboy500.enderlib.events.WorldConnectionEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
    @Inject(method = "onDisconnect",at = @At("HEAD"), cancellable = true)
    public void enderlib$disconnect(CallbackInfo ci) throws IOException {
        WorldConnectionEvent.LEAVE.invoker().leave((ServerPlayerEntity) (Object) this);
    }
    @Inject(method = "sleep",at = @At("HEAD"), cancellable = true)
    public void enderlib$sleep(BlockPos pos, CallbackInfo ci) {
        BedInteractionEvent.SLEEP.invoker().sleep((PlayerEntity) (Object) this, pos);
    }
    @Inject(method = "trySleep",at = @At("HEAD"), cancellable = true)
    public void enderlib$trySleep(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        BedInteractionEvent.TRY_SLEEP.invoker().sleep((PlayerEntity) (Object) this, pos);
    }
}
