package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.gui.ImageButton;

import com.mesabrook.ib.init.ModSounds;
import com.mesabrook.ib.net.telecom.InitiateCallPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiPhoneCall extends GuiPhoneBase {

	private String currentlyTypedNumber = "";
	private ImageButton call;
	private LabelButton keypad;
	private LabelButton recents;
	private ImageButton contacts;

	public GuiPhoneCall(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	public GuiPhoneCall(ItemStack phoneStack, EnumHand hand, String preloadedNumber)
	{
		this(phoneStack, hand);
		currentlyTypedNumber = preloadedNumber;
	}

	@Override
	protected String getInnerTextureFileName() {
		return phoneStackData.getIconTheme() + "/app_screen.png";
	}

	@Override
	public void initGui() {
		super.initGui();

		for(int i = 0; i < 9; i++)
		{
			int x = ((i % 3) - 1) * 25;
			int y = ((i / 3) - 1) * 25;

			ImageButton digit = new ImageButton(i + 1, INNER_X + INNER_TEX_WIDTH / 2 + x - 8, INNER_Y + INNER_TEX_HEIGHT / 2 + y + 5, 16, 16, phoneStackData.getIconTheme() + "/numpad/numpad_" + (i + 1) + ".png", 16, 16);
			buttonList.add(digit);
		}

		ImageButton digit0 = new ImageButton(0, INNER_X + INNER_TEX_WIDTH / 2 - 8, INNER_Y + INNER_TEX_HEIGHT / 2 + 55, 16, 16, phoneStackData.getIconTheme() + "/numpad/numpad_0.png", 16, 16);
		buttonList.add(digit0);

		call = new ImageButton(10, INNER_X + INNER_TEX_WIDTH / 2 + 17, INNER_Y + INNER_TEX_HEIGHT / 2 + 55, 16, 16, phoneStackData.getIconTheme() + "/numpad/numpad_call.png", 16, 16);
		contacts = new ImageButton(11, INNER_X + INNER_TEX_WIDTH / 5 + 16, INNER_Y + INNER_TEX_HEIGHT / 2 + 55, 16, 16, phoneStackData.getIconTheme() + "/icn_contacts.png", 16, 16);
		buttonList.add(call);
		buttonList.add(contacts);
		keypad = new LabelButton(11, 0, INNER_Y + INNER_TEX_HEIGHT - 34, "Keypad", 0xFFFFFF);
		keypad.x = INNER_X + INNER_TEX_WIDTH / 2 - keypad.width - 20;
		buttonList.add(keypad);

		recents = new LabelButton(12, 0, INNER_Y + INNER_TEX_HEIGHT - 34, "Recents", 0xFFFFFF);
		recents.x = INNER_X + INNER_TEX_WIDTH / 2 + 20;
		buttonList.add(recents);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {

		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		fontRenderer.drawString("Phone", scale(INNER_X + 4, uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);

		GlStateManager.scale(uBigFont, uBigFont, uBigFont);
		drawCenteredString(fontRenderer, getFormattedPhoneNumber(currentlyTypedNumber) + "|", scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 60, dBigFont), 0xFFFFFF);
		GlStateManager.scale(dBigFont, dBigFont, dBigFont);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.id >= 0 && button.id <= 9 && currentlyTypedNumber.length() < 7)
		{
			currentlyTypedNumber += Integer.toString(button.id);

			IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
			SoundEvent dtmfEvent = soundRegistry.getValue(new ResourceLocation(Reference.MODID, "dtmf_" + button.id));

			ISound dtmfSound = PositionedSoundRecord.getMasterRecord(dtmfEvent, 1F);
			Minecraft.getMinecraft().getSoundHandler().playSound(dtmfSound);
		}

		if (button.id == 10)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;

			try
			{
				if(player.world.provider.getDimension() == 0)
				{
					PositionedSoundRecord startSound = PositionedSoundRecord.getMasterRecord(ModSounds.STARTCALL, 1F);
					Minecraft.getMinecraft().getSoundHandler().playSound(startSound);

					if (currentlyTypedNumber == null || currentlyTypedNumber.equals(""))
					{
						PositionedSoundRecord sit = PositionedSoundRecord.getMasterRecord(ModSounds.SIT_1, 1F);
						Minecraft.getMinecraft().getSoundHandler().playSound(sit);

						GuiCallEnd badCall = new GuiCallEnd(getPhoneStack(), getHand(), "");
						badCall.setMessage("Phone number required!");
						mc.displayGuiScreen(badCall);

						return;
					}

					if (currentlyTypedNumber.equals(getCurrentPhoneNumber()))
					{
						PositionedSoundRecord sit = PositionedSoundRecord.getMasterRecord(ModSounds.SIT_1, 1F);
						Minecraft.getMinecraft().getSoundHandler().playSound(sit);

						GuiCallEnd badCall = new GuiCallEnd(getPhoneStack(), getHand(), currentlyTypedNumber);
						badCall.setMessage("Can't call same phone!");
						mc.displayGuiScreen(badCall);

						return;
					}

					Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneCalling(phoneStack, hand, currentlyTypedNumber));

					InitiateCallPacket packet = new InitiateCallPacket();
					packet.fromNumber = getCurrentPhoneNumber();
					packet.toNumber = currentlyTypedNumber;
					PacketHandler.INSTANCE.sendToServer(packet);
				}
				else
				{
					PositionedSoundRecord sit = PositionedSoundRecord.getMasterRecord(ModSounds.SIT_5, 1F);
					Minecraft.getMinecraft().getSoundHandler().playSound(sit);
					GuiCallEnd badCall = new GuiCallEnd(getPhoneStack(), getHand(), currentlyTypedNumber);
					badCall.setMessage("Unsupported Dimension");
					mc.displayGuiScreen(badCall);
				}
			}
			catch(Exception ex)
			{
				PositionedSoundRecord sit = PositionedSoundRecord.getMasterRecord(ModSounds.SIT_4, 1F);
				Minecraft.getMinecraft().getSoundHandler().playSound(sit);
				GuiCallEnd badCall = new GuiCallEnd(getPhoneStack(), getHand(), currentlyTypedNumber);
				badCall.setMessage("Unable to complete call");
				mc.displayGuiScreen(badCall);
				Main.logger.error(ex.toString());
			}
		}
		if (button.id == 12)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneRecents(phoneStack, hand));
		}

		if(button == contacts)
		{
			GuiAppSplashScreen<GuiAddressBook> guiA = new GuiAppSplashScreen<GuiAddressBook>(phoneStack, hand, GuiAddressBook.class);
			guiA.setLogoPath("icn_contacts.png");
			guiA.setAppName("Contacts");
			guiA.setSplashColor("green");
			Minecraft.getMinecraft().displayGuiScreen(guiA);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);

		if ((keyCode == Keyboard.KEY_DELETE || keyCode == Keyboard.KEY_BACK) && currentlyTypedNumber.length() > 0)
		{
			currentlyTypedNumber = currentlyTypedNumber.substring(0, currentlyTypedNumber.length() - 1);
		}
		else if (keyCode == Keyboard.KEY_NUMPADENTER || keyCode == Keyboard.KEY_RETURN)
		{
			this.mouseClicked(call.x + 1, call.y + 1, 0);
		}
		else if (Character.isDigit(typedChar))
		{
			int pressedNumber = Integer.parseInt(Character.toString(typedChar));

			for(GuiButton button : buttonList)
			{
				if (button.id == pressedNumber)
				{
					this.mouseClicked(button.x + 1, button.y + 1, 0);
					break;
				}
			}
		}
	}
}
