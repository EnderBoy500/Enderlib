package net.enderboy500.enderlib.misc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.Map;

public class ToolMaps {
    public static final Map<Block, BlockState> SHEAR = Maps.newHashMap((new ImmutableMap.Builder()).build());
    public static final Map<Block, BlockState> SWORD = Maps.newHashMap((new ImmutableMap.Builder()).build());
}
