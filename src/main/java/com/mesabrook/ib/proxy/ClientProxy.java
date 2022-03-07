package com.mesabrook.ib.proxy;

import com.mesabrook.ib.blocks.te.TileEntityPlaque;
import com.mesabrook.ib.blocks.te.TileEntityPlaqueRenderer;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding vestToggleKey;
	public static KeyBinding nvToggleKey;
	public static KeyBinding hammerSoundKey;
	public static KeyBinding policeHelmetKey;
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
		nvToggleKey = new KeyBinding("key.nvtoggle.toggle", Keyboard.KEY_SEMICOLON, "key.immersibrook.category");
		hammerSoundKey = new KeyBinding("key.hammer.toggle", Keyboard.KEY_R, "key.immersibrook.category");
		policeHelmetKey = new KeyBinding("key.police.toggle", Keyboard.KEY_P, "key.immersibrook.category");

		ClientRegistry.registerKeyBinding(vestToggleKey);
		ClientRegistry.registerKeyBinding(nvToggleKey);
		ClientRegistry.registerKeyBinding(hammerSoundKey);
		ClientRegistry.registerKeyBinding(policeHelmetKey);
	}
}
