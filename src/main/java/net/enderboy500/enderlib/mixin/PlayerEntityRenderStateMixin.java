package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.util.interfaces.PlayerRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class PlayerEntityRenderStateMixin implements PlayerRenderStateAccessor {
    @Unique
    private Avatar player;

    @Override
    public Avatar player() {
        return player;
    }

    @Override
    public void setPlayer(Avatar player) {
        this.player = player;
    }
}
