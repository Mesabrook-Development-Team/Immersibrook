package rz.mesabrook.wbtc.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.SoundRandomizer;
import rz.mesabrook.wbtc.util.config.ModConfig;

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
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(64));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
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
		super.addInformation(stack, world, tooltip, flag);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
}
