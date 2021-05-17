package rz.mesabrook.wbtc.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.init.SoundInit;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

public class BlockStatue extends Block implements IHasModel
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final AxisAlignedBB CORE = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);

	public BlockStatue(String name, MapColor color)
	{
		super(Material.ROCK, color);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.METAL);
		setHardness(1.0F);
		setResistance(3.0F);
		setHarvestLevel("pickaxe", 1);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(1));
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
	public boolean causesSuffocation(IBlockState state)
	{
		return false;
	}
	
	@Override
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		AxisAlignedBB AABB = this.CORE;
		return AABB;
	}
	
	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
	
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    // Tooltips
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(this.getUnlocalizedName().contains("statue_owo"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + "Being noticed by senpai (This is a test trophy)");
		}
		else if(this.getUnlocalizedName().contains("statue_rz"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Being one of Mesabrook's founding members.");
		}
		else if(this.getUnlocalizedName().contains("statue_csx"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD + "Founding the nation.");
		}
		else if(this.getUnlocalizedName().contains("statue_td"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Being one of Mesabrook's founding members.");
		}
		else if(this.getUnlocalizedName().contains("statue_tlz"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Being one of Mesabrook's founding members.");
		}
		else if(this.getUnlocalizedName().contains("statue_lw"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Being one of Mesabrook's founding members.");
		}
		else if(this.getUnlocalizedName().contains("statue_svv"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Being one of Mesabrook's founding members.");
		}
		else if(this.getUnlocalizedName().contains("statue_md"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Being one of Mesabrook's founding members.");
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }
	
	// Play a sound when the player right-clicks on a statue.
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			try
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				
				Random rand = new Random();
				float pitch = 0.5F + rand.nextFloat();
				
				if (pitch > 1.25F) {pitch = 1.25F;}
				else if(pitch < 0.75F) {pitch = 0.75F;}
				
				if(this.getUnlocalizedName().contains("statue_owo"))
				{
					packet.soundName = "owo";
				}
				else if(this.getUnlocalizedName().contains("statue_rz"))
				{
					packet.soundName = "rz_trophy";
				}
				else if(this.getUnlocalizedName().contains("statue_csx"))
				{	
					packet.soundName = "csx_trophy";
				}
				else if(this.getUnlocalizedName().contains("statue_td"))
				{
					packet.soundName = "td_trophy";
				}
				else if(this.getUnlocalizedName().contains("statue_tlz"))
				{
					packet.soundName = "tlz_trophy";
				}
				else if(this.getUnlocalizedName().contains("statue_md"))
				{
					packet.soundName = "md_trophy";
				}
				else if(this.getUnlocalizedName().contains("statue_lw"))
				{
					packet.soundName = "lw_trophy";
				}
				else if(this.getUnlocalizedName().contains("statue_svv"))
				{
					packet.soundName = "svv_trophy";
				}
				
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			catch(Exception e)
			{
				
			}
		}
		return true;
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(player != null && !world.isRemote)
		{
			PlaySoundPacket packet = new PlaySoundPacket();
			packet.pos = pos;
			packet.soundName = "oof";
			PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
		}
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
}
