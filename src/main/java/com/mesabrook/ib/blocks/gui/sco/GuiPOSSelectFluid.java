package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.ArrayList;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.te.TileEntityFluidMeter;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.TrackedFluidData;
import com.mesabrook.ib.net.sco.POSGetRegisterFluidMetersPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiPOSSelectFluid extends GuiPOSMainBase {

	private GuiImageLabelButton cancel;
	private ImageButton nextPage;
	private ImageButton prevPage;
	private ArrayList<TileEntityFluidMeter> meters = new ArrayList<>();
	private boolean dataRetrieved = false;
	private int page = 0;
	
	public GuiPOSSelectFluid(TileEntityRegister register) {
		super(register);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		meters.clear();
		firstTick = true;
		page = 0;
		
		cancel = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 41, 83, 20, "   Cancel", new ResourceLocation(Reference.MODID, "textures/gui/sco/red_x.png"), 16, 16, 16, 16, ImageOrientation.Left)
				.setEnabledColor(0x373737);
		nextPage = new ImageButton(0, innerLeft + innerWidth - 14, innerTop + 150, 8, 8, new ResourceLocation(Reference.MODID, "textures/gui/sco/resultset_next.png"), 16, 16, 16, 16);
		prevPage = new ImageButton(0, innerLeft + 113, innerTop + 150, 8, 8, new ResourceLocation(Reference.MODID, "textures/gui/sco/resultset_previous.png"), 16, 16, 16, 16);
		
		setupButtons();
	}
	
	private void setupButtons()
	{
		buttonList.clear();
		
		buttonList.add(cancel);
		
		prevPage.enabled = page > 0;
		nextPage.enabled = page < meters.size() / 8;
		
		buttonList.add(nextPage);
		buttonList.add(prevPage);
		
		for(int i = 0; i < 8; i++)
		{
			int meterIndex = (page) * 8 + i;
			if (meters.size() <= meterIndex)
			{
				break;
			}
			
			TileEntityFluidMeter meter = meters.get(meterIndex);
			TrackedFluidData fluidData = null;
			for(TrackedFluidData trackedData : register.getTrackedFluidData())
			{
				if (trackedData.getFluidMeterPos().equals(meter.getPos()))
				{
					fluidData = trackedData;
					break;
				}
			}
			
			if (fluidData == null)
			{
				continue;
			}
			
			GuiImageLabelButton meterButton = new GuiImageLabelButton(meterIndex + 1, innerLeft + (i % 2 == 0 ? 115 : 183), innerTop + (47 + 24 * (i / 2)), 64, 20, "  " + fluidData.getName(), new ItemStack(Items.BUCKET), ImageOrientation.Left)
					.setEnabledColor(0x373737)
					.setDisabledColor(0xc6c6c6);
			meterButton.enabled = meter.getFluidCounter() > 0;
			buttonList.add(meterButton);
		}
	}

	private boolean firstTick = true;
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		if (firstTick)
		{
			POSGetRegisterFluidMetersPacket getFluidMeters = new POSGetRegisterFluidMetersPacket();
			getFluidMeters.registerPos = register.getPos();
			PacketHandler.INSTANCE.sendToServer(getFluidMeters);
			
			firstTick = false;
		}
		
		fontRenderer.drawString("Pay for Fluid", innerLeft + 116, innerTop + 29, 0);
		final String pageText = "Page " + (page + 1) + "/" + ((meters.size() / 8) + 1);
		int textWidth = fontRenderer.getStringWidth(pageText);
		GlStateManager.scale(0.5F, 0.5F, 1);
		fontRenderer.drawString(pageText, (innerLeft + 121 + ((innerLeft + innerWidth - 14) - (innerLeft + 121)) / 2) * 2 - (textWidth / 2), (innerTop + 153) * 2, 0x74A3E0);
		GlStateManager.scale(2F, 2F, 1);
		
		if (!dataRetrieved)
		{
			fontRenderer.drawString("Loading...", innerLeft + 181 - (fontRenderer.getStringWidth("Loading...") / 2), innerTop + 55 - (fontRenderer.FONT_HEIGHT / 2), 0);
		}
		else if (meters.size() <= 0)
		{
			fontRenderer.drawString("No Meters", innerLeft + 181 - (fontRenderer.getStringWidth("No Meters") / 2), innerTop + 55 - (fontRenderer.FONT_HEIGHT / 2), 0);
		}
		
		for(GuiButton button : buttonList)
		{
			if (button.id > 0 && button.enabled && button.isMouseOver())
			{
				TileEntityFluidMeter meter = meters.get(button.id - 1);
				if (meter == null)
				{
					break;
				}
				
				ArrayList<String> text = new ArrayList<>();
				String lastUnlocalizedFluid;
				switch(meter.getLastUnlocalizedFluid())
				{
					case "fluid.tile.water":
						lastUnlocalizedFluid = "tile.water.name";
						break;
					case "fluid.tile.lava":
						lastUnlocalizedFluid = "tile.lava.name";
						break;
					default:
						lastUnlocalizedFluid = meter.getLastUnlocalizedFluid();
				}
				final String fluidReadout = I18n.format(lastUnlocalizedFluid);
				text.add(TextFormatting.DARK_GREEN + fluidReadout);
				text.add(Integer.toString(meter.getFluidCounter()) + "mb");
				
				drawHoveringText(text, mouseX, mouseY);
				
				break;
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == cancel)
		{
			mc.displayGuiScreen(new GuiPOSInSession(register));
			return;
		}
		
		if (button == prevPage && page > 0)
		{
			page--;
			setupButtons();
		}
		
		if (button == nextPage && page < meters.size() / 8)
		{
			page++;
			setupButtons();
		}
	}
	
	public void onDataReceived(ArrayList<TileEntityFluidMeter> fluidMeters)
	{
		meters = fluidMeters;
		setupButtons();
		dataRetrieved = true;
	}
}
