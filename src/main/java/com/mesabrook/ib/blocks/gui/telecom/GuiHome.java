package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.gui.sco.GuiStoreMode;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.util.GuiUtil;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHome extends GuiPhoneBase {

	private ResourceLocation dockTexture = new ResourceLocation("wbtc", "textures/gui/telecom/system/dock.png");
	public GuiHome(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	ImageButton button;
	ImageButton button2;
	ImageButton button3;
	ImageButton button4;
	ImageButton button5;
	ImageButton button6;
	ImageButton button7;
	ImageButton button8;
	ImageButton button9;
	ImageButton csNotifications;

	@Override
	protected String getInnerTextureFileName()
	{
		return "wallpapers/gui_phone_bg_" + Integer.toString(phoneStackData.getHomeBackground()) + ".png";
	}

	@Override
	public void initGui() {
		super.initGui();
		// Phone
		button = new ImageButton(0, INNER_X + 5, INNER_Y + 163, 32, 32, phoneStackData.getIconTheme() + "/icn_phone.png", 32, 32);
		buttonList.add(button);

		// Messages
		button2 = new ImageButton(1, button.x + 40, INNER_Y + 163, 32, 32, phoneStackData.getIconTheme() + "/icn_mail.png", 32, 32);
		buttonList.add(button2);

		// Address Book
		button3 = new ImageButton(2, button2.x + 40, INNER_Y + 163, 32, 32, phoneStackData.getIconTheme() + "/icn_contacts.png", 32, 32);
		buttonList.add(button3);

		// Settings
		button4 = new ImageButton(3, button3.x + 40, INNER_Y + 163, 32, 32, phoneStackData.getIconTheme() + "/icn_settings.png", 32, 32);
		buttonList.add(button4);

		// Music App
		button5 = new ImageButton(4, INNER_X + 5, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_musicplayer.png", 32, 32);
		buttonList.add(button5);

		// Calculator App
		button6 = new ImageButton(5, INNER_X + 46, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_calc.png", 32, 32);
		buttonList.add(button6);

		// Help App
		button7 = new ImageButton(6, INNER_X + 86, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_help.png", 32, 32);
		buttonList.add(button7);
		
		// Store Mode Launcher
		button8 = new ImageButton(7, button3.x + 40, INNER_Y + 24, 32, 32, phoneStackData.getIconTheme() + "/icn_storemode.png", 32, 32);
		buttonList.add(button8);
		
		// Magic 8 Ball App
		button9 = new ImageButton(8, INNER_X + 5, INNER_Y + 64, 32, 32, phoneStackData.getIconTheme() + "/icn_eightball.png", 32, 32);
		buttonList.add(button9);
		
		// Company Studio Notifications App
		csNotifications = new ImageButton(9, INNER_X + 46, INNER_Y + 64, 32, 32, new ResourceLocation(Reference.MODID, "textures/app/icon/icn_cstudio.png"), 64, 64, 64, 64);
		buttonList.add(csNotifications);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks)
	{
		super.doDraw(mouseX, mouseY, partialticks);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		Minecraft.getMinecraft().getTextureManager().bindTexture(dockTexture);
		drawScaledCustomSizeModalRect(INNER_X, INNER_Y + 160, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, INNER_TEX_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH - 0, INNER_TEX_HEIGHT - 175, 324, 450);

		if(mouseX >= button.x && mouseY >= button.y && mouseX < button.x + button.width && mouseY < button.y + button.height)
		{
			drawCenteredString(fontRenderer, "Phone", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button2.x && mouseY >= button2.y && mouseX < button2.x + button2.width && mouseY < button2.y + button2.height)
		{
			drawCenteredString(fontRenderer, "Messages", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button3.x && mouseY >= button3.y && mouseX < button3.x + button3.width && mouseY < button3.y + button3.height)
		{
			drawCenteredString(fontRenderer, "Contacts", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button4.x && mouseY >= button4.y && mouseX < button4.x + button4.width && mouseY < button4.y + button4.height)
		{
			drawCenteredString(fontRenderer, "Settings", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button5.x && mouseY >= button5.y && mouseX < button5.x + button5.width && mouseY < button5.y + button5.height)
		{
			drawCenteredString(fontRenderer, "Sound Player", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button6.x && mouseY >= button6.y && mouseX < button6.x + button6.width && mouseY < button6.y + button6.height)
		{
			drawCenteredString(fontRenderer, "Calculator", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button7.x && mouseY >= button7.y && mouseX < button7.x + button7.width && mouseY < button7.y + button7.height)
		{
			drawCenteredString(fontRenderer, "Support", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button8.x && mouseY >= button8.y && mouseX < button8.x + button8.width && mouseY < button8.y + button8.height)
		{
			drawCenteredString(fontRenderer, "Employee Mode", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(mouseX >= button9.x && mouseY >= button9.y && mouseX < button9.x + button9.width && mouseY < button9.y + button9.height)
		{
			drawCenteredString(fontRenderer, "Magic 8 Ball", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if (GuiUtil.isPointWithinBounds(mouseX, mouseY, csNotifications.x, csNotifications.y, csNotifications.width, csNotifications.height))
		{
			drawCenteredString(fontRenderer, "Company Notifications", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);
		}
		else if(phoneStackData.getBatteryLevel() <= 100)
		{
			drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.DARK_RED + "Low Battery").getFormattedText(), INNER_X + 80, INNER_Y + 148, 0xFFFFFF);
		}

		GlStateManager.disableAlpha();
		GlStateManager.color(1, 1, 1);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		switch(button.id) {
			case 0:				
				launchAppViaSplashScreen("icn_phone.png", "Phone", "green", GuiEmptyPhone.class, () ->
				{
					PhoneQueryPacket queryPacket = new PhoneQueryPacket();
	                queryPacket.forNumber = getCurrentPhoneNumber();

	                int nextID = ClientSideHandlers.TelecomClientHandlers.getNextHandlerID();

	                ClientSideHandlers.TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, ClientSideHandlers.TelecomClientHandlers::onPhoneQueryResponseForPhoneApp);
	                queryPacket.clientHandlerCode = nextID;
	                PacketHandler.INSTANCE.sendToServer(queryPacket);
				});
				break;
			case 1:
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "App Coming Soon", 0xFFFFFF));
				break;
			case 2:
				launchAppViaSplashScreen("icn_contacts.png", "Contacts", "green", GuiAddressBook.class);
				break;
			case 3:
				launchAppViaSplashScreen("icn_settings.png", "Settings", "blue", GuiSettings.class);
				break;
			case 4:				
				launchAppViaSplashScreen("icn_musicplayer.png", "Sound Player", "blue", GuiSoundPlayer.class);
				break;
			case 5:
				launchAppViaSplashScreen("icn_calc.png", "Calculator", "red", GuiCalculator.class);
				break;
			case 6:
				try
				{
					ModUtils.openWebLinkThroughMC("https://github.com/RavenholmZombie/Immersibrook/wiki/Minedroid-Help");
				}
				catch (Exception e)
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.misc.error").getFormattedText(), 0xFFFFFF));
					e.printStackTrace();
				}
				break;
			case 7:
				Minecraft.getMinecraft().displayGuiScreen(new GuiStoreMode());
				break;
			case 8:
				launchAppViaSplashScreen("icn_eightball.png", "Magic 8 Ball", "red", GuiMagicEightBall.class);
				break;
			case 9:
				launchAppViaSplashScreen(new ResourceLocation(Reference.MODID, "textures/app/icon/icn_cstudio.png"), "Company Notifications", "blue", GuiCompanyStudioNotifications.class);
				break;
		}
	}
	
	private <NextApp extends GuiPhoneBase> void launchAppViaSplashScreen(String logoPath, String appName, String splashColor, Class<NextApp> nextAppClazz)
	{
		launchAppViaSplashScreen(logoPath, null, appName, splashColor, nextAppClazz, null);
	}
	
	private <NextApp extends GuiPhoneBase> void launchAppViaSplashScreen(String logoPath, String appName, String splashColor, Class<NextApp> nextAppClazz, Runnable postOpenRun)
	{
		launchAppViaSplashScreen(logoPath, null, appName, splashColor, nextAppClazz, postOpenRun);
	}
	
	private <NextApp extends GuiPhoneBase> void launchAppViaSplashScreen(ResourceLocation logoResourceLocation, String appName, String splashColor, Class<NextApp> nextAppClazz)
	{
		launchAppViaSplashScreen(null, logoResourceLocation, appName, splashColor, nextAppClazz, null);
	}
	
	private <NextApp extends GuiPhoneBase> void launchAppViaSplashScreen(String logoPath, ResourceLocation logoResourceLocation, String appName, String splashColor, Class<NextApp> nextAppClazz, Runnable postOpenRun)
	{
		GuiAppSplashScreen<NextApp> guiC = new GuiAppSplashScreen<NextApp>(phoneStack, hand, nextAppClazz, postOpenRun);
		guiC.setLogoPath(logoPath);
		guiC.setLogoResourceLocation(logoResourceLocation);
		guiC.setAppName(appName);
		guiC.setSplashColor(splashColor);
		Minecraft.getMinecraft().displayGuiScreen(guiC);
	}
}
