package com.mesabrook.ib.items.armor;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.PlaySoundPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class NightVisionGoggles extends Item implements IHasModel
{
	public NightVisionGoggles(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		setMaxStackSize(1);
		if(ModConfig.nightVisionDamage)
		{
			setMaxDamage(1000000000);
		}

		ModItems.ITEMS.add(this);
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

		if(armorSlot.isEmpty() && item.getItem() == ModItems.NV_GOGGLES)
		{
			player.setItemStackToSlot(EntityEquipmentSlot.HEAD, item.copy());
			item.setCount(0);
			if(!world.isRemote)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = player.getPosition();
				packet.soundName = "nv";
				PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
		}
		else
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
		}
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		if(playerIn instanceof EntityPlayer)
		{
			Triggers.trigger(Triggers.WEAR_NV, playerIn);
		}
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		NBTTagCompound tag = itemStack.getTagCompound();
		boolean nightvision = true;

		if (tag != null)
		{
			nightvision = tag.getBoolean("nightvision");
		}

		if(player instanceof EntityPlayer && nightvision)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 210, 1, true, false));
			if(ModConfig.nightVisionDamage)
			{
				itemStack.damageItem(1, player);
			}
		}
		else if(player instanceof EntityPlayer && !nightvision)
		{
			player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
		}
	}
}
