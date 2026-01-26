package net.enderboy500.enderlib;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.enderboy500.enderlib.misc.EquipmentStateCycleKeys;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class EnderLibConfig {
    public static ConfigClassHandler<EnderLibConfig> HANDLER = ConfigClassHandler.createBuilder(EnderLibConfig.class)
            .id(Identifier.of(EnderLib.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("enderlib.json5"))
                    .setJson5(true)
                    .build())
            .build();

    public static EnderLibConfig getInstance() {
        return HANDLER.instance();
    }

    @SerialEntry
    public EquipmentStateCycleKeys swapKey = EquipmentStateCycleKeys.RightClick;
}
