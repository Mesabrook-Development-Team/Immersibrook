package com.mesabrook.ib.blocks.te;

import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.blocks.BlockRegister;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class TileEntityRegisterRenderer extends TileEntitySpecialRenderer<TileEntityRegister> {

	private final double width = 9.6678;
	private final double height = 8.0565;
	
	private final double scaleDown = 1D / 16;
	private final double scaleUp = 1D / scaleDown;
	
	@Override
	public void render(TileEntityRegister te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if (getWorld().getBlockState(te.getPos()).getBlock() != ModBlocks.SCO_POS)
		{
			return;
		}
		
		setupMatrix(te, x, y, z);
		
		switch(te.getRegisterStatus())
		{
			case Uninitialized:
			case Initializing:
				drawUninitializedScreen();
				break;
			case WaitingForNetwork:
				drawWaitingForNetwork();
				break;
			case InSession:
				drawInUse();
				break;
			case Online:
				drawGeneralTexture("textures/gui/sco/sco_welcome.png");
				break;
			case Offline:
				drawGeneralTexture("textures/gui/sco/sco_offline.png");
				break;
			case InternalStorageFull:
				drawGeneralTexture("textures/gui/sco/sco_inv_full.png");
				break;
			case PaymentCardInUse:
			case PaymentCard:
				drawGeneralTexture("textures/gui/sco/sco_gui_card_selected.png");
				break;
			case PaymentCash:
				drawGeneralTexture("textures/gui/sco/sco_gui_cash.png");
				break;
			case PaymentSelect:
				drawGeneralTexture("textures/gui/sco/sco_spt.png");
				break;
			case TransactionProcessing:
				drawGeneralTexture("textures/gui/sco/sco_gui_payment_processing.png");
				break;
			case TransactionComplete:
				drawGeneralTexture("textures/gui/sco/sco_gui_payment_complete.png");
				break;
		}
		
		if (te.getInsertedCardStack() != null)
		{
			drawInsertedCardStack(te.getInsertedCardStack());
		}
		
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
	
	private void setupMatrix(TileEntityRegister te, double x, double y, double z)
	{
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.translate(x + 0.5, y + 0.5, z + 0.68);
		IBlockState registerState = getWorld().getBlockState(te.getPos());
		switch(registerState.getValue(BlockRegister.FACING))
		{
			case NORTH:
				GlStateManager.rotate(180, 0, 1, 0);
				break;
			case WEST:
				GlStateManager.rotate(270, 0, 1, 0);
				break;
			case SOUTH:
				break;
			case EAST:
				GlStateManager.rotate(90, 0, 1, 0);
				break;
		}
	
		GlStateManager.scale(1/16F, -1/16F, -1/16F);
		GlStateManager.rotate(-22.5F, 1, 0, 0);
		
		GlStateManager.translate(-4.89, -3, -2.09);		
	}
	
	private void drawBackground(BufferBuilder builder)
	{
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		builder.pos(0, height, 0).endVertex();
		builder.pos(width, height, 0).endVertex();
		builder.pos(width, 0, 0).endVertex();
		builder.pos(0, 0, 0).endVertex();
	}
	
	private void drawBackgroundTextured(BufferBuilder builder)
	{
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		builder.pos(width, height, 0).tex(1,1).endVertex();
		builder.pos(width, 0, 0).tex(1, 0).endVertex();
		builder.pos(0, 0, 0).tex(0, 0).endVertex();
		builder.pos(0, height, 0).tex(0, 1).endVertex();
	}
	
	private void drawUninitializedScreen()
	{
		Tessellator tess = Tessellator.getInstance();
		
		GlStateManager.disableTexture2D();
		GlStateManager.color(0, 0.36F, 1);
		
		drawBackground(tess.getBuffer());
		tess.draw();
		
		GlStateManager.enableTexture2D();
		GlStateManager.color(1, 1, 1);
		GlStateManager.translate(0, 0, -0.01);
		
		String uninitializedLabel = TextFormatting.BOLD + "* UNINITIALIZED *";
		int fontWidth = getFontRenderer().getStringWidth(uninitializedLabel);

		GlStateManager.scale(scaleDown, scaleDown, 1);
		getFontRenderer().drawString(uninitializedLabel, (int)(width * scaleUp / 2 - (fontWidth / 2)), 8, 0xFFFFFF);
		GlStateManager.scale(scaleUp, scaleUp, 1);
	}
	private void drawWaitingForNetwork()
	{
		advanceAnimationFrames();
		
		bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/sco/shock_blue.png"));
		Tessellator tess = Tessellator.getInstance();
		GlStateManager.color(1, 1, 1);
		drawBackgroundTextured(tess.getBuffer());
		tess.draw();
		
		GlStateManager.translate(0, 0, -0.01);
		
		bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/sco/net_frame_" + currentWaitForNetworkFrame + ".png"));
		BufferBuilder builder = tess.getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		builder.pos(2.8339, 2.5, 0).tex(0, 0).endVertex();
		builder.pos(2.8339, 6.5, 0).tex(0, 1).endVertex();
		builder.pos(6.8339, 6.5, 0).tex(1, 1).endVertex();
		builder.pos(6.8339, 2.5, 0).tex(1, 0).endVertex();
		tess.draw();
		
		String waitingForNetworkLabel = TextFormatting.BOLD + "* WAITING FOR NETWORK *";
		int fontWidth = getFontRenderer().getStringWidth(waitingForNetworkLabel);

		GlStateManager.scale(scaleDown, scaleDown, 1);
		getFontRenderer().drawString(waitingForNetworkLabel, (int)(width * scaleUp / 2 - (fontWidth / 2)), 8, 0xFFFFFF);
		GlStateManager.scale(scaleUp, scaleUp, 1);
	}

	
	private int currentWaitForNetworkFrame = 0;
	private int currentInUseFrame = 0;
	private long nextUpdateTime = Minecraft.getSystemTime() + 1000;
	private void advanceAnimationFrames() {
		if (Minecraft.getSystemTime() >= nextUpdateTime)
		{
			if (++currentWaitForNetworkFrame > 4)
			{
				currentWaitForNetworkFrame = 0;
			}
			
			if (++currentInUseFrame > 7)
			{
				currentInUseFrame = 0;
			}
			
			nextUpdateTime = Minecraft.getSystemTime() + 500;
		}
	}
	
	private void drawGeneralTexture(String texturePath)
	{
		bindTexture(new ResourceLocation(Reference.MODID, texturePath));
		Tessellator tess = Tessellator.getInstance();
		GlStateManager.color(1, 1, 1);
		drawBackgroundTextured(tess.getBuffer());
		tess.draw();
	}
	
	private void drawInsertedCardStack(ItemStack stack)
	{
		GlStateManager.rotate(90, 0, 0, 1);
		GlStateManager.scale(2.5, -2.5, -2.5);
		GlStateManager.translate(2.5, -1.1, -0.8);
		
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.NONE);

		GlStateManager.translate(-2.5, 1.1, 0.8);
		GlStateManager.scale(1F/2.5, -1F/2.5, -1F/2.5);
		GlStateManager.rotate(-90, 0, 0, 1);
	}
	
	private void drawInUse()
	{
		advanceAnimationFrames();
		
		bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/sco/sco_inuse_" + currentInUseFrame + ".png"));
		Tessellator tess = Tessellator.getInstance();
		GlStateManager.color(1, 1, 1);
		drawBackgroundTextured(tess.getBuffer());
		tess.draw();
	}
}
