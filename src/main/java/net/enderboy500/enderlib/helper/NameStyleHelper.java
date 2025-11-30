package net.enderboy500.enderlib.helper;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.util.UUID;

public interface NameStyleHelper {

    static Text coloredName(String translationKey, TextColor color) {
        return Text.translatable(translationKey).styled(style ->
            style.withColor(color)
                    .withItalic(false)
        );
    }

    static Text styledColoredName(String translationKey, TextColor color, boolean italic, boolean bold, boolean obfuscated, boolean strikeThrough, boolean underLine) {
        return Text.translatable(translationKey).styled(style ->
                style.withColor(color)
                        .withItalic(italic)
                        .withBold(bold)
                        .withObfuscated(obfuscated)
                        .withStrikethrough(strikeThrough)
                        .withUnderline(underLine)
        );
    }

    static Text styledColoredNameWithFont(String translationKey, TextColor color, boolean italic, boolean bold, boolean obfuscated, boolean strikeThrough, boolean underLine, Identifier font) {
        return Text.translatable(translationKey).styled(style ->
                style.withColor(color)
                        .withItalic(italic)
                        .withBold(bold)
                        .withObfuscated(obfuscated)
                        .withStrikethrough(strikeThrough)
                        .withUnderline(underLine)
                        .withFont(font)
        );
    }

    static Text coloredLiteralKeyName(String literalKey, TextColor color) {
        return Text.literal(literalKey).styled(style ->
                style.withColor(color)
                        .withItalic(false)
        );
    }

    static Text styledColoredLiteralKeyName(String literalKey, TextColor color, boolean italic, boolean bold, boolean obfuscated, boolean strikeThrough, boolean underLine) {
        return Text.literal(literalKey).styled(style ->
                style.withColor(color)
                        .withItalic(italic)
                        .withBold(bold)
                        .withObfuscated(obfuscated)
                        .withStrikethrough(strikeThrough)
                        .withUnderline(underLine)
        );
    }

    static Text styledColoredLiteralKeyNameWithFont(String literalKey, TextColor color, boolean italic, boolean bold, boolean obfuscated, boolean strikeThrough, boolean underLine, Identifier font) {
        return Text.literal(literalKey).styled(style ->
                style.withColor(color)
                        .withItalic(italic)
                        .withBold(bold)
                        .withObfuscated(obfuscated)
                        .withStrikethrough(strikeThrough)
                        .withUnderline(underLine)
                        .withFont(font)
        );
    }
}
