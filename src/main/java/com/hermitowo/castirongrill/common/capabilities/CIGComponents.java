package com.hermitowo.castirongrill.common.capabilities;

import java.util.function.Supplier;
import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompat;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.BlockCapabilities;
import net.dries007.tfc.common.component.TFCComponents;

public class CIGComponents
{
    public static final DeferredRegister<DataComponentType<?>> COMPONENT = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, CastIronGrill.MOD_ID);

    public static void register(RegisterCapabilitiesEvent event)
    {
        if (FirmalifeCompat.isModLoaded())
        {
            registerInventory(event, FirmalifeCompat.getStovetopCastIronGrillBlockEntity());
            event.registerBlockEntity(BlockCapabilities.HEAT, FirmalifeCompat.getStovetopCastIronGrillBlockEntity().get(), (be, ctx) -> be.getInventory());
        }
    }

    private static void registerInventory(RegisterCapabilitiesEvent event, Supplier<? extends BlockEntityType<? extends InventoryBlockEntity<?>>> type)
    {
        event.registerBlockEntity(BlockCapabilities.ITEM, type.get(), InventoryBlockEntity::getSidedInventory);
    }

    private static <T> TFCComponents.Id<T> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec)
    {
        return new TFCComponents.Id<>(COMPONENT.register(name, () -> new DataComponentType.Builder<T>()
            .persistent(codec)
            .networkSynchronized(streamCodec)
            .build()));
    }
}
