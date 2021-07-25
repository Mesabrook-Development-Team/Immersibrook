package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import rz.mesabrook.wbtc.items.armor.FaceMasks;
import rz.mesabrook.wbtc.items.armor.NightVisionGoggles;
import rz.mesabrook.wbtc.items.armor.SafetyVest;
import rz.mesabrook.wbtc.items.tools.ItemBanHammer;
import rz.mesabrook.wbtc.net.NVTogglePacket;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.net.SoundRandomizerPacket;
import rz.mesabrook.wbtc.net.VestTogglePacket;
import rz.mesabrook.wbtc.proxy.ClientProxy;
import rz.mesabrook.wbtc.util.SoundRandomizer;

@EventBusSubscriber(Side.CLIENT)
public class KeyEventHandler
{
	private static TextComponentTranslation vestToggle;
	private static TextComponentTranslation nvToggle;
	private static TextComponentTranslation hammerShift;
	
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e)
	{
		if (ClientProxy.vestToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.armorInventory.get(2); // 2 = chest
			
			vestToggle = new TextComponentTranslation("im.vest.toggle");
			vestToggle.getStyle().setColor(TextFormatting.GREEN);

			if (!(stack.getItem() instanceof SafetyVest))
			{
				return;
			}
			else
			{
				player.sendMessage(vestToggle);
				player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
				VestTogglePacket packet = new VestTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}

		if(ClientProxy.nvToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.armorInventory.get(3); // 3 = head

			nvToggle = new TextComponentTranslation("im.nv.toggle");
			nvToggle.getStyle().setColor(TextFormatting.GREEN);

			if (!(stack.getItem() instanceof NightVisionGoggles))
			{
				return;
			}
			else
			{
				player.sendMessage(nvToggle);
				player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
				NVTogglePacket packet = new NVTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}

		if(ClientProxy.hammerSoundKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.getCurrentItem();
			hammerShift = new TextComponentTranslation("im.hammer.shift");
			hammerShift.getStyle().setColor(TextFormatting.AQUA);

			if(!(stack.getItem() instanceof ItemBanHammer))
			{
				return;
			}

			if(stack.hasTagCompound())
			{
				if(stack.getTagCompound().hasKey("sndID") && SoundRandomizer.hammerResult != null)
				{
					SoundRandomizer.HammerRandomizer();
					SoundRandomizerPacket packet = new SoundRandomizerPacket();
					packet.soundID = SoundRandomizer.hammerResult;
					PacketHandler.INSTANCE.sendToServer(packet);
					player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
					player.sendMessage(hammerShift);
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + stack.getTagCompound().getString("sndID")));
				}
				else
				{
					SoundRandomizer.HammerRandomizer();
					SoundRandomizerPacket packet = new SoundRandomizerPacket();
					PacketHandler.INSTANCE.sendToServer(packet);
					player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
					player.sendMessage(hammerShift);
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + stack.getTagCompound().getString("sndID")));
				}
			}
			else
			{
				SoundRandomizerPacket packet = new SoundRandomizerPacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}
	}
}
