package net.enderboy500.enderlib.util.interfaces;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface ToolMaps {
    Map<Block, BlockState> SHEAR = Maps.newHashMap((new ImmutableMap.Builder()).build());
    Map<Block, BlockState> SWORD = Maps.newHashMap((new ImmutableMap.Builder()).build());
}
