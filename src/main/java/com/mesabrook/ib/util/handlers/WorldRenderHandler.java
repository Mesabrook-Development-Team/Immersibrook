package com.mesabrook.ib.util.handlers;

import java.util.Arrays;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.ImmutableCollection;
import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.blocks.sco.BlockShelf;
import com.mesabrook.ib.blocks.sco.ProductPlacement;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.ShelvingTileEntityRenderer;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.proxy.ClientProxy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class WorldRenderHandler {

	@SubscribeEvent
	public static void worldTextRender(RenderGameOverlayEvent.Text e)
	{
		handleStoreShelfPriceDisplay(e);
		
		IEmployeeCapability cap = Minecraft.getMinecraft().player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		if (cap.getLocationID() == 0)
		{
			return;
		}
		
		String text = "On Duty: " + cap.getLocationEmployee().Location.Company.Name + " (" + cap.getLocationEmployee().Location.Name + ")";
		
		Minecraft.getMinecraft().fontRenderer.drawString(text, 0, 0, 0xFFFFFF);
	}
	
	public static BlockPos thisTickViewedBlockPos = null;
	public static AxisAlignedBB thisTickViewedBoundingBox = null;
	private static void handleStoreShelfPriceDisplay(RenderGameOverlayEvent.Text e) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		RayTraceResult lookingRTRResult = player.rayTrace(Minecraft.getMinecraft().playerController.getBlockReachDistance(), e.getPartialTicks());
		boolean didFindShelf = false;
		try
		{
			if (lookingRTRResult.typeOfHit == Type.BLOCK)
			{
				IBlockState blockState = Minecraft.getMinecraft().world.getBlockState(lookingRTRResult.getBlockPos());
				if (blockState.getBlock() instanceof BlockShelf)
				{
					TileEntity te = Minecraft.getMinecraft().world.getTileEntity(lookingRTRResult.getBlockPos());
					if (!(te instanceof ShelvingTileEntity))
					{
						return;
					}
					
					float partialTicks = e.getPartialTicks();
					
					AxisAlignedBB foundBox = BlockShelf.findSubBoundingBox(lookingRTRResult.getBlockPos(), blockState, player, partialTicks);
					
					if (foundBox != null)
					{
						foundBox = ((BlockShelf)blockState.getBlock()).ROTATED_BOX_TO_ORIGINAL.get(foundBox);
						ProductPlacement placement = ((BlockShelf)blockState.getBlock()).getProductPlacementByBoundingBox(foundBox);
						
						if (placement != null && 
								(!ShelvingTileEntityRenderer.priceDisplayInformationsByBlockPos.containsKey(te.getPos().toLong()) ||
										!ShelvingTileEntityRenderer.priceDisplayInformationsByBlockPos.get(te.getPos().toLong()).stream().anyMatch(spdi -> spdi.placementID == placement.getPlacementID())) &&
								Arrays.stream(((ShelvingTileEntity)te).getProductSpots()).filter(ps -> ps.getPlacementID() == placement.getPlacementID() && !ps.getItems()[0].isEmpty()).findAny().isPresent())
						{
							thisTickViewedBlockPos = lookingRTRResult.getBlockPos();
							thisTickViewedBoundingBox = foundBox;
							if (ClientProxy.shelfPriceKey.getKeyCode() == Keyboard.KEY_NONE)
							{
								Minecraft.getMinecraft().fontRenderer.drawString("Assign keybind for price", e.getResolution().getScaledWidth() / 2 + 11, e.getResolution().getScaledHeight() / 2 + 11, 0xa81100);
							}
							else
							{
								Minecraft.getMinecraft().fontRenderer.drawString("Press " + ClientProxy.shelfPriceKey.getDisplayName() + " to show price", e.getResolution().getScaledWidth() / 2 + 11, e.getResolution().getScaledHeight() / 2 + 11, 0xFFFFFF);
							}
							didFindShelf = true;
						}
					}
				}
			}
		}
		finally
		{
			if (!didFindShelf)
			{
				thisTickViewedBlockPos = null;
				thisTickViewedBoundingBox = null;
			}
		}
	}
}
