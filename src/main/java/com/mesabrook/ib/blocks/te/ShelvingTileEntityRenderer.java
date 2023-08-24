package com.mesabrook.ib.blocks.te;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.commons.lang3.tuple.Pair;
import org.dynmap.jetty.util.ArrayUtil;

import com.mesabrook.ib.blocks.sco.BlockShelf;
import com.mesabrook.ib.blocks.sco.ProductPlacement;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity.ProductSpot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.client.model.pipeline.VertexLighterSmoothAo;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;
import scala.actors.threadpool.Arrays;

public class ShelvingTileEntityRenderer extends FastTESR<ShelvingTileEntity> {
	@Override
	public void renderTileEntityFast(ShelvingTileEntity te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial, BufferBuilder buffer) {
		if (!(te.getWorld().getBlockState(te.getPos()).getBlock() instanceof BlockShelf))
		{
			return;
		}
		
		BlockShelf shelf = (BlockShelf)te.getBlockType();
		EnumFacing blockFacing = te.getWorld().getBlockState(te.getPos()).getValue(BlockShelf.FACING);
		EnumFacing[] facings = EnumFacing.VALUES;
		facings = ArrayUtil.addToArray(facings, null, EnumFacing.class);
		int lightMap = te.getWorld().getCombinedLight(te.getPos().offset(blockFacing), 0);
		
		for(ProductSpot spot : te.getProductSpots())
		{
			Vector3f offset = new Vector3f((float)x, (float)y, (float)z);
			float positionOffsetX = 0;
			float positionOffsetZ = 0;
			ProductPlacement placement = shelf.getProductPlacementByID(spot.getPlacementID());
			if (placement == null)
			{
				continue;
			}
			
			AxisAlignedBB spotBB = placement.getBoundingBox();
			spotBB = shelf.ORIGINAL_BOX_TO_ROTATED_BY_FACING.get(blockFacing).get(spotBB);
			
			offset.y += spotBB.minY;
			float xOffsetFactor = 0;
			float zOffsetFactor = 0;
			if (blockFacing == EnumFacing.SOUTH)
			{
				offset.x += spotBB.maxX;
				offset.z += spotBB.maxZ;
				
				zOffsetFactor = 1;
			}
			else if (blockFacing == EnumFacing.EAST)
			{
				offset.x += spotBB.maxX;
				offset.z += spotBB.minZ;
				
				xOffsetFactor = 1;
			}
			else if (blockFacing == EnumFacing.NORTH)
			{
				offset.x += spotBB.minX;
				offset.z += spotBB.minZ;
				
				zOffsetFactor = -1;
			}
			else if (blockFacing == EnumFacing.WEST)
			{
				offset.x += spotBB.minX;
				offset.z += spotBB.maxZ;
				
				xOffsetFactor = -1;
			}
			
			float scale = (float)(spotBB.maxY - spotBB.minY);
			if (scale > spotBB.maxX - spotBB.minX)
			{
				scale = (float)(spotBB.maxX - spotBB.minX);
			}
			
			TRSRTransformation baseTransform = new TRSRTransformation(blockFacing);
			baseTransform = baseTransform.compose(new TRSRTransformation(
					null,
					null,
					new Vector3f(scale, scale, scale),
					null));
			
			ItemStack[] items = spot.getItems();
			for (int i = 0; i < items.length; i++)
			{
				float maximumX = xOffsetFactor == 0 ? 0F : Float.MIN_VALUE;
				float maximumZ = zOffsetFactor == 0 ? 0F : Float.MIN_VALUE;
				ItemStack item = items[i];
				if (item.isEmpty())
				{
					continue;
				}
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, getWorld(), null);
				Pair<? extends IBakedModel, Matrix4f> pair = model.handlePerspective(TransformType.NONE);
				TRSRTransformation itemTransform = baseTransform;
				if (pair.getRight() != null)
				{
					Matrix4f itemMatrix = itemTransform.getMatrix();
					itemMatrix.mul(pair.getRight());
					itemTransform = new TRSRTransformation(itemMatrix);
				}
				model = pair.getLeft();
				for(EnumFacing facing : facings)
				{
					for(BakedQuad quad : model.getQuads(null, facing, 0))
					{
						Vector3f vecLoc = new Vector3f();
						BakedQuad newQuad = transform(quad, itemTransform, offset, vecLoc, positionOffsetX, positionOffsetZ);
//						LightUtil.renderQuadColor(buffer, newQuad, 15 << 20);
						buffer.addVertexData(newQuad.getVertexData());
						
						if ((xOffsetFactor > 0 && vecLoc.x < maximumX) ||
							(xOffsetFactor < 0 && vecLoc.x > maximumX))
						{
							maximumX = vecLoc.x;
						}
						
						if ((zOffsetFactor > 0 && vecLoc.z < maximumZ) ||
							(zOffsetFactor < 0 && vecLoc.z > maximumZ))
						{
							maximumZ = vecLoc.z;
						}
					}
				}
				
				positionOffsetX += maximumX;
				positionOffsetZ += maximumZ;
			}
		}
	}
	
	protected static BakedQuad transform(BakedQuad quad, final TRSRTransformation transform, Vector3f offset, Vector3f ret, float additionalXOffset, float additionalZOffset) {
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(quad.getFormat());
		final IVertexConsumer consumer = new VertexTransformer(builder) {
			@Override
			public void put(int element, float... data) {
				VertexFormatElement formatElement = quad.getFormat().getElement(element);
				switch(formatElement.getUsage()) {
				case POSITION: {
					float[] newData = new float[4];
					Vector4f vec = new Vector4f(data);
					transform.getMatrix().transform(vec);
					vec.get(newData);
					ret.x = newData[0];
					ret.y = newData[1];
					ret.z = newData[2];
					newData[0] += offset.x + additionalXOffset;
					newData[1] += offset.y;
					newData[2] += offset.z + additionalZOffset;
					parent.put(element, newData);
					break;
				}
				case NORMAL:
					float[] newData = Arrays.copyOf(data, data.length);					
					newData[0] = 1F;
//					newData[1] = 1.87F;
//					newData[2] = 1.87F;
//					newData[3] = 1.87F;
					parent.put(element, newData);
					break;
				default: {
					parent.put(element, data);
					break;
				}
				}
			}
		};
		quad.pipe(consumer);
		return builder.build();
	}
}
