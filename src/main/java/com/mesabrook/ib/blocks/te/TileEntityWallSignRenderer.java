package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.blocks.*;
import net.minecraft.block.state.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;

public class TileEntityWallSignRenderer extends TileEntitySpecialRenderer<TileEntityWallSign>
{
    @Override
    public void render(TileEntityWallSign te, double x, double y, double z, float partialTicks, int destroyStage,
                       float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        if (te.getLineOne() != "" || te.getLineTwo() != "")
        {
            IBlockState block = te.getWorld().getBlockState(te.getPos());
            if (block != null && !(block.getBlock() instanceof BlockWallSign))
            {
                return;
            }

            EnumFacing facing = block.getValue(BlockPlaque.FACING);
            GlStateManager.pushMatrix();

            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0.725, 0.5);
            GlStateManager.rotate(-(facing.getHorizontalIndex() + 2) * 90, 0, 1, 0);
            GlStateManager.translate(0.165, -0.125, 0.471);
            GlStateManager.scale(-1, -1, 1);
            FontRenderer fontRenderer = getFontRenderer();

            if (te.getLineOne() != "")
            {
                int stringWidth = fontRenderer.getStringWidth(te.getLineOne());
                double horizontalScaleAmount = 1;
                if (stringWidth > 43)
                {
                    horizontalScaleAmount = 1 / (stringWidth * (1 / 43.0));
                    stringWidth = 43;
                }

                GlStateManager.scale(horizontalScaleAmount, 1, 1);

                int translateAmount = -(stringWidth - 43) / 2;
                GlStateManager.scale(1/128.0, 1/128.0, 1/128.0);
                GlStateManager.translate(translateAmount, 0, 0);
                fontRenderer.drawString(te.getLineOne(), 0, 0, 0xFFFFFF);

                GlStateManager.translate(-translateAmount, 0, 0);
                GlStateManager.scale(128, 128, 128);
                GlStateManager.scale(1 / horizontalScaleAmount, 1, 1);
            }

            if (te.getLineTwo() != "")
            {
                GlStateManager.translate(0, 0.15625, 0);

                int stringWidth = fontRenderer.getStringWidth(te.getLineTwo());
                double horizontalScaleAmount = 1;
                if (stringWidth > 43)
                {
                    horizontalScaleAmount = 1 / (stringWidth * (1 / 43.0));
                    stringWidth = 43;
                }

                GlStateManager.scale(horizontalScaleAmount, 1, 1);

                int translateAmount = -(stringWidth - 43) / 2;
                GlStateManager.scale(1/128.0, 1/128.0, 1/128.0);
                GlStateManager.translate(translateAmount, 0, 0);
                fontRenderer.drawString(te.getLineTwo(), 0, 0, 0xFFFFFF);
            }

            GlStateManager.popMatrix();
        }
    }
}
