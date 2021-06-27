package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import rz.mesabrook.wbtc.net.VestTogglePacket;
import rz.mesabrook.wbtc.proxy.ClientProxy;

@EventBusSubscriber(Side.CLIENT)
public class KeyEventHandler {
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e)
	{
		if (ClientProxy.vestToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;

			player.sendMessage(new TextComponentTranslation("im.vest.toggle"));
			player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
			VestTogglePacket packet = new VestTogglePacket();
			PacketHandler.INSTANCE.sendToServer(packet);
		}
	}
}
