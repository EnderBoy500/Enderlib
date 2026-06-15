package net.enderboy500.enderlib.util.interfaces;

import net.minecraft.world.entity.Avatar;

public interface PlayerRenderStateAccessor {
    Avatar player();
    void setPlayer(Avatar player);
}
