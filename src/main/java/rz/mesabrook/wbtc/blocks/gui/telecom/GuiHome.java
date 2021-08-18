package rz.mesabrook.wbtc.blocks.gui.telecom;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHome extends GuiPhoneBase {

	public GuiHome(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "gui_phone_bg_1.png";
	}

	@Override
	public void initGui() {
		super.initGui();
		ImageButton button = new ImageButton(0, INNER_X + 4, INNER_Y + 19, 32, 32, "icn_phone.png", 32, 32);
		buttonList.add(button);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		switch(button.id) {
			case 0:
				Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneCall(phoneStack, hand));
				break;
		}
	}
}
