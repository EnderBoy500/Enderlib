package net.enderboy500.enderlib.misc;

import net.minecraft.util.Identifier;

public class CustomHeart {
    private final String id;
    private final String heartFull;
    private final String heartHalf;
    private final String container;
    private final String containerBlinking;

    public CustomHeart(String modId, String heartFull, String heartHalf, String container, String containerBlinking) {
        this.id = modId;
        this.heartFull = heartFull;
        this.heartHalf = heartHalf;
        this.container = container;
        this.containerBlinking = containerBlinking;
    }

    public String getId() {
        return id;
    }

    public String getHeartFull() {
        return heartFull;
    }

    public String getHeartHalf() {
        return heartHalf;
    }

    public String getContainer() {
        return container;
    }

    public String getContainerBlinking() {
        return containerBlinking;
    }

    public static CustomHeart create(String modId, String heartFull, String heartHalf, String container, String containerBlinking) {
        return new CustomHeart(modId, heartFull, heartHalf, container, containerBlinking);
    }

    public static CustomHeart createVanilla(String heartFull, String heartHalf, String container, String containerBlinking) {
        return new CustomHeart(Identifier.DEFAULT_NAMESPACE, heartFull, heartHalf, container, containerBlinking);
    }
    public static CustomHeart create(String modId, String heartPrefix, String containerPrefix) {
        return new CustomHeart(modId, heartPrefix + "_full", heartPrefix + "_half", containerPrefix, containerPrefix + "_blinking");
    }
    public static CustomHeart create(String modId, String basePrefix) {
        return new CustomHeart(modId, basePrefix + "_full", basePrefix + "_half", basePrefix + "_container", basePrefix + "_container_blinking");
    }
}
