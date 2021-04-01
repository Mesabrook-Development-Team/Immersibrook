package rz.mesabrook.wbtc.blocks.te;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import rz.mesabrook.wbtc.blocks.BlockPlaque;

public class TileEntityPlaqueRenderer extends TileEntitySpecialRenderer<TileEntityPlaque>{
	@Override
	public void render(TileEntityPlaque te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		if (te.getAwardedTo() != "" || te.getAwardedFor() != "")
		{
			IBlockState block = te.getWorld().getBlockState(te.getPos());
			if (block != null && !(block.getBlock() instanceof BlockPlaque))
			{
				return;
			}
			
			EnumFacing facing = block.getValue(BlockPlaque.FACING);
			GlStateManager.pushMatrix();
			
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0.5, 0.5, 0.5);
			GlStateManager.rotate(-(facing.getHorizontalIndex() + 2) * 90, 0, 1, 0);
			GlStateManager.translate(0.3125, -0.125, 0.40625);
			GlStateManager.scale(-1, -1, 1);
			FontRenderer fontRenderer = getFontRenderer();
			
			if (te.getAwardedTo() != "")
			{
				int stringWidth = fontRenderer.getStringWidth(te.getAwardedTo());
				double horizontalScaleAmount = 1;
				if (stringWidth > 78)
				{
					horizontalScaleAmount = 1 / (stringWidth * (1 / 78.0));
					stringWidth = 78;
				}
				
				GlStateManager.scale(horizontalScaleAmount, 1, 1);
				
				int translateAmount = -(stringWidth - 78) / 2;
				GlStateManager.scale(1/128.0, 1/128.0, 1/128.0);
				GlStateManager.translate(translateAmount, 0, 0);
				fontRenderer.drawString(te.getAwardedTo(), 0, 0, 0xFFFFFF);
				
				GlStateManager.translate(-translateAmount, 0, 0);
				GlStateManager.scale(128, 128, 128);
				GlStateManager.scale(1 / horizontalScaleAmount, 1, 1);
			}
			
			if (te.getAwardedFor() != "")
			{
				GlStateManager.translate(0, 0.15625, 0);
				
				int stringWidth = fontRenderer.getStringWidth(te.getAwardedFor());
				double horizontalScaleAmount = 1;
				if (stringWidth > 78)
				{
					horizontalScaleAmount = 1 / (stringWidth * (1 / 78.0));
					stringWidth = 78;
				}
				
				GlStateManager.scale(horizontalScaleAmount, 1, 1);
				
				int translateAmount = -(stringWidth - 78) / 2;
				GlStateManager.scale(1/128.0, 1/128.0, 1/128.0);
				GlStateManager.translate(translateAmount, 0, 0);
				fontRenderer.drawString(te.getAwardedFor(), 0, 0, 0xFFFFFF);
			}
			
			GlStateManager.popMatrix();
		}
	}
}
