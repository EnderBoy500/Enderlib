package net.enderboy500.enderlib.client.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.ArmPosing;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class AdvancedArmorModel<S extends BipedEntityRenderState> extends BipedEntityModel<S> {
    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart left_arm;
    public final ModelPart right_arm;
    public final ModelPart left_leg;
    public final ModelPart right_leg;

    public AdvancedArmorModel(ModelPart modelPart) {
        super(modelPart);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.75F))
                .uv(0, 0).cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.6F))
                .uv(0, 32).cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(32, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.75F))
                .uv(32, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData left_shoe = modelPartData.addChild("left_shoe", ModelPartBuilder.create().uv(16, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.75F))
                .uv(16, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.origin(1.9F, 12.0F, 0.0F));

        ModelPartData right_shoe = modelPartData.addChild("right_shoe", ModelPartBuilder.create().uv(16, 48).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.75F)).mirrored(false)
                .uv(16, 48).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)).mirrored(false), ModelTransform.origin(-1.9F, 12.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(48, 32).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.75F))
                .uv(32, 32).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.origin(5.0F, 2.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(48, 32).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.75F)).mirrored(false)
                .uv(32, 32).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)).mirrored(false), ModelTransform.origin(-5.0F, 2.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }


}
