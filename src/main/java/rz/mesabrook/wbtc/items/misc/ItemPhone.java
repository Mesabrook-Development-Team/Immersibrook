package rz.mesabrook.wbtc.items.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.Reference;

public class ItemPhone extends Item implements IHasModel {

	public ItemPhone(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0);
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
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public static class NBTData implements INBTSerializable<NBTTagCompound>
	{
		private int phoneNumber;

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

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			int phoneNumber = getPhoneNumber();
			if (phoneNumber != 0)
			{
				tag.setInteger(Reference.PHONE_NUMBER_NBTKEY, phoneNumber);
			}
			
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
		}
		
	}
}
