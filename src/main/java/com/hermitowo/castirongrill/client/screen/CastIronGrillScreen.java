package com.hermitowo.castirongrill.client.screen;

import com.hermitowo.castirongrill.CastIronGrill;
import com.hermitowo.castirongrill.common.blockentities.CastIronGrillBlockEntity;
import com.hermitowo.castirongrill.common.container.CastIronGrillContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.common.component.heat.Heat;
import net.dries007.tfc.config.TFCConfig;

public class CastIronGrillScreen extends BlockEntityScreen<CastIronGrillBlockEntity, CastIronGrillContainer>
{
    private static final ResourceLocation BACKGROUND = CastIronGrill.rl("textures/gui/cast_iron_grill_firepit.png");

    public CastIronGrillScreen(CastIronGrillContainer container, Inventory playerInventory, Component name)
    {
        super(container, playerInventory, name, BACKGROUND);
        inventoryLabelY += 20;
        imageHeight += 20;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY)
    {
        super.renderBg(graphics, partialTicks, mouseX, mouseY);
        int temp = Heat.scaleTemperatureForGui(blockEntity.getTemperature());
        if (temp > 0)
        {
            graphics.blit(texture, leftPos + 30, topPos + 76 - Math.min(51, temp), 176, 0, 15, 5);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY)
    {
        super.renderTooltip(graphics, mouseX, mouseY);
        if (RenderHelpers.isInside(mouseX, mouseY, leftPos + 30, topPos + 76 - 51, 15, 51))
        {
            final var text = TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(blockEntity.getTemperature());
            if (text != null)
            {
                graphics.renderTooltip(font, text, mouseX, mouseY);
            }
        }
    }
}
