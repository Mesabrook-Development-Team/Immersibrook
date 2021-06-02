package rz.mesabrook.wbtc.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.SoundRandomizer;
import rz.mesabrook.wbtc.util.TooltipRandomizer;

public class MiscBlock extends Block implements IHasModel
{
	public MiscBlock(String name, Material material, SoundType sound, CreativeTabs tab, float lightLevel)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(sound);
		setHardness(8.0F);
		setResistance(8.0F);
		setCreativeTab(tab);
		setHarvestLevel("pickaxe", 0);
		setLightLevel(lightLevel);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		
		if(this.getUnlocalizedName().contains("wbtc_checkerboard") || this.getUnlocalizedName().contains("panel_checkerboard"))
		{
			tooltip.add(TextFormatting.AQUA + "It's not a bug, it's a " + TextFormatting.OBFUSCATED + "Featuretm");
		}
		else if(this.getUnlocalizedName().contains("aluminum_block"))
		{
			tooltip.add(TextFormatting.AQUA + "Can be used as a Beacon base");
		}
		else if(this.getUnlocalizedName().contains("synthetic_turf"))
		{
			tooltip.add(TextFormatting.GOLD + "It glows in the dark and is not approved by the NFL.");
		}
		else if(this.getUnlocalizedName().contains("tileboard"))
		{
			tooltip.add(TextFormatting.AQUA + "It's baaaaack");
		}
		else if(this.getUnlocalizedName().contains("cat_block"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "The greatest block to ever exist ever");
		}
		
		super.addInformation(stack, world, tooltip, flag);
	}
	
	// For the Aluminum Block
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
	{
		if(this.getUnlocalizedName().contains("aluminum_block") || this.getUnlocalizedName().contains("cat_block"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		try
		{
			if(this.getUnlocalizedName().contains("cat_block"))
			{
				SoundRandomizer.CatCubeRandomizer();
				if(SoundRandomizer.catResult == null)
				{
					world.playSound(player, pos, SoundEvents.ENTITY_CAT_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				else
				{
					world.playSound(player, pos, SoundRandomizer.catResult, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception ex)
		{
			Main.logger.error(ex);
			SoundRandomizer.CatCubeRandomizer();
			return false;
		}
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
}
