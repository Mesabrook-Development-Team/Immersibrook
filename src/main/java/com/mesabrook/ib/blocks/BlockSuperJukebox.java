package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityCustomJukebox;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSuperJukebox extends BlockJukebox implements IHasModel
{
	public BlockSuperJukebox(String name)
	{
		super();
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.METAL);
        setHardness(2.0F);
        setResistance(10.0F);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
	
	@Override
    public boolean hasTileEntity(IBlockState state) 
	{
        return true;
    }
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
	    return new TileEntityCustomJukebox();
	}
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityCustomJukebox tileEntity = (TileEntityCustomJukebox) worldIn.getTileEntity(pos);
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (tileEntity != null) {
            ItemStack currentRecord = tileEntity.getRecord();

            if (currentRecord.isEmpty()) {
                // Insert record
                tileEntity.setRecord(itemstack.copy());
                worldIn.setBlockState(pos, state.withProperty(HAS_RECORD, Boolean.TRUE), 2);
                worldIn.playEvent(null, 1010, pos, Item.getIdFromItem(itemstack.getItem()));
                itemstack.shrink(1);
                return true;
            } else {
                // Eject record
                tileEntity.setRecord(ItemStack.EMPTY);
                worldIn.setBlockState(pos, state.withProperty(HAS_RECORD, Boolean.FALSE), 2);
                worldIn.playEvent(1010, pos, 0);
                spawnAsEntity(worldIn, pos, currentRecord);
                return true;
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
