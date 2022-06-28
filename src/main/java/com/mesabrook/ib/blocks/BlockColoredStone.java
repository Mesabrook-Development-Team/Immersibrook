package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.SoundTypeInit;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockColoredStone extends Block implements IHasModel {

	public static PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
	
	public BlockColoredStone() {
		super(Material.ROCK);
		setUnlocalizedName("colored_stone");
		setRegistryName("colored_stone");
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(8.0F);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		setHarvestLevel("pickaxe", 0);
		setLightLevel(0);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this)
				{
					public int getMetadata(int damage) { return damage; }
				}.setRegistryName(this.getRegistryName()).setMaxStackSize(64).setHasSubtypes(true));
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < 16; i++)
		{
			Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), i, "color=" + EnumDyeColor.byMetadata(i).getDyeColorName());
		}
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {		
		for(int i = 0; i < 16; i++)
		{
			items.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR).getMetadata();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
	}
}