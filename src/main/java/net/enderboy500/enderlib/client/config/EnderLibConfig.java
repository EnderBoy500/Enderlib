package net.enderboy500.enderlib.client.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.enderboy500.enderlib.misc.EquipmentStateCycleKeys;

public class EnderLibConfig extends MidnightConfig{
    public static final String CONFIG = "EnderLib Config";
    @MidnightConfig.Entry(category = CONFIG) public static EquipmentStateCycleKeys equipmentStateCycleKeys = EquipmentStateCycleKeys.RightClick;
}
