package com.mesabrook.ib.items.armor;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class WearableHat extends Item implements IHasModel
{
    public WearableHat(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxDamage(200);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack item = player.getHeldItem(hand);
        ItemStack armorSlot = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

        if(armorSlot.isEmpty())
        {
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, item.copy());
            item.setCount(0);

            if(!world.isRemote)
            {
                if(this == ModItems.SANTA_HAT)
                {
                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = player.getPosition();
                    packet.soundName = "jingles";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
                }
            }

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {

    }
}
