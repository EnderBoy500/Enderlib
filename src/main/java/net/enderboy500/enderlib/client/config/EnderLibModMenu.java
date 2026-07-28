package net.enderboy500.enderlib.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.enderboy500.enderlib.EnderLib;

public class EnderLibModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> EnderLibConfig.getScreen(parent, EnderLib.MOD_ID);
    }
}
