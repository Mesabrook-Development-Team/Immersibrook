package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.blocks.BlockDiscGolfBasket;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDiscGolfBasket extends TileEntity {
	ItemStackHandler discHandler;
	
	public TileEntityDiscGolfBasket()
	{
		discHandler = new ItemStackHandler(9)
		{
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return false;
			}
			
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				
				markDirty();
				IBlockState state = world.getBlockState(pos);
				world.notifyBlockUpdate(pos, state, state, 3);
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
			
			@Override
			public void deserializeNBT(NBTTagCompound nbt) {
				for(int i = 0; i < getSlots(); i++)
				{
					this.stacks.set(i, ItemStack.EMPTY);
				}
				
				super.deserializeNBT(nbt);
			}
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("disc_inv"))
		{
			discHandler.deserializeNBT(compound.getCompoundTag("disc_inv"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("disc_inv", discHandler.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return !(oldState.getBlock() instanceof BlockDiscGolfBasket) || !(newSate.getBlock() instanceof BlockDiscGolfBasket);
	}
	
	public ItemStackHandler getDiscInventory()
	{
		return discHandler;
	}
}
