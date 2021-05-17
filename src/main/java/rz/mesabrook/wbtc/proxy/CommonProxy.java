package rz.mesabrook.wbtc.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta) {}
	public void preInit(FMLPreInitializationEvent e) {MinecraftForge.EVENT_BUS.register(this);}
	public void init(FMLInitializationEvent e) {}
}
