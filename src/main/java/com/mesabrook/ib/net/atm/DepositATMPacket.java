package com.mesabrook.ib.net.atm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.PostData;
import com.mesabrook.ib.util.handlers.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DepositATMPacket implements IMessage {

	public long accountID;
	public BigDecimal amount;
	public long companyIDOwner;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(accountID);
		ByteBufUtils.writeUTF8String(buf, amount.toPlainString());
		buf.writeLong(companyIDOwner);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		accountID = buf.readLong();
		amount = new BigDecimal(ByteBufUtils.readUTF8String(buf));
		companyIDOwner = buf.readLong();
	}
	
	public static class Handler implements IMessageHandler<DepositATMPacket, IMessage>
	{
		@Override
		public IMessage onMessage(DepositATMPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(DepositATMPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			TreeMap<ItemMoney, ArrayList<ItemStack>> moneyStacksByMoneyType = new TreeMap<>();
			for(Item item : ModItems.ITEMS)
			{
				if (!(item instanceof ItemMoney))
				{
					continue;
				}
				
				moneyStacksByMoneyType.put((ItemMoney)item, new ArrayList<>());
			}
			
			BigDecimal currentPocketAmount = new BigDecimal(0);
			for(int i = 0; i < player.inventory.getSizeInventory(); i++)
			{
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (!(stack.getItem() instanceof ItemMoney))
				{
					continue;
				}
				
				ItemMoney itemType = (ItemMoney)stack.getItem();
				moneyStacksByMoneyType.get(itemType).add(stack);
				
				currentPocketAmount = currentPocketAmount.add(new BigDecimal(itemType.getValue()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(stack.getCount())));
			}
			
			if (currentPocketAmount.compareTo(message.amount) < 0)
			{
				player.sendMessage(new TextComponentString(TextFormatting.BOLD + "You do not have enough money to make this deposit"));
				return;
			}
			
			// Removes money and provides change, if any, as a result of this transaction
			BigDecimal remainingAmount = new BigDecimal(message.amount.toPlainString());
			for(Entry<ItemMoney, ArrayList<ItemStack>> moneyEntry : moneyStacksByMoneyType.descendingMap().entrySet())
			{
				BigDecimal value = new BigDecimal(moneyEntry.getKey().getValue()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
				for(int i = 0; i < moneyEntry.getValue().size(); i++)
				{
					ItemStack stack = moneyEntry.getValue().get(i);
					int shrinkAmount = 0;
					for(int j = 0; j < stack.getCount(); j++)
					{
						remainingAmount = remainingAmount.subtract(value);
						shrinkAmount++;
						
						if (remainingAmount.compareTo(new BigDecimal(0)) <= 0)
						{
							break;
						}
					}
					
					stack.shrink(shrinkAmount);
					if (remainingAmount.compareTo(new BigDecimal(0)) < 0) 
					{
						List<ItemStack> change = ItemMoney.getMoneyStackForAmount(remainingAmount.abs());
						
						for(ItemStack changeStack : change)
						{
							InventoryHelper.spawnItemStack(player.world, player.posX, player.posY, player.posZ, changeStack);
						}
					}
					
					if (remainingAmount.compareTo(new BigDecimal(0)) <= 0)
					{
						break;
					}
				}
				
				if (remainingAmount.compareTo(new BigDecimal(0)) <= 0)
				{
					break;
				}
			}
			
			// Notify server to perform deposit
			DepositObject deposit = new DepositObject();
			deposit.AccountID = message.accountID;
			deposit.Amount = message.amount;
			deposit.CompanyIDOwner = message.companyIDOwner;
			PostData post = new PostData(API.Company, "AccountIBAccess/Deposit", deposit, new Class<?>[0]);
			post.getHeaderOverrides().put("PlayerName", player.getName());
			
			DataRequestTask task = new DataRequestTask(post);
			task.getData().put("playerID", player.getUniqueID());
			task.getData().put("amount", message.amount);
			ServerTickHandler.atmDepositRequests.add(task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
		
		private static class DepositObject
		{
			public long AccountID;
			public BigDecimal Amount;
			public long CompanyIDOwner;
		}
	}
}
