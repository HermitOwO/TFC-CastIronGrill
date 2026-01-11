package com.hermitowo.castirongrill.common;

import java.util.function.Supplier;
import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.items.CIGItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.dries007.tfc.common.TFCCreativeTabs;

@SuppressWarnings({"unused", "SameParameterValue"})
public class CIGCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CastIronGrill.MOD_ID);

    public static final TFCCreativeTabs.Id MAIN = register("main", () -> new ItemStack(CIGItems.CAST_IRON_GRILL_FIREPIT.get()), CIGCreativeTabs::fillTab);

    private static TFCCreativeTabs.Id register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        return new TFCCreativeTabs.Id(CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
            .icon(icon)
            .title(Component.translatable("castirongrill.creative_tab." + name))
            .displayItems(displayItems)
            .build()), displayItems);
    }

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        accept(out, CIGItems.CAST_IRON_GRILL);
        accept(out, CIGItems.CAST_IRON_GRILL_FIREPIT);
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            CastIronGrill.LOGGER.error("BlockItem with no Item added to creative tab: {}", reg);
            return;
        }
        out.accept(reg.get());
    }
}
