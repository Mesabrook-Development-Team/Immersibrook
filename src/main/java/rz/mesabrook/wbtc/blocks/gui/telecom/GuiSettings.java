package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;

public class GuiSettings extends GuiPhoneBase {

	public GuiSettings(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		ImageButton button = new ImageButton(0, INNER_X, INNER_Y + 33, INNER_TEX_WIDTH, 26, "btn_lock_screen.png", 324, 52);
		buttonList.add(button);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString("Settings", INNER_X + 3, INNER_Y + 20, 0xFFFFFF);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 0)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsLockScreen(phoneStack, hand));
		}
	}
}
