package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.SoundInit;
import com.mesabrook.ib.net.PlaySoundPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ToyPopper extends Item implements IHasModel
{
    public ToyPopper(String name, int maxUses)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxDamage(maxUses);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        PlaySoundPacket packet = new PlaySoundPacket();
        ItemStack item = player.getHeldItem(hand);
        try
        {
            if(!world.isRemote)
            {
                SoundRandomizer.PopRandomizer();

                if(!player.isCreative())
                {
                    packet.pos = player.getPosition();
                    packet.soundName = SoundRandomizer.popResult;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
                    item.damageItem(1, player);
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
                }
                else
                {
                    packet.pos = player.getPosition();
                    packet.soundName = SoundRandomizer.popResult;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
                }

            }
            else
            {
                SoundRandomizer.SPPopRandomizer();
                if(SoundRandomizer.popResultSP == null)
                {
                    SoundRandomizer.SPPopRandomizer();
                    world.playSound(player, player.getPosition(), SoundInit.POP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
                }
                else
                {
                    world.playSound(player, player.getPosition(), SoundRandomizer.popResultSP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
                }
            }
        }
        catch(Exception ex)
        {
            Main.logger.error(ex);
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(ModConfig.funnyTooltips)
        {
            if(this.getUnlocalizedName().contains("popper"))
            {
                tooltip.add(TextFormatting.AQUA + "A popping fidget toy");
                super.addInformation(stack, world, tooltip, flag);
            }
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
