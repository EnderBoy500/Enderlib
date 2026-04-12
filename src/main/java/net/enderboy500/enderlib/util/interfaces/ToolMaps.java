package net.enderboy500.enderlib.util.interfaces;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.Map;

public interface ToolMaps {
    static final Map<Block, BlockState> SHEAR = Maps.newHashMap((new ImmutableMap.Builder()).build());
    static final Map<Block, BlockState> SWORD = Maps.newHashMap((new ImmutableMap.Builder()).build());
}
