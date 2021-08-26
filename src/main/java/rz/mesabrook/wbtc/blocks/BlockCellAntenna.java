package rz.mesabrook.wbtc.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.config.ModConfig;
import rz.mesabrook.wbtc.util.saveData.AntennaData;

public class BlockCellAntenna extends Block implements IHasModel {

	public BlockCellAntenna(String name)
	{
		super(Material.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.METAL);
		setHardness(8.0F);
		setResistance(8.0F);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		setHarvestLevel("pickaxe", 0);
		setTickRandomly(true);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(ItemBlock.getItemFromBlock(this), 0);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		checkHeight(worldIn, pos);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		AntennaData.getOrCreate(worldIn).removeHeight(pos);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		super.randomTick(worldIn, pos, state, random);
		checkHeight(worldIn, pos);
	}
	
	private void checkHeight(World world, BlockPos currentPosition)
	{
		if (world.isRemote)
		{
			return;
		}
		
		int startX = currentPosition.getX() - (ModConfig.cellAntennaHeightScanWidth / 2);
		int startZ = currentPosition.getZ() - (ModConfig.cellAntennaHeightScanWidth / 2);
		int heightTotal = 0;
		
		for(int x = startX; x < (startX + ModConfig.cellAntennaHeightScanWidth); x++)
		{
			for (int z = startZ; z < (startZ + ModConfig.cellAntennaHeightScanWidth); z++)
			{
				boolean encounteredGround = false;
				for(int y = currentPosition.getY() - 1; y > (currentPosition.getY() - ModConfig.cellAntennaOptimalHeight); y--)
				{
					BlockPos pos = new BlockPos(x, y, z);
					IBlockState state = world.getBlockState(pos);
					if (state.isFullCube())
					{
						encounteredGround = true;
						heightTotal += currentPosition.getY() - y - 1;
						break;
					}
				}
				
				if (!encounteredGround)
				{
					heightTotal += ModConfig.cellAntennaOptimalHeight;
				}
			}
		}
		
		int effectiveHeight = heightTotal / (int)Math.pow(ModConfig.cellAntennaHeightScanWidth, 2);
		AntennaData.getOrCreate(world).setHeight(currentPosition, effectiveHeight);
	}
}
