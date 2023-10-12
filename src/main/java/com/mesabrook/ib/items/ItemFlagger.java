package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFlagger extends Item implements IHasModel
{
    public ItemFlagger(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
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

    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        try
        {
            ItemStack currentStack = playerIn.getHeldItem(handIn);
            if(worldIn.isRemote)
            {
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }
            int slot = getSlotForStack(playerIn, currentStack);

            if(currentStack.getItem() == ModItems.FLAGGER_STOP)
            {
                ItemStack changedStack = new ItemStack(ModItems.FLAGGER_SLOW);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);

                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else
            {
                ItemStack changedStack = new ItemStack(ModItems.FLAGGER_STOP);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);

                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
        }
        catch(Exception ex)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Item cannot be used in your off-hand."));
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
    }

    private int getSlotForStack(EntityPlayer player, ItemStack stack)
    {
        for (int i = 0; i < player.inventory.mainInventory.size(); ++i)
        {
            if (!((ItemStack)player.inventory.mainInventory.get(i)).isEmpty() && stackEqualExact(stack, player.inventory.mainInventory.get(i)))
            {
                return i;
            }
        }

        return -1;
    }

    private boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
