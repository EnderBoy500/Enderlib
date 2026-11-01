package net.enderboy500.enderlib.data;

import net.enderboy500.enderlib.util.AutoLang;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class EnderLibLangGenerator extends FabricLanguageProvider {
    protected EnderLibLangGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        for (Item item : AutoLang.itemList) {
            translationBuilder.add(item, AutoLang.getTranslationFromItem(BuiltInRegistries.ITEM,item));
        }
        for (Block block : AutoLang.blockList) {
            translationBuilder.add(block, AutoLang.getTranslationFromItem(BuiltInRegistries.BLOCK, block));
        }
        for (EntityType<?> entityType : AutoLang.entityTypeList) {
            translationBuilder.add(entityType, AutoLang.getTranslationFromItem(BuiltInRegistries.ENTITY_TYPE, entityType));
        }
    }
}
