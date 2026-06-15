package net.enderboy500.enderlib.util.interfaces;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface ToolMaps {
    static final Map<Block, BlockState> SHEAR = Maps.newHashMap((new ImmutableMap.Builder()).build());
    static final Map<Block, BlockState> SWORD = Maps.newHashMap((new ImmutableMap.Builder()).build());
}
