package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import rz.mesabrook.wbtc.net.NVTogglePacket;
import rz.mesabrook.wbtc.net.VestTogglePacket;
import rz.mesabrook.wbtc.proxy.ClientProxy;

@EventBusSubscriber(Side.CLIENT)
public class KeyEventHandler
{
	private static TextComponentTranslation vestToggle;
	private static TextComponentTranslation nvToggle;
	
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e)
	{
		if (ClientProxy.vestToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			
			vestToggle = new TextComponentTranslation("im.vest.toggle");
			vestToggle.getStyle().setColor(TextFormatting.GREEN);

			player.sendMessage(vestToggle);
			player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
			VestTogglePacket packet = new VestTogglePacket();
			PacketHandler.INSTANCE.sendToServer(packet);
		}

		if(ClientProxy.nvToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;

			nvToggle = new TextComponentTranslation("im.nv.toggle");
			nvToggle.getStyle().setColor(TextFormatting.GREEN);

			player.sendMessage(nvToggle);
			player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
			NVTogglePacket packet = new NVTogglePacket();
			PacketHandler.INSTANCE.sendToServer(packet);
		}
	}
}