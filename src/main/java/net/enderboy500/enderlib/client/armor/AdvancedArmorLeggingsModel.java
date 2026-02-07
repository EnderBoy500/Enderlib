package net.enderboy500.enderlib.client.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class AdvancedArmorLeggingsModel extends BipedEntityModel<BipedEntityRenderState> {
    private final ModelPart body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public AdvancedArmorLeggingsModel(ModelPart root) {
        super(root);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.left_leg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.right_leg = root.getChild(EntityModelPartNames.RIGHT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F))
                .uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(24, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F))
                .uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.4F)), ModelTransform.origin(1.9F, 12.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false)
                .uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.origin(-1.9F, 12.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}