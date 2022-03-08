package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.PlaySoundPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		World w = playerIn.world;
		if(!playerIn.isSneaking() && target.getHealth() < target.getMaxHealth())
		{
			if(target instanceof EntityPlayer || target instanceof EntityAnimal)
			{
				target.setHealth(target.getMaxHealth());
				stack.damageItem(1, playerIn);
				playerIn.sendStatusMessage(new TextComponentString(TextFormatting.AQUA + new TextComponentTranslation("im.healed").getFormattedText() + " " + target.getName()), true);

				if(!w.isRemote)
				{
					PlaySoundPacket packet = new PlaySoundPacket();
					packet.pos = playerIn.getPosition();
					packet.soundName = "heal";
					PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 10));
				}

				if(playerIn instanceof EntityPlayer)
				{
					Triggers.trigger(Triggers.FIRST_AID_OTHER_PLAYER, playerIn);
				}
				return true;
			}
			else
			{
				target.setHealth(target.getHealth() / 2);
				target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5000, 10));
				stack.damageItem(ModConfig.firstAidUses / 2, playerIn);

				TextComponentTranslation harmful = new TextComponentTranslation("im.healed.wrong");
				harmful.getStyle().setBold(true);
				harmful.getStyle().setColor(TextFormatting.RED);

				playerIn.sendStatusMessage(new TextComponentString(harmful.getFormattedText()), true);
				return false;
			}
		}
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack item = player.getHeldItem(hand);
		if(player.isSneaking())
		{
			if(!player.isCreative())
			{
				if(player.getHealth() < player.getMaxHealth())
				{
					player.setHealth(player.getMaxHealth());
					item.damageItem(1, player);
					player.sendStatusMessage(new TextComponentString(TextFormatting.AQUA + new TextComponentTranslation("im.selfheal").getFormattedText()), true);

					if(!world.isRemote)
					{
						PlaySoundPacket packet = new PlaySoundPacket();
						packet.pos = player.getPosition();
						packet.soundName = "heal";
						PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 10));
					}

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
		else
		{
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(GuiScreen.isShiftKeyDown())
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("im.firstaidkit").getFormattedText());
		}
		else
		{
			tooltip.add(TextFormatting.WHITE + new TextComponentTranslation("im.tooltip.hidden").getFormattedText());
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
