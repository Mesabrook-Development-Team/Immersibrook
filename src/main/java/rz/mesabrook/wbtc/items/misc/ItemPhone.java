package rz.mesabrook.wbtc.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.PhoneWallpaperRandomizer;
import rz.mesabrook.wbtc.util.Reference;

import javax.annotation.Nullable;
import java.util.List;

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
		if(stack.getItem() == ModItems.PHONE_MESABROOK)
		{
			tooltip.add(TextFormatting.AQUA + new TextComponentTranslation("im.tooltip.phone.gov").getFormattedText());
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		PhoneWallpaperRandomizer.ShuffleWallpaper();
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
