package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
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
import rz.mesabrook.wbtc.init.SoundInit;
import rz.mesabrook.wbtc.items.armor.FaceMasks;
import rz.mesabrook.wbtc.items.armor.NightVisionGoggles;
import rz.mesabrook.wbtc.items.armor.PoliceHelmet;
import rz.mesabrook.wbtc.items.armor.SafetyVest;
import rz.mesabrook.wbtc.items.tools.ItemBanHammer;
import rz.mesabrook.wbtc.net.*;
import rz.mesabrook.wbtc.proxy.ClientProxy;
import rz.mesabrook.wbtc.util.SoundRandomizer;

@EventBusSubscriber(Side.CLIENT)
public class KeyEventHandler
{
	private static TextComponentTranslation vestToggle;
	private static TextComponentTranslation vestToggleOff;
	private static TextComponentTranslation nvToggleOn;
	private static TextComponentTranslation nvToggleOff;
	private static TextComponentTranslation hammerShift;
	private static TextComponentTranslation policeOn;
	private static TextComponentTranslation policeOff;

	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e)
	{
		if (ClientProxy.vestToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.armorInventory.get(2); // 2 = chest
			
			vestToggle = new TextComponentTranslation("im.vest.toggle");
			vestToggle.getStyle().setColor(TextFormatting.GOLD);
			vestToggleOff = new TextComponentTranslation("im.vest.toggle.off");
			vestToggleOff.getStyle().setColor(TextFormatting.GOLD);

			if (!(stack.getItem() instanceof SafetyVest))
			{
				return;
			}
			else
			{
				if(player.isPotionActive(MobEffects.GLOWING))
				{
					player.sendStatusMessage(new TextComponentString(vestToggleOff.getFormattedText()), true);
				}
				else
				{
					player.sendStatusMessage(new TextComponentString(vestToggle.getFormattedText()), true);
				}

				player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
				VestTogglePacket packet = new VestTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}

		if(ClientProxy.nvToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.armorInventory.get(3); // 3 = head

			nvToggleOn = new TextComponentTranslation("im.nv.toggle.on");
			nvToggleOn.getStyle().setColor(TextFormatting.GOLD);
			nvToggleOff = new TextComponentTranslation("im.nv.toggle.off");
			nvToggleOff.getStyle().setColor(TextFormatting.GOLD);

			if (!(stack.getItem() instanceof NightVisionGoggles))
			{
				return;
			}
			else
			{
				if(player.isPotionActive(MobEffects.NIGHT_VISION))
				{
					player.sendStatusMessage(new TextComponentString(nvToggleOff.getFormattedText()), true);
				}
				else
				{
					player.sendStatusMessage(new TextComponentString(nvToggleOn.getFormattedText()), true);
				}

				player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
				NVTogglePacket packet = new NVTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}

		if(ClientProxy.hammerSoundKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.getCurrentItem();

			if(!(stack.getItem() instanceof ItemBanHammer))
			{
				return;
			}

			player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
			SoundRandomizerPacket packet = new SoundRandomizerPacket();
			PacketHandler.INSTANCE.sendToServer(packet);
		}

		if(ClientProxy.policeHelmetKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.armorInventory.get(3); // 3 = head

			policeOn = new TextComponentTranslation("im.police.toggle.on");
			policeOn.getStyle().setBold(true);
			policeOn.getStyle().setColor(TextFormatting.AQUA);
			policeOff = new TextComponentTranslation("im.police.toggle.off");
			policeOff.getStyle().setBold(true);
			policeOff.getStyle().setColor(TextFormatting.AQUA);

			if (!(stack.getItem() instanceof PoliceHelmet))
			{
				return;
			}
			else
			{
				if(player.isPotionActive(MobEffects.SPEED))
				{
					player.sendStatusMessage(new TextComponentString(policeOff.getFormattedText()), true);
					player.playSound(SoundInit.RADIO_CLOSE, 1.0F, 1.0F);
					player.playSound(SoundInit.LEOTEU_OFF, 1.0F, 1.0F);
				}
				else
				{
					player.sendStatusMessage(new TextComponentString(policeOn.getFormattedText()), true);
					player.playSound(SoundInit.LEOTEU_ON, 1.0F, 1.0F);
				}

				player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
				PoliceEffectsTogglePacket packet = new PoliceEffectsTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}
	}
}
