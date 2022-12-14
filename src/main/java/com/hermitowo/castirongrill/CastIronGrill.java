package com.hermitowo.castirongrill;

import com.hermitowo.castirongrill.client.CIGClientEvents;
import com.hermitowo.castirongrill.client.CIGForgeClientEvents;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import com.hermitowo.castirongrill.common.items.CIGItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(CastIronGrill.MOD_ID)
public class CastIronGrill
{
    public static final String MOD_ID = "castirongrill";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CastIronGrill()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);

        CIGBlocks.BLOCKS.register(bus);
        CIGItems.ITEMS.register(bus);
        CIGBlockEntities.BLOCK_ENTITIES.register(bus);
        CIGContainerTypes.CONTAINERS.register(bus);

        ForgeEventHandler.init();

        MinecraftForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            CIGClientEvents.init();
            CIGForgeClientEvents.init();
        }
    }

    private void setup(FMLCommonSetupEvent event)
    {
    }
}
