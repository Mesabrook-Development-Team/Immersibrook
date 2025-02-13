package com.mesabrook.ib.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;

public class BlockChrysotile extends Block implements IHasModel
{
	public BlockChrysotile(String name) 
	{
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(name);
		setSoundType(SoundType.STONE);
		setHardness(1.5F);
		setResistance(8.0F);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		setHarvestLevel("pickaxe", 0);
		setLightLevel(0);
		
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	List<ItemStack> drops = new ArrayList<>();
    	Random rand = new Random();
    	int numberOfDrops = 1 + rand.nextInt(4);

        if (rand.nextInt(25) == 0) 
        {
            drops.add(new ItemStack(ModItems.ASBESTOS, numberOfDrops));
            drops.add(new ItemStack(Item.getItemFromBlock(this)));
        }
        else 
        { 
            drops.add(new ItemStack(Item.getItemFromBlock(this)));
        }
        
        return drops;
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);	
	}
}
