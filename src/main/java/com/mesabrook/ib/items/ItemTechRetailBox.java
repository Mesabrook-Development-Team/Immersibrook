package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mrcrayfish.device.init.DeviceBlocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTechRetailBox extends Item implements IHasModel
{
    private ItemStack itemInBox;
    public ItemTechRetailBox(String name, ItemStack phoneToGive)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
        itemInBox = phoneToGive;
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

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        try
        {
            ItemStack currentStack = playerIn.getHeldItem(handIn);

            if(playerIn instanceof EntityPlayer)
            {
                Triggers.trigger(Triggers.PHONE_USE, playerIn);
            }

            if(worldIn.isRemote)
            {
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }
            int slot = getSlotForStack(playerIn, currentStack);

            if(!worldIn.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = playerIn.getPosition();
                packet.soundName = "phone_unbox";
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
            }

            ItemStack changedStack = itemInBox;
            playerIn.inventory.setInventorySlotContents(slot, changedStack);

            if(itemInBox.getItem() instanceof ItemPhone)
            {
                playerIn.addItemStackToInventory(new ItemStack(ModItems.OT_CHARGER, 1));
            }

            // Laptop retail logic
            Map<Item, Integer> laptopMeta = new HashMap<>();
            laptopMeta.put(ModItems.LAPTOP_BOX_WHITE, 0);
            laptopMeta.put(ModItems.LAPTOP_BOX_ORANGE, 1);
            laptopMeta.put(ModItems.LAPTOP_BOX_MAGENTA, 2);
            laptopMeta.put(ModItems.LAPTOP_BOX_LBLUE, 3);
            laptopMeta.put(ModItems.LAPTOP_BOX_YELLOW, 4);
            laptopMeta.put(ModItems.LAPTOP_BOX_LIME, 5);
            laptopMeta.put(ModItems.LAPTOP_BOX_PINK, 6);
            laptopMeta.put(ModItems.LAPTOP_BOX_GRAY, 7);
            laptopMeta.put(ModItems.LAPTOP_BOX_SILVER, 8);
            laptopMeta.put(ModItems.LAPTOP_BOX_CYAN, 9);
            laptopMeta.put(ModItems.LAPTOP_BOX_PURPLE, 10);
            laptopMeta.put(ModItems.LAPTOP_BOX_BLUE, 11);
            laptopMeta.put(ModItems.LAPTOP_BOX_BROWN, 12);
            laptopMeta.put(ModItems.LAPTOP_BOX_GREEN, 13);
            laptopMeta.put(ModItems.LAPTOP_BOX_RED, 14);
            laptopMeta.put(ModItems.LAPTOP_BOX_BLACK, 15);

            // Router retail logic
            Map<Item, Integer> routerMetadata = new HashMap<>();
            routerMetadata.put(ModItems.ROUTER_BOX_WHITE, 0);
            routerMetadata.put(ModItems.ROUTER_BOX_ORANGE, 1);
            routerMetadata.put(ModItems.ROUTER_BOX_MAGENTA, 2);
            routerMetadata.put(ModItems.ROUTER_BOX_LBLUE, 3);
            routerMetadata.put(ModItems.ROUTER_BOX_YELLOW, 4);
            routerMetadata.put(ModItems.ROUTER_BOX_LIME, 5);
            routerMetadata.put(ModItems.ROUTER_BOX_PINK, 6);
            routerMetadata.put(ModItems.ROUTER_BOX_GRAY, 7);
            routerMetadata.put(ModItems.ROUTER_BOX_SILVER, 8);
            routerMetadata.put(ModItems.ROUTER_BOX_CYAN, 9);
            routerMetadata.put(ModItems.ROUTER_BOX_PURPLE, 10);
            routerMetadata.put(ModItems.ROUTER_BOX_BLUE, 11);
            routerMetadata.put(ModItems.ROUTER_BOX_BROWN, 12);
            routerMetadata.put(ModItems.ROUTER_BOX_GREEN, 13);
            routerMetadata.put(ModItems.ROUTER_BOX_RED, 14);
            routerMetadata.put(ModItems.ROUTER_BOX_BLACK, 15);

            // Printer retail logic
            Map<Item, Integer> printerMetadata = new HashMap<>();
            printerMetadata.put(ModItems.PRINTER_BOX_WHITE, 0);
            printerMetadata.put(ModItems.PRINTER_BOX_ORANGE, 1);
            printerMetadata.put(ModItems.PRINTER_BOX_MAGENTA, 2);
            printerMetadata.put(ModItems.PRINTER_BOX_LBLUE, 3);
            printerMetadata.put(ModItems.PRINTER_BOX_YELLOW, 4);
            printerMetadata.put(ModItems.PRINTER_BOX_LIME, 5);
            printerMetadata.put(ModItems.PRINTER_BOX_PINK, 6);
            printerMetadata.put(ModItems.PRINTER_BOX_GRAY, 7);
            printerMetadata.put(ModItems.PRINTER_BOX_SILVER, 8);
            printerMetadata.put(ModItems.PRINTER_BOX_CYAN, 9);
            printerMetadata.put(ModItems.PRINTER_BOX_PURPLE, 10);
            printerMetadata.put(ModItems.PRINTER_BOX_BLUE, 11);
            printerMetadata.put(ModItems.PRINTER_BOX_BROWN, 12);
            printerMetadata.put(ModItems.PRINTER_BOX_GREEN, 13);
            printerMetadata.put(ModItems.PRINTER_BOX_RED, 14);
            printerMetadata.put(ModItems.PRINTER_BOX_BLACK, 15);

            Integer metadataLaptop = laptopMeta.get(currentStack.getItem());
            Integer metadataRouter = routerMetadata.get(currentStack.getItem());
            Integer metadataPrinter = printerMetadata.get(currentStack.getItem());

            if(currentStack.getItem().getUnlocalizedName().contains("laptop") && metadataLaptop != null)
            {
                changedStack = new ItemStack(DeviceBlocks.LAPTOP, 1, metadataLaptop);
            }
            if(currentStack.getItem().getUnlocalizedName().contains("router") && metadataRouter != null)
            {
                changedStack = new ItemStack(DeviceBlocks.ROUTER, 1, metadataRouter);
            }
            if(currentStack.getItem().getUnlocalizedName().contains("printer") && metadataPrinter != null)
            {
                changedStack = new ItemStack(DeviceBlocks.PRINTER, 1, metadataPrinter);
            }

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, changedStack);
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
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
