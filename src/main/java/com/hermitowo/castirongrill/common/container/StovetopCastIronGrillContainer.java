package com.hermitowo.castirongrill.common.container;

import com.hermitowo.castirongrill.common.blockentities.StovetopCastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompatBouncer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import net.dries007.tfc.common.capabilities.BlockCapabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.slot.CallbackSlot;
import net.dries007.tfc.util.Helpers;

public class StovetopCastIronGrillContainer extends BlockEntityContainer<StovetopCastIronGrillBlockEntity>
{
    public static StovetopCastIronGrillContainer create(StovetopCastIronGrillBlockEntity grill, Inventory playerInventory, int windowId)
    {
        return new StovetopCastIronGrillContainer(grill, windowId).init(playerInventory, 20);
    }

    public StovetopCastIronGrillContainer(StovetopCastIronGrillBlockEntity grill, int windowId)
    {
        super(FirmalifeCompatBouncer.ContainerTypes.STOVETOP_CAST_IRON_GRILL.get(), windowId, grill);

        addDataSlots(grill.getSyncableData());
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
        {
            case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, StovetopCastIronGrillBlockEntity.SLOTS, false);
            case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
        };
    }

    @Override
    protected void addContainerSlots()
    {
        final IItemHandler inv = Helpers.getCapability(BlockCapabilities.ITEM, blockEntity);
        if (inv != null)
        {
            for (int i = 0; i < StovetopCastIronGrillBlockEntity.SLOTS; i++)
            {
                addSlot(new CallbackSlot(blockEntity, inv, i, 62 + i * 18, 20));
            }
        }
    }
}
