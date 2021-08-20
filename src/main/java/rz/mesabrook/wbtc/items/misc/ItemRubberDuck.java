package rz.mesabrook.wbtc.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.DamageSourceDuckBoom;
import rz.mesabrook.wbtc.util.IHasModel;
import net.minecraft.item.Item;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemRubberDuck extends Item implements IHasModel
{
    private final TextComponentTranslation angry = new TextComponentTranslation("im.angry");
    public static final DamageSource DUCK_GO_BOOM = new DamageSourceDuckBoom("duckboom");
    public ItemRubberDuck(String name)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
        angry.getStyle().setColor(TextFormatting.RED);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.isCreative())
        {
            if(!worldIn.isRemote)
            {
                PlaySoundPacket packet = new PlaySoundPacket();
                packet.pos = playerIn.getPosition();
                packet.soundName = "duck";
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
            }
        }
        else
        {
            if(!worldIn.isRemote)
            {
                PlaySoundPacket packet = new PlaySoundPacket();
                if(itemstack.getItem() == ModItems.RUBBER_DUCK_EVIL)
                {
                    Random chooser = new Random();
                    int chanceOfGoingBoom;
                    chanceOfGoingBoom = chooser.nextInt(5);

                    switch(chanceOfGoingBoom)
                    {
                        case 0:
                            packet.pos = playerIn.getPosition();
                            packet.soundName = "duck";
                            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                            break;
                        case 1:
                            packet.pos = playerIn.getPosition();
                            packet.soundName = "duck";
                            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                            break;
                        case 2:
                            packet.pos = playerIn.getPosition();
                            packet.soundName = "duck";
                            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                            break;
                        case 3:
                            packet.pos = playerIn.getPosition();
                            packet.soundName = "duck";
                            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                            break;
                        case 4:
                            packet.pos = playerIn.getPosition();
                            packet.soundName = "duckboom";
                            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));

                            worldIn.createExplosion(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, 1F, true);
                            playerIn.attackEntityFrom(DUCK_GO_BOOM, 100F);
                    }
                }
                else
                {
                    packet.pos = playerIn.getPosition();
                    packet.soundName = "duck";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(stack.getItem() == ModItems.RUBBER_DUCK_EVIL)
        {
            tooltip.add(angry.getFormattedText());
        }
    }
}
