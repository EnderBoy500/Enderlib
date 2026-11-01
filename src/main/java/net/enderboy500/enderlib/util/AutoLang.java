package net.enderboy500.enderlib.util;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AutoLang {
    public static List<Item> itemList = new ArrayList<>();
    public static List<Block> blockList = new ArrayList<>();
    public static List<EntityType<?>> entityTypeList = new ArrayList<>();

    public static <T> String getTranslationFromItem(Registry<T> registry, T object) {
        return setupString(registry.getKey(object).getPath());
    }

    public static String setupString(String text) {
        if (text == null || text.isEmpty()) return text;

        StringBuilder builder = new StringBuilder();

        boolean formatNext = false;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.toCharArray()[i];
            if (i == 0) {
                ch = Character.toUpperCase(ch);
            } else if (ch == '_' || ch == '/' || ch == '.') {
                ch = ' ';
                formatNext = true;
            } else if (formatNext) {
                ch = Character.toUpperCase(ch);
                formatNext = false;
            }

            builder.append(ch);
        }

        return builder.toString();
    }
}
