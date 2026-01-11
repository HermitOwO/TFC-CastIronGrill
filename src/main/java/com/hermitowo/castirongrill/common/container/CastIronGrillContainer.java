package com.hermitowo.castirongrill.common.container;

import com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.slot.CallbackSlot;
import net.dries007.tfc.util.Helpers;

import static com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity.*;

public class CastIronGrillContainer extends BlockEntityContainer<CastIronGrillBlockEntity>
{
    public static CastIronGrillContainer create(CastIronGrillBlockEntity grill, Inventory playerInv, int windowId)
    {
        return new CastIronGrillContainer(grill, windowId).init(playerInv, 20);
    }

    private CastIronGrillContainer(CastIronGrillBlockEntity grill, int windowId)
    {
        super(CIGContainerTypes.CAST_IRON_GRILL.get(), windowId, grill);

        addDataSlots(grill.getSyncableData());
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
        {
            case MAIN_INVENTORY, HOTBAR ->
            {
                if (Helpers.isItem(stack, TFCTags.Items.FIREPIT_FUEL))
                {
                    yield !moveItemStackTo(stack, SLOT_FUEL_CONSUME, SLOT_FUEL_INPUT + 1, false);
                }
                else
                {
                    yield !moveItemStackTo(stack, SLOT_EXTRA_INPUT_START, SLOT_EXTRA_INPUT_END + 1, false);
                }
            }
            case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
        };
    }

    @Override
    protected void addContainerSlots()
    {
        for (int i = 0; i < 4; i++) // Fuel
        {
            addSlot(new CallbackSlot(blockEntity, i, 8, 70 - 18 * i));
        }
        for (int i = SLOT_EXTRA_INPUT_START; i <= SLOT_EXTRA_INPUT_END; i++) // Grill input
        {
            addSlot(new CallbackSlot(blockEntity, i, 62 + (i - SLOT_EXTRA_INPUT_START) * 18, 20));
        }
    }
}
