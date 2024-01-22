package com.mesabrook.ib.items.commerce;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity.ProductSpot;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemShoppingBasket extends Item implements IHasModel
{
    public ItemShoppingBasket()
    {
        setUnlocalizedName("shopping_basket");
        setRegistryName("shopping_basket");
        setMaxStackSize(1);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setHasSubtypes(true);

        ModItems.ITEMS.add(this);
    }
    
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    	super.getSubItems(tab, items);
    	
    	if (isInCreativeTab(tab))
    	{
    		for(int i = 0; i < EnumDyeColor.values().length; i++)
			{
    			items.add(new ItemStack(this, 1, i));
			}
    	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(new TextComponentString(TextFormatting.GREEN + "Your ideal shopping companion!").getFormattedText());
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
    		EnumFacing facing, float hitX, float hitY, float hitZ) {
    	IBlockState state = worldIn.getBlockState(pos);
    	if (state.getBlock() == ModBlocks.SCO_SCANNER)
    	{
    		return EnumActionResult.SUCCESS;
    	}
    	return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack basketStack = playerIn.getHeldItem(handIn);
    	if (!worldIn.isRemote)
    	{
    		playerIn.openGui(Main.instance, Reference.GUI_SHOPPING_BASKET, worldIn, handIn.ordinal(), 0, 0);
    	}
        
    	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, basketStack);
    }

    @Override
    public void registerModels()
    {
        for(EnumDyeColor dyeColor : EnumDyeColor.values())
        {
        	Main.proxy.registerItemRenderer(this, dyeColor.getMetadata(), "color=" + dyeColor.getUnlocalizedName() + ",down=false");
        	ModelBakery.registerItemVariants(this, new ModelResourceLocation(getRegistryName(), "color=" + dyeColor.getUnlocalizedName() + ",down=true"));
        }
    }
    
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	NBTTagCompound tag = super.getNBTShareTag(stack);
    	
    	if (tag == null)
    	{
    		tag = new NBTTagCompound();
    	}
    	
    	if (stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
    	{
    		tag.setTag("wbtc_inv", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), null));
    	}
    	
    	return tag;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }
    
    @Override
    public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
    	super.readNBTShareTag(stack, nbt);
    	
    	if (nbt.hasKey("wbtc_inv"))
    	{
    		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), null, nbt.getTag("wbtc_inv"));
    	}
    }
    
    @Override
    public boolean hasCustomEntity(ItemStack stack) {
    	return true;
    }
    
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
    	EntityItem newEntity = new EntityItem(world, location.posX, location.posY, location.posZ, itemstack)
		{
			@Override
			public void setDead() {
				super.setDead();
				
				if (this.world.isRemote || !itemstack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) || getAge() < lifespan)
				{
					return;
				}
				
				IItemHandler inventory = itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				for(int i = 0; i < inventory.getSlots(); i++)
				{
					ItemStack invStack = inventory.getStackInSlot(i);
					if (!invStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
					{
						continue;
					}
					
					ISecuredItem securedItem = invStack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
					TileEntity te = this.world.getTileEntity(securedItem.getHomeLocation());
					if (te == null || !(te instanceof ShelvingTileEntity))
					{
						return;
					}
					
					ShelvingTileEntity shelf = (ShelvingTileEntity)te;
					ProductSpot[] spots = shelf.getProductSpots();
					if (!Arrays.stream(spots).anyMatch(ps -> ps.getPlacementID() == securedItem.getHomeSpot()))
					{
						return;
					}
					
					ItemStack[] stacks = Arrays.stream(spots).filter(ps -> ps.getPlacementID() == securedItem.getHomeSpot()).findFirst().get().getItems();
					if (!stacks[stacks.length - 1].isEmpty())
					{
						return;
					}
					
					int indexToSet = -1;
					for(int j = 0; j < stacks.length; j++)
					{
						if (stacks[j].isEmpty())
						{
							indexToSet = j;
							break;
						}
					}
					stacks[indexToSet] = invStack;
					this.world.notifyBlockUpdate(shelf.getPos(), this.world.getBlockState(shelf.getPos()), this.world.getBlockState(shelf.getPos()), 3);
					shelf.markDirty();
				}
			}
		};
		newEntity.setPickupDelay(40);
		newEntity.motionX = location.motionX;
		newEntity.motionY = location.motionY;
		newEntity.motionZ = location.motionZ;
		return newEntity;
    }
}
