package com.hermitowo.castirongrill.common.items;

import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.items.TFCItems;

@SuppressWarnings("unused")
public class CIGItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, CastIronGrill.MOD_ID);

    public static final TFCItems.ItemId CAST_IRON_GRILL = new TFCItems.ItemId(ITEMS.register("cast_iron_grill", () -> new CastIronGrillItem(new Item.Properties())));

    public static final TFCItems.ItemId CAST_IRON_GRILL_FIREPIT = fromBlock(CIGBlocks.CAST_IRON_GRILL_FIREPIT);

    public static <B extends Block> TFCItems.ItemId fromBlock(TFCBlocks.Id<B> block)
    {
        return new TFCItems.ItemId(ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties())));
    }

}
