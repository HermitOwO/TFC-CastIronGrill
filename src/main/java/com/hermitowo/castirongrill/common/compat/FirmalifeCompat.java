package com.hermitowo.castirongrill.common.compat;

import com.hermitowo.castirongrill.common.blockentities.StovetopCastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.container.StovetopCastIronGrillContainer;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.container.TFCContainerTypes;

public class FirmalifeCompat
{
    public static TFCBlocks.Id<Block> getStovetopCastIronGrillBlock()
    {
        return FirmalifeCompatBouncer.Blocks.STOVETOP_CAST_IRON_GRILL;
    }

    public static TFCBlockEntities.Id<StovetopCastIronGrillBlockEntity> getStovetopCastIronGrillBlockEntity()
    {
        return FirmalifeCompatBouncer.BlockEntities.STOVETOP_CAST_IRON_GRILL;
    }

    public static TFCContainerTypes.Id<StovetopCastIronGrillContainer> getStovetopCastIronGrillContainer()
    {
        return FirmalifeCompatBouncer.ContainerTypes.STOVETOP_CAST_IRON_GRILL;
    }

    public static boolean isModLoaded()
    {
        return ModList.get().isLoaded("firmalife");
    }
}
