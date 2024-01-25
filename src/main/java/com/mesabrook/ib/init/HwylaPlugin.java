package com.mesabrook.ib.init;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.mesabrook.ib.blocks.sco.BlockShelf;
import com.mesabrook.ib.blocks.sco.BlockShelfCloseableUpper;
import com.mesabrook.ib.blocks.sco.ProductPlacement;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity.ProductSpot;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

@WailaPlugin
public class HwylaPlugin implements IWailaPlugin {

	@Override
	public void register(IWailaRegistrar registrar) {
		ShelvingData shelvingData = new ShelvingData();
		registrar.registerBodyProvider(shelvingData, BlockShelf.class);
		registrar.registerStackProvider(shelvingData, BlockShelf.class);
	}
	
	public static class ShelvingData implements IWailaDataProvider
	{
		@Override
		public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
				IWailaConfigHandler config) {
			ItemStack stack = getSpotItemStack(accessor);
			if (stack == null)
			{
				return tooltip;
			}
			
			if (!stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				return tooltip;
			}
			
			ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
			ItemStack innerStack = securedItem.getInnerStack();
			if (innerStack.isEmpty())
			{
				return tooltip;
			}
			
			tooltip.add(String.format("%s (x%s)", innerStack.getDisplayName(), innerStack.getCount()));
			
			return tooltip;
		}
		
		@Override
		public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
			ItemStack stack = getSpotItemStack(accessor);
			if (stack == null)
			{
				if (accessor.getBlock() instanceof BlockShelfCloseableUpper)
				{
					IBlockState lowerBlock = accessor.getWorld().getBlockState(accessor.getPosition().down());
					return new ItemStack(lowerBlock.getBlock());
				}
				return IWailaDataProvider.super.getWailaStack(accessor, config);
			}
			
			return stack;
		}
		
		private ItemStack getSpotItemStack(IWailaDataAccessor accessor)
		{
			if (!(accessor.getTileEntity() instanceof ShelvingTileEntity))
			{
				return null;
			}
			
			AxisAlignedBB boundingBoxLookingAt = BlockShelf.findSubBoundingBox(accessor.getPosition(), accessor.getBlockState(), accessor.getPlayer(), (float)accessor.getPartialFrame());
			if (boundingBoxLookingAt == null)
			{
				return null;
			}
			
			BlockShelf block = (BlockShelf)accessor.getBlockState().getBlock();
			AxisAlignedBB nonRotatedBox = block.getOriginalBoxFromRotated(accessor.getBlockState(), boundingBoxLookingAt);
			
			ProductPlacement placement = ((BlockShelf)accessor.getBlockState().getBlock()).getProductPlacementByBoundingBox(nonRotatedBox);
			if (placement == null)
			{
				return null;
			}
			
			ShelvingTileEntity shelf = (ShelvingTileEntity)accessor.getTileEntity();
			Optional<ProductSpot> productSpot = Arrays.stream(shelf.getProductSpots()).filter(ps -> ps.getPlacementID() == placement.getPlacementID()).findFirst();
			if (!productSpot.isPresent())
			{
				return null;
			}
			
			ItemStack stack = productSpot.get().getItems()[0];
			if (stack.isEmpty())
			{
				return null;
			}
			
			return stack;
		}
	}
}
