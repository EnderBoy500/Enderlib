package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.util.interfaces.PlayerRenderStateAccessor;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements PlayerRenderStateAccessor {
    @Unique
    private PlayerLikeEntity player;

    @Override
    public PlayerLikeEntity player() {
        return player;
    }

    @Override
    public void setPlayer(PlayerLikeEntity player) {
        this.player = player;
    }
}
