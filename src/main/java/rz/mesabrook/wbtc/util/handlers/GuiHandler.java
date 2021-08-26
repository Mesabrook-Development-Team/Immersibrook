package rz.mesabrook.wbtc.util.handlers;

import java.util.Optional;

import com.google.common.collect.Comparators;

import it.unimi.dsi.fastutil.ints.IntComparators;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import rz.mesabrook.wbtc.blocks.container.ContainerTrashBin;
import rz.mesabrook.wbtc.blocks.gui.GuiFoodBox;
import rz.mesabrook.wbtc.blocks.gui.GuiPlaque;
import rz.mesabrook.wbtc.blocks.gui.GuiTrashBin;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiEmptyPhone;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiPhoneActivate;
import rz.mesabrook.wbtc.blocks.te.TileEntityTrashBin;
import rz.mesabrook.wbtc.items.misc.ItemPhone;
import rz.mesabrook.wbtc.net.telecom.PhoneQueryPacket;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.GUI_TRASHBIN) return new ContainerTrashBin(player.inventory, (TileEntityTrashBin)world.getTileEntity(new BlockPos(x,y,z)), player);	
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.GUI_TRASHBIN) return new GuiTrashBin(player.inventory, (TileEntityTrashBin)world.getTileEntity(new BlockPos(x,y,z)), player);
		else if (ID == Reference.GUI_PLAQUE) return new GuiPlaque(EnumHand.values()[x]);
		else if (ID == Reference.GUI_FOODBOX) return new GuiFoodBox(EnumHand.values()[x]);
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
				return new GuiPhoneActivate(hand, stack);
			}
			else
			{
				Optional<Integer> maxID = TelecomClientHandlers.phoneQueryResponseHandlers.keySet().stream().max(Integer::compare);
				int nextID = 1;
				if (maxID.isPresent())
				{
					nextID = maxID.get() + 1;
				}
				
				TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, TelecomClientHandlers::onPhoneQueryResponsePacket);
				PhoneQueryPacket query = new PhoneQueryPacket();
				query.forNumber = phoneNumber;
				query.clientHandlerCode = nextID;
				PacketHandler.INSTANCE.sendToServer(query);
				
				return new GuiEmptyPhone(stack, hand);
			}
		}
		else return null;
	}
	
	public static void registerGUIs()
	{
		
	}
}
