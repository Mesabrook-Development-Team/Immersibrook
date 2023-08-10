package com.mesabrook.ib.items.commerce.models;

import java.util.Collections;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemSecurityBoxModel implements IBakedModel {

	private IBakedModel originalSecurityBoxModel;
	private ItemSecurityBoxItemOverrideList overrideList;
	
	public ItemSecurityBoxModel(IBakedModel originalSecurityBoxModel)
	{
		this.originalSecurityBoxModel = originalSecurityBoxModel;
		this.overrideList = new ItemSecurityBoxItemOverrideList(Collections.EMPTY_LIST);
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return originalSecurityBoxModel.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return originalSecurityBoxModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return originalSecurityBoxModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return originalSecurityBoxModel.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return overrideList;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		Matrix4f matrix4f = originalSecurityBoxModel.handlePerspective(cameraTransformType).getRight();
		return Pair.of(this, matrix4f);
	}

}
