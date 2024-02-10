package com.mesabrook.ib.items.weapons;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemWeapon extends ItemSword implements IHasModel
{
	private final TextComponentTranslation emerald = new TextComponentTranslation("im.cliche");
	public ItemWeapon(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);

		emerald.getStyle().setItalic(true);
		emerald.getStyle().setColor(TextFormatting.GREEN);

		ModItems.ITEMS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(ModConfig.funnyTooltips)
		{
			if(this.getUnlocalizedName().contains("sod"))
			{
				tooltip.add(TextFormatting.RED + "noo you can't just break a sword in half and call it a sod!");
				tooltip.add(TextFormatting.AQUA + "hehe sod go swish slash");
			}
		}

		if(stack.getItem() == ModItems.EMERALD_SWORD)
		{
			tooltip.add(emerald.getFormattedText());
		}
		if(stack.getItem() == ModItems.PIANO)
		{
			tooltip.add(TextFormatting.RED + "Wait, where'd you get that piano?");
		}
		super.addInformation(stack, world, tooltip, flag);
	}
	
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(stack.getItem() == ModItems.PIANO)
		{
			if(player.world.isRemote)
			{
				ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
				packet.pos = player.getPosition();
				packet.soundName = "piano";
				packet.rapidSounds = false;
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
				return false;
			}
			return false;
		}
		return false;
    }

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
