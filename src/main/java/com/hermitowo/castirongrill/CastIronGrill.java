package com.hermitowo.castirongrill;

import com.hermitowo.castirongrill.client.CIGClientEvents;
import com.hermitowo.castirongrill.client.CIGClientForgeEvents;
import com.hermitowo.castirongrill.common.CIGCreativeTabs;
import com.hermitowo.castirongrill.common.CIGInteractionManager;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.capabilities.CIGComponents;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompat;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompatBouncer;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import com.hermitowo.castirongrill.common.items.CIGItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(CastIronGrill.MOD_ID)
public class CastIronGrill
{
    public static final String MOD_ID = "castirongrill";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CastIronGrill(IEventBus bus)
    {
        CIGBlocks.BLOCKS.register(bus);
        CIGItems.ITEMS.register(bus);
        CIGBlockEntities.BLOCK_ENTITIES.register(bus);
        CIGContainerTypes.CONTAINERS.register(bus);
        CIGCreativeTabs.CREATIVE_TABS.register(bus);
        CIGComponents.COMPONENT.register(bus);

        bus.addListener(this::setup);
        bus.addListener(CIGComponents::register);

        CIGForgeEvents.init();

        if (FirmalifeCompat.isModLoaded())
        {
            FirmalifeCompatBouncer.Blocks.init();
            FirmalifeCompatBouncer.BlockEntities.init();
            FirmalifeCompatBouncer.ContainerTypes.init();
        }

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            CIGClientEvents.init(bus);
            CIGClientForgeEvents.init();
        }
    }

    public void setup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            if (FirmalifeCompat.isModLoaded())
            {
                CIGInteractionManager.init();
            }
        });
    }

    public static ResourceLocation rl(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
