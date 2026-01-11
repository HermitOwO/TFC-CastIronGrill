package com.hermitowo.castirongrill.client.render.blockentity;

import com.hermitowo.castirongrill.common.blockentities.StovetopCastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.blocks.devices.StovetopCastIronGrillBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class StovetopCastIronGrillBlockEntityRenderer implements BlockEntityRenderer<StovetopCastIronGrillBlockEntity>
{
    @Override
    public void render(StovetopCastIronGrillBlockEntity grill, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        if (grill.getLevel() == null)
            return;
        final var cap = grill.getInventory();
        for (int i = 0; i < StovetopCastIronGrillBlockEntity.SLOTS; ++i)
        {
            ItemStack item = cap.getStackInSlot(i);
            if (!item.isEmpty())
            {
                poseStack.pushPose();
                final Vec3 pos = StovetopCastIronGrillBlock.SLOT_CENTERS.get(i);
                poseStack.translate(pos.x, 0.003125 + 1f / 16, pos.z);
                poseStack.scale(0.3F, 0.3F, 0.3F);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, buffer, grill.getLevel(), 0);
                poseStack.popPose();
            }
        }
    }
}
