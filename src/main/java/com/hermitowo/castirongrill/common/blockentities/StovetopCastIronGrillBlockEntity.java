package com.hermitowo.castirongrill.common.blockentities;

import com.eerussianguy.firmalife.common.FLHelpers;
import com.eerussianguy.firmalife.common.blockentities.ApplianceBlockEntity;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompatBouncer;
import com.hermitowo.castirongrill.common.container.StovetopCastIronGrillContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.PartialItemHandler;
import net.dries007.tfc.common.component.food.FoodCapability;
import net.dries007.tfc.common.component.food.FoodTraits;
import net.dries007.tfc.common.component.heat.HeatCapability;
import net.dries007.tfc.common.component.heat.IHeat;
import net.dries007.tfc.common.recipes.HeatingRecipe;

public class StovetopCastIronGrillBlockEntity extends ApplianceBlockEntity<StovetopCastIronGrillBlockEntity.Inventory>
{
    @SuppressWarnings("unused")
    public static void serverTick(Level level, BlockPos pos, BlockState state, StovetopCastIronGrillBlockEntity grill)
    {
        grill.checkForLastTickSync();
        grill.checkForCalendarUpdate();

        if (grill.needsRecipeUpdate)
        {
            grill.updateCachedRecipe();
        }

        grill.tickTemperature();
        grill.handleCooking();
    }

    public static final int SLOTS = 2;

    private final HeatingRecipe[] cachedRecipes;
    private boolean needsRecipeUpdate = true;

    public StovetopCastIronGrillBlockEntity(BlockPos pos, BlockState state)
    {
        super(FirmalifeCompatBouncer.BlockEntities.STOVETOP_CAST_IRON_GRILL.get(), pos, state, Inventory::new, FLHelpers.blockEntityName("stovetop_cast_iron_grill"));

        sidedInventory
            .on(new PartialItemHandler(inventory).insert(0, 1), Direction.UP)
            .on(new PartialItemHandler(inventory).extract(0, 1), Direction.Plane.HORIZONTAL);
        cachedRecipes = new HeatingRecipe[SLOTS];
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, net.minecraft.world.entity.player.Inventory inventory, Player player)
    {
        return StovetopCastIronGrillContainer.create(this, inventory, containerId);
    }

    @Override
    public int getSlotStackLimit(int slot)
    {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return HeatCapability.get(stack) != null;
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider access)
    {
        super.loadAdditional(nbt, access);
        needsRecipeUpdate = true;
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        super.setAndUpdateSlots(slot);
        needsRecipeUpdate = true;
    }

    protected void handleCooking()
    {
        assert level != null;
        for (int slot = 0; slot < SLOTS; slot++)
        {
            final ItemStack inputStack = inventory.getStackInSlot(slot);
            final IHeat cap = HeatCapability.get(inputStack);
            if (cap != null)
            {
                HeatCapability.addTemp(cap, temperature);
                final HeatingRecipe recipe = cachedRecipes[slot];
                if (recipe != null && recipe.isValidTemperature(cap.getTemperature()))
                {
                    ItemStack output = recipe.assembleItem(inputStack);
                    FoodCapability.applyTrait(output, FoodTraits.WOOD_GRILLED);
                    FLHelpers.roundCreationDate(output);
                    inventory.setStackInSlot(slot, output);
                    markForSync();
                }
            }
        }
    }

    protected void updateCachedRecipe()
    {
        assert level != null;
        for (int slot = 0; slot < SLOTS; slot++)
        {
            final ItemStack stack = inventory.getStackInSlot(slot);
            cachedRecipes[slot] = stack.isEmpty() ? null : HeatingRecipe.getRecipe(stack);
        }
    }

    public static class Inventory extends ApplianceBlockEntity.ApplianceInventory
    {
        public Inventory(InventoryBlockEntity<?> entity)
        {
            super(entity, SLOTS);
        }
    }
}
