package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.telecom.GetReceptionStrengthPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SpecialBezelRandomizer;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiPhoneBase extends GuiScreen
{
	protected ItemStack phoneStack;
	protected final EnumHand hand;
	protected ItemPhone.NBTData phoneStackData;
	
	protected final int OUTER_TEX_WIDTH = 176;
	protected final int OUTER_TEX_HEIGHT = 222;
	protected final int INNER_TEX_WIDTH = 162;
	protected final int INNER_TEX_HEIGHT = 222;
	protected final int INNER_TEX_X_OFFSET = 7;
	protected final int INNER_TEX_Y_OFFSET = 7;
	protected final int GUI_WIDTH = 352;
	protected final int GUI_HEIGHT = 444;
	protected final double dLittleFont = 0.75;
	protected final double uLittleFont = 1 / 0.75;
	protected final double uBigFont = 2.1;
	protected final double dBigFont = 1 / 2.1;
	protected int OUTER_X;
	protected int OUTER_Y;
	protected int INNER_X;
	protected int INNER_Y;
	protected int WIDTH_SCALE;
	protected int HEIGHT_SCALE;
	private int BACK_X;
	private int HOME_X;
	private boolean firstTick = true;
	private int timeToNextBezel;
	private int timeToRefreshStack = 200;
	
	private final int STATUS_BAR_HEIGHT = 14;
	private SignalStrengths signalStrength = SignalStrengths.unknown;
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public GuiPhoneBase(ItemStack phoneStack, EnumHand hand)
	{
		this.phoneStack = phoneStack;
		this.hand = hand;
		this.phoneStackData = new ItemPhone.NBTData();
		this.phoneStackData.deserializeNBT(this.phoneStack.getTagCompound());
	}
	
	public void setSignalStrength(SignalStrengths signalStrength)
	{
		this.signalStrength = signalStrength;
	}
	
	public SignalStrengths getSignalStrength()
	{
		return signalStrength;
	}
	
	@Override
	public void initGui() {
		super.initGui();

		try
		{
			OUTER_X = (width - OUTER_TEX_WIDTH) / 2;
			OUTER_Y = (height - OUTER_TEX_HEIGHT) / 2;
			INNER_X = OUTER_X + INNER_TEX_X_OFFSET;
			INNER_Y = OUTER_Y + INNER_TEX_Y_OFFSET;
			WIDTH_SCALE = (int)(1 / ((double)OUTER_TEX_WIDTH / GUI_WIDTH));
			HEIGHT_SCALE = (int)(1 / ((double)OUTER_TEX_HEIGHT / GUI_HEIGHT));
			BACK_X = INNER_X + (INNER_TEX_WIDTH / 4) - 8;
			HOME_X = (INNER_X + INNER_TEX_WIDTH) - (INNER_TEX_WIDTH / 4) - 8;

			ImageButton homeButton = new ImageButton(999, INNER_X + INNER_TEX_WIDTH / 2 - 4, INNER_Y + INNER_TEX_HEIGHT - 23, 8, 8, phoneStackData.getIconTheme() + "/gui_btn_home.png", 32, 32);
			buttonList.add(homeButton);

			homeButton.visible = renderControlBar();

			firstTick = true;

			// Back button to be implemented when determined necessary
//			ImageButton backButton = new ImageButton(998, INNER_X + (INNER_TEX_WIDTH / 4) - 4, INNER_Y + INNER_TEX_HEIGHT - 23, 8, 8, "gui_btn_back.png", 32, 32);
//			buttonList.add(backButton);
		}
		catch(Exception ex)
		{
			GuiPhoneCrashed crashGui = new GuiPhoneCrashed(phoneStack, hand);

			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			ex.printStackTrace(printWriter);
			printWriter.flush();

			crashGui.setErrorTitle(ex.getMessage());
			crashGui.setErrorStackTrace(writer.toString());

			Minecraft.getMinecraft().displayGuiScreen(crashGui);
		}
	}
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		try
		{
			if (firstTick)
			{
				if (!isPhoneSetup())
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneActivate(phoneStack, hand));
					return;
				}

				firstDrawingTick(mouseX, mouseY, partialTicks);
				firstTick = false;
			}
			drawDefaultBackground();
			
			if (--timeToRefreshStack <= 0)
			{
				phoneStack = Minecraft.getMinecraft().player.getHeldItem(hand);
				phoneStackData = NBTData.getFromItemStack(phoneStack);
				
				timeToRefreshStack = 200;
			}

			if(phoneStackData.getIsPhoneDead())
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiDeadPhone(phoneStack, hand));

				ClientSoundPacket soundPacket = new ClientSoundPacket();
				soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
				soundPacket.soundName = "phone_battery_low";
				PacketHandler.INSTANCE.sendToServer(soundPacket);
			}

			// Phone border
			if(phoneStack.getItem() == ModItems.PHONE_SPECIAL)
			{
				if(ModConfig.specialPhoneBezel)
				{
					timeToNextBezel++;
				}
				if(timeToNextBezel > ModConfig.colorInterval)
				{
					SpecialBezelRandomizer.RandomBezel();
					timeToNextBezel = 0;
				}
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/phone_bezel_" + SpecialBezelRandomizer.bezel + ".png"));
				drawScaledCustomSizeModalRect(OUTER_X, OUTER_Y, 0, 0, OUTER_TEX_WIDTH * WIDTH_SCALE, OUTER_TEX_HEIGHT * HEIGHT_SCALE, OUTER_TEX_WIDTH, OUTER_TEX_HEIGHT, 512, 512);
			}
			else
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", String.format("textures/gui/telecom/%s.png", ((ItemPhone)phoneStack.getItem()).getBezelTextureName())));
				drawScaledCustomSizeModalRect(OUTER_X, OUTER_Y, 0, 0, OUTER_TEX_WIDTH * WIDTH_SCALE, OUTER_TEX_HEIGHT * HEIGHT_SCALE, OUTER_TEX_WIDTH, OUTER_TEX_HEIGHT, 512, 512);
			}

			// Background
			String innerTexName = getInnerTextureFileName();
			if (innerTexName != null)
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + innerTexName));
				drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, INNER_TEX_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, INNER_TEX_HEIGHT - 14, 324, 450);
			}

			// Inner content
			doDraw(mouseX, mouseY, partialTicks);

			GlStateManager.color(1, 1, 1);
			// Upper bar
			if(renderTopBar())
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + phoneStackData.getIconTheme() + "/gui_top_statusbar.png"));
				drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, STATUS_BAR_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, STATUS_BAR_HEIGHT, 512, 512);

				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + signalStrength.getTextureName()));
				drawScaledCustomSizeModalRect(INNER_X + INNER_TEX_WIDTH - 34, INNER_Y - 1, 0, 0, 16, 16, 16, 16, 16, 16);

				fontRenderer.drawString(getTime(), INNER_X + 2, INNER_Y + 3, 0xFFFFFF);
				fontRenderer.drawString("Bell", INNER_X + INNER_TEX_WIDTH - 52, INNER_Y + 3, 0xFFFFFF);

				if(phoneStackData.getIsDebugModeEnabled())
				{
					Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + "tray_debug.png"));
					drawScaledCustomSizeModalRect(INNER_X + INNER_TEX_WIDTH - 66, INNER_Y + 2, 0, 0, 16, 16, 10, 10, 16, 16);
				}
				
				int levelPart = ModConfig.smartphoneMaxBattery / 5;
				int chargeLevel = phoneStackData.getBatteryLevel() / levelPart;
				if (chargeLevel > 4)
				{
					chargeLevel = 4;
				}
				
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/bat_" + chargeLevel + ".png"));
				drawScaledCustomSizeModalRect(INNER_X + INNER_TEX_WIDTH - 18, INNER_Y - 1, 0, 0, 16, 16, 16, 16, 16, 16);
			}

			// Lower bar
			if (renderControlBar())
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + phoneStackData.getIconTheme() + "/gui_navbar.png"));
				drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, INNER_TEX_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, INNER_TEX_HEIGHT + 1, 512, 512);
			}

			super.drawScreen(mouseX, mouseY, partialTicks);

			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).tick(INNER_X, INNER_Y, INNER_TEX_WIDTH, INNER_TEX_HEIGHT);
		}
		catch(Exception ex)
		{
			GuiPhoneCrashed crashGui = new GuiPhoneCrashed(phoneStack, hand);

			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			ex.printStackTrace(printWriter);
			printWriter.flush();

			crashGui.setErrorTitle(ex.getMessage());
			crashGui.setErrorStackTrace(writer.toString());

			Minecraft.getMinecraft().displayGuiScreen(crashGui);
		}
	}

	private boolean isPhoneSetup()
	{
		return phoneStackData.getPhoneNumber() != 0;
	}
	
	protected void doDraw(int mouseX, int mouseY, float partialticks){}
	
	protected void firstDrawingTick(int mouseX, int mouseY, float partialTicks)
	{
		GetReceptionStrengthPacket packet = new GetReceptionStrengthPacket();
		PacketHandler.INSTANCE.sendToServer(packet);
	}
	
	protected String getTime()
	{
		long time = Minecraft.getMinecraft().world.getWorldTime();
		int hours = (int) ((Math.floor(time / 1000.0) + 6) % 24); // '6' is the offset
	    int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
	    return String.format("%02d:%02d", hours, minutes);
	}

	protected String getIRLTime()
	{
		LocalDateTime timeObject = LocalDateTime.now();
		DateTimeFormatter formattedTime;

		if(!phoneStackData.getShowingMilitaryIRLTime())
		{
			// AM-PM
			formattedTime = DateTimeFormatter.ofPattern("h:mm:ss a");
		}
		else
		{
			// 24-hr
			formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");
		}
		String formattedTimeConversion = timeObject.format(formattedTime);
		return formattedTimeConversion + " IRL";
	}
	
	protected abstract String getInnerTextureFileName();

	protected int scale(int number, double scale)
	{
		return (int)(number * scale);
	}

	public static String getFormattedPhoneNumber(String number)
	{
		if (number.equals(Reference.PHONE_CONFERENCE_NAME))
		{
			return number;
		}
		String formattedNumber = number;
		if (formattedNumber.length() >= 3)
		{
			formattedNumber = formattedNumber.substring(0, 3) + "-" + formattedNumber.substring(3, formattedNumber.length());
		}
		return formattedNumber;
	}

	protected String truncateIfExceeds(FontRenderer font, String text, int availableSpace, double scale)
	{
		double fontWidth = font.getStringWidth(text);
		if (fontWidth > availableSpace / scale)
		{
			text = font.trimStringToWidth(text, (int)(availableSpace / scale));
			text = text.substring(0, text.length() - 3) + "...";
		}
		
		return text;
	}
	
	public ItemStack getPhoneStack() {
		return phoneStack;
	}

	public EnumHand getHand() {
		return hand;
	}

	public String getCurrentPhoneNumber()
	{
		return phoneStackData.getPhoneNumberString();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		try
		{
			super.actionPerformed(button);

			// Home Button
			if (button.id == 999)
			{
				if (isPhoneUnlocked)
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiHome(phoneStack, hand));
				}
				else
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiLockScreen(phoneStack, hand));
				}
			}
		}
		catch(Exception ex)
		{
			GuiPhoneCrashed crashGui = new GuiPhoneCrashed(phoneStack, hand);

			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			ex.printStackTrace(printWriter);
			printWriter.flush();

			crashGui.setErrorTitle(ex.getMessage());
			crashGui.setErrorStackTrace(writer.toString());

			Minecraft.getMinecraft().displayGuiScreen(crashGui);
		}
	}

	protected boolean renderControlBar() { return true; }
	protected boolean renderTopBar() {return true;}
	
	public static boolean lastGuiWasPhone;
	public static boolean isPhoneUnlocked;
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();

		if (!lastGuiWasPhone)
		{
			isPhoneUnlocked = false;
			try
			{
				ClientSoundPacket packet = new ClientSoundPacket();
				packet.pos = Minecraft.getMinecraft().player.getPosition();
				packet.modID = Reference.MODID;
				packet.soundName = "phone_off";
				packet.useDelay = false;
				PacketHandler.INSTANCE.sendToServer(packet);
			}
			catch(NullPointerException ex)
			{
				Main.logger.error("[" + Reference.MODNAME + "] An error occurred in " + GuiPhoneBase.class.getName());
				Main.logger.error(ex);
				Main.logger.error("[" + Reference.MODNAME + "] Please report this error to us.");
			}
		}
		lastGuiWasPhone = false;
	}
}
