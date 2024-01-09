package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.te.TileEntityFluidMeter;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.TrackedFluidData;
import com.mesabrook.ib.net.sco.POSAddNewFluidMetersPacket;
import com.mesabrook.ib.net.sco.POSGetNearbyFluidMetersPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiCheckBox;

public class GuiPOSFluidMeterAddList extends GuiPOSMainBase {

	private final int scanDistance;
	
	private ArrayList<TileEntityFluidMeter> fluidMeters = new ArrayList<>();
	private HashSet<Integer> selectedFluidMeters = new HashSet<>();
	private int page = 0;
	
	private GuiImageLabelButton cancel;
	private GuiImageLabelButton add;
	private ImageButton prev;
	private ImageButton next;
	
	public GuiPOSFluidMeterAddList(TileEntityRegister register, int scanDistance)
	{
		super(register);
		this.scanDistance = scanDistance;
	}
	
	@Override
	protected String getBackingTextureName() {
		return "sco_gui_grid.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		cancel = new GuiImageLabelButton(0, innerLeft + 7, innerTop + innerHeight - 20, 45, 13, "   Cancel", new ResourceLocation(Reference.MODID, "textures/gui/sco/red_x.png"), 10, 10, 10, 10, ImageOrientation.Left)
				.setEnabledColor(0xFF0000);
		add = new GuiImageLabelButton(0, innerLeft + innerWidth - 42, innerTop + innerHeight - 20, 35, 13, "Add   ", new ResourceLocation(Reference.MODID, "textures/gui/add.png"), 10, 10, 10, 10, ImageOrientation.Right)
				.setEnabledColor(0x00FF00);
		prev = new ImageButton(0, innerLeft + (innerWidth / 2) - 52, innerTop + innerHeight - 21, 16, 16, new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16);
		next = new ImageButton(0, innerLeft + (innerWidth / 2) + 35, innerTop + innerHeight - 21, 16, 16, new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_right.png"), 16, 16, 16, 16);
		
		setupButtons();
	}
	
	private void setupButtons()
	{
		buttonList.clear();
		
		prev.enabled = page > 0;
		next.enabled = page < fluidMeters.size() / 13;
		
		buttonList.add(cancel);
		buttonList.add(add);
		buttonList.add(prev);
		buttonList.add(next);
		
		if (dataReceived)
		{
			for(int i = page * 13; i < (page + 1) * 13; i++)
			{
				if (i >= fluidMeters.size())
				{
					break;
				}
				
				GuiCheckBox checkbox = new GuiCheckBox(i, innerLeft + 10, innerTop + 40 + 11 * (i - page * 13), "", selectedFluidMeters.contains(i));
				buttonList.add(checkbox);
			}
		}
	}
	
	private boolean firstTick = true;
	private boolean dataReceived = false;
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		if (firstTick)
		{
			POSGetNearbyFluidMetersPacket getNearby = new POSGetNearbyFluidMetersPacket();
			getNearby.registerPos = register.getPos();
			getNearby.scanDistance = scanDistance;
			PacketHandler.INSTANCE.sendToServer(getNearby);
			
			firstTick = false;
		}
		
		fontRenderer.drawString("Meter Position", innerLeft + 21, innerTop + 29, 0);
		fontRenderer.drawString("Fluid", innerLeft + 111, innerTop + 29, 0);
		fontRenderer.drawString("Amt", innerLeft + 211, innerTop + 29, 0);
		fontRenderer.drawString(String.format("Page %s/%s", page + 1, fluidMeters.size() / 13 + 1), innerLeft + (innerWidth / 2) - fontRenderer.getStringWidth(String.format("Page %s/%s", page + 1, fluidMeters.size() / 13)) / 2, innerTop + innerHeight - 17, 0);
		
		if (!dataReceived)
		{
			fontRenderer.drawString("Loading...", innerLeft + (innerWidth /2) - (fontRenderer.getStringWidth("Loading...") / 2), innerTop + innerHeight / 2 - fontRenderer.FONT_HEIGHT / 2, 0);
		}
		else if (fluidMeters.size() <= 0)
		{
			fontRenderer.drawString("No fluid meters", innerLeft + (innerWidth /2) - (fontRenderer.getStringWidth("No fluid meters") / 2), innerTop + innerHeight / 2 - fontRenderer.FONT_HEIGHT / 2, 0);
		}
		else
		{
			for(int i = page * 13; i < 13 * (page + 1); i++)
			{
				if (i >= fluidMeters.size())
				{
					break;
				}
				
				TileEntityFluidMeter meter = fluidMeters.get(i);
				fontRenderer.drawString(String.format("%s,%s,%s", meter.getPos().getX(), meter.getPos().getY(), meter.getPos().getZ()), innerLeft + 21, innerTop + 42 + 11 * (i - page * 13), 0);
				FluidStack fluid = FluidRegistry.getFluidStack(meter.getLastFluid(), 1);
				String lastFluid = fluid == null ? "" : fontRenderer.trimStringToWidth(fluid.getLocalizedName(), 100);
				fontRenderer.drawString(lastFluid.isEmpty() ? "---" : lastFluid, innerLeft + 111, innerTop + 42 + 11 * (i - page * 13), 0);
				fontRenderer.drawString(Integer.toString(meter.getFluidCounter()), innerLeft + 211, innerTop + 42 + 11 * (i - page * 13), 0);
			}
			
		}
	}
	
	public void onDataReceived(NBTTagList tagList)
	{
		fluidMeters.clear();
		selectedFluidMeters.clear();
		ImmutableList<TrackedFluidData> currentFluidDatum = register.getTrackedFluidData();
		for(NBTBase tag : tagList)
		{
			if (!(tag instanceof NBTTagCompound))
			{
				continue;
			}
			
			NBTTagCompound compound = (NBTTagCompound)tag;
			
			TileEntityFluidMeter meter = new TileEntityFluidMeter();
			meter.handleUpdateTag(compound);
			for(TrackedFluidData currentData : currentFluidDatum)
			{
				if (currentData.getFluidMeterPos().equals(meter.getPos()))
				{
					meter = null;
					break;
				}
			}
			if (meter == null)
			{
				continue;
			}
			
			fluidMeters.add(meter);
			selectedFluidMeters.add(fluidMeters.size() - 1);
		}
		
		dataReceived = true;
		setupButtons();
	}
	
	public void onSaveResponse(NBTTagCompound updateTag)
	{
		register.handleUpdateTag(updateTag);
		
		mc.displayGuiScreen(new GuiPOSFluidMeterList(register));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == cancel)
		{
			mc.displayGuiScreen(new GuiPOSFluidMeterList(register));
			return;
		}
		
		if (button == add)
		{
			for(GuiButton aButton : buttonList)
			{
				aButton.enabled = false;
			}
			
			POSAddNewFluidMetersPacket addNewFluids = new POSAddNewFluidMetersPacket();
			addNewFluids.registerPos = register.getPos();
			for(int i = 0; i < fluidMeters.size(); i++)
			{
				if (!selectedFluidMeters.contains(i))
				{
					continue;
				}
				
				TileEntityFluidMeter meter = fluidMeters.get(i);
				addNewFluids.newBlockPos.add(meter.getPos());
			}
			
			PacketHandler.INSTANCE.sendToServer(addNewFluids);
		}
		
		if (button == next && page < fluidMeters.size() / 13)
		{
			page++;
			setupButtons();
		}
		
		if (button == prev && page > 0)
		{
			page--;
			setupButtons();
		}
		
		if (button instanceof GuiCheckBox)
		{
			GuiCheckBox checkBox = (GuiCheckBox)button;
			if (checkBox.isChecked())
			{
				selectedFluidMeters.add(checkBox.id);
			}
			else
			{
				selectedFluidMeters.remove(checkBox.id);
			}
		}
	}
}
