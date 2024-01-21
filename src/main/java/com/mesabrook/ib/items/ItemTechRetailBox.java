package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mrcrayfish.device.init.DeviceBlocks;
import com.mrcrayfish.device.init.DeviceItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
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
            tooltip.add(new TextComponentString(TextFormatting.WHITE + "Contents").getFormattedText());
            tooltip.add(new TextComponentString("1 Craytech Laptop, ").getFormattedText() + getColor(stack).getFormattedText());
        }
        if(stack.getItem().getUnlocalizedName().contains("router"))
        {
            tooltip.add(new TextComponentString(TextFormatting.GRAY + "§oManufactured for Crayfish Ind. by MSAC").getFormattedText());
            tooltip.add(new TextComponentString(" ").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.WHITE + "Contents").getFormattedText());
            tooltip.add(new TextComponentString("1 Craytech Router, ").getFormattedText() + getColor(stack).getFormattedText());
            tooltip.add(new TextComponentString("1 Ethernet Cable").getFormattedText());
        }
        if(stack.getItem().getUnlocalizedName().contains("printer"))
        {
            tooltip.add(new TextComponentString(TextFormatting.GRAY + "§oManufactured for Crayfish Ind. by MSAC").getFormattedText());
            tooltip.add(new TextComponentString(" ").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.WHITE + "Contents").getFormattedText());
            tooltip.add(new TextComponentString("1 Craytech Printer, ").getFormattedText() + getColor(stack).getFormattedText());
            tooltip.add(new TextComponentString("16 Paper").getFormattedText());
        }
        if(stack.getItem().getUnlocalizedName().contains("phone"))
        {
            tooltip.add(new TextComponentString(TextFormatting.GRAY + "§oManufactured by MSAC").getFormattedText());
            tooltip.add(new TextComponentString(" ").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.WHITE + "Contents").getFormattedText());
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
        ItemStack boxItem = playerIn.getHeldItem(handIn);
        ItemStack productStack = new ItemStack(Items.STICK);

        if(!(boxItem.getItem() instanceof ItemTechRetailBox))
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, boxItem);
        }

        // Phones
        if(boxItem.getItem() == ModItems.BOX_WHITE)
        {
            productStack = new ItemStack(ModItems.PHONE_WHITE);
        }
        if(boxItem.getItem() == ModItems.BOX_ORANGE)
        {
            productStack = new ItemStack(ModItems.PHONE_ORANGE);
        }
        if(boxItem.getItem() == ModItems.BOX_MAGENTA)
        {
            productStack = new ItemStack(ModItems.PHONE_MAGENTA);
        }
        if(boxItem.getItem() == ModItems.BOX_LBLUE)
        {
            productStack = new ItemStack(ModItems.PHONE_LBLUE);
        }
        if(boxItem.getItem() == ModItems.BOX_LIME)
        {
            productStack = new ItemStack(ModItems.PHONE_LIME);
        }
        if(boxItem.getItem() == ModItems.BOX_YELLOW)
        {
            productStack = new ItemStack(ModItems.PHONE_YELLOW);
        }
        if(boxItem.getItem() == ModItems.BOX_PINK)
        {
            productStack = new ItemStack(ModItems.PHONE_PINK);
        }
        if(boxItem.getItem() == ModItems.BOX_GRAY)
        {
            productStack = new ItemStack(ModItems.PHONE_GRAY);
        }
        if(boxItem.getItem() == ModItems.BOX_SILVER)
        {
            productStack = new ItemStack(ModItems.PHONE_SILVER);
        }
        if(boxItem.getItem() == ModItems.BOX_CYAN)
        {
            productStack = new ItemStack(ModItems.PHONE_CYAN);
        }
        if(boxItem.getItem() == ModItems.BOX_PURPLE)
        {
            productStack = new ItemStack(ModItems.PHONE_PURPLE);
        }
        if(boxItem.getItem() == ModItems.BOX_BLUE)
        {
            productStack = new ItemStack(ModItems.PHONE_BLUE);
        }
        if(boxItem.getItem() == ModItems.BOX_BROWN)
        {
            productStack = new ItemStack(ModItems.PHONE_BROWN);
        }
        if(boxItem.getItem() == ModItems.BOX_GREEN)
        {
            productStack = new ItemStack(ModItems.PHONE_GREEN);
        }
        if(boxItem.getItem() == ModItems.BOX_RED)
        {
            productStack = new ItemStack(ModItems.PHONE_RED);
        }
        if(boxItem.getItem() == ModItems.BOX_BLACK)
        {
            productStack = new ItemStack(ModItems.PHONE_BLACK);
        }
        if(boxItem.getItem() == ModItems.OT_CHARGER_BOX)
        {
            productStack = new ItemStack(ModItems.OT_CHARGER);
        }
        if(boxItem.getItem() == ModItems.SIM_BOX)
        {
            productStack = new ItemStack(ModItems.SIM_CARD);
        }

        // Laptops
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_WHITE)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 0);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_ORANGE)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 1);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_MAGENTA)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 2);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_LBLUE)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 3);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_LIME)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 5);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_YELLOW)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 4);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_PINK)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 6);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_GRAY)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 7);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_SILVER)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 8);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_CYAN)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 9);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_PURPLE)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 10);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_BLUE)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 11);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_BROWN)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 12);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_GREEN)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 13);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_RED)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 14);
        }
        if(boxItem.getItem() == ModItems.LAPTOP_BOX_BLACK)
        {
            productStack = new ItemStack(DeviceBlocks.LAPTOP, 1, 15);
        }

        // Routers
        if(boxItem.getItem() == ModItems.ROUTER_BOX_WHITE)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 0);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_ORANGE)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 1);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_MAGENTA)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 2);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_LBLUE)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 3);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_LIME)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 5);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_YELLOW)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 4);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_PINK)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 6);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_GRAY)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 7);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_SILVER)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 8);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_CYAN)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 9);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_PURPLE)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 10);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_BLUE)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 11);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_BROWN)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 12);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_GREEN)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 13);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_RED)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 14);
        }
        if(boxItem.getItem() == ModItems.ROUTER_BOX_BLACK)
        {
            productStack = new ItemStack(DeviceBlocks.ROUTER, 1, 15);
        }

        // Printers
        if(boxItem.getItem() == ModItems.PRINTER_BOX_WHITE)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 0);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_ORANGE)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 1);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_MAGENTA)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 2);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_LBLUE)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 3);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_LIME)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 5);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_YELLOW)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 4);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_PINK)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 6);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_GRAY)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 7);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_SILVER)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 8);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_CYAN)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 9);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_PURPLE)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 10);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_BLUE)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 11);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_BROWN)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 12);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_GREEN)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 13);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_RED)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 14);
        }
        if(boxItem.getItem() == ModItems.PRINTER_BOX_BLACK)
        {
            productStack = new ItemStack(DeviceBlocks.PRINTER, 1, 15);
        }

        if(!worldIn.isRemote)
        {
            ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
            packet.pos = playerIn.getPosition();
            packet.soundName = "phone_unbox";
            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
            worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, productStack));
            playerIn.inventoryContainer.detectAndSendChanges();

            if(boxItem.getItem().getUnlocalizedName().contains("phone"))
            {
                worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, new ItemStack(ModItems.OT_CHARGER_BOX)));
            }

            if(boxItem.getItem().getUnlocalizedName().contains("router"))
            {
                worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, new ItemStack(DeviceItems.ETHERNET_CABLE, 1)));
            }

            if(boxItem.getItem().getUnlocalizedName().contains("printer"))
            {
                worldIn.spawnEntity(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, new ItemStack(Items.PAPER, 16)));
            }

            playerIn.setHeldItem(handIn, ItemStack.EMPTY);

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, boxItem);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, boxItem);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
