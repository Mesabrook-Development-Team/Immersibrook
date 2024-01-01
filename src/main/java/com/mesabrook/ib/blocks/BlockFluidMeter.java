package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityFluidMeter;
import com.mesabrook.ib.blocks.te.TileEntityFluidMeter.FlowDirection;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockFluidMeter extends ImmersiblockRotationalManyBB implements IHasModel
{
    public static final PropertyBool ISUP = PropertyBool.create("isup");
    private static final AxisAlignedBB switchBB = ModUtils.getPixelatedAABB(5.57, 6.173, 1.9185, 6.38, 8.398, 3.1235);
    private static final AxisAlignedBB bodyBB = ModUtils.getPixelatedAABB(3.14, 0, 3.14, 12.86, 16, 12.86);

    public BlockFluidMeter(String name)
    {
    	super(name, Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 2F, true, switchBB, bodyBB);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, ISUP);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	boolean isUp = true;
    	TileEntity te = worldIn.getTileEntity(pos);
    	if (!(te instanceof TileEntityFluidMeter))
    	{
    		return state;
    	}
    	
    	isUp = ((TileEntityFluidMeter)te).getFlowDirection() == FlowDirection.Up;
    	return state.withProperty(ISUP, isUp);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(new TextComponentString(TextFormatting.GREEN + "A device that meters the flow of any fluid passing through it and then reports it to a Point Of Sale system. \n").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.RED + "[ALERT] Fluid Meter is not paired with a register! You must pair it to a register before it'll work.").getFormattedText());
        }
        else
        {
            tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Press [SHIFT] for more info.").getFormattedText());
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	return new TileEntityFluidMeter();
    }
    
    @Override
    public boolean onSubBoundingBoxActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, AxisAlignedBB subBoundingBox) {
    	if (worldIn.isRemote || subBoundingBox != switchBB)
    	{
    		return true;
    	}
    	
    	TileEntity te = worldIn.getTileEntity(pos);
    	if (te instanceof TileEntityFluidMeter)
    	{
    		TileEntityFluidMeter fluidMeter = (TileEntityFluidMeter)te;
    		fluidMeter.setFlowDirection(fluidMeter.getFlowDirection() == FlowDirection.Up ? FlowDirection.Down : FlowDirection.Up);
    	}
    	
    	return true;
    }
}
