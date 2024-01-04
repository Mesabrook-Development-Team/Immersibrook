package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.ArrayList;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.TrackedFluidData;
import com.mesabrook.ib.net.sco.POSRemoveFluidMeterPacket;
import com.mesabrook.ib.net.sco.POSResetFluidMeterCounterPacket;
import com.mesabrook.ib.net.sco.POSUpdateFluidMeterNamePacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

public class GuiPOSFluidMeterList extends GuiPOSMainBase {

	private static final String deleteButtonIdentifier = "delete";
	private static final String resetButtonIdentifier = "reset";
	
	private GuiImageLabelButton back;
	private GuiImageLabelButton add;
	private ImageButton prev;
	private ImageButton next;
	
	private ArrayList<GuiTextField> nameFields = new ArrayList<>();
	
	private int page = 0;	
	
	public GuiPOSFluidMeterList(TileEntityRegister register) {
		super(register);
	}

	@Override
	protected String getBackingTextureName() {
		return "sco_gui_grid.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		back = new GuiImageLabelButton(0, innerLeft + 7, innerTop + innerHeight - 20, 40, 13, "   Back", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 12, 12, 12, 12, ImageOrientation.Left)
				.setEnabledColor(0);
		add = new GuiImageLabelButton(0, innerLeft + innerWidth - 42, innerTop + innerHeight - 20, 35, 13, "Add   ", new ResourceLocation(Reference.MODID, "textures/gui/add.png"), 10, 10, 10, 10, ImageOrientation.Right)
				.setEnabledColor(0x00FF00);
		prev = new ImageButton(0, innerLeft + (innerWidth / 2) - 52, innerTop + innerHeight - 21, 16, 16, new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16);
		next = new ImageButton(0, innerLeft + (innerWidth / 2) + 35, innerTop + innerHeight - 21, 16, 16, new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_right.png"), 16, 16, 16, 16);
		
		setupButtonsAndFields();
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		fontRenderer.drawString("Meter Position", innerLeft + 10, innerTop + 29, 0);
		fontRenderer.drawString("Name", innerLeft + 100, innerTop + 29, 0);
		fontRenderer.drawString("Opt", innerLeft + innerWidth - 29, innerTop + 29, 0);
		fontRenderer.drawString(String.format("Page %s/%s", page + 1, (register.getTrackedFluidData().size() - 1) / 13 + 1), innerLeft + (innerWidth / 2) - fontRenderer.getStringWidth(String.format("Page %s/%s", page + 1, register.getTrackedFluidData().size() / 13)) / 2, innerTop + innerHeight - 17, 0);
		
		if (register.getTrackedFluidData().size() <= 0)
		{
			fontRenderer.drawString("No fluid meters", innerLeft + (innerWidth /2) - (fontRenderer.getStringWidth("No fluid meters") / 2), innerTop + innerHeight / 2 - fontRenderer.FONT_HEIGHT / 2, 0);
		}
		else
		{
			for(GuiTextField field : nameFields)
			{
				field.drawTextBox();
			}
			
			ImmutableList<TrackedFluidData> fluidDatum = register.getTrackedFluidData();
			for(int i = page * 13; i < 13 * (page + 1); i++)
			{
				if (i >= fluidDatum.size())
				{
					break;
				}
				
				TrackedFluidData fluidData = fluidDatum.get(i);
				fontRenderer.drawString(String.format("%s,%s,%s", fluidData.getFluidMeterPos().getX(), fluidData.getFluidMeterPos().getY(), fluidData.getFluidMeterPos().getZ()), innerLeft + 10, innerTop + 42 + 11 * (i - page * 13), 0);
			}
		}
	}
	
	private void setupButtonsAndFields()
	{
		nameFields.clear();
		buttonList.clear();
		buttonList.add(add);
		buttonList.add(back);
		buttonList.add(prev);
		buttonList.add(next);
		
		ImmutableList<TrackedFluidData> fluidDatum = register.getTrackedFluidData();
		
		prev.enabled = page > 0;
		next.enabled = page < (fluidDatum.size() - 1) / 13;
		
		for(int i = page * 13; i < 13 * (page + 1); i++)
		{
			if (i >= fluidDatum.size())
			{
				break;
			}
			
			TrackedFluidData fluidData = fluidDatum.get(i);
			
			GuiTextField testText = new GuiTextField(i, fontRenderer, innerLeft + 100, innerTop + 41 + 11 * (i - page * 13), 120, fontRenderer.FONT_HEIGHT);
			testText.setText(fluidData.getName());
			nameFields.add(testText);
			
			ImageButton deleteButton = new ImageButton(i + 1, innerLeft + innerWidth - 18, innerTop + 41 + 11 * (i - page * 13), 9, 9, new ResourceLocation(Reference.MODID, "textures/gui/sco/red_x.png"), 16, 16, 16, 16);
			deleteButton.displayString = deleteButtonIdentifier;
			buttonList.add(deleteButton);
			
			ImageButton resetButton = new ImageButton(i + 1, innerLeft + innerWidth - 29, innerTop + 41 + 11 * (i - page * 13), 9, 9, new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_undo.png"), 16, 16, 16, 16);
			resetButton.displayString = resetButtonIdentifier;
			buttonList.add(resetButton);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			mc.displayGuiScreen(new GuiPOSAdmin(register));
			return;
		}
		
		if (button == add)
		{
			mc.displayGuiScreen(new GuiPOSFluidMeterAddRange(register));
			return;
		}
		
		if (button == prev && page > 0)
		{
			page--;
			setupButtonsAndFields();
		}
		
		if (button == next && page < (register.getTrackedFluidData().size() - 1) / 13)
		{
			page++;
			setupButtonsAndFields();
		}
		
		if (button.id > 0)
		{
			TrackedFluidData fluidData = register.getTrackedFluidData().get(button.id - 1);
			if (deleteButtonIdentifier.equalsIgnoreCase(button.displayString))
			{
				POSRemoveFluidMeterPacket remove = new POSRemoveFluidMeterPacket();
				remove.registerPos = register.getPos();
				remove.fluidMeterPos = fluidData.getFluidMeterPos();
				PacketHandler.INSTANCE.sendToServer(remove);
				
				register.removeTrackedFluidData(fluidData.getFluidMeterPos());
				
				setupButtonsAndFields();
			}
			
			if (resetButtonIdentifier.equalsIgnoreCase(button.displayString))
			{
				POSResetFluidMeterCounterPacket reset = new POSResetFluidMeterCounterPacket();
				reset.fluidMeterPos = fluidData.getFluidMeterPos();
				PacketHandler.INSTANCE.sendToServer(reset);
				
				ImageButton resetButton = (ImageButton)button;
				resetButton.setTextureRL(new ResourceLocation(Reference.MODID, "textures/misc/go.png"));
				resetButton.setTexHeight(32);
				resetButton.setTexWidth(32);
				resetButton.setUvHeight(32);
				resetButton.setUvWidth(32);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		for(GuiTextField nameField : nameFields)
		{
			nameField.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		for(GuiTextField nameField : nameFields)
		{
			if (nameField.textboxKeyTyped(typedChar, keyCode))
			{
				TrackedFluidData fluidData = register.getTrackedFluidData().get(nameField.getId());
				fluidData.setName(nameField.getText());
				
				POSUpdateFluidMeterNamePacket updatePacket = new POSUpdateFluidMeterNamePacket();
				updatePacket.registerPos = register.getPos();
				updatePacket.fluidMeterPos = fluidData.getFluidMeterPos();
				updatePacket.newName = nameField.getText();
				PacketHandler.INSTANCE.sendToServer(updatePacket);
			}
		}
	}
}
