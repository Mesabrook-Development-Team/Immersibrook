package rz.mesabrook.wbtc.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
		
		super.addInformation(stack, world, tooltip, flag);
	}
	
	// For the Aluminum Block
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
	{
		if(this.getUnlocalizedName().contains("aluminum_block"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
