package rz.mesabrook.wbtc.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void init(FMLInitializationEvent e) {}
}
