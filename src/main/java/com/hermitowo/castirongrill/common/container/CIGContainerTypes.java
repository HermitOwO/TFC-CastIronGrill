package com.hermitowo.castirongrill.common.container;

import java.util.function.Supplier;
import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.dries007.tfc.util.registry.RegistrationHelpers;

@SuppressWarnings({"RedundantTypeArguments", "SameParameterValue"})
public class CIGContainerTypes
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, CastIronGrill.MOD_ID);

    public static final TFCContainerTypes.Id<CastIronGrillContainer> CAST_IRON_GRILL = CIGContainerTypes.<CastIronGrillBlockEntity, CastIronGrillContainer>registerBlock("cast_iron_grill", CIGBlockEntities.CAST_IRON_GRILL, CastIronGrillContainer::create);

    public static <T extends InventoryBlockEntity<?>, C extends BlockEntityContainer<T>>  TFCContainerTypes.Id<C> registerBlock(String name, Supplier<BlockEntityType<T>> type, BlockEntityContainer.Factory<T, C> factory)
    {
        return new TFCContainerTypes.Id<>(RegistrationHelpers.registerBlockEntityContainer(CONTAINERS, name, type, factory));
    }
}
