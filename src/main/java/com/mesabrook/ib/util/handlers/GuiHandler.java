package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.blocks.container.ContainerRation;
import com.mesabrook.ib.blocks.container.ContainerRegisterSecurityBoxInventory;
import com.mesabrook.ib.blocks.container.ContainerShoppingBasket;
import com.mesabrook.ib.blocks.container.ContainerSmartphone;
import com.mesabrook.ib.blocks.container.ContainerStampBook;
import com.mesabrook.ib.blocks.container.ContainerTaggingStation;
import com.mesabrook.ib.blocks.container.ContainerTaggingStationUntag;
import com.mesabrook.ib.blocks.container.ContainerTrashBin;
import com.mesabrook.ib.blocks.container.ContainerWallet;
import com.mesabrook.ib.blocks.gui.GuiAboutImmersibrook;
import com.mesabrook.ib.blocks.gui.GuiCompanyNotifications;
import com.mesabrook.ib.blocks.gui.GuiFoodBox;
import com.mesabrook.ib.blocks.gui.GuiPlaque;
import com.mesabrook.ib.blocks.gui.GuiRation;
import com.mesabrook.ib.blocks.gui.GuiSoundEmitter;
import com.mesabrook.ib.blocks.gui.GuiStampBook;
import com.mesabrook.ib.blocks.gui.GuiTOS;
import com.mesabrook.ib.blocks.gui.GuiTrashBin;
import com.mesabrook.ib.blocks.gui.GuiWallSign;
import com.mesabrook.ib.blocks.gui.GuiWallet;
import com.mesabrook.ib.blocks.gui.atm.GuiATMHome;
import com.mesabrook.ib.blocks.gui.commerce.GuiShoppingBasket;
import com.mesabrook.ib.blocks.gui.sco.GuiPOSSecurityBoxInventory;
import com.mesabrook.ib.blocks.gui.sco.GuiPOSStarter;
import com.mesabrook.ib.blocks.gui.sco.GuiStoreMode;
import com.mesabrook.ib.blocks.gui.sco.GuiTaggingStation;
import com.mesabrook.ib.blocks.gui.sco.GuiTaggingStationUntag;
import com.mesabrook.ib.blocks.gui.telecom.GuiBubbleSplashAnim;
import com.mesabrook.ib.blocks.gui.telecom.GuiDeadPhone;
import com.mesabrook.ib.blocks.gui.telecom.GuiEmptyPhone;
import com.mesabrook.ib.blocks.gui.telecom.GuiLowBatWarning;
import com.mesabrook.ib.blocks.gui.telecom.GuiNewEmergencyAlert;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneActivate;
import com.mesabrook.ib.blocks.gui.telecom.GuiSmartphoneInv;
import com.mesabrook.ib.blocks.gui.telecom.GuiThermalWarning;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityTaggingStation;
import com.mesabrook.ib.blocks.te.TileEntityTrashBin;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.GUI_TRASHBIN) return new ContainerTrashBin(player.inventory, (TileEntityTrashBin)world.getTileEntity(new BlockPos(x,y,z)), player);
		else if (ID == Reference.GUI_STAMP_BOOK) return new ContainerStampBook(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]);
		else if (ID == Reference.GUI_TAGGING_STATION) return new ContainerTaggingStation(player.inventory, ((TileEntityTaggingStation)world.getTileEntity(new BlockPos(x,y,z))).getTaggingStationInventory(), new BlockPos(x,y,z));
		else if (ID == Reference.GUI_RATION) return new ContainerRation(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]);
		else if (ID == Reference.GUI_REGISTER_SECURITY_BOX_INVENTORY) return new ContainerRegisterSecurityBoxInventory(player.inventory, (TileEntityRegister)world.getTileEntity(new BlockPos(x,y,z)));
		else if (ID == Reference.GUI_WALLET) return new ContainerWallet(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]);
		else if (ID == Reference.GUI_TAGGING_STATION_UNTAG) return new ContainerTaggingStationUntag(player.inventory, new BlockPos(x,y,z));
		else if (ID == Reference.GUI_SHOPPING_BASKET) return new ContainerShoppingBasket(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]);
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.GUI_TRASHBIN) return new GuiTrashBin(player.inventory, (TileEntityTrashBin)world.getTileEntity(new BlockPos(x,y,z)), player);
		else if (ID == Reference.GUI_PLAQUE) return new GuiPlaque(EnumHand.values()[x]);
		else if (ID == Reference.GUI_FOODBOX) return new GuiFoodBox(EnumHand.values()[x]);
		else if (ID == Reference.GUI_ABOUT) return new GuiAboutImmersibrook();
		else if (ID == Reference.GUI_PHONE) 
		{
			EnumHand hand = EnumHand.values()[x]; 
			ItemStack stack = player.getHeldItem(hand); 
			NBTTagCompound stackData = stack.getTagCompound();
			ItemPhone.NBTData stackNBTData = new ItemPhone.NBTData();
			stackNBTData.deserializeNBT(stackData);
			String phoneNumber = stackNBTData.getPhoneNumberString();
			boolean hasToDoOOBE = stackNBTData.getNeedToDoOOBE();
			boolean isPhoneDead = stackNBTData.getIsPhoneDead();
			
			if (phoneNumber == null)
			{
				return new GuiPhoneActivate(stack, hand);
			}
			else if (GuiNewEmergencyAlert.labelsByNumber.containsKey(stackNBTData.getPhoneNumber()) || GuiNewEmergencyAlert.textByNumber.containsKey(stackNBTData.getPhoneNumber()))
			{
				return new GuiNewEmergencyAlert(stack, hand);
			}
			else if(hasToDoOOBE)
			{
				return new GuiBubbleSplashAnim(stack, hand);
			}
			else if(stackNBTData.getBatteryLevel() <= 0)
			{
				return new GuiDeadPhone(stack, hand);
			}
			else if(stackNBTData.getBatteryLevel() <= 100)
			{
				return new GuiLowBatWarning(stack, hand);
			}
			else if(player.dimension == -1)
			{
				return new GuiThermalWarning(stack, hand);
			}
			else
			{
				int nextID = TelecomClientHandlers.getNextHandlerID();
				
				TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, TelecomClientHandlers::onPhoneQueryResponsePacket);
				PhoneQueryPacket query = new PhoneQueryPacket();
				query.forNumber = phoneNumber;
				query.clientHandlerCode = nextID;
				PacketHandler.INSTANCE.sendToServer(query);
				
				return new GuiEmptyPhone(stack, hand);
			}
		}
		else if (ID == Reference.GUI_STAMP_BOOK) return new GuiStampBook(new ContainerStampBook(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]));
		else if (ID == Reference.GUI_RATION) return new GuiRation(new ContainerRation(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]));
		else if (ID == Reference.GUI_SMARTPHONE_INV) return new GuiSmartphoneInv(new ContainerSmartphone(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]));
		else if (ID == Reference.GUI_WALLSIGN) return new GuiWallSign(EnumHand.values()[x]);
		else if (ID == Reference.GUI_SCO_POS)
		{
			TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
			if (te == null || !(te instanceof TileEntityRegister))
			{
				return null;
			}
			
			return new GuiPOSStarter((TileEntityRegister)te);
		}
		else if (ID == Reference.GUI_SCO_STOREMODE)
		{
			return new GuiStoreMode();
		}
		else if (ID == Reference.GUI_TAGGING_STATION)
		{
			return new GuiTaggingStation(player.inventory, ((TileEntityTaggingStation)world.getTileEntity(new BlockPos(x,y,z))).getTaggingStationInventory(), new BlockPos(x, y, z));
		}
		else if (ID == Reference.GUI_TOS) return new GuiTOS();
		else if (ID == Reference.GUI_SOUND_EMITTER) return new GuiSoundEmitter(player.swingingHand, new BlockPos(x,y,z));
		else if (ID == Reference.GUI_ATM) return new GuiATMHome((TileEntityATM)world.getTileEntity(new BlockPos(x,y,z)));
		else if (ID == Reference.GUI_REGISTER_SECURITY_BOX_INVENTORY) return new GuiPOSSecurityBoxInventory((TileEntityRegister)world.getTileEntity(new BlockPos(x,y,z)), player.inventory);
		else if (ID == Reference.GUI_WALLET) return new GuiWallet(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]);
		else if (ID == Reference.GUI_TAGGING_STATION_UNTAG)
		{
			return new GuiTaggingStationUntag(player.inventory, new BlockPos(x, y, z));
		}
		else if (ID == Reference.GUI_SHOPPING_BASKET) return new GuiShoppingBasket(new ContainerShoppingBasket(player.inventory, player.getHeldItem(EnumHand.values()[x]), EnumHand.values()[x]), player.getHeldItem(EnumHand.values()[x]).getMetadata());
		else if (ID == Reference.GUI_COMPANY_NOTIFICATIONS) return new GuiCompanyNotifications();
		else return null;
	}
	
	public static void registerGUIs()
	{
		
	}
}
