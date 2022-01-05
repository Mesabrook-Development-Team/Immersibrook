package rz.mesabrook.wbtc.blocks.gui.telecom;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import rz.mesabrook.wbtc.util.Reference;

public class Toaster {
	private static Toaster INSTANCE;
	
	public static Toaster getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new Toaster();
		}
		
		return INSTANCE;
	}
	
	private Toaster() {}
	
	private Queue<Toast> toastQueue = new LinkedList<>();
	private Toast currentToast = null;
	private Stages currentStage = Stages.FadeIn;
	private int currentStageTimer;
	
	public void queueToast(Toast toast)
	{
		toastQueue.add(toast);
	}
	
	public void tick(int innerX, int innerY, int innerWidth, int innerHeight)
	{
		if (currentToast == null)
		{
			currentToast = toastQueue.poll();
			
			if (currentToast == null)
			{
				return;
			}
		}
		
		switch(currentStage)
		{
			case FadeIn:
				currentStageTimer += currentToast.getFadeInFactor();
				if (currentStageTimer > 100)
				{
					currentStageTimer = 100;
				}
				
				draw(innerX, innerY, innerWidth, innerHeight, (float)currentStageTimer / 100F);
				if (currentStageTimer == 100)
				{
					currentStage = Stages.Display;
					currentStageTimer = 0;
				}
				break;
			case Display:
				currentStageTimer++;
				draw(innerX, innerY, innerWidth, innerHeight, 1F);
				if (currentStageTimer >= currentToast.getDisplayTicks())
				{
					currentStageTimer = 100;
					currentStage = Stages.FadeOut;
				}
				break;
			case FadeOut:
				currentStageTimer -= currentToast.getFadeOutFactor();
				if (currentStageTimer <= 0)
				{
					currentStage = Stages.FadeIn;
					currentStageTimer = 0;
					currentToast = null;
					return;
				}
				
				draw(innerX, innerY, innerWidth, innerHeight, (float)currentStageTimer / 100F);
				break;
		}
	}
	
	private void draw(int innerX, int innerY, int innerWidth, int innerHeight, float alpha)
	{
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		
		GlStateManager.color(1, 1, 1, alpha);
		
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		int stringWidth = font.getStringWidth(currentToast.getText());
		int totalWidth = stringWidth + 40;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/toast.png"));
		GuiScreen.drawScaledCustomSizeModalRect(
				innerX + innerWidth / 2 - totalWidth / 2, // x
				innerY + innerHeight - 45, // y
				0, // u
				0, // v
				totalWidth / 2, // uWidth
				20,  // uHeight
				totalWidth / 2, // width
				16, // height
				400, // tileWidth
				20); // tileHeight
		GuiScreen.drawScaledCustomSizeModalRect(
				innerX + innerWidth / 2, // x
				innerY + innerHeight - 45, // y 
				400 - totalWidth / 2, // u
				0, // v
				totalWidth / 2, // uWidth 
				20, //uHeight
				totalWidth / 2, // width 
				16, // height 
				400, // tileWidth
				20); // tileHeight
		
		font.drawString(currentToast.getText(), (innerX + innerWidth / 2) - stringWidth / 2, innerY + innerHeight - 41, currentToast.getColor() | ((int)(alpha * 255) << 24));
		
		GlStateManager.color(1, 1, 1, 1);
	}
	
	private enum Stages
	{
		FadeIn,
		Display,
		FadeOut
	}
}
