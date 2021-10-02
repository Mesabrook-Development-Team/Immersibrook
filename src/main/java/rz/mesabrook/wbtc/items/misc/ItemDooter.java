package rz.mesabrook.wbtc.items.misc;

import com.pam.harvestcraft.item.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

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
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
