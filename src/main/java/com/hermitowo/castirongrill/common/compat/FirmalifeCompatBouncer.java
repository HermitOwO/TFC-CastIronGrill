package com.hermitowo.castirongrill.common.compat;

import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blockentities.StovetopCastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.blocks.devices.StovetopCastIronGrillBlock;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import com.hermitowo.castirongrill.common.container.StovetopCastIronGrillContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.container.TFCContainerTypes;

@SuppressWarnings({"RedundantTypeArguments", "SameParameterValue"})
public class FirmalifeCompatBouncer
{
    public static class Blocks
    {
        public static void init() {}

        public static final TFCBlocks.Id<Block> STOVETOP_CAST_IRON_GRILL = CIGBlocks.register("stovetop_cast_iron_grill", () -> new StovetopCastIronGrillBlock(ExtendedProperties.of().sound(SoundType.METAL).strength(2F).blockEntity(BlockEntities.STOVETOP_CAST_IRON_GRILL).serverTicks(StovetopCastIronGrillBlockEntity::serverTick).noOcclusion()));
    }

    public static class BlockEntities
    {
        public static void init() {}

        public static final TFCBlockEntities.Id<StovetopCastIronGrillBlockEntity> STOVETOP_CAST_IRON_GRILL = CIGBlockEntities.register("stovetop_cast_iron_grill", StovetopCastIronGrillBlockEntity::new, Blocks.STOVETOP_CAST_IRON_GRILL);
    }

    public static class ContainerTypes
    {
        public static void init() {}

        public static final TFCContainerTypes.Id<StovetopCastIronGrillContainer> STOVETOP_CAST_IRON_GRILL = CIGContainerTypes.<StovetopCastIronGrillBlockEntity, StovetopCastIronGrillContainer>registerBlock("stovetop_cast_iron_grill", BlockEntities.STOVETOP_CAST_IRON_GRILL, StovetopCastIronGrillContainer::create);
    }
}
