package com.hermitowo.castirongrill;

import com.hermitowo.castirongrill.common.blocks.CIGBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.util.events.StartFireEvent;

public class CIGForgeEvents
{
    public static void init()
    {
        final IEventBus bus = NeoForge.EVENT_BUS;

        bus.addListener(CIGForgeEvents::onFireStart);
    }

    public static void onFireStart(StartFireEvent event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Block block = state.getBlock();

        if (block == CIGBlocks.CAST_IRON_GRILL_FIREPIT.get() && event.isStrong())
        {
            final BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof AbstractFirepitBlockEntity<?> firepit && firepit.light(state))
            {
                event.setCanceled(true);
            }
        }
    }
}