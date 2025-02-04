package com.mesabrook.ib.items;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.PotionInit;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPepto extends ItemFood implements IHasModel
{
	public ItemPepto(String name)
	{
		super(1, 10, false);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}
	
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
    	return EnumAction.DRINK;
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
    	if(!worldIn.isRemote)
    	{
    		if(entityLiving.isPotionActive(MobEffects.HUNGER))
    		{
    			entityLiving.removeActivePotionEffect(MobEffects.HUNGER);
    		}
    		if(entityLiving.isPotionActive(MobEffects.NAUSEA))
    		{
    			entityLiving.removeActivePotionEffect(MobEffects.NAUSEA);
    		}
    		if(entityLiving.isPotionActive(MobEffects.POISON))
    		{
    			entityLiving.removeActivePotionEffect(MobEffects.POISON);
    		}
    		if(entityLiving.isPotionActive(PotionInit.ASBESTOS_EFFECT))
    		{
    			entityLiving.removeActivePotionEffect(PotionInit.ASBESTOS_EFFECT);
    		}
    	}

    	stack.shrink(1);
    	return stack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(TextFormatting.GRAY + "Bismuth Subsalicylate");
		tooltip.add(TextFormatting.WHITE + "A commonly-used stomach relief aid for indigestion, upset stomach, heartburn, and diarrhea.");
	}
	
    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
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
