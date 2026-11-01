package net.enderboy500.enderlib.util.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableBlockEntity {
    void tick(Level level, BlockPos pos);

    static <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pWorld) {
        return pWorld.isClientSide() ? null : (level, pos, state, blockEntity) -> {
            if (blockEntity instanceof TickableBlockEntity tickableBlockEntity) tickableBlockEntity.tick(level, pos);
        };
    }
}
