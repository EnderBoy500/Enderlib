package net.enderboy500.enderlib.util.interfaces;

import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerRenderStateAccessor {
    PlayerLikeEntity player();
    void setPlayer(PlayerLikeEntity player);
}
