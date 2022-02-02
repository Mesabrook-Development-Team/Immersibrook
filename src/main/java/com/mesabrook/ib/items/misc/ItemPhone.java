package com.mesabrook.ib.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneBase;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemPhone extends Item implements IHasModel {

	private String bezelTextureName;
	public ItemPhone(String name, String bezelTextureName)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		this.bezelTextureName = bezelTextureName;
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		NBTTagCompound tag = stack.getTagCompound();
		ItemPhone.NBTData stackData = new ItemPhone.NBTData();
		stackData.deserializeNBT(tag);
		String phoneNumber = "If you're seeing this, then something broke lolxd";

		if(stackData.getPhoneNumberString() != null)
		{
			phoneNumber = GuiPhoneBase.getFormattedPhoneNumber(stackData.getPhoneNumberString());
			tooltip.add(TextFormatting.BLUE + new TextComponentTranslation("im.tooltip.phone").getFormattedText() + " " + TextFormatting.GREEN + phoneNumber);
		}
		else
		{
			tooltip.add(TextFormatting.RED + new TextComponentTranslation("im.tooltip.activate").getFormattedText());
		}

		if(this == ModItems.PHONE_MESABROOK)
		{
			tooltip.add(TextFormatting.AQUA + new TextComponentTranslation("im.tooltip.phone.gov").getFormattedText());
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (worldIn.isRemote)
		{
			ItemStack currentPhone = playerIn.getHeldItem(handIn);
			if (currentPhone.getItem() != this)
			{
				return super.onItemRightClick(worldIn, playerIn, handIn);
			}
			
			playerIn.openGui(Main.instance, Reference.GUI_PHONE, worldIn, handIn.ordinal(), 0, 0);
		}

		if(playerIn instanceof EntityPlayer)
		{
			Triggers.trigger(Triggers.PHONE_USE, playerIn);
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	public String getBezelTextureName()
	{
		return bezelTextureName;
	}
	
	public static class NBTData implements INBTSerializable<NBTTagCompound>
	{
		private int phoneNumber;
		private SecurityStrategies securityStrategy;
		private int pin;
		private UUID uuid;
		private int homeBackground = 1;
		private int lockBackground = 1;
		private int chatTone = 1;
		private int ringTone = 1;
		
		public static NBTData getFromItemStack(ItemStack phoneStack)
		{
			if (!(phoneStack.getItem() instanceof ItemPhone))
			{
				return null;
			}
			
			NBTTagCompound stackTag = phoneStack.getTagCompound();
			if (stackTag == null)
			{
				stackTag = new NBTTagCompound();
				phoneStack.setTagCompound(stackTag);
			}
			
			NBTData data = new NBTData();
			data.deserializeNBT(stackTag);
			return data;
		}

		public int getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(int phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		
		public String getPhoneNumberString()
		{
			if (getPhoneNumber() == 0)
			{
				return null;
			}
			
			return Integer.toString(getPhoneNumber());
		}
		
		public void setPhoneNumberString(String phoneNumber)
		{
			try
			{
				setPhoneNumber(Integer.parseInt(phoneNumber));
			}
			catch(NumberFormatException ex)
			{
				setPhoneNumber(0);
			}
		}

		public SecurityStrategies getSecurityStrategy()
		{
			return securityStrategy == null ? SecurityStrategies.None : securityStrategy;
		}
		
		public void setSecurityStrategy(SecurityStrategies strategy)
		{
			securityStrategy = strategy;
		}
		
		public int getPin() {
			return pin;
		}

		public void setPin(int pin) {
			this.pin = pin;
		}

		public UUID getUuid() {
			return uuid;
		}

		public void setUuid(UUID uuid) {
			this.uuid = uuid;
		}

		public int getHomeBackground() {
			return homeBackground;
		}

		public void setHomeBackground(int homeBackground) {
			this.homeBackground = homeBackground;
		}

		public int getLockBackground() {
			return lockBackground;
		}

		public void setLockBackground(int lockBackground) {
			this.lockBackground = lockBackground;
		}

		public int getChatTone() {
			return chatTone;
		}

		public void setChatTone(int chatTone) {
			this.chatTone = chatTone;
		}

		public int getRingTone() {
			return ringTone;
		}

		public void setRingTone(int ringTone) {
			this.ringTone = ringTone;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			int phoneNumber = getPhoneNumber();
			if (phoneNumber != 0)
			{
				tag.setInteger(Reference.PHONE_NUMBER_NBTKEY, phoneNumber);
			}
			
			tag.setInteger(Reference.SECURITY_STRATEGY_NBTKEY, getSecurityStrategy().ordinal());
			tag.setInteger(Reference.SECURITY_PIN_NBTKEY, getPin());
			
			if (getUuid() != null)
			{
				tag.setTag(Reference.SECURITY_UUID_NBTKEY, NBTUtil.createUUIDTag(getUuid()));
			}
			
			tag.setInteger(Reference.HOME_BACKGROUND, getHomeBackground());
			tag.setInteger(Reference.LOCK_BACKGROUND, getLockBackground());
			tag.setInteger(Reference.CHAT_TONE, getChatTone());
			tag.setInteger(Reference.RING_TONE, getRingTone());
			
			return tag;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			if (nbt == null)
			{
				return;
			}
			
			if (nbt.hasKey(Reference.PHONE_NUMBER_NBTKEY))
			{
				setPhoneNumber(nbt.getInteger(Reference.PHONE_NUMBER_NBTKEY));
			}
			
			if (nbt.hasKey(Reference.SECURITY_STRATEGY_NBTKEY))
			{
				setSecurityStrategy(SecurityStrategies.fromIndex(nbt.getInteger(Reference.SECURITY_STRATEGY_NBTKEY)));
			}
			
			if (nbt.hasKey(Reference.SECURITY_PIN_NBTKEY))
			{
				setPin(nbt.getInteger(Reference.SECURITY_PIN_NBTKEY));
			}
			
			if (nbt.hasKey(Reference.SECURITY_UUID_NBTKEY))
			{
				setUuid(NBTUtil.getUUIDFromTag(nbt.getCompoundTag(Reference.SECURITY_UUID_NBTKEY)));
			}
			
			if (nbt.hasKey(Reference.HOME_BACKGROUND))
			{
				setHomeBackground(nbt.getInteger(Reference.HOME_BACKGROUND));
			}
			
			if (nbt.hasKey(Reference.LOCK_BACKGROUND))
			{
				setLockBackground(nbt.getInteger(Reference.LOCK_BACKGROUND));
			}
			
			if (nbt.hasKey(Reference.CHAT_TONE))
			{
				setChatTone(nbt.getInteger(Reference.CHAT_TONE));
			}
			
			if (nbt.hasKey(Reference.RING_TONE))
			{
				setRingTone(nbt.getInteger(Reference.RING_TONE));
			}
		}
		
		public enum SecurityStrategies
		{
			None,
			PIN,
			UUID;
			
			public static SecurityStrategies fromIndex(int index)
			{
				for(SecurityStrategies strategy : SecurityStrategies.values())
				{
					if (strategy.ordinal() == index)
					{
						return strategy;
					}
				}
				
				return None;
			}
		}
	}
}
