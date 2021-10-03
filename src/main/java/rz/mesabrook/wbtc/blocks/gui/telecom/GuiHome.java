package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.net.telecom.PhoneQueryPacket;
import rz.mesabrook.wbtc.util.PhoneWallpaperRandomizer;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiHome extends GuiPhoneBase {

	public GuiHome(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName()
	{
		if(PhoneWallpaperRandomizer.wallpaper != null)
		{
			return PhoneWallpaperRandomizer.wallpaper;
		}
		else
		{
			return "gui_phone_bg_1.png";
		}
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
				Minecraft.getMinecraft().displayGuiScreen(new GuiEmptyPhone(phoneStack, hand));
				
				PhoneQueryPacket queryPacket = new PhoneQueryPacket();
				queryPacket.forNumber = getCurrentPhoneNumber();
				
				int nextID = TelecomClientHandlers.getNextHandlerID();
				
				TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, TelecomClientHandlers::onPhoneQueryResponseForPhoneApp);
				queryPacket.clientHandlerCode = nextID;
				PacketHandler.INSTANCE.sendToServer(queryPacket);
				break;
		}
	}
}
