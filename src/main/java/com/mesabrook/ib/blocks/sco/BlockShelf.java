package com.mesabrook.ib.blocks.sco;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.mesabrook.ib.blocks.ImmersiblockRotationalManyBB;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockShelf extends ImmersiblockRotationalManyBB {

	private final HashMap<Integer, ProductPlacement> productPlacementByID = new HashMap<>();
	private final HashMap<AxisAlignedBB, ProductPlacement> productPlacementByBB = new HashMap<>();
	public BlockShelf(String name, ProductPlacement[] productPlacements, AxisAlignedBB[] nonShelvingBoundingBoxes) {
		super(name, Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, true, ArrayUtils.addAll(Arrays.stream(productPlacements).map(pp -> pp.getBoundingBox()).collect(Collectors.toList()).toArray(new AxisAlignedBB[0]), nonShelvingBoundingBoxes));
		for(ProductPlacement placement : productPlacements)
		{
			productPlacementByID.put(placement.getPlacementID(), placement);
			productPlacementByBB.put(placement.getBoundingBox(), placement);
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new ShelvingTileEntity();
	}
	
	@Override
	public boolean onSubBoundingBoxActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, AxisAlignedBB subBoundingBox) {
		if (worldIn.isRemote)
		{
			return true;
		}
		
		TileEntity te = worldIn.getTileEntity(pos);
		if (!(te instanceof ShelvingTileEntity) || !productPlacementByBB.containsKey(subBoundingBox))
		{
			return false;
		}
		
		ProductPlacement placement = productPlacementByBB.get(subBoundingBox);		
		ShelvingTileEntity shelf = (ShelvingTileEntity)te;
		return shelf.onActivated(playerIn, hand, placement);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (worldIn.isRemote)
		{
			return;
		}
		
		boolean isPlayer = false;
		if (placer instanceof EntityPlayer)
		{
			IEmployeeCapability employeeCap = placer.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			if (employeeCap.getLocationID() != 0 && employeeCap.getLocationEmployee().ManageInventory)
			{
				ShelvingTileEntity te = (ShelvingTileEntity)worldIn.getTileEntity(pos);
				te.setLocationIDOwner(employeeCap.getLocationID());
				
				return;
			}
			
			isPlayer = true;
		}
		
		if (isPlayer)
		{
			placer.sendMessage(new TextComponentString("You must be on duty and have permission to manage inventory."));
		}
		
		worldIn.setBlockToAir(pos);
	}
	
	public ProductPlacement getProductPlacementByID(int id)
	{
		return productPlacementByID.get(id);
	}
	
	public ProductPlacement getProductPlacementByBoundingBox(AxisAlignedBB boundingBox)
	{
		return productPlacementByBB.get(boundingBox);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}
}
