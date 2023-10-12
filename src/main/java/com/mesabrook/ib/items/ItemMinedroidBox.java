package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
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
import javax.xml.soap.Text;
import java.util.List;

public class ItemMinedroidBox extends Item implements IHasModel
{
    public ItemMinedroidBox(String name)
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
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(stack.getItem() == ModItems.BOX_WHITE)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.WHITE + "Snow").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_ORANGE)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.GOLD + "Orange").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_MAGENTA)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.LIGHT_PURPLE + "Magenta").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_LBLUE)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.BLUE + "Aqua").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_YELLOW)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.YELLOW + "Sunshine").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_PINK)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.LIGHT_PURPLE + "Pink").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_GRAY)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.DARK_GRAY + "Graphite").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_SILVER)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.GRAY + "Silver").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_CYAN)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.DARK_AQUA + "Cyan").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_BLUE)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.DARK_BLUE + "Blue").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_PURPLE)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.DARK_PURPLE + "Purple").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_GREEN)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.DARK_GREEN + "Leaf").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_RED)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.RED + "Rose").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_BLACK)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.DARK_GRAY + "Midnight").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_LIME)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.GREEN + "Lime").getFormattedText());
        }
        if(stack.getItem() == ModItems.BOX_BROWN)
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Product: " + TextFormatting.RESET + "MSAC Minedroid Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Color: " + TextFormatting.RESET + TextFormatting.DARK_RED + "Chocolate").getFormattedText());
        }
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(new TextComponentString(TextFormatting.BOLD + " ").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.BOLD + "Package Contents:").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.WHITE + "1 x MSAC Smartphone").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.WHITE + "1 x MSAC One-Time Charger").getFormattedText());
        }
        else
        {
            tooltip.add(TextFormatting.WHITE + " ");
            tooltip.add(TextFormatting.WHITE + "Press [SHIFT] for package contents.");
        }
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
            playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER));

            if(!worldIn.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = playerIn.getPosition();
                packet.soundName = "phone_unbox";
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Congratulations on your purchase of a new MSAC Minedroid smartphone!"));
            }

            if(currentStack.getItem() == ModItems.BOX_WHITE)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_WHITE);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_ORANGE)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_ORANGE);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_MAGENTA)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_MAGENTA);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_LBLUE)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_LBLUE);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_YELLOW)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_YELLOW);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_LIME)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_LIME);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_PINK)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_PINK);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_GRAY)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_GRAY);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_SILVER)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_SILVER);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_CYAN)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_CYAN);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_PURPLE)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_PURPLE);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_BLUE)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_BLUE);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_BROWN)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_BROWN);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_GREEN)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_GREEN);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_RED)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_RED);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
            else if(currentStack.getItem() == ModItems.BOX_BLACK)
            {
                ItemStack changedStack = new ItemStack(ModItems.PHONE_BLACK);
                playerIn.inventory.setInventorySlotContents(slot, changedStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
            }
        }
        catch(Exception ex)
        {
            playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Item cannot be used in your off-hand."));
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
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
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
