package com.hermitowo.castirongrill.common;

import com.eerussianguy.firmalife.common.blocks.oven.OvenBottomBlock;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompat;
import com.hermitowo.castirongrill.common.items.CIGItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import net.dries007.tfc.util.InteractionManager;

public class CIGInteractionManager
{
    public static void init()
    {
        InteractionManager.register(Ingredient.of(CIGItems.CAST_IRON_GRILL), InteractionManager.Target.BLOCKS, (stack, context) -> {
            final Level level = context.getLevel();
            final BlockPos pos = context.getClickedPos();
            final BlockPos abovePos = pos.above();

            if (context.getClickedFace() == Direction.UP && level.getBlockState(pos).getBlock() instanceof OvenBottomBlock && level.getBlockState(abovePos).isAir())
            {
                level.setBlockAndUpdate(abovePos, FirmalifeCompat.getStovetopCastIronGrillBlock().get().defaultBlockState());
                if (context.getPlayer() == null || !context.getPlayer().isCreative()) stack.shrink(1);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        });
    }
}
