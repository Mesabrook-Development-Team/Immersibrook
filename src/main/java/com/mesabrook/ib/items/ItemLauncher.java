package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLauncher extends Item implements IHasModel
{
    public ItemLauncher(String name)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        setMaxDamage(64);

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
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(new TextComponentString(TextFormatting.RED + "[VERY WIP - BUGGY!]").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.GREEN + "This nifty gadget takes an item from your inventory and launches it!").getFormattedText());
        }
        else
        {
            tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Press [SHIFT] for more info.").getFormattedText());
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack heldItem = playerIn.getHeldItem(handIn);

        for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++)
        {
            ItemStack stackInSlot = playerIn.inventory.getStackInSlot(i);
            if(!stackInSlot.isEmpty() && !(stackInSlot.getItem() instanceof ItemLauncher))
            {
                fireItem(worldIn, playerIn, stackInSlot);
                stackInSlot.shrink(1);

                if(!playerIn.isCreative())
                {
                    heldItem.damageItem(1, playerIn);
                }

                return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
    }

    private void fireItem(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(!world.isRemote)
        {
            EntityItem firedItem = new EntityItem(world, player.posX, player.posY + player.getEyeHeight(), player.posZ, itemStack);

            firedItem.motionX = -Math.sin(Math.toRadians(player.rotationYaw)) * Math.cos(Math.toRadians(player.rotationPitch));
            firedItem.motionY = -Math.sin(Math.toRadians(player.rotationPitch));
            firedItem.motionZ = Math.cos(Math.toRadians(player.rotationYaw)) * Math.cos(Math.toRadians(player.rotationPitch));

            // Adjust the speed of the fired item
            double speed = 1.5;
            firedItem.motionX *= speed;
            firedItem.motionY *= speed;
            firedItem.motionZ *= speed;

            world.spawnEntity(firedItem);
            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.0F);

            itemStack.damageItem(1, player);
            itemStack.shrink(1);
            player.inventoryContainer.detectAndSendChanges();
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
