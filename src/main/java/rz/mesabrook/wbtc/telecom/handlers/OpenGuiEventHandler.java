package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiPhoneBase;

@EventBusSubscriber
@SideOnly(Side.CLIENT)
public class OpenGuiEventHandler {
	@SubscribeEvent
	public static void onGuiOpen(GuiOpenEvent e)
	{
		GuiPhoneBase.lastGuiWasPhone = e.getGui() instanceof GuiPhoneBase;
	}
}
