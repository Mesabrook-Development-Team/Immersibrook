package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.net.telecom.GetReceptionStrengthPacket;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

@SideOnly(Side.CLIENT)
public abstract class GuiPhoneBase extends GuiScreen {
	protected final ItemStack phoneStack;
	protected final EnumHand hand;
	
	protected final int OUTER_TEX_WIDTH = 176;
	protected final int OUTER_TEX_HEIGHT = 222;
	protected final int INNER_TEX_WIDTH = 162;
	protected final int INNER_TEX_HEIGHT = 222;
	protected final int INNER_TEX_X_OFFSET = 7;
	protected final int INNER_TEX_Y_OFFSET = 7;
	protected final int GUI_WIDTH = 352;
	protected final int GUI_HEIGHT = 444;
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
	
	public GuiPhoneBase(ItemStack phoneStack, EnumHand hand)
	{
		this.phoneStack = phoneStack;
		this.hand = hand;
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
	}
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (firstTick)
		{
			firstDrawingTick(mouseX, mouseY, partialTicks);
			firstTick = false;
		}
		drawDefaultBackground();
		
		// Phone border
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/phone_bezel.png"));
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
		
		// Upper bar		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/gui_top_statusbar.png"));
		drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, STATUS_BAR_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, STATUS_BAR_HEIGHT, 512, 512);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + signalStrength.getTextureName()));
		drawScaledCustomSizeModalRect(INNER_X + INNER_TEX_WIDTH - 16, INNER_Y - 1, 0, 0, 16, 16, 14, 14, 16, 16);
		
		fontRenderer.drawString(getTime(), INNER_X + 2, INNER_Y + 3, 0xFFFFFF);
		
		// Lower bar
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/gui_navbar.png"));
		drawScaledCustomSizeModalRect(INNER_X, INNER_Y, 0, 0, INNER_TEX_WIDTH * WIDTH_SCALE, INNER_TEX_HEIGHT * HEIGHT_SCALE, INNER_TEX_WIDTH, INNER_TEX_HEIGHT, 512, 512);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/gui_btn_back.png"));
		drawScaledCustomSizeModalRect(BACK_X, INNER_Y + INNER_TEX_HEIGHT - 23, 0, 0, 32, 32, 8, 8, 32, 32);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/gui_btn_home.png"));
		drawScaledCustomSizeModalRect(HOME_X, INNER_Y + INNER_TEX_HEIGHT - 23, 0, 0, 32, 32, 8, 8, 32, 32);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	protected void doDraw(int mouseX, int mouseY, float partialticks){}
	
	protected void firstDrawingTick(int mouseX, int mouseY, float partialTicks)
	{
		GetReceptionStrengthPacket packet = new GetReceptionStrengthPacket();
		PacketHandler.INSTANCE.sendToServer(packet);
	}
	
	private String getTime()
	{
		long time = Minecraft.getMinecraft().world.getWorldTime();
		int hours = (int) ((Math.floor(time / 1000.0) + 8) % 24); // '8' is the offset
	    int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
	    return String.format("%02d:%02d", hours, minutes);
	}
	
	protected abstract String getInnerTextureFileName();
}
