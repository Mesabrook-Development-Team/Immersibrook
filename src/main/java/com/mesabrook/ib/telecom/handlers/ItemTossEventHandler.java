package com.mesabrook.ib.telecom.handlers;

import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;

@EventBusSubscriber
public class ItemTossEventHandler {

	@SubscribeEvent
	public static void onItemToss(ItemTossEvent e)
	{
		if (!e.getPlayer().world.isRemote)
		{
			return;
		}
		
		if (!(e.getEntityItem().getItem().getItem() instanceof ItemPhone))
		{
			return;
		}
		
		ItemPhone.NBTData data = new ItemPhone.NBTData();
		data.deserializeNBT(e.getEntityItem().getItem().getTagCompound());
		String phoneNumber = data.getPhoneNumberString();
		
		if (phoneNumber != null)
		{
			TelecomClientHandlers.onPhoneToss(phoneNumber);
		}
	}
}
