package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.items.misc.ItemPhone;
import rz.mesabrook.wbtc.net.telecom.PhoneTossedPacket;
import rz.mesabrook.wbtc.telecom.CallManager;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

import java.util.HashMap;

@EventBusSubscriber
public class ContainerOpenHandler {
	@SubscribeEvent
	public static void onOpen(PlayerContainerEvent.Open e)
	{
		e.getContainer().addListener(new PhoneContainerListener());
	}
	
	public static class PhoneContainerListener implements IContainerListener
	{
		HashMap<Integer, ItemPhone.NBTData> phoneStacks = new HashMap<>();
		@Override
		public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList)
		{
			for(int i = 0; i < containerToSend.inventorySlots.size(); i++)
			{
				Slot slot = containerToSend.inventorySlots.get(i);
				if (!(slot.inventory instanceof InventoryPlayer)) { continue; }
				if (slot.getStack().getItem() instanceof ItemPhone)
				{
					ItemPhone.NBTData data = new ItemPhone.NBTData();
					data.deserializeNBT(slot.getStack().getTagCompound());
					phoneStacks.put(i, data);
				}
			}
		}

		@Override
		public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
			Slot slot = containerToSend.getSlot(slotInd);
			if (!(slot.inventory instanceof InventoryPlayer)) { return; }
			
			InventoryPlayer playerInventory = (InventoryPlayer)slot.inventory;
			EntityPlayerMP player = (EntityPlayerMP)playerInventory.player;
			
			CallManager manager = CallManager.instance();			
			if (!phoneStacks.containsKey(slotInd) && stack.getItem() instanceof ItemPhone) // phone added
			{
				ItemPhone.NBTData data = new ItemPhone.NBTData();
				data.deserializeNBT(stack.getTagCompound());
				
				String phoneNumber = data.getPhoneNumberString();
				if (phoneNumber != null)
				{
					manager.phoneQuery(player, phoneNumber);
				}
				
				phoneStacks.put(slotInd, data);
			}
			else if (phoneStacks.containsKey(slotInd) && !(stack.getItem() instanceof ItemPhone)) // phone removed
			{
				ItemPhone.NBTData data = phoneStacks.get(slotInd);
				
				String phoneNumber = data.getPhoneNumberString();
				if (phoneNumber != null)
				{
					PhoneTossedPacket toss = new PhoneTossedPacket();
					toss.tossedPhoneNumber = phoneNumber;
					PacketHandler.INSTANCE.sendTo(toss, player);
				}
				
				phoneStacks.remove(slotInd);
			}
			else if (phoneStacks.containsKey(slotInd) && stack.getItem() instanceof ItemPhone) // phone swap
			{
				ItemPhone.NBTData tossedData = phoneStacks.get(slotInd);
				
				String tossedPhoneNumber = tossedData.getPhoneNumberString();
				if (tossedPhoneNumber != null)
				{
					PhoneTossedPacket toss = new PhoneTossedPacket();
					toss.tossedPhoneNumber = tossedPhoneNumber;
					PacketHandler.INSTANCE.sendTo(toss, player);
				}
				
				phoneStacks.remove(slotInd);
				
				ItemPhone.NBTData data = new ItemPhone.NBTData();
				data.deserializeNBT(stack.getTagCompound());
				
				String phoneNumber = data.getPhoneNumberString();
				if (phoneNumber != null)
				{
					manager.phoneQuery(player, phoneNumber);
				}
				
				phoneStacks.put(slotInd, data);
			}
		}

		@Override
		public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) { }

		@Override
		public void sendAllWindowProperties(Container containerIn, IInventory inventory) { }
		
	}
}
