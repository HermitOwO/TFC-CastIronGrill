package com.hermitowo.castirongrill.client;

import com.hermitowo.castirongrill.client.render.blockentity.CastIronGrillBlockEntityRenderer;
import com.hermitowo.castirongrill.client.render.blockentity.StovetopCastIronGrillBlockEntityRenderer;
import com.hermitowo.castirongrill.client.screen.CastIronGrillScreen;
import com.hermitowo.castirongrill.client.screen.StovetopCastIronGrillScreen;
import com.hermitowo.castirongrill.common.blockentities.CIGBlockEntities;
import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import com.hermitowo.castirongrill.common.compat.FirmalifeCompat;
import com.hermitowo.castirongrill.common.container.CIGContainerTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class CIGClientEvents
{
    public static void init(IEventBus bus)
    {
        bus.addListener(CIGClientEvents::clientSetup);
        bus.addListener(CIGClientEvents::registerMenuScreens);
        bus.addListener(CIGClientEvents::registerEntityRenderers);
    }

    @SuppressWarnings("deprecation")
    public static void clientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(CIGBlocks.CAST_IRON_GRILL_FIREPIT.get(), RenderType.cutout());

            if (FirmalifeCompat.isModLoaded())
            {
                ItemBlockRenderTypes.setRenderLayer(FirmalifeCompat.getStovetopCastIronGrillBlock().get(), RenderType.cutout());
            }
        });
    }

    public static void registerMenuScreens(RegisterMenuScreensEvent event)
    {
        event.register(CIGContainerTypes.CAST_IRON_GRILL.get(), CastIronGrillScreen::new);

        if (FirmalifeCompat.isModLoaded())
        {
            event.register(FirmalifeCompat.getStovetopCastIronGrillContainer().get(), StovetopCastIronGrillScreen::new);
        }
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(CIGBlockEntities.CAST_IRON_GRILL.get(), ctx -> new CastIronGrillBlockEntityRenderer());

        if (FirmalifeCompat.isModLoaded())
        {
            event.registerBlockEntityRenderer(FirmalifeCompat.getStovetopCastIronGrillBlockEntity().get(), ctx -> new StovetopCastIronGrillBlockEntityRenderer());
        }
    }
}
