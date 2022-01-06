package rz.mesabrook.wbtc.blocks.gui.telecom;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import com.google.common.collect.ImmutableSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import rz.mesabrook.wbtc.util.Reference;

public class Toaster {	
	private static HashMap<String, Toaster> toastersByPhoneNumber = new HashMap<>();
	
	public static Toaster forPhoneNumber(String phoneNumber)
	{
		Toaster toaster = toastersByPhoneNumber.get(phoneNumber);
		if (toaster == null)
		{
			toaster = new Toaster();
			toastersByPhoneNumber.put(phoneNumber, toaster);
		}
		
		return toaster;
	}
	
	public static ImmutableSet<Entry<String, Toaster>> all()
	{
		return ImmutableSet.copyOf(toastersByPhoneNumber.entrySet());
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
		tick(innerX, innerY, innerWidth, innerHeight, true);
	}
	
	public void tickNoDraw()
	{
		tick(0,0,0,0,false);
	}
	
	public void tick(int innerX, int innerY, int innerWidth, int innerHeight, boolean doDraw)
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
				
				if (doDraw)
				{
					draw(innerX, innerY, innerWidth, innerHeight, (float)currentStageTimer / 100F);
				}
				
				if (currentStageTimer == 100)
				{
					currentStage = Stages.Display;
					currentStageTimer = 0;
				}
				break;
			case Display:
				currentStageTimer++;
				if (doDraw)
				{
					draw(innerX, innerY, innerWidth, innerHeight, 1F);
				}
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
				if (doDraw)
				{
					draw(innerX, innerY, innerWidth, innerHeight, (float)currentStageTimer / 100F);
				}
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