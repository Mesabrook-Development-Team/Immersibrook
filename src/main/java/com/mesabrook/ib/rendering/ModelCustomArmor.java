package com.mesabrook.ib.rendering;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCustomArmor extends ModelBiped
{
	public ModelRenderer shape15;
	
	public ModelCustomArmor()
	{
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.shape15 = new ModelRenderer(this, 82, 0);
        this.shape15.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.shape15.addBox(-1.0F, -5.0F, -1.0F, 10, 5, 12, 0.0F);
        
        this.bipedHead.addChild(shape15);
	}
	
    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        this.bipedHead.render(scale);
        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) 
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
