package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.net.telecom.GetReceptionStrengthPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiPhoneBase extends GuiScreen {
	protected final ItemStack phoneStack;
	protected final EnumHand hand;
	protected final ItemPhone.NBTData phoneStackData;
	
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
		
		OUTER_X = (width - OUTER_TEX_WIDTH) / 2;
		OUTER_Y = (height - OUTER_TEX_HEIGHT) / 2;
		INNER_X = OUTER_X + INNER_TEX_X_OFFSET;
		INNER_Y = OUTER_Y + INNER_TEX_Y_OFFSET;
		WIDTH_SCALE = (int)(1 / ((double)OUTER_TEX_WIDTH / GUI_WIDTH));
		HEIGHT_SCALE = (int)(1 / ((double)OUTER_TEX_HEIGHT / GUI_HEIGHT));
		BACK_X = INNER_X + (INNER_TEX_WIDTH / 4) - 8;
		HOME_X = (INNER_X + INNER_TEX_WIDTH) - (INNER_TEX_WIDTH / 4) - 8;
		
		ImageButton homeButton = new ImageButton(999, INNER_X + INNER_TEX_WIDTH / 2 - 4, INNER_Y + INNER_TEX_HEIGHT - 23, 8, 8, "gui_btn_home.png", 32, 32);
		buttonList.add(homeButton);
		
		homeButton.visible = renderControlBar();
		
		firstTick = true;

		// Back button to be implemented when determined necessary
//		ImageButton backButton = new ImageButton(998, INNER_X + (INNER_TEX_WIDTH / 4) - 4, INNER_Y + INNER_TEX_HEIGHT - 23, 8, 8, "gui_btn_back.png", 32, 32);
//		buttonList.add(backButton);
	}
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
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
		
		// Phone border
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", String.format("textures/gui/telecom/%s.png", ((ItemPhone)phoneStack.getItem()).getBezelTextureName())));
		drawScaledCustomSizeModalRect(OUTER_X, OUTER_Y, 0, 0, OUTER_TEX_WIDTH * WIDTH_SCALE, OUTER_TEX_HEIGHT * HEIGHT_SCALE, OUTER_TEX_WIDTH, OUTER_TEX_HEIGHT, 512, 512);
		
		// Background
		String innerTexName = getInnerTextureFileName();
		if (innerTexName != null)
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + innerTexName));
			drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, INNER_TEX_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, INNER_TEX_HEIGHT, 512, 512);
		}
		
		// Inner content
		doDraw(mouseX, mouseY, partialTicks);

		GlStateManager.color(1, 1, 1);
		// Upper bar
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/gui_top_statusbar.png"));
		drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, STATUS_BAR_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, STATUS_BAR_HEIGHT, 512, 512);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + signalStrength.getTextureName()));
		drawScaledCustomSizeModalRect(INNER_X + INNER_TEX_WIDTH - 16, INNER_Y - 1, 0, 0, 16, 16, 14, 14, 16, 16);
		
		fontRenderer.drawString(getTime(), INNER_X + 2, INNER_Y + 3, 0xFFFFFF);
		
		// Lower bar
		if (renderControlBar())
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/gui_navbar.png"));
			drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, INNER_TEX_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, INNER_TEX_HEIGHT, 512, 512);
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).tick(INNER_X, INNER_Y, INNER_TEX_WIDTH, INNER_TEX_HEIGHT);
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
		int hours = (int) ((Math.floor(time / 1000.0) + 8) % 24); // '8' is the offset
	    int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
	    return String.format("%02d:%02d", hours, minutes);
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
	protected void actionPerformed(GuiButton button) throws IOException {
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

	protected boolean renderControlBar() { return true; }
	
	public static boolean lastGuiWasPhone;
	public static boolean isPhoneUnlocked;
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		
		if (!lastGuiWasPhone)
		{
			isPhoneUnlocked = false;
		}
		lastGuiWasPhone = false;
	}
}
