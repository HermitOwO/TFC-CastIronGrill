package com.hermitowo.castirongrill.common.blockentities;

import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.container.CastIronGrillContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.common.capabilities.PartialItemHandler;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodTraits;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.inventory.ItemStackInventory;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;

public class CastIronGrillBlockEntity extends AbstractFirepitBlockEntity<ItemStackHandler>
{
    public static final int SLOT_EXTRA_INPUT_START = 4;
    public static final int SLOT_EXTRA_INPUT_END = 5;

    private static final Component NAME = Component.translatable(CastIronGrill.MOD_ID + ".block_entity.cast_iron_grill");

    private final HeatingRecipe[] cachedRecipes;

    public CastIronGrillBlockEntity(BlockPos pos, BlockState state)
    {
        super(CIGBlockEntities.CAST_IRON_GRILL.get(), pos, state, defaultInventory(6), NAME);

        cachedRecipes = new HeatingRecipe[2];

        if (TFCConfig.SERVER.firePitEnableAutomation.get())
        {
            sidedInventory
                .on(new PartialItemHandler(inventory).insert(SLOT_FUEL_INPUT).extract(4, 5), Direction.Plane.HORIZONTAL)
                .on(new PartialItemHandler(inventory).insert(4, 5), Direction.UP);
        }
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, Inventory playerInv, Player player)
    {
        return CastIronGrillContainer.create(this, playerInv, windowID);
    }

    @Override
    public int getSlotStackLimit(int slot)
    {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        if (slot >= SLOT_EXTRA_INPUT_START && slot <= SLOT_EXTRA_INPUT_END)
        {
            return stack.getCapability(HeatCapability.CAPABILITY).isPresent();
        }
        return super.isItemValid(slot, stack);
    }

    @Override
    protected void handleCooking()
    {
        assert level != null;
        for (int slot = SLOT_EXTRA_INPUT_START; slot <= SLOT_EXTRA_INPUT_END; slot++)
        {
            final ItemStack inputStack = inventory.getStackInSlot(slot);
            final int finalSlot = slot;
            inputStack.getCapability(HeatCapability.CAPABILITY, null).ifPresent(cap ->
            {
                HeatCapability.addTemp(cap, temperature);
                HeatingRecipe recipe = cachedRecipes[finalSlot - SLOT_EXTRA_INPUT_START];
                if (recipe != null && recipe.isValidTemperature(cap.getTemperature()))
                {
                    ItemStack output = recipe.assemble(new ItemStackInventory(inputStack), level.registryAccess());
                    FoodCapability.applyTrait(output, FoodTraits.WOOD_GRILLED);
                    inventory.setStackInSlot(finalSlot, output);
                    markForSync();
                }
            });
        }
    }

    @Override
    protected void coolInstantly()
    {
        for (ItemStack stack : Helpers.iterate(inventory))
        {
            stack.getCapability(HeatCapability.CAPABILITY).ifPresent(cap -> cap.setTemperature(0));
        }
    }

    @Override
    protected void updateCachedRecipe()
    {
        assert level != null;
        for (int slot = SLOT_EXTRA_INPUT_START; slot <= SLOT_EXTRA_INPUT_END; slot++)
        {
            final ItemStack stack = inventory.getStackInSlot(slot);
            cachedRecipes[slot - SLOT_EXTRA_INPUT_START] = stack.isEmpty() ? null : HeatingRecipe.getRecipe(new ItemStackInventory(stack));
        }
    }

}
