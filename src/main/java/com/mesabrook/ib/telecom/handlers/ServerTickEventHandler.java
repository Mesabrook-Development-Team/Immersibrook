package com.mesabrook.ib.telecom.handlers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.telecom.CallManager;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager;
import com.mesabrook.ib.util.Reference;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

@EventBusSubscriber
public class ServerTickEventHandler {

	@SubscribeEvent
	public static void onTick(ServerTickEvent e)
	{
		if (e.phase != Phase.END)
		{
			return;
		}
		
		CallManager.instance().tick();
		WirelessEmergencyAlertManager.instance().sendAlerts();
		tickBatteryDrain();
	}
	
	private static int drainCounter = 200;
	private static void tickBatteryDrain()
	{		
		if (--drainCounter > 0)
		{
			return;
		}
		
		for(EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
		{
			List<ItemStack> phoneStacks = Stream.concat(player.inventory.offHandInventory.stream(), Stream.concat(player.inventory.armorInventory.stream(), player.inventory.mainInventory.stream())).filter(stack -> stack.getItem() instanceof ItemPhone).collect(Collectors.toList());
			for(ItemStack phoneStack : phoneStacks)
			{	
				ItemPhone.NBTData nbtData = ItemPhone.NBTData.getFromItemStack(phoneStack);
				if (nbtData.getBatteryLevel() > 0)
				{
					nbtData.setBatteryLevel(nbtData.getBatteryLevel() - 1);
				}
				
				NBTTagCompound phoneTag = phoneStack.getTagCompound();
				phoneTag.merge(nbtData.serializeNBT());
				Main.logger.info("Phone charge: " + phoneTag.getInteger(Reference.BATTERY_LEVEL));
			}
		}
		
		drainCounter = 200;
	}
}
