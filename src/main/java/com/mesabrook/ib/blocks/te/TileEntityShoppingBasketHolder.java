package com.mesabrook.ib.blocks.te;

import java.util.ArrayList;
import java.util.Stack;

import javax.vecmath.Vector4f;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityShoppingBasketHolder extends TileEntity {
	private static final int MAX_BASKETS = 8;
	private Stack<ItemStack> baskets = new Stack<>();
	private ArrayList<int[]> modelVertexData = new ArrayList<>();
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList tagList = compound.getTagList("baskets", NBT.TAG_COMPOUND);
		tagListToBaskets(tagList);
	}
	
	private void tagListToBaskets(NBTTagList tagList)
	{
		baskets.clear();
		baskets.setSize(tagList.tagCount());
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTBase nbt = tagList.get(i);
			if (nbt instanceof NBTTagEnd)
			{
				baskets.setElementAt(ItemStack.EMPTY, i);
			}
			else if (nbt instanceof NBTTagCompound)
			{
				NBTTagCompound tag = (NBTTagCompound)nbt;
				ItemStack stack = new ItemStack(tag);
				baskets.setElementAt(stack, i);
			}
		}
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			rebuildModel();
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {		
		compound.setTag("baskets", basketsToTagList());
		return super.writeToNBT(compound);
	}
	
	private NBTTagList basketsToTagList()
	{
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < baskets.size(); i++)
		{
			tagList.appendTag(baskets.get(i).serializeNBT());
		}
		
		return tagList;
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound =  super.getUpdateTag();
		compound.setTag("baskets", basketsToTagList());
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		
		tagListToBaskets(tag.getTagList("baskets", NBT.TAG_COMPOUND));
		
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	public ImmutableList<ItemStack> getBaskets()
	{
		return ImmutableList.copyOf(baskets);
	}
	
	public boolean pushBasket(ItemStack basketStack)
	{
		if (baskets.size() >= MAX_BASKETS || basketStack.getItem() != ModItems.SHOPPING_BASKET || !basketStack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			return false;
		}
		
		IItemHandler itemHandler = basketStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			if (!itemHandler.getStackInSlot(i).isEmpty())
			{
				return false;
			}
		}
		
		baskets.push(basketStack);
		markDirty();
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			rebuildModel();
		}
		
		return true;
	}
	
	public ItemStack popBasket()
	{
		if (baskets.size() <= 0)
		{
			return null;
		}
		
		ItemStack stack = baskets.pop();
		markDirty();
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			rebuildModel();
		}
		
		return stack;
	}

	@SideOnly(Side.CLIENT)
	private void rebuildModel()
	{
		modelVertexData.clear();
		
		EnumFacing[] facings = EnumFacing.VALUES;
		facings = ArrayUtils.add(facings, null);
		
		int basketCounter = 0;
		for(ItemStack basket : getBaskets())
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
					modelVertexData.add(transformedQuad.getVertexData());
				}
			}
			
			basketCounter++;
		}
	}
	
	@SideOnly(Side.CLIENT)
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

	public ArrayList<int[]> getModelVertexData()
	{
		return modelVertexData;
	}
}
