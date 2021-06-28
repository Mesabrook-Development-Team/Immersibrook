package rz.mesabrook.wbtc.items.misc;

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

public class ItemMesabrookIcon extends Item implements IHasModel
{
    public ItemMesabrookIcon(String name)
    {
        setMaxStackSize(1);
        setMaxDamage(212);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModItems.ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (!worldIn.isRemote)
        {
            if(playerIn instanceof EntityPlayer)
            {
                Triggers.trigger(Triggers.MESARANG, playerIn);
            }

            PlaySoundPacket packet = new PlaySoundPacket();
            packet.pos = playerIn.getPosition();
            packet.soundName = "woosh";
            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));

            EntityMesabrookM mesarang = new EntityMesabrookM(worldIn, playerIn, playerIn.getHeldItem(handIn).getItemDamage());
            mesarang.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(mesarang);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
