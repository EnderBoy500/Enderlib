package net.enderboy500.enderlib.options;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.enderboy500.enderlib.EnderLib;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.LenientJsonParser;
import net.minecraft.util.datafix.DataFixTypes;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public class EnderLibOptions {
    private final File optionsFile;
    private static final Splitter OPTION_SPLITTER = Splitter.on(':').limit(2);
    private static final Gson GSON = new Gson();

    public OptionInstance<EquipmentStateCycleKeys> equipmentStateCycleKey;

    public EnderLibOptions(File workingDirectory) {
        this.optionsFile = new File(workingDirectory, "config/enderlib.txt");

        this.equipmentStateCycleKey = new OptionInstance<>("options.enderlib.equipmentStateCycleKeys", (value) -> Tooltip.create(value.getTooltip()), (var0, value) -> value.getTranslatable(), new OptionInstance.Enum<>(Arrays.asList(EquipmentStateCycleKeys.values()), EquipmentStateCycleKeys.CODEC), EquipmentStateCycleKeys.CODEC, EquipmentStateCycleKeys.RIGHT_CLICK, (var0) -> {});
    }

    private CompoundTag dataFix(final CompoundTag tag) {
        int version = 0;

        try {
            version = tag.getString("version").map(Integer::parseInt).orElse(0);
        } catch (RuntimeException var4) {
        }

        return DataFixTypes.OPTIONS.updateToCurrentVersion(Minecraft.getInstance().getFixerUpper(), tag, version);
    }

    private static boolean isTrue(String value) {
        return "true".equals(value);
    }

    private static boolean isFalse(String value) {
        return "false".equals(value);
    }

    private void processOptions(final FieldAccess access) {
        access.process("equipmentStateCycleKeys", equipmentStateCycleKey);
    }

    public void save() {
        try (final PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8))) {
            writer.println("version:" + SharedConstants.getCurrentVersion().dataVersion().version());
            this.processOptions(new FieldAccess() {
                public void writePrefix(final String name) {
                    writer.print(name);
                    writer.print(':');
                }

                public <T> void process(final String name, final OptionInstance<T> option) {
                    option.codec().encodeStart(JsonOps.INSTANCE, option.get()).ifError((error) -> EnderLib.LOGGER.error("Error saving option {}: {}", option, error.message())).ifSuccess((element) -> {
                        this.writePrefix(name);
                        writer.println(GSON.toJson(element));
                    });
                }

                public int process(final String name, final int value) {
                    this.writePrefix(name);
                    writer.println(value);
                    return value;
                }

                public boolean process(final String name, final boolean value) {
                    this.writePrefix(name);
                    writer.println(value);
                    return value;
                }

                public String process(final String name, final String value) {
                    this.writePrefix(name);
                    writer.println(value);
                    return value;
                }

                public float process(final String name, final float value) {
                    this.writePrefix(name);
                    writer.println(value);
                    return value;
                }

                public <T> T process(final String name, final T value, final Function<String, T> reader, final Function<T, String> converter) {
                    this.writePrefix(name);
                    writer.println(converter.apply(value));
                    return value;
                }
            });
        } catch (Exception e) {
            EnderLib.LOGGER.error("Failed to save options", e);
        }
    }

    public void load() {
        try {
            if (!this.optionsFile.exists()) return;

            CompoundTag rawOptions = new CompoundTag();

            try (BufferedReader reader = Files.newReader(this.optionsFile, StandardCharsets.UTF_8)) {
                reader.lines().forEach((line) -> {
                    try {
                        Iterator<String> iterator = OPTION_SPLITTER.split(line).iterator();
                        rawOptions.putString(iterator.next(), iterator.next());
                    } catch (Exception var3) {
                        EnderLib.LOGGER.warn("Skipping bad option: {}", line);
                    }

                });
            }

            final CompoundTag options = this.dataFix(rawOptions);
            this.processOptions(new FieldAccess() {

                private @Nullable String getValue(final String name) {
                    Tag tag = options.get(name);
                    if (tag == null) {
                        return null;
                    } else if (tag instanceof StringTag) {
                        StringTag var3 = (StringTag) tag;
                        StringTag var10000 = var3;

                        String value = var10000.value();
                        return value;
                    } else {
                        throw new IllegalStateException("Cannot read field of wrong type, expected string: " + String.valueOf(tag));
                    }
                }

                public <T> void process(final String name, final OptionInstance<T> option) {
                    String result = this.getValue(name);
                    if (result != null) {
                        JsonElement element = LenientJsonParser.parse(result.isEmpty() ? "\"\"" : result);
                        DataResult var10000 = option.codec().parse(JsonOps.INSTANCE, element).ifError((error) -> EnderLib.LOGGER.error("Error parsing option value {} for option {}: {}", result, option, error.message()));
                        Objects.requireNonNull(option);
                        var10000.ifSuccess(o -> option.set((T) o));
                    }

                }

                public int process(final String name, final int current) {
                    String result = this.getValue(name);
                    if (result != null) {
                        try {
                            return Integer.parseInt(result);
                        } catch (NumberFormatException e) {
                            EnderLib.LOGGER.warn("Invalid integer value for option {} = {}", new Object[]{name, result, e});
                        }
                    }

                    return current;
                }

                public boolean process(final String name, final boolean current) {
                    String result = this.getValue(name);
                    return result != null ? isTrue(result) : current;
                }

                public String process(final String name, final String current) {
                    return MoreObjects.firstNonNull(this.getValue(name), current);
                }

                public float process(final String name, final float current) {
                    String result = this.getValue(name);
                    if (result != null) {
                        if (isTrue(result)) {
                            return 1.0F;
                        }

                        if (isFalse(result)) {
                            return 0.0F;
                        }

                        try {
                            return Float.parseFloat(result);
                        } catch (NumberFormatException e) {
                            EnderLib.LOGGER.warn("Invalid floating point value for option {} = {}", new Object[]{name, result, e});
                        }
                    }

                    return current;
                }

                public <T> T process(final String name, final T current, final Function<String, T> reader, final Function<T, String> writer) {
                    String rawResult = this.getValue(name);
                    return (T) (rawResult == null ? current : reader.apply(rawResult));
                }
            });
        } catch (Exception e) {
            EnderLib.LOGGER.error("Failed to load options", e);
        }
    }

    public interface FieldAccess extends OptionAccess {
        int process(String var1, int var2);

        boolean process(String var1, boolean var2);

        String process(String var1, String var2);

        float process(String var1, float var2);

        <T> T process(String var1, T var2, Function<String, T> var3, Function<T, String> var4);
    }

    private interface OptionAccess {
        <T> void process(String var1, OptionInstance<T> var2);
    }
}
