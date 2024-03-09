package com.mesabrook.ib.items.misc;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.ModSounds;
import com.mesabrook.ib.items.misc.ItemStamp.StampTypes;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStampBook extends Item implements IHasModel
{
    public ItemStampBook(String name)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
	{
		return false;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("im.tooltip.stampbook").getFormattedText());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.playSound(ModSounds.BOOK_OPEN, 1.0F, 1.0F);
        if(playerIn instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.STAMPBOOK, playerIn);
        }
        
        playerIn.openGui(Main.instance, Reference.GUI_STAMP_BOOK, worldIn, handIn.ordinal(), 0, 0);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
    	return new StampBookCapabilityProvider();
    }
    
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	NBTTagCompound tag = super.getNBTShareTag(stack);
		
		if (tag == null)
		{
			tag = new NBTTagCompound();
		}
		
		IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		NBTBase capTag = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, handler, null);
		tag.setTag("ClientInventory", capTag);
		
		return tag;
    }
    
    @Override
	public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
		super.readNBTShareTag(stack, nbt);
		
		if (nbt != null && nbt.hasKey("ClientInventory"))
		{
			IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, handler, null, nbt.getTag("ClientInventory"));
		}
	}
    
    public static class StampBookCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<NBTTagCompound>
    {
    	private final ItemStackHandler handler;
    	
    	public StampBookCapabilityProvider()
    	{    		
    		handler = new ItemStackHandler(24)
			{
    			@Override
    			public boolean isItemValid(int slot, ItemStack stack) {
    				if (!(stack.getItem() instanceof ItemStamp))
    				{
    					return false;
    				}
    				
    				ItemStamp stamp = (ItemStamp)stack.getItem();
    				
    				switch(slot)
    				{
	    				case 0:
	    					return stamp.getStampType() == StampTypes.AutumnValley;
	    				case 1:
	    					return stamp.getStampType() == StampTypes.IronRiver;
	    				case 2:
	    					return stamp.getStampType() == StampTypes.RavenholmCity;
	    				case 3:
	    					return stamp.getStampType() == StampTypes.SodorCity;
	    				case 4:
	    					return stamp.getStampType() == StampTypes.CrystalBeach;
	    				case 5:
	    					return stamp.getStampType() == StampTypes.Clayton;
    					default:
    						return true;
    				}
    			}
    			
    			@Override
    			public int getSlotLimit(int slot) {
    				if (slot >= 0 && slot <= 5)
    				{
    					return 1;
    				}
    				
    				return super.getSlotLimit(slot);
    			}
			};
    	}

    	@Override
    	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    	}
    	
    	@Override
    	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    		{
    			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
    		}
    		
    		return null;
    	}
    	
		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setTag("stamps", handler.serializeNBT());
			return compound;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			if (nbt.hasKey("stamps"))
			{
				handler.deserializeNBT((NBTTagCompound)nbt.getTag("stamps"));
			}
		}
    }
}
