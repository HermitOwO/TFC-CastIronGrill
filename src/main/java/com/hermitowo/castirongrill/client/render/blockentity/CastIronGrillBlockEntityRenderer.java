package com.hermitowo.castirongrill.client.render.blockentity;

import com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.blocks.devices.CastIronGrillBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import net.dries007.tfc.client.render.blockentity.FirepitBlockEntityRenderer;

import static com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity.*;

public class CastIronGrillBlockEntityRenderer extends FirepitBlockEntityRenderer<CastIronGrillBlockEntity>
{
    @Override
    public void render(CastIronGrillBlockEntity grill, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        super.render(grill, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);

        for (int i = SLOT_EXTRA_INPUT_START; i <= SLOT_EXTRA_INPUT_END; i++)
        {
            final ItemStack item = grill.getInventory().getStackInSlot(i);
            if (!item.isEmpty())
            {
                poseStack.pushPose();
                final Vec3 pos = CastIronGrillBlock.SLOT_CENTERS.get(i);
                poseStack.translate(pos.x, 0.003125D + pos.y, pos.z);
                poseStack.scale(0.3f, 0.3f, 0.3f);
                poseStack.mulPose(Axis.XP.rotationDegrees(90F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(180F));

                Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, buffer, grill.getLevel(), 0);
                poseStack.popPose();
            }
        }
    }
}
