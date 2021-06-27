package rz.mesabrook.wbtc.proxy;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rz.mesabrook.wbtc.blocks.te.TileEntityPlaque;
import rz.mesabrook.wbtc.blocks.te.TileEntityPlaqueRenderer;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding vestToggleKey;
	public void registerItemRenderer(Item item, int meta)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
		ClientSideHandlers.loadCreativeGUI();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlaque.class, new TileEntityPlaqueRenderer());
		
		vestToggleKey = new KeyBinding("key.vestToggle.toggle", Keyboard.KEY_V, "key.immersibrook.category");
		ClientRegistry.registerKeyBinding(vestToggleKey);
	}
}
