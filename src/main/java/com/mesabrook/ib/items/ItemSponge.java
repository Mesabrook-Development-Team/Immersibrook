package com.mesabrook.ib.items;

import com.mesabrook.ib.*;
import com.mesabrook.ib.init.*;
import com.mesabrook.ib.net.*;
import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.*;
import java.util.*;

public class ItemSponge extends Item implements IHasModel
{
    public ItemSponge(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        setMaxDamage(3);

        ModItems.ITEMS.add(this);
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack item = player.getHeldItem(hand);
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileEntitySign)
            {
                TileEntitySign sign = (TileEntitySign)te;
                sign.setPlayer(player);
                ObfuscationReflectionHelper.setPrivateValue(TileEntitySign.class, sign, true, "field_145916_j", "isEditable");
                player.openEditSign(sign);

                if(!player.isCreative())
                {
                    item.damageItem(1, player);
                }

                PlaySoundPacket packet = new PlaySoundPacket();
                packet.pos = pos;
                packet.soundName = "sponge_use";
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 10));

                return EnumActionResult.PASS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(new TextComponentTranslation("tooltip.sponge").getFormattedText());
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
