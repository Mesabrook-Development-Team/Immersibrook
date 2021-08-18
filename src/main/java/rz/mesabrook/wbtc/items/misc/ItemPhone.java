package rz.mesabrook.wbtc.items.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
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
			
			if (currentPhone.getTagCompound() == null || !currentPhone.getTagCompound().hasKey(Reference.PHONE_NUMBER_NBTKEY))
			{
				playerIn.openGui(Main.instance, Reference.GUI_PHONE_ACTIVATE, worldIn, handIn.ordinal(), 0, 0);
			}
			else
			{
				playerIn.openGui(Main.instance, Reference.GUI_PHONE, worldIn, handIn.ordinal(), 0, 0);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
