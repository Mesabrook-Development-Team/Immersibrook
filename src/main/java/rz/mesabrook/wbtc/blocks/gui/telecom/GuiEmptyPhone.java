package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiEmptyPhone extends GuiPhoneBase {
	
	public GuiEmptyPhone(ItemStack phoneStack, EnumHand hand)
	{
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
}
