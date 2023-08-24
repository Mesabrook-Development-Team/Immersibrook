package com.mesabrook.ib.blocks.te;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.commons.lang3.tuple.Pair;
import org.dynmap.jetty.util.ArrayUtil;
import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.blocks.sco.BlockShelf;
import com.mesabrook.ib.blocks.sco.ProductPlacement;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity.ProductSpot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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

// BREAK INCASE OF PERFORMANCE PROBLEMS! https://web.archive.org/web/20220704085618/https://forums.minecraftforge.net/topic/78157-solved1122-most-efficient-way-to-render/
public class ShelvingTileEntityRenderer extends TileEntitySpecialRenderer<ShelvingTileEntity> {	
	@Override
	public void render(ShelvingTileEntity te, double x, double y, double z, float partialTicks,
			int destroyStage, float partial) {
		if (!(te.getWorld().getBlockState(te.getPos()).getBlock() instanceof BlockShelf))
		{
			return;
		}
		
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
		
		BlockShelf shelf = (BlockShelf)te.getBlockType();
		EnumFacing blockFacing = te.getWorld().getBlockState(te.getPos()).getValue(BlockShelf.FACING);
		
		float angle = 0;
		switch(blockFacing)
		{
			case SOUTH:
				angle = 180;
				break;
			case NORTH: // Do nothing
				break;
			case WEST:
				angle = 90;
				break;
			case EAST:
				angle = 270;
				break;
		}
		
		GlStateManager.rotate(angle, 0, 1, 0);
		GlStateManager.translate(-0.5, -0.5, -0.5);
		
		EnumFacing[] facings = EnumFacing.VALUES;
		facings = ArrayUtil.addToArray(facings, null, EnumFacing.class);
		
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
		
		for(ProductSpot spot : te.getProductSpots())
		{
			float positionOffsetZ = 0;
			ProductPlacement placement = shelf.getProductPlacementByID(spot.getPlacementID());
			if (placement == null)
			{
				continue;
			}
			
			AxisAlignedBB spotBB = placement.getBoundingBox();
			
			Vector3f offset = new Vector3f((float)spotBB.minX, (float)spotBB.minY, (float)spotBB.minZ);
			
			float scale = (float)(spotBB.maxY - spotBB.minY);
			if (scale > spotBB.maxX - spotBB.minX)
			{
				scale = (float)(spotBB.maxX - spotBB.minX);
			}
			
			TRSRTransformation baseTransform = new TRSRTransformation(
					null,
					null,
					new Vector3f(scale, scale, scale),
					null);
			
			ItemStack[] items = spot.getItems();
			for (int i = 0; i < items.length; i++)
			{
				float maximumZ = Float.MIN_VALUE;
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
						BakedQuad newQuad = transform(quad, itemTransform, offset, vecLoc, positionOffsetZ);
						buffer.addVertexData(newQuad.getVertexData());
						
						if (vecLoc.z > maximumZ)
						{
							maximumZ = vecLoc.z;
						}
					}
				}
				
				positionOffsetZ += maximumZ;
			}
		}
		
		tess.draw();
		GlStateManager.popMatrix();
	}
	
	protected static BakedQuad transform(BakedQuad quad, final TRSRTransformation transform, Vector3f offset, Vector3f ret, float additionalZOffset) {
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
					newData[0] += offset.x;
					newData[1] += offset.y;
					newData[2] += offset.z + additionalZOffset;
					parent.put(element, newData);
					break;
				}
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
