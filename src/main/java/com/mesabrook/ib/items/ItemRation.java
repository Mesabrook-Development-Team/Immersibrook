package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.SoundInit;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemRation extends Item implements IHasModel
{
    public ItemRation(String name)
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.playSound(SoundInit.RATION_OPEN, 1.0F, 1.0F);
        playerIn.openGui(Main.instance, Reference.GUI_RATION, worldIn, handIn.ordinal(), 0, 0);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new RationCapabilityProvider();
    }

    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
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
    public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt)
    {
        super.readNBTShareTag(stack, nbt);

        if (nbt != null && nbt.hasKey("ClientInventory"))
        {
            IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, handler, null, nbt.getTag("ClientInventory"));
        }
    }

    public static class RationCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<NBTTagCompound>
    {
        private final ItemStackHandler handler;

        public RationCapabilityProvider()
        {
            handler = new ItemStackHandler(9)
            {
                @Override
                public boolean isItemValid(int slot, ItemStack stack)
                {
                    return true;
                }
            };
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
            }

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("rationcontents", handler.serializeNBT());
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            if (nbt.hasKey("rationcontents"))
            {
                handler.deserializeNBT((NBTTagCompound)nbt.getTag("rationcontents"));
            }
        }
    }
}
