package rz.mesabrook.wbtc.blocks.stairs;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

public class MiscStairs extends BlockStairs implements IHasModel
{
	public MiscStairs(String name, IBlockState modelState, SoundType snd, String harvestTool, int harvestLevel)
	{
		super(modelState);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(1.8F);
		setResistance(1.8F);
		setSoundType(snd);
		setHarvestLevel(harvestTool, harvestLevel);
		
		this.useNeighborBrightness = true;
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }
	
	@Override
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1;
	}
	

	
	@Override
    protected boolean canSilkHarvest()
    {
        return true;
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
