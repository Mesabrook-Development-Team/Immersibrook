package rz.mesabrook.wbtc.telecom.handlers;

import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiPhoneBase;
import rz.mesabrook.wbtc.blocks.gui.telecom.Toaster;

@EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class WorldTickEventHandler {
	@SubscribeEvent
	public static void advanceInactiveToasts(WorldTickEvent e)
	{
		if (e.phase == Phase.END)
		{
			for(Entry<String, Toaster> toasterEntry : Toaster.all())
			{
				if (Minecraft.getMinecraft().currentScreen == null ||
						!(Minecraft.getMinecraft().currentScreen instanceof GuiPhoneBase) ||
						!((GuiPhoneBase)Minecraft.getMinecraft().currentScreen).getCurrentPhoneNumber().equals(toasterEntry.getKey()))
				{
					toasterEntry.getValue().tickNoDraw();
				}
			}
		}
	}
}
