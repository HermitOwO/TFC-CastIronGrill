package com.hermitowo.castirongrill.common.blockentities;

import java.util.function.Supplier;
import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.util.registry.RegistrationHelpers;

@SuppressWarnings("SameParameterValue")
public class CIGBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CastIronGrill.MOD_ID);

    public static final TFCBlockEntities.Id<CastIronGrillBlockEntity> CAST_IRON_GRILL = register("cast_iron_grill", CastIronGrillBlockEntity::new, CIGBlocks.CAST_IRON_GRILL_FIREPIT);

    public static <T extends BlockEntity> TFCBlockEntities.Id<T> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return new TFCBlockEntities.Id<>(RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block));
    }
}
