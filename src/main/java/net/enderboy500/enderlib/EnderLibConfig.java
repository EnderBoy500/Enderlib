package net.enderboy500.enderlib;

import eu.midnightdust.lib.config.MidnightConfig;

public class EnderLibConfig extends MidnightConfig {
    public static final String ENDERLIB = "enderlib";

    @Comment(category = ENDERLIB, centered = true) public static Comment enderlib;
    @Comment(category = ENDERLIB) public static Comment spacer1;
    @Entry(category = ENDERLIB) public static EquipmentStateCycleKeys equipmentStateCycleKey = EquipmentStateCycleKeys.QuickMove;
}
