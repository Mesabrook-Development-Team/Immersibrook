package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mrcrayfish.device.init.DeviceBlocks;
import com.mrcrayfish.device.init.DeviceItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTechRetailBox extends Item implements IHasModel
{
    private TextComponentString itemColor;
    public ItemTechRetailBox(String name)
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
        if(stack.getItem().getUnlocalizedName().contains("laptop"))
        {
            tooltip.add(new TextComponentString(TextFormatting.GRAY + "§oManufactured for Crayfish Ind. by MSAC").getFormattedText());
            tooltip.add(new TextComponentString(" ").getFormattedText());
            tooltip.add(new TextComponentString("§f§n§lContents").getFormattedText());
            tooltip.add(new TextComponentString("1 Craytech Laptop, ").getFormattedText() + getColor(stack).getFormattedText());
        }
        if(stack.getItem().getUnlocalizedName().contains("router"))
        {
            tooltip.add(new TextComponentString(TextFormatting.GRAY + "§oManufactured for Crayfish Ind. by MSAC").getFormattedText());
            tooltip.add(new TextComponentString(" ").getFormattedText());
            tooltip.add(new TextComponentString("§f§n§lContents").getFormattedText());
            tooltip.add(new TextComponentString("1 Craytech Router, ").getFormattedText() + getColor(stack).getFormattedText());
            tooltip.add(new TextComponentString("1 Ethernet Cable").getFormattedText());
        }
        if(stack.getItem().getUnlocalizedName().contains("printer"))
        {
            tooltip.add(new TextComponentString(TextFormatting.GRAY + "§oManufactured for Crayfish Ind. by MSAC").getFormattedText());
            tooltip.add(new TextComponentString(" ").getFormattedText());
            tooltip.add(new TextComponentString("§f§n§lContents").getFormattedText());
            tooltip.add(new TextComponentString("1 Craytech Printer, ").getFormattedText() + getColor(stack).getFormattedText());
            tooltip.add(new TextComponentString("1 Paper").getFormattedText());
        }
        if(stack.getItem().getUnlocalizedName().contains("phone"))
        {
            tooltip.add(new TextComponentString(TextFormatting.GRAY + "§oManufactured by MSAC").getFormattedText());
            tooltip.add(new TextComponentString(" ").getFormattedText());
            tooltip.add(new TextComponentString("§f§n§lContents").getFormattedText());
            tooltip.add(new TextComponentString("1 Minedroid Smartphone, ").getFormattedText() + getColor(stack).getFormattedText());
            tooltip.add(new TextComponentString("1 One-Time Charger").getFormattedText());

        }
    }

    private TextComponentString getColor(ItemStack itemStack)
    {
        if(itemStack.getItem() == ModItems.BOX_WHITE || itemStack.getItem() == ModItems.LAPTOP_BOX_WHITE || itemStack.getItem() == ModItems.ROUTER_BOX_WHITE || itemStack.getItem() == ModItems.PRINTER_BOX_WHITE)
        {
            return this.itemColor = new TextComponentString(TextFormatting.WHITE + "White");
        }
        if(itemStack.getItem() == ModItems.BOX_ORANGE || itemStack.getItem() == ModItems.LAPTOP_BOX_ORANGE || itemStack.getItem() == ModItems.ROUTER_BOX_ORANGE || itemStack.getItem() == ModItems.PRINTER_BOX_ORANGE)
        {
            return this.itemColor = new TextComponentString(TextFormatting.GOLD + "Orange");
        }
        if(itemStack.getItem() == ModItems.BOX_MAGENTA || itemStack.getItem() == ModItems.LAPTOP_BOX_MAGENTA || itemStack.getItem() == ModItems.ROUTER_BOX_MAGENTA || itemStack.getItem() == ModItems.PRINTER_BOX_MAGENTA)
        {
            return this.itemColor = new TextComponentString(TextFormatting.LIGHT_PURPLE + "Magenta");
        }
        if(itemStack.getItem() == ModItems.BOX_LBLUE || itemStack.getItem() == ModItems.LAPTOP_BOX_LBLUE || itemStack.getItem() == ModItems.ROUTER_BOX_LBLUE || itemStack.getItem() == ModItems.PRINTER_BOX_LBLUE)
        {
            return this.itemColor = new TextComponentString(TextFormatting.BLUE + "Light Blue");
        }
        if(itemStack.getItem() == ModItems.BOX_YELLOW || itemStack.getItem() == ModItems.LAPTOP_BOX_YELLOW || itemStack.getItem() == ModItems.ROUTER_BOX_YELLOW || itemStack.getItem() == ModItems.PRINTER_BOX_YELLOW)
        {
            return this.itemColor = new TextComponentString(TextFormatting.YELLOW + "Yellow");
        }
        if(itemStack.getItem() == ModItems.BOX_LIME || itemStack.getItem() == ModItems.LAPTOP_BOX_LIME || itemStack.getItem() == ModItems.ROUTER_BOX_LIME || itemStack.getItem() == ModItems.PRINTER_BOX_LIME)
        {
            return this.itemColor = new TextComponentString(TextFormatting.GREEN + "Lime");
        }
        if(itemStack.getItem() == ModItems.BOX_PINK || itemStack.getItem() == ModItems.LAPTOP_BOX_PINK || itemStack.getItem() == ModItems.ROUTER_BOX_PINK || itemStack.getItem() == ModItems.PRINTER_BOX_PINK)
        {
            return this.itemColor = new TextComponentString(TextFormatting.LIGHT_PURPLE + "Pink");
        }
        if(itemStack.getItem() == ModItems.BOX_GRAY || itemStack.getItem() == ModItems.LAPTOP_BOX_GRAY || itemStack.getItem() == ModItems.ROUTER_BOX_GRAY || itemStack.getItem() == ModItems.PRINTER_BOX_GRAY)
        {
            return this.itemColor = new TextComponentString(TextFormatting.DARK_GRAY + "Gray");
        }
        if(itemStack.getItem() == ModItems.BOX_SILVER || itemStack.getItem() == ModItems.LAPTOP_BOX_SILVER || itemStack.getItem() == ModItems.ROUTER_BOX_SILVER || itemStack.getItem() == ModItems.PRINTER_BOX_SILVER)
        {
            return this.itemColor = new TextComponentString(TextFormatting.GRAY + "Silver");
        }
        if(itemStack.getItem() == ModItems.BOX_CYAN || itemStack.getItem() == ModItems.LAPTOP_BOX_CYAN || itemStack.getItem() == ModItems.ROUTER_BOX_CYAN || itemStack.getItem() == ModItems.PRINTER_BOX_CYAN)
        {
            return this.itemColor = new TextComponentString(TextFormatting.AQUA + "Cyan");
        }
        if(itemStack.getItem() == ModItems.BOX_PURPLE || itemStack.getItem() == ModItems.LAPTOP_BOX_PURPLE || itemStack.getItem() == ModItems.ROUTER_BOX_PURPLE || itemStack.getItem() == ModItems.PRINTER_BOX_PURPLE)
        {
            return this.itemColor = new TextComponentString(TextFormatting.DARK_PURPLE + "Purple");
        }
        if(itemStack.getItem() == ModItems.BOX_BLUE || itemStack.getItem() == ModItems.LAPTOP_BOX_BLUE || itemStack.getItem() == ModItems.ROUTER_BOX_BLUE || itemStack.getItem() == ModItems.PRINTER_BOX_BLUE)
        {
            return this.itemColor = new TextComponentString(TextFormatting.DARK_BLUE + "Blue");
        }
        if(itemStack.getItem() == ModItems.BOX_BROWN || itemStack.getItem() == ModItems.LAPTOP_BOX_BROWN || itemStack.getItem() == ModItems.ROUTER_BOX_BROWN || itemStack.getItem() == ModItems.PRINTER_BOX_BROWN)
        {
            return this.itemColor = new TextComponentString(TextFormatting.GOLD + "Brown");
        }
        if(itemStack.getItem() == ModItems.BOX_GREEN || itemStack.getItem() == ModItems.LAPTOP_BOX_GREEN || itemStack.getItem() == ModItems.ROUTER_BOX_GREEN || itemStack.getItem() == ModItems.PRINTER_BOX_GREEN)
        {
            return this.itemColor = new TextComponentString(TextFormatting.DARK_GREEN + "Green");
        }
        if(itemStack.getItem() == ModItems.BOX_RED || itemStack.getItem() == ModItems.LAPTOP_BOX_RED || itemStack.getItem() == ModItems.ROUTER_BOX_RED || itemStack.getItem() == ModItems.PRINTER_BOX_RED)
        {
            return this.itemColor = new TextComponentString(TextFormatting.RED + "Red");
        }
        if(itemStack.getItem() == ModItems.BOX_BLACK || itemStack.getItem() == ModItems.LAPTOP_BOX_BLACK || itemStack.getItem() == ModItems.ROUTER_BOX_BLACK || itemStack.getItem() == ModItems.PRINTER_BOX_BLACK)
        {
            return this.itemColor = new TextComponentString(TextFormatting.BLACK + "Black");
        }
        else
        {
            return this.itemColor = new TextComponentString("NYI");
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(!worldIn.isRemote)
        {
            if(countEmptySlots(playerIn) >= 1)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = playerIn.getPosition();
                packet.soundName = "phone_unbox";
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));

                playerIn.inventory.setInventorySlotContents(handIn.ordinal(), ItemStack.EMPTY);
                // OT Charger
                if(itemstack.getItem() == ModItems.OT_CHARGER_BOX)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }

                // Phones
                if(itemstack.getItem() == ModItems.BOX_WHITE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_WHITE,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_ORANGE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_ORANGE,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_MAGENTA)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_MAGENTA,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_LBLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_LBLUE,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_YELLOW)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_YELLOW,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_LIME)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_LIME,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_PINK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_PINK,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_GRAY)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_GRAY,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_SILVER)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_SILVER,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_CYAN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_CYAN,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_BLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_BLUE,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_PURPLE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_PURPLE,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_BROWN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_BROWN,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_GREEN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_GREEN,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_RED)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_RED,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }
                if(itemstack.getItem() == ModItems.BOX_BLACK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.PHONE_BLACK,1));
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER,1));
                }

                // Laptops
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_WHITE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 0));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_ORANGE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 1));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_MAGENTA)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 2));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_LBLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 3));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_YELLOW)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 4));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_LIME)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 5));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_PINK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 6));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_GRAY)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 7));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_SILVER)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 8));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_CYAN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 9));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_PURPLE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 10));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_BLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 11));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_BROWN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 12));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_GREEN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 13));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_RED)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 14));
                }
                if(itemstack.getItem() == ModItems.LAPTOP_BOX_BLACK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.LAPTOP,1, 15));
                }

                // Routers
                if(itemstack.getItem() == ModItems.ROUTER_BOX_WHITE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 0));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_ORANGE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 1));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_MAGENTA)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 2));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_LBLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 3));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_YELLOW)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 4));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_LIME)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 5));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_PINK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 6));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_GRAY)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 7));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_SILVER)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 8));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_CYAN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 9));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_PURPLE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 10));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_BLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 11));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_BROWN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 12));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_GREEN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 13));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_RED)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 14));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }
                if(itemstack.getItem() == ModItems.ROUTER_BOX_BLACK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.ROUTER,1, 15));
                    playerIn.addItemStackToInventory(new ItemStack(DeviceItems.ETHERNET_CABLE,1));
                }

                // Printer
                if(itemstack.getItem() == ModItems.PRINTER_BOX_WHITE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 0));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_ORANGE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 1));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_MAGENTA)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 2));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_LBLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 3));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_YELLOW)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 4));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_LIME)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 5));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_PINK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 6));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_GRAY)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 7));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_SILVER)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 8));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_CYAN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 9));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_PURPLE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 10));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_BLUE)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 11));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_BROWN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 12));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_GREEN)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 13));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_RED)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 14));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
                if(itemstack.getItem() == ModItems.PRINTER_BOX_BLACK)
                {
                    playerIn.addItemStackToInventory(new ItemStack(DeviceBlocks.PRINTER,1, 15));
                    playerIn.addItemStackToInventory(new ItemStack(Items.PAPER,1));
                }
            }
            else
            {
                playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You need at least one clear inventory slot before you can open this item."));
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }

    private int countEmptySlots(EntityPlayer player)
    {
        int count = 0;
        for (ItemStack stack : player.inventory.mainInventory)
        {
            if(stack.isEmpty())
            {
                count++;
            }
        }
        return count;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
