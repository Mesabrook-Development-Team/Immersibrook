package rz.mesabrook.wbtc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.ModUtils;
import rz.mesabrook.wbtc.util.SoundRandomizer;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockStatue extends Block implements IHasModel
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected final ArrayList<AxisAlignedBB> AABBs;

	public BlockStatue(String name, MapColor color, AxisAlignedBB unrotatedAABB)
	{
		super(Material.ROCK, color);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.METAL);
		setHardness(1.0F);
		setResistance(3.0F);
		setHarvestLevel("pickaxe", 1);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		AABBs = new ArrayList<AxisAlignedBB>(Arrays.asList(
				ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.DOWN, false),
				ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.UP, false),
				ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.NORTH, false),
				ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.SOUTH, false),
				ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.WEST, false),
				ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.EAST, false),
				unrotatedAABB, unrotatedAABB // Array fill to ensure that the array size covers 4 bit (meta & 0x07).
		));

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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
	}
	
	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
		SoundRandomizer.OWOTrophyRandomizer();
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
		else if(this.getUnlocalizedName().contains("trophy_two_years"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Celebrating Two Years of Mesabrook!");
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		SoundRandomizer.OWOTrophyRandomizer();
		if(!world.isRemote)
		{
			Random rand = new Random();
			float csxPitch = 0.5F + rand.nextFloat();

			if(csxPitch > 1.25F) {csxPitch = 1.25F;}
			else if(csxPitch < 0.75F) {csxPitch = 0.75F;}

			if(state.getBlock() == ModBlocks.STATUE_OWO)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = SoundRandomizer.owoResult;
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));

				if(player instanceof EntityPlayer)
				{
					Triggers.trigger(Triggers.OWO, player);
				}
			}
			if(state.getBlock() == ModBlocks.STATUE_RZ)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "rz_trophy";
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			if(state.getBlock() == ModBlocks.STATUE_CSX)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "csx_trophy";
				packet.pitch = csxPitch;
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			if(state.getBlock() == ModBlocks.STATUE_TD)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "td_trophy";
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			if(state.getBlock() == ModBlocks.STATUE_TLZ)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "tlz_trophy";
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			if(state.getBlock() == ModBlocks.STATUE_LW)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "lw_trophy";
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			if(state.getBlock() == ModBlocks.STATUE_MD)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "md_trophy";
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			if(state.getBlock() == ModBlocks.STATUE_SVV)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "svv_trophy";
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
			if(state.getBlock() == ModBlocks.STATUE_TWO)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "two_years";
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
		}
		return true;
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			if(player instanceof EntityPlayer)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = pos;
				packet.soundName = "oof";
				packet.volume = 1.0F;
				packet.pitch = 1.0F;
				PacketHandler.INSTANCE.sendToAllAround(packet, new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 25));
			}
		}
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
}
