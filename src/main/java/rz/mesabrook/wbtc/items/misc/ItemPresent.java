package rz.mesabrook.wbtc.items.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.init.SoundInit;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.ItemRandomizer;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

public class ItemPresent extends Item implements IHasModel
{
    public ItemPresent(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        setMaxDamage(1);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        ItemRandomizer.RandomizeItem();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack item = player.getHeldItem(hand);
        if(!world.isRemote)
        {
            ItemRandomizer.RandomizeItem();
            if(!player.isCreative())
            {
                player.addItemStackToInventory(ItemRandomizer.giftItem);
                player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + new TextComponentTranslation("im.present").getFormattedText()), true);
                PlaySoundPacket packet = new PlaySoundPacket();
                packet.pos = player.getPosition();
                packet.soundName = "rip";
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 5));
                if(item.getItem() instanceof ItemPresent)
                {
                    item.setCount(0);
                }
            }
            else
            {
                PlaySoundPacket packet = new PlaySoundPacket();
                packet.pos = player.getPosition();
                packet.modID = "minecraft";
                packet.soundName = "entity.item.pickup";
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 5));
                player.addItemStackToInventory(ItemRandomizer.giftItem);
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
    }
}
