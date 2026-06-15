package net.enderboy500.enderlib.mixin;

import com.mojang.datafixers.util.Either;
import net.enderboy500.enderlib.events.BedInteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "startSleeping",at = @At("HEAD"), cancellable = true)
    public void enderlib$sleep(BlockPos pos, CallbackInfo ci) {
        BedInteractionEvent.SLEEP.invoker().sleep((Player) (Object) this, pos);
    }
    @Inject(method = "startSleepInBed",at = @At("HEAD"), cancellable = true)
    public void enderlib$trySleep(BlockPos pos, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir) {
        BedInteractionEvent.TRY_SLEEP.invoker().sleep((Player) (Object) this, pos);
    }
}
