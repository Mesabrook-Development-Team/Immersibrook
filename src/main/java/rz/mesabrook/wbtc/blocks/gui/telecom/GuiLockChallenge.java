package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import rz.mesabrook.wbtc.util.PhoneWallpaperRandomizer;

public class GuiLockChallenge extends GuiPhoneBase {

	public GuiLockChallenge(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		if(PhoneWallpaperRandomizer.wallpaper != null)
		{
			return PhoneWallpaperRandomizer.wallpaper;
		}
		else
		{
			return "gui_phone_bg_1.png";
		}
	}

}
