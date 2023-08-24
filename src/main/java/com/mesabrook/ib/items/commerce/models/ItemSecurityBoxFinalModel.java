package com.mesabrook.ib.items.commerce.models;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class ItemSecurityBoxFinalModel implements IBakedModel {

	private IBakedModel parentModel;
	private IBakedModel subModel;
	private boolean isItem;
	
	public ItemSecurityBoxFinalModel(IBakedModel parentModel, IBakedModel subModel, boolean isItem)
	{
		this.parentModel = parentModel;
		this.subModel = subModel;
		this.isItem = isItem;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		ArrayList<BakedQuad> quads = new ArrayList<>();
		
		if (subModel != null)
		{
			for(BakedQuad quad : subModel.getQuads(state, side, rand))
			{
				quads.add(scaleAndTranslateQuad(quad, new Vec3d(0.25, 0.05, 0.2), 0.75F));
			}
		}
		
		quads.addAll(parentModel.getQuads(state, side, rand));
		return quads;
	}
	
	// Thank you SnowyEgret!!
	// https://forums.minecraftforge.net/topic/32652-18-solved-translate-and-scale-a-bakedquad/
	private BakedQuad scaleAndTranslateQuad(BakedQuad q, Vec3d t, float s) {

		int[] v = q.getVertexData().clone();
		// leftRigh, upDown, frontBack
		double lr, ud, fb;

		// A quad has four verticies
		// indices of x values of vertices are 0, 7, 14, 21
		// indices of y values of vertices are 1, 8, 15, 22
		// indices of z values of vertices are 2, 9, 16, 23

		// east: x
		// south: z
		// up: y

		switch (q.getFace()) {
		case UP:
			// Quad up is towards north
			lr = t.x;
			ud = t.z;
			fb = t.y;

			v[0] = transform(v[0], lr, s);
			v[7] = transform(v[7], lr, s);
			v[14] = transform(v[14], lr, s);
			v[21] = transform(v[21], lr, s);

			v[1] = transform(v[1], fb, s);
			v[8] = transform(v[8], fb, s);
			v[15] = transform(v[15], fb, s);
			v[22] = transform(v[22], fb, s);

			v[2] = transform(v[2], ud, s);
			v[9] = transform(v[9], ud, s);
			v[16] = transform(v[16], ud, s);
			v[23] = transform(v[23], ud, s);
			break;

		case DOWN:
			// Quad up is towards south
			lr = t.x;
			ud = t.z;
			fb = t.y;

			v[0] = transform(v[0], lr, s);
			v[7] = transform(v[7], lr, s);
			v[14] = transform(v[14], lr, s);
			v[21] = transform(v[21], lr, s);

			v[1] = transform(v[1], fb, s);
			v[8] = transform(v[8], fb, s);
			v[15] = transform(v[15], fb, s);
			v[22] = transform(v[22], fb, s);

			v[2] = transform(v[2], -ud, s);
			v[9] = transform(v[9], -ud, s);
			v[16] = transform(v[16], -ud, s);
			v[23] = transform(v[23], -ud, s);
			break;

		case WEST:
			lr = t.z;
			ud = t.y;
			fb = t.x;

			v[0] = transform(v[0], fb, s);
			v[7] = transform(v[7], fb, s);
			v[14] = transform(v[14], fb, s);
			v[21] = transform(v[21], fb, s);

			v[1] = transform(v[1], ud, s);
			v[8] = transform(v[8], ud, s);
			v[15] = transform(v[15], ud, s);
			v[22] = transform(v[22], ud, s);

			v[2] = transform(v[2], lr, s);
			v[9] = transform(v[9], lr, s);
			v[16] = transform(v[16], lr, s);
			v[23] = transform(v[23], lr, s);
			break;

		case EAST:
			lr = t.z;
			ud = t.y;
			fb = t.x;

			v[0] = transform(v[0], fb, s);
			v[7] = transform(v[7], fb, s);
			v[14] = transform(v[14], fb, s);
			v[21] = transform(v[21], fb, s);

			v[1] = transform(v[1], ud, s);
			v[8] = transform(v[8], ud, s);
			v[15] = transform(v[15], ud, s);
			v[22] = transform(v[22], ud, s);

			v[2] = transform(v[2], lr, s);
			v[9] = transform(v[9], lr, s);
			v[16] = transform(v[16], lr, s);
			v[23] = transform(v[23], lr, s);
			break;

		case NORTH:
			lr = t.x;
			ud = t.y;
			fb = t.z;

			v[0] = transform(v[0], lr, s);
			v[7] = transform(v[7], lr, s);
			v[14] = transform(v[14], lr, s);
			v[21] = transform(v[21], lr, s);

			v[1] = transform(v[1], ud, s);
			v[8] = transform(v[8], ud, s);
			v[15] = transform(v[15], ud, s);
			v[22] = transform(v[22], ud, s);

			v[2] = transform(v[2], fb, s);
			v[9] = transform(v[9], fb, s);
			v[16] = transform(v[16], fb, s);
			v[23] = transform(v[23], fb, s);
			break;

		case SOUTH:
			// Case where quad is aligned with world coordinates
			lr = t.x;
			ud = t.y;
			fb = t.z;

			v[0] = transform(v[0], lr, s);
			v[7] = transform(v[7], lr, s);
			v[14] = transform(v[14], lr, s);
			v[21] = transform(v[21], lr, s);

			v[1] = transform(v[1], ud, s);
			v[8] = transform(v[8], ud, s);
			v[15] = transform(v[15], ud, s);
			v[22] = transform(v[22], ud, s);

			v[2] = transform(v[2], fb, s);
			v[9] = transform(v[9], fb, s);
			v[16] = transform(v[16], fb, s);
			v[23] = transform(v[23], fb, s);
			break;

		default:
			System.out.println("Unexpected face=" + q.getFace());
			break;
		}

		return new BakedQuad(v, q.getTintIndex(), q.getFace(), q.getSprite(), q.shouldApplyDiffuseLighting(), q.getFormat());
	}

	private int transform(int i, double t, float s) {
		float f = Float.intBitsToFloat(i);
		f = (float)(f + t) * s;
		return Float.floatToRawIntBits(f);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return parentModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return parentModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return parentModel.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		throw new UnsupportedOperationException("The finalised model does not have an override list.");
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		Matrix4f matrix4f = parentModel.handlePerspective(cameraTransformType).getRight();
		return Pair.of(this, matrix4f);
	}
}
