package com.mesabrook.ib.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(stack.getItem() == ModItems.ARMY_CAP)
		{
			tooltip.add(TextFormatting.GREEN + "Republic of Mesabrook Army");
		}
		if(stack.getItem() == ModItems.NAVY_CAP)
		{
			tooltip.add(TextFormatting.BLUE + "Republic of Mesabrook Navy");
		}
		if(stack.getItem() == ModItems.AF_CAP)
		{
			tooltip.add(TextFormatting.AQUA + "Republic of Mesabrook Air Force");
		}
		if(stack.getItem() == ModItems.MC_CAP)
		{
			tooltip.add(TextFormatting.GOLD + "Republic of Mesabrook Marine Corps");
		}
		if(stack.getItem() == ModItems.SF_CAP)
		{
			tooltip.add(TextFormatting.GRAY + "Republic of Mesabrook Space Force");
			tooltip.add(TextFormatting.BLUE + "Mesabrook Space Agency (MSA)");
		}
		if(stack.getItem() == ModItems.MARSHALS_CAP)
		{
			tooltip.add(TextFormatting.WHITE + "Republic of Mesabrook Marshals Service");
			tooltip.add(TextFormatting.ITALIC + "To Protect and Serve.");
		}
	}

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {

    }
}
