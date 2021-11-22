package rz.mesabrook.wbtc.items.misc;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.init.SoundInit;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.Reference;

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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("im.tooltip.stampbook").getFormattedText());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.playSound(SoundInit.BOOK_OPEN, 1.0F, 1.0F);
        if(playerIn instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.STAMPBOOK, playerIn);
        }
        
        playerIn.openGui(Main.instance, Reference.GUI_STAMP_BOOK, worldIn, handIn.ordinal(), 0, 0);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
    	return new StampBookCapabilityProvider(stack);
    }
    
    public static class StampBookCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<NBTTagCompound>
    {
    	private final ItemStackHandler handler;
    	private ItemStack stack;
    	
    	public StampBookCapabilityProvider(ItemStack stack)
    	{
    		this.stack = stack;
    		
    		handler = new ItemStackHandler(24);
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
