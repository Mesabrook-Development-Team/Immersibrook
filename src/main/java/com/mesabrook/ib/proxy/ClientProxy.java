package com.mesabrook.ib.proxy;

import org.lwjgl.input.Keyboard;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.ShelvingTileEntityRenderer;
import com.mesabrook.ib.blocks.te.TileEntityPlaque;
import com.mesabrook.ib.blocks.te.TileEntityPlaqueRenderer;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegisterRenderer;
import com.mesabrook.ib.blocks.te.TileEntityWallSign;
import com.mesabrook.ib.blocks.te.TileEntityWallSignRenderer;
import com.mesabrook.ib.items.commerce.models.ItemSecurityBoxModel;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ClientProxy extends CommonProxy
{
	public static KeyBinding vestToggleKey;
	public static KeyBinding nvToggleKey;
	public static KeyBinding hammerSoundKey;
	public static KeyBinding policeHelmetKey;
	public void registerItemRenderer(Item item, int meta)
	{
		registerItemRenderer(item, meta, "inventory");
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String variantName) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variantName));
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWallSign.class, new TileEntityWallSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRegister.class, new TileEntityRegisterRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(ShelvingTileEntity.class, new ShelvingTileEntityRenderer());

		vestToggleKey = new KeyBinding("key.vestToggle.toggle", Keyboard.KEY_V, "key.immersibrook.category");
		nvToggleKey = new KeyBinding("key.nvtoggle.toggle", Keyboard.KEY_SEMICOLON, "key.immersibrook.category");
		hammerSoundKey = new KeyBinding("key.hammer.toggle", Keyboard.KEY_R, "key.immersibrook.category");
		policeHelmetKey = new KeyBinding("key.police.toggle", Keyboard.KEY_P, "key.immersibrook.category");

		ClientRegistry.registerKeyBinding(vestToggleKey);
		ClientRegistry.registerKeyBinding(nvToggleKey);
		ClientRegistry.registerKeyBinding(hammerSoundKey);
		ClientRegistry.registerKeyBinding(policeHelmetKey);

		Main.logger.info("Starting download of statue player skins");

		// Broken, needs to be fixed @CSX8600
		// SkinDownloader.downloadSkin(Reference.RZ_UUID);
	}
	
	final String[] shelvingModels = new String[]
	{
		"shelf_four_peghooks",
		"shelf_one_level_two_peghooks",
		"shelf_two_levels_no_peghooks"
	};
	
	@SubscribeEvent
	public static void onModelBakeEvent(ModelBakeEvent e)
	{
		ModelResourceLocation securityBoxRL = new ModelResourceLocation("wbtc:security_box", "inventory");
		Object object = e.getModelRegistry().getObject(securityBoxRL);
		if (object instanceof IBakedModel)
		{
			IBakedModel existingModel = (IBakedModel)object;
			ItemSecurityBoxModel customModel = new ItemSecurityBoxModel(existingModel);
			e.getModelRegistry().putObject(securityBoxRL, customModel);
		}
		
		
	}
}
