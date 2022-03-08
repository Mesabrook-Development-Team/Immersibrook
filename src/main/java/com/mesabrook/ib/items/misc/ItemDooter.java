package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.PlaySoundPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ItemDooter extends Item implements IHasModel
{
    public ItemDooter(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        setMaxDamage(69);

        ModItems.ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack item = player.getHeldItem(hand);
        if(player instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.DOOT, player);
        }

        if(!player.isCreative())
        {
            item.damageItem(1, player);
            if(!world.isRemote)
            {
                PlaySoundPacket packet = new PlaySoundPacket();
                packet.pos = player.getPosition();
                packet.soundName = "doot";
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
        }
        else
        {
            if(!world.isRemote)
            {
                PlaySoundPacket packet = new PlaySoundPacket();
                packet.pos = player.getPosition();
                packet.soundName = "doot";
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
        }
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
