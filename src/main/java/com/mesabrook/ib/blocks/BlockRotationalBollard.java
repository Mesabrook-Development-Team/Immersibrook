package com.mesabrook.ib.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.ZapDeathSource;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRotationalBollard extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static AxisAlignedBB EXTENDED_COL_BB;
    protected final ArrayList<AxisAlignedBB> AABBs;
    private String styleForTooltip = null;
    
    public BlockRotationalBollard(String name, AxisAlignedBB unrotatedAABB, AxisAlignedBB colliderAABB, float lightLevel, String tooltip, float hardness)
    {
    	super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.METAL);
        setHardness(hardness);
        setResistance(3.0F);
        setLightLevel(lightLevel);
        this.EXTENDED_COL_BB = colliderAABB;
        this.styleForTooltip = tooltip;
        setTickRandomly(true);
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
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
    	if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_UP)
    	{
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            double d0 = (double)pos.getX() + 0.55D - (double)(rand.nextFloat() * 0.1F);
            double d1 = (double)pos.getY() + 1.15D - (double)(rand.nextFloat() * 0.1F);
            double d2 = (double)pos.getZ() + 0.55D - (double)(rand.nextFloat() * 0.1F);
            double d3 = (double)(0.4F - (rand.nextFloat() + rand.nextFloat()) * 0.4F);
            
            if (rand.nextInt(5) == 0)
            {
                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, d0 + (double)enumfacing.getFrontOffsetX() * d3, d1 + (double)enumfacing.getFrontOffsetY() * d3, d2 + (double)enumfacing.getFrontOffsetZ() * d3, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
            }
    	}
    }
    
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	
    	if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_UP)
    	{
    		try
    		{
        		EntityLivingBase victim = (EntityLivingBase) entityIn;
        		
        		
        		if (!(victim instanceof EntityPlayer) || !((EntityPlayer) victim).isCreative())
        		{
        			ZapDeathSource zap = new ZapDeathSource("zap");
            		if(worldIn.rand.nextInt(15) >= 10 && worldIn.isBlockLoaded(pos))
            		{
            			for (int i = 0; i < 9; i++) 
            			{
                            double offsetX = (worldIn.rand.nextDouble() - 0.5) * 2.0;
                            double offsetY = (worldIn.rand.nextDouble() - 0.5) * 2.0;
                            double offsetZ = (worldIn.rand.nextDouble() - 0.5) * 2.0;
                            worldIn.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
                                victim.posX + offsetX,
                                victim.posY + offsetY + victim.getEyeHeight(),
                                victim.posZ + offsetZ,
                                0.0D, 0.0D, 0.0D);
                        }

                		ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                		packet.pos = pos;
                		packet.soundName = "zap";
                		packet.rapidSounds = false;
                		packet.pitch = worldIn.rand.nextFloat();
                		PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(victim.dimension, victim.posX, victim.posY, victim.posZ, 25));
            		}
        			victim.attackEntityFrom(zap, 8F);
        		}
        		
    		}
    		catch(Exception ex)
    		{
    			
    		}
    	}
    }
    
    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) 
    {
    	if(!worldIn.isRemote)
    	{
    		IBlockState state = worldIn.getBlockState(pos);
    		if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_UP || state.getBlock() == ModBlocks.SHOCK_BOLLARD_DOWN)
    		{
    			if(!playerIn.isCreative())
    			{
    				ZapDeathSource zap = new ZapDeathSource("zap");
        			for (int i = 0; i < 9; i++) 
        			{
                        double offsetX = (worldIn.rand.nextDouble() - 0.5) * 2.0;
                        double offsetY = (worldIn.rand.nextDouble() - 0.5) * 2.0;
                        double offsetZ = (worldIn.rand.nextDouble() - 0.5) * 2.0;
                        worldIn.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
                            playerIn.posX + offsetX,
                            playerIn.posY + offsetY + playerIn.getEyeHeight(),
                            playerIn.posZ + offsetZ,
                            0.0D, 0.0D, 0.0D);
                    }
            		playerIn.attackEntityFrom(zap, 15F);
    			}
    			
        		ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
        		packet.pos = pos;
        		packet.soundName = "zap";
        		packet.rapidSounds = false;
        		packet.pitch = worldIn.rand.nextFloat();
        		PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
    		}
    	}
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		String name = this.styleForTooltip;
		tooltip.add(TextFormatting.GRAY + "Style: " + name);
		tooltip.add(TextFormatting.BLUE + "Creates a 1-1/2 blocks tall hitbox when extended, preventing larger entities (like vehicles) from passing over them.");
		tooltip.add("Requires " + TextFormatting.RED + "Redstone" + TextFormatting.RESET + " to activate.");
		
		if(stack.getItem().getUnlocalizedName().contains("shock"))
		{
			tooltip.add(TextFormatting.RED + "SHOCK HAZARD - Bollard will shock all mobs and players who come into contact with it when extended.");
		}
	}
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
    	if(state.getBlock() == ModBlocks.WALL_UP) return new ItemStack(ModBlocks.WALL_DOWN);
    	if(state.getBlock() == ModBlocks.WALL_DOWN) return new ItemStack(ModBlocks.WALL_DOWN);
    	
    	if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_UP) return new ItemStack(ModBlocks.SHOCK_BOLLARD_DOWN);
    	if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_DOWN) return new ItemStack(ModBlocks.SHOCK_BOLLARD_DOWN);
    	
    	return ItemStack.EMPTY;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }
    
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
    	if (worldIn instanceof World && ((World) worldIn).isBlockPowered(pos))
    	{
    		return ModUtils.getRotatedAABB(EXTENDED_COL_BB, state.getBlock().getBedDirection(state, worldIn, pos), false);
    	}
    	return state.getBoundingBox(worldIn, pos);
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
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
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
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	if(worldIn.isBlockPowered(pos))
    	{
    		extend(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    		if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_UP)
    		{
    			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX(), pos.getY(), pos.getZ(), 1, 1, 1);
    		}
    	}
    	else
    	{
    		retract(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    	}
    }
    
    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
    	if(world instanceof World)
    	{
    		World worldIn = (World) world;
    		IBlockState state = worldIn.getBlockState(pos);
    		if(worldIn.isBlockPowered(pos))
    		{
    			extend(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    		}
    		else
    		{
    			retract(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    		}
    	}
    }
    
    private void extend(World world, BlockPos pos, EnumFacing direction, IBlockState state)
    {
    	if(state.getBlock() == ModBlocks.WALL_DOWN) world.setBlockState(pos, ModBlocks.WALL_UP.getDefaultState().withProperty(FACING, direction));
    	if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_DOWN) world.setBlockState(pos, ModBlocks.SHOCK_BOLLARD_UP.getDefaultState().withProperty(FACING, direction));
    }
    
    private void retract(World world, BlockPos pos, EnumFacing direction, IBlockState state)
    {
    	if(state.getBlock() == ModBlocks.WALL_UP) world.setBlockState(pos, ModBlocks.WALL_DOWN.getDefaultState().withProperty(FACING, direction));
    	if(state.getBlock() == ModBlocks.SHOCK_BOLLARD_UP) world.setBlockState(pos, ModBlocks.SHOCK_BOLLARD_DOWN.getDefaultState().withProperty(FACING, direction));
    }
}
