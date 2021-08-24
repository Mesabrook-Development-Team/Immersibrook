package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import rz.mesabrook.wbtc.items.misc.ItemPhone;
import rz.mesabrook.wbtc.util.Reference;

// TODO: Make this extend GuiPhoneBase and add overrides to not render the top or bottom bars
public class GuiEmptyPhone extends GuiScreen {

	private final ItemStack phoneStack;
	private final EnumHand hand;
	private final ItemPhone.NBTData stackData;
	private final int texWidth = 176;
	private final int texHeight = 222;
	
	public GuiEmptyPhone(ItemStack phoneStack, EnumHand hand)
	{
		this.phoneStack = phoneStack;
		this.hand = hand;
		stackData = new ItemPhone.NBTData();
		stackData.deserializeNBT(phoneStack.getTagCompound());
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		//Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/phone_bezel.png"));
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", String.format("textures/gui/telecom/%s.png", ((ItemPhone)phoneStack.getItem()).getBezelTextureName())));
		drawScaledCustomSizeModalRect((width - texWidth) / 2, (height - texHeight) / 2, 0, 0, 352, 444, texWidth, texHeight, 512, 512);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public ItemStack getPhoneStack() {
		return phoneStack;
	}

	public EnumHand getHand() {
		return hand;
	}
	
	public String getPhoneNumber()
	{
		return stackData.getPhoneNumberString();
	}
}
