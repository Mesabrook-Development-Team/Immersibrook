package com.mesabrook.ib.blocks.te;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.mesabrook.ib.blocks.BlockFluidMeter;
import com.mesabrook.ib.util.IndependentTimer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.I18n;

public class TileEntityFluidMeterRenderer extends TileEntitySpecialRenderer<TileEntityFluidMeter> {

	private final String maxWidthCharacters = "WWWWWWWWWWWWWWW";
	private static int scrollLeft;
	private static int scrollCharacterWidth;
	private static IndependentTimer scrollTimer = new IndependentTimer();
	
	@Override
	public void render(TileEntityFluidMeter te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		
		scrollLeft = -getFontRenderer().getStringWidth(maxWidthCharacters) / 2;
		scrollCharacterWidth = getFontRenderer().getStringWidth(maxWidthCharacters.substring(0, 1));
		
		IBlockState blockState = getWorld().getBlockState(te.getPos());
		if (!(blockState.getBlock() instanceof BlockFluidMeter))
		{
			return;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		
		GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
		GlStateManager.rotate(-(blockState.getValue(BlockFluidMeter.FACING).getHorizontalIndex() + 2) * 90, 0, 1, 0);
		GlStateManager.translate(0F, 0.295F, -0.325F);
				
		GlStateManager.scale(-1, -1, 1);
		GlStateManager.scale(1F/256, 1F/256, 1F/256);
		
		final String mbReadout = Integer.toString(te.getFluidCounter()) + "mb";
		if (mbReadout.length() < 15)
		{
			renderReadoutCentered(mbReadout, 0);
		}
		else
		{
			renderScroll(mbReadout, 0, te::getLevelScroll, te::setLevelScroll);
		}
		
		String lastUnlocalizedFluid;
		switch(te.getLastUnlocalizedFluid())
		{
			case "fluid.tile.water":
				lastUnlocalizedFluid = "tile.water.name";
				break;
			case "fluid.tile.lava":
				lastUnlocalizedFluid = "tile.lava.name";
				break;
			default:
				lastUnlocalizedFluid = te.getLastUnlocalizedFluid();
		}
		final String fluidReadout = I18n.format(lastUnlocalizedFluid);
		if (fluidReadout.length() < 15)
		{
			renderReadoutCentered(fluidReadout, 1);
		}
		else
		{
			renderScroll(fluidReadout, 1, te::getFluidScroll, te::setFluidScroll);
		}
		
		String locationNameReadout = te.getLocationOwnerName();
		if (locationNameReadout.length() < 15)
		{
			renderReadoutCentered(locationNameReadout, 2);
		}
		else
		{
			renderScroll(locationNameReadout, 2, te::getOwnerScroll, te::setOwnerScroll);
		}
		
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
	
	private void renderReadoutCentered(String readout, int line)
	{
		int stringWidth = getFontRenderer().getStringWidth(readout);
		getFontRenderer().drawString(readout, -stringWidth / 2, line * getFontRenderer().FONT_HEIGHT, 0xFFFFFF);
	}
	
	private void renderScroll(String readout, int line, Supplier<Integer> scrollSupplier, Consumer<Integer> scrollConsumer)
	{
		boolean doScrollUpdates = false;
		scrollTimer.update();
		if (scrollTimer.getElapsedTime() >= 500)
		{
			doScrollUpdates = true;
			scrollTimer.reset();
		}
		
		int currentScroll = scrollSupplier.get();
		int currentX = scrollLeft;
		String readoutSubbed = readout;
		
		if (currentScroll < 15)
		{
			currentX += scrollCharacterWidth * (15 - currentScroll);
			readoutSubbed = readoutSubbed.substring(0, currentScroll);
			
			if (doScrollUpdates)
			{
				scrollConsumer.accept(currentScroll + 1);
			}
		}
		else
		{
			int length = readout.length() - currentScroll + 15;
			if (length > 15)
			{
				length = 15;
			}
			
			readoutSubbed = readoutSubbed.substring(currentScroll - 15, currentScroll - 15 + length);
			
			if (doScrollUpdates)
			{
				if (length == 0)
				{
					scrollConsumer.accept(0);
				}
				else
				{
					scrollConsumer.accept(currentScroll + 1);
				}
			}
		}
		
		getFontRenderer().drawString(readoutSubbed, currentX, getFontRenderer().FONT_HEIGHT * line, 0xFFFFFF);
	}
}
