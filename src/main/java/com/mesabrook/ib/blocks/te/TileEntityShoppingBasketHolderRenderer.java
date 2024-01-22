package com.mesabrook.ib.blocks.te;

import javax.vecmath.Vector4f;

import org.dynmap.jetty.util.ArrayUtil;
import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.blocks.commerce.BlockShoppingBasketHolder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;

public class TileEntityShoppingBasketHolderRenderer extends TileEntitySpecialRenderer<TileEntityShoppingBasketHolder> {

	@Override
	public void render(TileEntityShoppingBasketHolder te, double x, double y, double z, float partialTicks,
			int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		if (!(te.getWorld().getBlockState(te.getPos()).getBlock() instanceof BlockShoppingBasketHolder))
		{
			return;
		}
		
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
		
		EnumFacing blockFacing = te.getWorld().getBlockState(te.getPos()).getValue(BlockShoppingBasketHolder.FACING);
		
		float angle = 0;
		switch(blockFacing)
		{
			case SOUTH:
			case NORTH:
				angle = 90;
				break;
		}
		
		GlStateManager.rotate(angle, 0, 1, 0);
		GlStateManager.translate(-0.5, -0.4, -0.5);
		
		EnumFacing[] facings = EnumFacing.VALUES;
		facings = ArrayUtil.addToArray(facings, null, EnumFacing.class);
		
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
		
		int basketCounter = 0;
		for(ItemStack basket : te.getBaskets())
		{
			if (basket == null || basket.isEmpty())
			{
				continue;
			}
			
			String variant = "color=";
			EnumDyeColor dyeColor = EnumDyeColor.byMetadata(basket.getMetadata());
			variant += dyeColor.getUnlocalizedName() + ",down=true";
			
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(new ModelResourceLocation(basket.getItem().getRegistryName(), variant));
			model = model.getOverrides().handleItemState(model, basket, getWorld(), null);
			
			for(EnumFacing facing : facings)
			{
				for(BakedQuad quad : model.getQuads(null, facing, 0))
				{
					BakedQuad transformedQuad = transform(quad, basketCounter * 0.125F);
					buffer.addVertexData(transformedQuad.getVertexData());
				}
			}
			
			basketCounter++;
		}
		
		tess.draw();
		GlStateManager.popMatrix();
	}
	
	protected static BakedQuad transform(BakedQuad quad, float additionalYOffset) {
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(quad.getFormat());
		final IVertexConsumer consumer = new VertexTransformer(builder) {
			@Override
			public void put(int element, float... data) {
				VertexFormatElement formatElement = quad.getFormat().getElement(element);
				switch(formatElement.getUsage()) {
				case POSITION: {
					float[] newData = new float[4];
					Vector4f vec = new Vector4f(data);
					vec.get(newData);
					float yScale = 1F - (newData[1] / 0.4F);
					if (newData[0] > 0.5F)
					{
						newData[0] -= 0.0625F * yScale;
					}
					else
					{
						newData[0] += 0.0625F * yScale;
					}
					newData[1] += additionalYOffset;
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
