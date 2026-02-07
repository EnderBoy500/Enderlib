package net.enderboy500.enderlib.client.armor;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;

public class AdvancedArmorFeatureRenderer <S extends BipedEntityRenderState, M extends BipedEntityModel<S>, A extends BipedEntityModel<S>> extends FeatureRenderer<S, M> {
    private final EquipmentModelData<A> field_61804;
    private final EquipmentModelData<A> field_61805;
    private final EquipmentRenderer equipmentRenderer;

    public AdvancedArmorFeatureRenderer(FeatureRendererContext<S, M> featureRendererContext, EquipmentModelData<A> equipmentModelData, EquipmentRenderer equipmentRenderer) {
        this(featureRendererContext, equipmentModelData, equipmentModelData, equipmentRenderer);
    }

    public AdvancedArmorFeatureRenderer(FeatureRendererContext<S, M> context, EquipmentModelData<A> equipmentModelData, EquipmentModelData<A> equipmentModelData2, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.field_61804 = equipmentModelData;
        this.field_61805 = equipmentModelData2;
        this.equipmentRenderer = equipmentRenderer;
    }

    public static boolean hasModel(ItemStack stack, EquipmentSlot slot) {
        EquippableComponent equippableComponent = (EquippableComponent)stack.get(DataComponentTypes.EQUIPPABLE);
        return equippableComponent != null && hasModel(equippableComponent, slot);
    }

    private static boolean hasModel(EquippableComponent component, EquipmentSlot slot) {
        return component.assetId().isPresent() && component.slot() == slot;
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state, float limbAngle, float limbDistance) {
        this.renderArmor(matrices, queue, state.equippedChestStack, EquipmentSlot.CHEST, light, state);
        this.renderArmor(matrices, queue, state.equippedLegsStack, EquipmentSlot.LEGS, light, state);
        this.renderArmor(matrices, queue, state.equippedFeetStack, EquipmentSlot.FEET, light, state);
        this.renderArmor(matrices, queue, state.equippedHeadStack, EquipmentSlot.HEAD, light, state);
    }
    private void renderArmor(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack stack, EquipmentSlot slot, int light, S bipedEntityRenderState) {
        EquippableComponent equippableComponent = (EquippableComponent)stack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null && hasModel(equippableComponent, slot)) {
            A bipedEntityModel = this.getModel(bipedEntityRenderState, slot);
            EquipmentModel.LayerType layerType = EquipmentModel.LayerType.HUMANOID;
            this.equipmentRenderer.render(layerType, (RegistryKey)equippableComponent.assetId().orElseThrow(), bipedEntityModel, bipedEntityRenderState, stack, matrices, orderedRenderCommandQueue, light, bipedEntityRenderState.outlineColor);
        }
    }

    private A getModel(S state, EquipmentSlot slot) {
        return (A)((state.baby ? this.field_61805 : this.field_61804).getModelData(slot));
    }
}
