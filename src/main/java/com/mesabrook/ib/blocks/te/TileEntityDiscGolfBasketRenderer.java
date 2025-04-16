package com.mesabrook.ib.blocks.te;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.Vec3d;

public class TileEntityDiscGolfBasketRenderer extends TileEntitySpecialRenderer<TileEntityDiscGolfBasket> {
	
	private final DiscInBasketLocation[] basketLocations;
	public TileEntityDiscGolfBasketRenderer()
	{
		basketLocations = new DiscInBasketLocation[9];
		basketLocations[0] = new DiscInBasketLocation(new Vec3d(.25, .1875, -.0625));
		basketLocations[1] = new DiscInBasketLocation(new Vec3d(.5, .625, -.0625));
		basketLocations[2] = new DiscInBasketLocation(new Vec3d(.75, .44, -.0625));
		basketLocations[3] = new DiscInBasketLocation(new Vec3d(.75, .125, -.0625));
		basketLocations[4] = new DiscInBasketLocation(new Vec3d(.25, .6, -.0625), Axis.Y, 10);
		basketLocations[5] = new DiscInBasketLocation(new Vec3d(.5, .125, -.0625), Axis.Y, 10);
		basketLocations[6] = new DiscInBasketLocation(new Vec3d(.75, .67, -.1), Axis.X, 10);
		basketLocations[7] = new DiscInBasketLocation(new Vec3d(.25, .4, -.1));
		basketLocations[8] = new DiscInBasketLocation(new Vec3d(.75, .3, -.1));
	}
	
	@Override
	public void render(TileEntityDiscGolfBasket te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		GlStateManager.translate(x, y, z);
		int effectiveIndex = 0;
		GlStateManager.rotate(90F, 1, 0, 0);
		for(int i = 0; i < Math.min(te.discHandler.getSlots(), basketLocations.length); i++)
		{
			ItemStack stack = te.discHandler.getStackInSlot(i);
			if (stack.isEmpty())
			{
				continue;
			}
			
			DiscInBasketLocation basketLocation = basketLocations[effectiveIndex];
			GlStateManager.translate(basketLocation.translation.x, basketLocation.translation.y, basketLocation.translation.z);
			if (basketLocation.rotationAxis != null)
			{
				GlStateManager.rotate(basketLocation.rotationAmount, 
						basketLocation.rotationAxis == Axis.X ? 1F : 0F,
						basketLocation.rotationAxis == Axis.Y ? 1F : 0F,
						basketLocation.rotationAxis == Axis.Z ? 1F : 0F);
			}
			
			GlStateManager.scale(0.75, 0.75, 0.75);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.GROUND);
			GlStateManager.scale(1/.75, 1/.75, 1/.75);
			
			if (basketLocation.rotationAxis != null)
			{
				GlStateManager.rotate(-basketLocation.rotationAmount, 
						basketLocation.rotationAxis == Axis.X ? 1F : 0F,
						basketLocation.rotationAxis == Axis.Y ? 1F : 0F,
						basketLocation.rotationAxis == Axis.Z ? 1F : 0F);
			}
			GlStateManager.translate(-basketLocation.translation.x, -basketLocation.translation.y, -basketLocation.translation.z);
			
			effectiveIndex++;
		}
		GlStateManager.rotate(-90F, 1, 0, 0);
	}
	
	private static class DiscInBasketLocation
	{
		public final Vec3d translation;
		public final Axis rotationAxis;
		public final float rotationAmount;
		
		public DiscInBasketLocation(Vec3d translation, Axis rotationAxis, float rotationAmount)
		{
			this.translation = translation;
			this.rotationAxis = rotationAxis;
			this.rotationAmount = rotationAmount;
		}
		
		public DiscInBasketLocation(Vec3d translation)
		{
			this(translation, null, 0);
		}
	}
}
