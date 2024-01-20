package com.mesabrook.ib.util.handlers;

import java.util.Arrays;
import java.util.Optional;

import javax.vecmath.Matrix3f;

import com.mesabrook.ib.blocks.sco.BlockShelf;
import com.mesabrook.ib.blocks.sco.ProductPlacement;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity.ProductSpot;
import com.mesabrook.ib.blocks.te.ShelvingTileEntityRenderer;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.SoundInit;
import com.mesabrook.ib.items.armor.NightVisionGoggles;
import com.mesabrook.ib.items.armor.PoliceHelmet;
import com.mesabrook.ib.items.armor.SafetyVest;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.NVTogglePacket;
import com.mesabrook.ib.net.PoliceEffectsTogglePacket;
import com.mesabrook.ib.net.SoundRandomizerPacket;
import com.mesabrook.ib.net.VestTogglePacket;
import com.mesabrook.ib.net.sco.QueryPricePacket;
import com.mesabrook.ib.proxy.ClientProxy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class KeyEventHandler
{
	private static TextComponentTranslation vestToggle;
	private static TextComponentTranslation vestToggleOff;
	private static TextComponentTranslation nvToggleOn;
	private static TextComponentTranslation nvToggleOff;
	private static TextComponentTranslation hammerShift;
	private static TextComponentTranslation policeOn;
	private static TextComponentTranslation policeOff;

	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e)
	{
		if (ClientProxy.vestToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.armorInventory.get(2); // 2 = chest
			
			vestToggle = new TextComponentTranslation("im.vest.toggle");
			vestToggle.getStyle().setColor(TextFormatting.GOLD);
			vestToggleOff = new TextComponentTranslation("im.vest.toggle.off");
			vestToggleOff.getStyle().setColor(TextFormatting.GOLD);

			if (!(stack.getItem() instanceof SafetyVest))
			{
				return;
			}
			else
			{
				if(player.isPotionActive(MobEffects.GLOWING))
				{
					player.sendStatusMessage(new TextComponentString(vestToggleOff.getFormattedText()), true);
				}
				else
				{
					player.sendStatusMessage(new TextComponentString(vestToggle.getFormattedText()), true);
				}

				player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
				VestTogglePacket packet = new VestTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}

		if(ClientProxy.nvToggleKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			World world = player.world;
			ItemStack stack = player.inventory.armorInventory.get(3); // 3 = head

			nvToggleOn = new TextComponentTranslation("im.nv.toggle.on");
			nvToggleOn.getStyle().setColor(TextFormatting.GOLD);
			nvToggleOff = new TextComponentTranslation("im.nv.toggle.off");
			nvToggleOff.getStyle().setColor(TextFormatting.GOLD);

			if (!(stack.getItem() instanceof NightVisionGoggles))
			{
				return;
			}
			else
			{
				if(player.isPotionActive(MobEffects.NIGHT_VISION))
				{
					player.sendStatusMessage(new TextComponentString(nvToggleOff.getFormattedText()), true);
					player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0F, 5.0F);
				}
				else
				{
					player.sendStatusMessage(new TextComponentString(nvToggleOn.getFormattedText()), true);
					ClientSoundPacket packet = new ClientSoundPacket();
					packet.pos = player.getPosition();
					packet.soundName = "nv";
					packet.useDelay = false;
					packet.range = 5;
					PacketHandler.INSTANCE.sendToServer(packet);
					player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.5F, 5.0F);
				}

				NVTogglePacket packet = new NVTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}

		if(ClientProxy.hammerSoundKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.getCurrentItem();

			if(!(stack.getItem() instanceof ItemBanHammer))
			{
				return;
			}

			player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
			SoundRandomizerPacket packet = new SoundRandomizerPacket();
			PacketHandler.INSTANCE.sendToServer(packet);
		}

		if(ClientProxy.policeHelmetKey.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.inventory.armorInventory.get(3); // 3 = head

			policeOn = new TextComponentTranslation("im.police.toggle.on");
			policeOn.getStyle().setBold(true);
			policeOn.getStyle().setColor(TextFormatting.AQUA);
			policeOff = new TextComponentTranslation("im.police.toggle.off");
			policeOff.getStyle().setBold(true);
			policeOff.getStyle().setColor(TextFormatting.AQUA);

			if (!(stack.getItem() instanceof PoliceHelmet))
			{
				return;
			}
			else
			{
				if(player.isPotionActive(MobEffects.SPEED))
				{
					player.sendStatusMessage(new TextComponentString(policeOff.getFormattedText()), true);
					player.playSound(SoundInit.RADIO_CLOSE, 1.0F, 1.0F);
					player.playSound(SoundInit.LEOTEU_OFF, 1.0F, 1.0F);
				}
				else
				{
					player.sendStatusMessage(new TextComponentString(policeOn.getFormattedText()), true);
					player.playSound(SoundInit.LEOTEU_ON, 1.0F, 1.0F);
				}

				player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3F, 1.0F);
				PoliceEffectsTogglePacket packet = new PoliceEffectsTogglePacket();
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}
		
		if(ClientProxy.shelfPriceKey.isPressed() && WorldRenderHandler.thisTickViewedBlockPos != null)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			
			IBlockState state = player.world.getBlockState(WorldRenderHandler.thisTickViewedBlockPos);
			ProductPlacement placement = ((BlockShelf)state.getBlock()).getProductPlacementByBoundingBox(WorldRenderHandler.thisTickViewedBoundingBox);
			if (ShelvingTileEntityRenderer.priceDisplayInformationsByBlockPos.containsKey(WorldRenderHandler.thisTickViewedBlockPos.toLong()) && 
					ShelvingTileEntityRenderer.priceDisplayInformationsByBlockPos.get(WorldRenderHandler.thisTickViewedBlockPos.toLong()).stream().anyMatch(spdi -> spdi.placementID == placement.getPlacementID()))
			{
				return;
			}
			
			ShelvingTileEntity shelving = (ShelvingTileEntity)player.world.getTileEntity(WorldRenderHandler.thisTickViewedBlockPos);
			Optional<ProductSpot> optProductSpot = Arrays.stream(shelving.getProductSpots()).filter(ps -> ps.getPlacementID() == placement.getPlacementID()).findFirst();
			if (!optProductSpot.isPresent())
			{
				return;
			}
			
			ProductSpot productSpot = optProductSpot.get();
			
			ItemStack stack = productSpot.getItems()[0];
			if (stack.isEmpty())
			{
				return;
			}
			
			if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
				stack = securedItem.getInnerStack();
				if (stack.isEmpty())
				{
					return;
				}
			}
			
			AxisAlignedBB rotatedBB = ((BlockShelf)state.getBlock()).ORIGINAL_BOX_TO_ROTATED_BY_FACING.get(state.getValue(BlockShelf.FACING)).get(placement.getBoundingBox());
			ShelvingTileEntityRenderer.ShelfPriceDisplayInformation displayInfo = new ShelvingTileEntityRenderer.ShelfPriceDisplayInformation();
			displayInfo.placementID = placement.getPlacementID();
			displayInfo.displayY = rotatedBB.maxY + 0.0625;
			displayInfo.timeInitiallyDisplayed = System.currentTimeMillis();
			switch(state.getValue(BlockShelf.FACING))
			{
				case NORTH:
					displayInfo.displayX = rotatedBB.maxX - (rotatedBB.maxX - rotatedBB.minX) / 2;
					displayInfo.displayZ = rotatedBB.minZ;
					break;
				case WEST:
					displayInfo.displayX = rotatedBB.minX;
					displayInfo.displayZ = rotatedBB.maxZ - (rotatedBB.maxZ - rotatedBB.minZ) / 2;
					break;
				case EAST:
					displayInfo.displayX = rotatedBB.maxX;
					displayInfo.displayZ = rotatedBB.maxZ - (rotatedBB.maxZ - rotatedBB.minZ) / 2;
					break;
				case SOUTH:
					displayInfo.displayX = rotatedBB.maxX - (rotatedBB.maxX - rotatedBB.minX) / 2;
					displayInfo.displayZ = rotatedBB.maxZ;
					break;
			}
			ShelvingTileEntityRenderer.priceDisplayInformationsByBlockPos.put(WorldRenderHandler.thisTickViewedBlockPos.toLong(), displayInfo);
			
			QueryPricePacket queryPacket = new QueryPricePacket();
			queryPacket.placementID = placement.getPlacementID();
			queryPacket.shelfPos = WorldRenderHandler.thisTickViewedBlockPos;			
			queryPacket.stack = stack;
			PacketHandler.INSTANCE.sendToServer(queryPacket);
		}
	}
}
