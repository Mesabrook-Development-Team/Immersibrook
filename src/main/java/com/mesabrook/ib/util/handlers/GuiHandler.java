package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.blocks.container.ContainerStampBook;
import com.mesabrook.ib.blocks.container.ContainerTrashBin;
import com.mesabrook.ib.blocks.gui.GuiAboutImmersibrook;
import com.mesabrook.ib.blocks.gui.GuiFoodBox;
import com.mesabrook.ib.blocks.gui.GuiPlaque;
import com.mesabrook.ib.blocks.gui.GuiStampBook;
import com.mesabrook.ib.blocks.gui.GuiTOS;
import com.mesabrook.ib.blocks.gui.GuiTrashBin;
import com.mesabrook.ib.blocks.gui.GuiWallSign;
import com.mesabrook.ib.blocks.gui.telecom.GuiEmptyPhone;
import com.mesabrook.ib.blocks.gui.telecom.GuiMobileAlert;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneActivate;
import com.mesabrook.ib.blocks.te.TileEntityTrashBin;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
			
			if (phoneNumber == null)
			{
				return new GuiPhoneActivate(stack, hand);
			}
			else if (GuiMobileAlert.labelsByNumber.containsKey(stackNBTData.getPhoneNumber()) || GuiMobileAlert.textByNumber.containsKey(stackNBTData.getPhoneNumber()))
			{
				return new GuiMobileAlert(stack, hand);
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
		else if (ID == Reference.GUI_WALLSIGN) return new GuiWallSign(EnumHand.values()[x]);
		else if (ID == Reference.GUI_TOS) return new GuiTOS();
		else return null;
	}
	
	public static void registerGUIs()
	{
		
	}
}
