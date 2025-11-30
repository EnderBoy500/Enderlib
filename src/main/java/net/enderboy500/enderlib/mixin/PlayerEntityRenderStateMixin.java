package net.enderboy500.enderlib.mixin;

import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin {
    @Nullable
    public boolean hideName;
}
