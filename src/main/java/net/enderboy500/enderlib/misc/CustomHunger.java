package net.enderboy500.enderlib.misc;

import net.minecraft.util.Identifier;

public class CustomHunger {
    private final String id;
    private final String hungerFull;
    private final String hungerHalf;
    private final String empty;

    public CustomHunger(String modId, String hungerFull, String hungerHalf, String empty) {
        this.id = modId;
        this.hungerFull = hungerFull;
        this.hungerHalf = hungerHalf;
        this.empty = empty;
    }

    public String getId() {
        return id;
    }

    public String getHungerFull() {
        return hungerFull;
    }

    public String getHungerHalf() {
        return hungerHalf;
    }

    public String getEmpty() {
        return empty;
    }

    public static CustomHunger create(String modId, String hungerFull, String hungerHalf, String empty) {
        return new CustomHunger(modId, hungerFull, hungerHalf, empty);
    }

    public static CustomHunger createVanilla(String hungerFull, String hungerHalf, String empty) {
        return new CustomHunger(Identifier.DEFAULT_NAMESPACE, hungerFull, hungerHalf, empty);
    }
}
