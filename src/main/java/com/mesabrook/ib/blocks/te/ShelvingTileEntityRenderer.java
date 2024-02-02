package com.mesabrook.ib.blocks.te;

import java.util.HashSet;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ArrayListMultimap;
import com.mesabrook.ib.apimodels.company.LocationItem;
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
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;

// BREAK INCASE OF PERFORMANCE PROBLEMS! https://web.archive.org/web/20220704085618/https://forums.minecraftforge.net/topic/78157-solved1122-most-efficient-way-to-render/
public class ShelvingTileEntityRenderer extends TileEntitySpecialRenderer<ShelvingTileEntity> {
	public static ArrayListMultimap<Long, ShelfPriceDisplayInformation> priceDisplayInformationsByBlockPos = ArrayListMultimap.create(); 
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
		facings = ArrayUtils.add(facings, null);
		
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
		
		if (priceDisplayInformationsByBlockPos.containsKey(te.getPos().toLong()))
		{			
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5, 0.5, 0.5);
			HashSet<Long> keysToRemove = new HashSet<>();
			HashSet<ShelfPriceDisplayInformation> valuesToRemove = new HashSet<>();
			
			for(ShelfPriceDisplayInformation priceInfo : priceDisplayInformationsByBlockPos.get(te.getPos().toLong()))
			{
				String display = "Fetching...";
				if (priceInfo.isRetrieved)
				{
					if (priceInfo.locationItem == null)
					{
						display = TextFormatting.DARK_RED + "Unknown";
					}
					else
					{
						if (priceInfo.locationItem.CurrentPromotionLocationItem != null && priceInfo.locationItem.CurrentPromotionLocationItem.PromotionPrice != null)
						{
							display = TextFormatting.STRIKETHROUGH + priceInfo.locationItem.BasePrice.toPlainString() + TextFormatting.RESET + TextFormatting.DARK_GREEN + " " + priceInfo.locationItem.CurrentPromotionLocationItem.PromotionPrice.toPlainString();
						}
						else
						{
							display = priceInfo.locationItem.BasePrice.toPlainString();
						}
					}
					
					if (System.currentTimeMillis() - priceInfo.timeInitiallyDisplayed >= 5000)
					{
						if (priceDisplayInformationsByBlockPos.get(te.getPos().toLong()).size() <= 1)
						{
							keysToRemove.add(te.getPos().toLong());
						}
						else
						{
							valuesToRemove.add(priceInfo);
						}
					}
				}
				else
				{
					if (System.currentTimeMillis() - priceInfo.timeInitiallyDisplayed >= 10000)
					{
						if (priceDisplayInformationsByBlockPos.get(te.getPos().toLong()).size() <= 1)
						{
							keysToRemove.add(te.getPos().toLong());
						}
						else
						{
							valuesToRemove.add(priceInfo);
						}
					}
				}
				drawNameplate(te, display, 0.5 + (x - 0.5 + priceInfo.displayX) * 2, 1.5 + (y - 1.5 + priceInfo.displayY) * 2, 0.5 + (z - 0.5 + priceInfo.displayZ) * 2, (int)Minecraft.getMinecraft().playerController.getBlockReachDistance());
			}
			
			for(ShelfPriceDisplayInformation valueToRemove : valuesToRemove)
			{
				priceDisplayInformationsByBlockPos.values().remove(valueToRemove);
			}
			
			for(long keyToRemove : keysToRemove)
			{
				priceDisplayInformationsByBlockPos.keySet().remove(keyToRemove);
			}
			
			GlStateManager.scale(2, 2, 2);
			GlStateManager.popMatrix();
		}
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
	
	public static class ShelfPriceDisplayInformation
	{
		public boolean isRetrieved = false;
		public int placementID;
		public long timeInitiallyDisplayed = 0;
		public double displayX;
		public double displayY;
		public double displayZ;
		public LocationItem locationItem;
	}
}
