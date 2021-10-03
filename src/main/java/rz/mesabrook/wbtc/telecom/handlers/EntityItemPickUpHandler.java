package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EntityItemPickUpHandler {
	@SubscribeEvent
	public static void pickupItem(EntityItemPickupEvent e)
	{
		/*
		 * if (e.getEntityPlayer().world.isRemote) { return; }
		 * 
		 * if (e.getItem().getItem().getItem() instanceof ItemPhone) { ItemPhone.NBTData
		 * stackData = new ItemPhone.NBTData();
		 * stackData.deserializeNBT(e.getItem().getItem().getTagCompound()); String
		 * phoneNumber = stackData.getPhoneNumberString();
		 * 
		 * if (phoneNumber != null) { PhoneQueryPacket query = new PhoneQueryPacket();
		 * query.forNumber = phoneNumber; PacketHandler.INSTANCE.sendToServer(query); }
		 * }
		 */
	}
}
