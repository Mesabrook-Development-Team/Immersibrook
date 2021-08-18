package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import rz.mesabrook.wbtc.blocks.container.ContainerTrashBin;
import rz.mesabrook.wbtc.blocks.gui.GuiFoodBox;
import rz.mesabrook.wbtc.blocks.gui.GuiPlaque;
import rz.mesabrook.wbtc.blocks.gui.GuiTrashBin;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiHome;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiPhoneActivate;
import rz.mesabrook.wbtc.blocks.te.TileEntityTrashBin;
import rz.mesabrook.wbtc.util.Reference;

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
		else if (ID == Reference.GUI_PHONE_ACTIVATE || ID == Reference.GUI_PHONE) 
		{
			EnumHand hand = EnumHand.values()[x]; 
			ItemStack stack = player.getHeldItem(hand); 
			if (ID == Reference.GUI_PHONE_ACTIVATE)
			{
				return new GuiPhoneActivate(hand, stack);
			}
			else
			{
				return new GuiHome(stack, hand);
			}
		}
		else return null;
	}
	
	public static void registerGUIs()
	{
		
	}
}
