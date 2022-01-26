package com.mesabrook.ib.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.PlaySoundPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFirstAidKit extends Item implements IHasModel
{	
	public ItemFirstAidKit(String name, CreativeTabs tab)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(1);
		setCreativeTab(tab);
		
		if(ModConfig.firstAidUses <= 0)
		{
			setMaxDamage(6);
			Main.logger.info("[" + Reference.MODNAME + " WARNING] Invalid Config Entry at firstAidUses. Number cannot be zero or less. Default uses have been set to six.");
		}
		else
		{
			setMaxDamage(ModConfig.firstAidUses);
			Main.logger.info("[" + Reference.MODNAME + "] First Aid Kit Uses: " + ModConfig.firstAidUses);
			Main.logger.info("[" + Reference.MODNAME + "] You can adjust this number in wbtc.cfg");
		}
		
		SoundRandomizer.RandomizeSound();
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack item = player.getHeldItem(hand);
		if(!player.isCreative())
		{
			if(player.getHealth() < player.getMaxHealth())
			{
				player.setHealth(100);
				world.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
				item.damageItem(1, player);

				if(player instanceof EntityPlayer)
				{
					Triggers.trigger(Triggers.FIRST_AID, player);
				}
				
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
			}
			else
			{
				SoundRandomizer.RandomizeSound();
				world.playSound(player, player.getPosition(), SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
			}
		}
		else
		{
			try
			{
				if(!world.isRemote)
				{
					player.sendMessage(new TextComponentString(TextFormatting.RED + "Not while in Creative Mode >:("));
					SoundRandomizer.RandomizeSound();
					PlaySoundPacket packet = new PlaySoundPacket();
					packet.pos = player.getPosition();					
					packet.soundName = SoundRandomizer.result;
					PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
				}
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
			}
			catch(Exception ex)
			{
				player.sendMessage(new TextComponentString(ex.getMessage()));
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(this.getUnlocalizedName().contains("first_aid"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Approved by the Mesabrook Department of Public Health");
		}
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
