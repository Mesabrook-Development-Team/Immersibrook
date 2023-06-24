package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.net.*;

@SideOnly(Side.CLIENT)
public class GuiHome extends GuiPhoneBase {

	public GuiHome(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName()
	{
		return "wallpapers/gui_phone_bg_" + Integer.toString(phoneStackData.getHomeBackground()) + ".png";
	}

	@Override
	public void initGui() {
		super.initGui();
		// Phone
		ImageButton button = new ImageButton(0, INNER_X + 5, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_phone.png", 32, 32);
		buttonList.add(button);

		// Messages
		ImageButton button2 = new ImageButton(1, INNER_X + 46, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_mail.png", 32, 32);
		buttonList.add(button2);

		// Address Book
		ImageButton button3 = new ImageButton(2, INNER_X + 86, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_contacts.png", 32, 32);
		buttonList.add(button3);

		// Settings
		ImageButton button4 = new ImageButton(3, INNER_X + 126, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_settings.png", 32, 32);
		buttonList.add(button4);

		// Music App
		ImageButton button5 = new ImageButton(4, INNER_X + 5, INNER_Y + 65, 32, 32, phoneStackData.getIconTheme() + "/icn_musicplayer.png", 32, 32);
		buttonList.add(button5);

		// Calculator App
		ImageButton button6 = new ImageButton(5, INNER_X + 46, INNER_Y + 65, 32, 32, phoneStackData.getIconTheme() + "/icn_calc.png", 32, 32);
		buttonList.add(button6);

		// Help App
		ImageButton button7 = new ImageButton(6, INNER_X + 86, INNER_Y + 65, 32, 32, phoneStackData.getIconTheme() + "/icn_help.png", 32, 32);
		buttonList.add(button7);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		switch(button.id) {
			case 0:
				Minecraft.getMinecraft().displayGuiScreen(new GuiEmptyPhone(phoneStack, hand));
				
				PhoneQueryPacket queryPacket = new PhoneQueryPacket();
				queryPacket.forNumber = getCurrentPhoneNumber();
				
				int nextID = TelecomClientHandlers.getNextHandlerID();
				
				TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, TelecomClientHandlers::onPhoneQueryResponseForPhoneApp);
				queryPacket.clientHandlerCode = nextID;
				PacketHandler.INSTANCE.sendToServer(queryPacket);
				break;
			case 1:
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "App Coming Soon", 0xFFFFFF));
				break;
			case 2:
				Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBook(phoneStack, hand));
				break;
			case 3:
				Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
				break;
			case 4:
				Minecraft.getMinecraft().displayGuiScreen(new GuiSoundPlayer(phoneStack, hand));
				break;
			case 5:
				Minecraft.getMinecraft().displayGuiScreen(new GuiCalculatorSplash(phoneStack, hand));
				break;
			case 6:
				try
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.misc.urlopened").getFormattedText(), 0xFFFFFF));
					ModUtils.openWebLink(new URI("https://github.com/RavenholmZombie/Immersibrook/wiki/Minedroid-Help"));
				}
				catch (URISyntaxException e)
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.misc.error").getFormattedText(), 0xFFFFFF));
					e.printStackTrace();
				}
				break;
		}
	}
}
