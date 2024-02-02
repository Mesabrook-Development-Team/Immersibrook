package com.mesabrook.ib.blocks.te;

import javax.vecmath.Vector4f;

import org.apache.commons.lang3.ArrayUtils;
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
		facings = ArrayUtils.add(facings, null);
		
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
		for(int[] vertexData : te.getModelVertexData())
		{
			buffer.addVertexData(vertexData);
		}
		
		tess.draw();
		GlStateManager.popMatrix();
	}
}
