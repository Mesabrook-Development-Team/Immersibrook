package com.mesabrook.ib.blocks.te;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class WirelessChargingPadRenderer extends TileEntitySpecialRenderer<TileEntityWirelessChargingPad>
{
    String label = "Ready";
    @Override
    public void render(TileEntityWirelessChargingPad te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(te.getPhoneItem().isEmpty())
            return;

        if(!te.getPhoneItem().isEmpty())
        {
            label = "Charging";
        }
        else
        {
            label = "Ready";
        }

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0.5, 0.5);
            GlStateManager.rotate(-90F * te.getRotation(), 0, 0.5F, 0);
            GlStateManager.translate(-0.5, -0.5, -0.5);
            GlStateManager.rotate(90F, 1, 0, 0);
            GlStateManager.translate(0.5, 0.480, -0.055);

            GlStateManager.scale(0.50F, 0.50F, 0.50F);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(te.getPhoneItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
        }
        GlStateManager.popMatrix();
    }
}
