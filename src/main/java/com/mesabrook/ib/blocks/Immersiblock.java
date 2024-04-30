package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.IndependentTimer;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Immersiblock extends Block implements IHasModel
{	
	public Immersiblock(String name, Material material, SoundType sound, CreativeTabs tab, float lightLevel)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(sound);
		setHardness(1.5F);
		setResistance(3.0F);
		setCreativeTab(tab);
		setHarvestLevel("pickaxe", 0);
		setLightLevel(lightLevel);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(64));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if(state.getBlock() == ModBlocks.BISMUTH_ORE)
		{
			return ModItems.RAW_BISMUTH;
		}
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		if(state.getBlock() == ModBlocks.BISMUTH_ORE)
		{
			return random.nextInt(2) + 1;
		}
		return super.quantityDropped(random);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(placer instanceof EntityPlayer)
		{
			if(this.getUnlocalizedName().contains("plastic_cube_"))
			{
				EntityPlayer player = (EntityPlayer) placer;
				Triggers.trigger(Triggers.MAKE_PLASTIC_CUBE, player);
			}
		}
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		
		if(this.getUnlocalizedName().contains("wbtc_checkerboard") || this.getUnlocalizedName().contains("panel_checkerboard"))
		{
			tooltip.add(TextFormatting.AQUA + "It's not a bug, it's a " + TextFormatting.OBFUSCATED + "Featuretm");
		}
		if(this.getUnlocalizedName().contains("tileboard"))
		{
			tooltip.add(TextFormatting.AQUA + "It's baaaaack");
		}
		
		if(this.getBlockState().getBlock() == ModBlocks.BISMUTH_BLOCK)
		{
			tooltip.add(TextFormatting.GREEN + "Taste the Rainbow!");
		}
		if(this.getBlockState().getBlock() == ModBlocks.DARK_BISMUTH)
		{
			tooltip.add(TextFormatting.GRAY + "Taste the Rainbow?");
			tooltip.add(TextFormatting.BLUE + "To make Iridescent Bismuth, process this block in an Arc Furnace");
		}
		
		super.addInformation(stack, world, tooltip, flag);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
}
