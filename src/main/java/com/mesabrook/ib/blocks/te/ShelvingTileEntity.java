package com.mesabrook.ib.blocks.te;

import java.util.HashMap;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.blocks.sco.BlockShelf;
import com.mesabrook.ib.blocks.sco.ProductPlacement;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.commerce.ItemSecurityBox;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ShelvingTileEntity extends TileEntity {	
	private HashMap<Integer, ProductSpot> productSpotsByPlacementID = new HashMap<>();
	private long locationIDOwner;
	
	private int displayListID = -1;
	private boolean needsRebuilt = true;
	
	public boolean onActivated(EntityPlayer player, EnumHand hand, ProductPlacement placement)
	{
		if (!productSpotsByPlacementID.containsKey(placement.getPlacementID()))
		{
			ProductSpot spot = new ProductSpot(placement.getPlacementID(), placement.getSpaces());
			productSpotsByPlacementID.put(placement.getPlacementID(), spot);
		}
		
		ItemStack playerStack = player.getHeldItem(hand);
		IEmployeeCapability employeeCap = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		ProductSpot spot = productSpotsByPlacementID.get(placement.getPlacementID());
		
		if (employeeCap.getLocationID() == getLocationIDOwner() && employeeCap.manageInventory())
		{
			if (playerStack.isEmpty()) // Pulling item off shelf
			{
				removeItemAsEmployee(spot, player, hand);
			}
			else // Putting something on shelf
			{
				addItemAsEmployee(spot, player, hand);
			}
		}
		else
		{
			if (playerStack.isEmpty() || playerStack.getItem() == ModItems.SHOPPING_BASKET) // Pulling something off shelf
			{
				removeItemAsCustomer(spot, player, hand);
			}
			else // Maybe putting back on shelf?
			{
				checkAndAddAsCustomer(spot, player, hand);
			}
		}
		
		return false;
	}
	
	private void removeItemAsEmployee(ProductSpot spot, EntityPlayer player, EnumHand hand)
	{
		ItemStack stackToRemove = spot.items[0];
		if (stackToRemove.isEmpty())
		{
			return;
		}
		
		IEmployeeCapability employeeCapability = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		if (stackToRemove.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
		{
			ISecuredItem secureCap = stackToRemove.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
			if (employeeCapability.getLocationID() == 0 || (secureCap.getLocationIDOwner() != 0 && secureCap.getLocationIDOwner() != employeeCapability.getLocationID())) // Player is an employee, just not for this item
			{
				removeItemAsCustomer(spot, player, hand);
				return;
			}
			
			secureCap.setHomeLocation(null);
			secureCap.setHomeSpot(-1);
		}
		
		player.setHeldItem(hand, stackToRemove);
		for(int i = 0; i < spot.items.length; i++)
		{
			if (i < spot.items.length - 1)
			{
				spot.items[i] = spot.items[i + 1];
			}
			else
			{
				spot.items[i] = ItemStack.EMPTY;
			}
		}
		
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	private void addItemAsEmployee(ProductSpot spot, EntityPlayer player, EnumHand hand)
	{
		if (!spot.items[spot.items.length - 1].isEmpty())
		{
			player.sendMessage(new TextComponentString("This spot is full!"));
			return;
		}
		
		int indexToSet = -1;
		for(int i = 0; i < spot.items.length; i++)
		{
			if (spot.items[i].isEmpty())
			{
				indexToSet = i;
				break;
			}
		}
		
		ItemStack placedStack = player.getHeldItem(hand).copy();
		if (placedStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
		{
			ISecuredItem secureCap = placedStack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
			if (secureCap.getLocationIDOwner() != 0 && secureCap.getLocationIDOwner() != getLocationIDOwner())
			{
				player.sendMessage(new TextComponentString(TextFormatting.RED + "This item belongs to a different store"));
				if (player.getHeldItem(hand).isEmpty())
				{
					player.setHeldItem(hand, placedStack);
				}
				else
				{
					player.getHeldItem(hand).grow(1);
				}
				
				return;
			}
			secureCap.setHomeLocation(getPos());
			secureCap.setHomeSpot(spot.placementID);
			placedStack.setCount(1);
		}
		
		spot.items[indexToSet] = placedStack;
		player.getHeldItem(hand).shrink(placedStack.getCount());
		
		markDirty();
		
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	private void checkAndAddAsCustomer(ProductSpot spot, EntityPlayer player, EnumHand hand)
	{
		ItemStack heldItem = player.getHeldItem(hand);
		ItemStack stackToSet = heldItem.copy();
		
		if (heldItem.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
		{
			ISecuredItem secureCap = heldItem.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
			if (secureCap.getHomeLocation().getY() == -1 || !secureCap.getHomeLocation().equals(getPos()) || secureCap.getHomeSpot() != spot.placementID)
			{
				return;
			}
			
			stackToSet.setCount(1);
		}
		
		if (!spot.items[spot.items.length - 1].isEmpty())
		{
			player.sendMessage(new TextComponentString("This spot is full!"));
			return;
		}
		
		int indexToSet = -1;
		for(int i = 0; i < spot.items.length; i++)
		{
			if (spot.items[i].isEmpty())
			{
				indexToSet = i;
				break;
			}
		}
		
		spot.items[indexToSet] = stackToSet;
		heldItem.shrink(stackToSet.getCount());
		
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	private void removeItemAsCustomer(ProductSpot spot, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = spot.items[0];
		if (stack.isEmpty())
		{
			return;
		}
		
		ItemStack heldItem = player.getHeldItem(hand);
		if (heldItem.getItem() == ModItems.SHOPPING_BASKET)
		{
			IItemHandler handler = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			int insertIndex = -1;
			for(int i = 0; i < handler.getSlots(); i++)
			{
				if (handler.getStackInSlot(i).isEmpty())
				{
					insertIndex = i;
					break;
				}
			}
			
			if (insertIndex == -1)
			{
				InventoryHelper.spawnItemStack(world, player.posX, player.posY + player.eyeHeight, player.posZ, stack);
			}
			else
			{
				handler.insertItem(insertIndex, stack, false);
				String stackName = stack.getDisplayName();
				int stackCount = stack.getCount();
				if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
				{
					stackName = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack().getDisplayName();
					stackCount = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack().getCount();
				}
				player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + stackName + " (x" + stackCount + ") added to shopping basket"), true);
			}
		}
		else
		{
			player.setHeldItem(hand, stack);			
		}
		for(int i = 0; i < spot.items.length; i++)
		{
			if (i < spot.items.length - 1)
			{
				spot.items[i] = spot.items[i + 1];
			}
			else
			{
				spot.items[i] = ItemStack.EMPTY;
			}
		}
		
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	public ProductSpot[] getProductSpots() { return productSpotsByPlacementID.values().toArray(new ProductSpot[0]); }
	
	// Data Stuff
	public long getLocationIDOwner()
	{
		return locationIDOwner;
	}
	
	public void setLocationIDOwner(long locationIDOwner)
	{
		this.locationIDOwner = locationIDOwner;
		markDirty();
	}
	
	@Override
	protected void setWorldCreate(World worldIn) {
		super.setWorldCreate(worldIn);
		this.setWorld(worldIn);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		locationIDOwner = compound.getLong("locationIDOwner");
		loadProductSpots(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		writeProductSpots(compound);
		compound.setLong("locationIDOwner", locationIDOwner);
		return super.writeToNBT(compound);
	}
	
	private void loadProductSpots(NBTTagCompound compound)
	{
		productSpotsByPlacementID.clear();
		
		if (compound.hasKey("productSpots"))
		{
			for(NBTBase tag : compound.getTagList("productSpots", NBT.TAG_COMPOUND))
			{
				ProductSpot spot = new ProductSpot(0, 0);
				spot.deserializeNBT((NBTTagCompound)tag);
				productSpotsByPlacementID.put(spot.placementID, spot);
			}
		}
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			markRebuild();
		}
	}
	
	private void writeProductSpots(NBTTagCompound compound)
	{
		NBTTagList list = new NBTTagList();
		for(ProductSpot spot : productSpotsByPlacementID.values())
		{
			list.appendTag(spot.serializeNBT());
		}
		compound.setTag("productSpots", list);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		writeProductSpots(compound);
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		loadProductSpots(tag);
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
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return !(oldState.getBlock() instanceof BlockShelf && newSate.getBlock() instanceof BlockShelf);
	}
	
	public static class ProductSpot implements INBTSerializable<NBTTagCompound>
	{
		private int placementID;
		private ItemStack[] items;
		public ProductSpot(int placementID, int spaces)
		{
			this.placementID = placementID;
			items = new ItemStack[spaces];
			for(int i = 0; i < spaces; i++)
			{
				items[i] = ItemStack.EMPTY;
			}
		}
		
		public void clearItemStacks()
		{
			for(int i = 0; i < items.length; i++)
			{
				items[i] = ItemStack.EMPTY;
			}
		}
		
		public int getPlacementID() { return placementID; }
		public ItemStack[] getItems() { return items; }
		
		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("placementID", placementID);
			tag.setInteger("spaces", items.length);
			NBTTagList list = new NBTTagList();
			for(ItemStack stack : items)
			{
				list.appendTag(stack.serializeNBT());
			}
			tag.setTag("items", list);
			return tag;
		}
		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			placementID = nbt.getInteger("placementID");
			items = new ItemStack[nbt.getInteger("spaces")];
			if (nbt.hasKey("items"))
			{
				NBTTagList list = nbt.getTagList("items", NBT.TAG_COMPOUND);
				int i = 0;
				for(NBTBase compound : list)
				{
					ItemStack newStack = new ItemStack((NBTTagCompound)compound);
					items[i++] = newStack;
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void markRebuild()
	{
		needsRebuilt = true;
	}
	
	@SideOnly(Side.CLIENT)
	public void rebuildModel()
	{
		if (displayListID >= 0)
		{
			GLAllocation.deleteDisplayLists(displayListID);
		}
		
		EnumFacing[] facings = EnumFacing.VALUES;
		facings = ArrayUtils.add(facings, null);
		
		BlockShelf shelf = (BlockShelf)getBlockType();

		
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder builder = tess.getBuffer();
		displayListID = GLAllocation.generateDisplayLists(1);
		GlStateManager.glNewList(displayListID, GL11.GL_COMPILE);
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
		
		for(ProductSpot spot : getProductSpots())
		{
			float positionOffsetZ = 0;
			ProductPlacement placement = shelf.getProductPlacementByID(spot.getPlacementID());
			if (placement == null)
			{
				continue;
			}
			
			AxisAlignedBB spotBB = placement.getBoundingBox();
			
			Vector3f offset = new Vector3f((float)spotBB.minX, (float)spotBB.minY, (float)spotBB.minZ);
			
			float scale = (float)(spotBB.maxY - spotBB.minY);
			if (scale > spotBB.maxX - spotBB.minX)
			{
				scale = (float)(spotBB.maxX - spotBB.minX);
			}
			
			TRSRTransformation baseTransform = new TRSRTransformation(
					null,
					null,
					new Vector3f(scale, scale, scale),
					null);
			
			ItemStack[] items = spot.getItems();
			for (int i = 0; i < items.length; i++)
			{
				float maximumZ = Float.MIN_VALUE;
				ItemStack item = items[i];
				if (item.isEmpty())
				{
					continue;
				}
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, getWorld(), null);
				Pair<? extends IBakedModel, Matrix4f> pair = model.handlePerspective(TransformType.NONE);
				TRSRTransformation itemTransform = baseTransform;
				if (pair.getRight() != null)
				{
					Matrix4f itemMatrix = itemTransform.getMatrix();
					itemMatrix.mul(pair.getRight());
					itemTransform = new TRSRTransformation(itemMatrix);
				}
				model = pair.getLeft();
				for(EnumFacing facing : facings)
				{
					for(BakedQuad quad : model.getQuads(null, facing, 0))
					{
						Vector3f vecLoc = new Vector3f();
						BakedQuad newQuad = transform(quad, itemTransform, offset, vecLoc, positionOffsetZ);
						builder.addVertexData(newQuad.getVertexData());
						
						if (vecLoc.z > maximumZ)
						{
							maximumZ = vecLoc.z;
						}
					}
				}
				
				positionOffsetZ += maximumZ;
			}
		}
		
		tess.draw();
		GlStateManager.glEndList();
		
		needsRebuilt = false;
	}
	
	@SideOnly(Side.CLIENT)
	protected static BakedQuad transform(BakedQuad quad, final TRSRTransformation transform, Vector3f offset, Vector3f ret, float additionalZOffset) {
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(quad.getFormat());
		final IVertexConsumer consumer = new VertexTransformer(builder) {
			@Override
			public void put(int element, float... data) {
				VertexFormatElement formatElement = quad.getFormat().getElement(element);
				switch(formatElement.getUsage()) {
				case POSITION: {
					float[] newData = new float[4];
					Vector4f vec = new Vector4f(data);
					transform.getMatrix().transform(vec);
					vec.get(newData);
					ret.x = newData[0];
					ret.y = newData[1];
					ret.z = newData[2];
					newData[0] += offset.x;
					newData[1] += offset.y;
					newData[2] += offset.z + additionalZOffset;
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

	public int getDisplayListID()
	{
		return displayListID;
	}
	
	public boolean getNeedsRebuild()
	{
		return needsRebuilt;
	}
}
