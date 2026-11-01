package net.enderboy500.enderlib.item.skin;

import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ItemSkin {
    private final Identifier modelId;
    private final String id;

    private final Map<String, Identifier> MAP = new HashMap<>();

    public ItemSkin(String id, Identifier modelId) {
        this.modelId = modelId;
        this.id = id;
        MAP.put(id, modelId);
    }


    public Identifier getModelId() {
        return modelId;
    }

    public Map<String, Identifier> getMap() {
        return MAP;
    }

    public Identifier getFromId(String id) {
        return MAP.get(id);
    }

    public String getId() {
        return id;
    }
}
