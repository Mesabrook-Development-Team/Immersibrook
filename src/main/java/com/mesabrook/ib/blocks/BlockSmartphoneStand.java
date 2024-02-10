package com.mesabrook.ib.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityPhoneStand;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSmartphoneStand extends Block implements IHasModel
{
    protected final ArrayList<AxisAlignedBB> AABBs;
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public BlockSmartphoneStand(String name, AxisAlignedBB unrotatedAABB)
    {
        super(Material.GLASS);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setSoundType(SoundType.GLASS);
        setResistance(100F);
        setHardness(1.0F);
        setHarvestLevel("pickaxe", 0);
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        EnumFacing standFacing = worldIn.getBlockState(pos).getValue(FACING).getOpposite();
        EnumFacing playerFacing = playerIn.getHorizontalFacing();

        if(!(tileEntity instanceof TileEntityPhoneStand))
        {
            return false;
        }

        if(standFacing != playerFacing)
        {
            return false;
        }

        TileEntityPhoneStand tileEntityPhoneStand = (TileEntityPhoneStand) tileEntity;
        IEmployeeCapability employee = playerIn.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
        boolean hasManageInventory = false;
        if (employee.getLocationEmployee() != null)
        {
        	hasManageInventory = employee.getLocationEmployee().ManageInventory;
        }
        
        if (tileEntityPhoneStand.getLocationIDOwner() == 0 && employee.getLocationID() != 0 && hasManageInventory)
        {
        	tileEntityPhoneStand.setLocationIDOwner(employee.getLocationID());
            if(!worldIn.isRemote)
            {
                playerIn.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "You have claimed this block successfully on behalf of your company."), true);
            }
            playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.5F);
            tileEntityPhoneStand.markDirty();
            tileEntityPhoneStand.sync();
            return true;
        }

        if(!heldItem.isEmpty() && tileEntityPhoneStand.getPhoneItem().isEmpty() && heldItem.getItem() instanceof ItemPhone && tileEntityPhoneStand.getLocationIDOwner() == employee.getLocationID() && hasManageInventory)
        {
            ItemStack copy = heldItem.copy();
            copy.setCount(1);
            tileEntityPhoneStand.setPhone(copy);
            tileEntityPhoneStand.setRotation(playerIn.getHorizontalFacing().getHorizontalIndex());
            tileEntityPhoneStand.sync();
            heldItem.shrink(1);
            worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_GLASS_HIT, SoundCategory.BLOCKS, 1.0F, 1.6F);
            return true;
        }

        if(!tileEntityPhoneStand.getPhoneItem().isEmpty())
        {
            if(!worldIn.isRemote)
            {
                if(tileEntityPhoneStand.getLocationIDOwner() == employee.getLocationID() && hasManageInventory)
                {
                    if(!playerIn.getHeldItem(hand).isEmpty())
                    {
                        playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "Your hand needs to be empty before you can remove the item from the stand."));
                    }
                    else
                    {
                        heldItem = tileEntityPhoneStand.getPhoneItem();
                        playerIn.addItemStackToInventory(heldItem);
                        tileEntityPhoneStand.setPhone(ItemStack.EMPTY);
                    }
                }
                else
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You cannot interact with this block, only the owner can interact with or break it."));
                }
            }

            tileEntityPhoneStand.markDirty();
            tileEntityPhoneStand.sync();
        }

        // Unclaim block
        if(tileEntityPhoneStand.getLocationIDOwner() == employee.getLocationID() && hasManageInventory && playerIn.isSneaking())
        {
            tileEntityPhoneStand.setLocationIDOwner(0);
            playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.5F);

            tileEntityPhoneStand.markDirty();
            tileEntityPhoneStand.sync();

            if(!worldIn.isRemote)
            {
                playerIn.sendStatusMessage(new TextComponentString(TextFormatting.RED + "You have unclaimed this block successfully."), true);
                heldItem = tileEntityPhoneStand.getPhoneItem();
                playerIn.addItemStackToInventory(heldItem);
            }
        }


        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityPhoneStand();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityPhoneStand te = (TileEntityPhoneStand) worldIn.getTileEntity(pos);
        if(!(te instanceof TileEntityPhoneStand))
        {
            return;
        }

        ModUtils.dropTileEntityInventoryItems(worldIn, pos, te);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        TileEntityPhoneStand tileEntityPhoneStand = (TileEntityPhoneStand) te;
        if(!tileEntityPhoneStand.getPhoneItem().isEmpty())
        {
            ModUtils.dropTileEntityInventoryItems(worldIn, pos, te);
        }
        state.getBlock().harvestBlock(worldIn, player, pos, state, te, stack);
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
    public boolean isFullCube(IBlockState state)
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
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {

		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityPhoneStand)
		{
			TileEntityPhoneStand tileEntityPhoneStand = (TileEntityPhoneStand) te;
			IEmployeeCapability employee = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
	
			if(tileEntityPhoneStand.getLocationIDOwner() != 0)
			{
				if(tileEntityPhoneStand.getLocationIDOwner() == employee.getLocationID() && employee.getLocationEmployee().ManageInventory)
				{
					if (!world.isRemote)
					{
						player.sendMessage(new TextComponentString(TextFormatting.RED + "Unclaim the block first before breaking it."));
					}
				}
				else
				{
					if (!world.isRemote)
					{
						player.sendMessage(new TextComponentString(TextFormatting.RED + "Only the owner of this block can break it."));
					}
				}
				return false;
			}
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        this.setDefaultFacing(world, pos, state);
        if(placer instanceof EntityPlayer)
        {
            if(world.isRemote)
            {
                placer.sendMessage(new TextComponentString("Right click the stand to claim it."));
            }
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

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)blockState.getValue(FACING)).getIndex() & 0x7);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }
}
