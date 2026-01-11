package com.hermitowo.castirongrill.common.blocks;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.devices.CastIronGrillBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;

@SuppressWarnings("SameParameterValue")
public class CIGBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, CastIronGrill.MOD_ID);

    public static final TFCBlocks.Id<Block> CAST_IRON_GRILL_FIREPIT = register("cast_iron_grill_firepit", () -> new CastIronGrillBlock(ExtendedProperties.of(MapColor.DIRT).strength(0.4F, 0.4F).sound(SoundType.NETHER_WART).randomTicks().noOcclusion().lightLevel(litBlockEmission(15)).blockEntity(CIGBlockEntities.CAST_IRON_GRILL).pathType(PathType.DAMAGE_FIRE).<AbstractFirepitBlockEntity<?>>ticks(AbstractFirepitBlockEntity::serverTick, AbstractFirepitBlockEntity::clientTick)));

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue)
    {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    public static <T extends Block> TFCBlocks.Id<T> register(String name, Supplier<T> blockSupplier)
    {
        return new TFCBlocks.Id<>(BLOCKS.register(name, blockSupplier));
    }
}
