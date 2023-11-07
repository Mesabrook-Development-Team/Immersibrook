package com.mesabrook.ib.blocks.te;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class ItemStandRenderer extends TileEntitySpecialRenderer<TileEntityPhoneStand>
{
    @Override
    public void render(TileEntityPhoneStand te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(te.getPhoneItem().isEmpty())
            return;

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0.5, 0.5);
            GlStateManager.rotate(-90F * te.getRotation(), 0, 0.5F, 0);
            GlStateManager.translate(-0.5, -0.5, -0.5);
            GlStateManager.rotate(45F, 1, 0, 0);
            GlStateManager.translate(0.5, 0.54, 0.130);

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
