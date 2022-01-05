package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiLockScreen extends GuiPhoneBase {

	public GuiLockScreen(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return null;
	}
	
	@Override
	protected boolean renderNotificationBar() {
		return false;
	}
}
