package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.blocks.te.*;
import com.mesabrook.ib.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FuckingTileEntityHandler
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityTrashBin.class, new ResourceLocation(Reference.MODID + ":trash_bin"));
		GameRegistry.registerTileEntity(TileEntityPlaque.class, new ResourceLocation(Reference.MODID + ":plaque"));
		GameRegistry.registerTileEntity(TileEntityFoodBox.class, new ResourceLocation(Reference.MODID + ":foodbox"));
		GameRegistry.registerTileEntity(TileEntityWallSign.class, new ResourceLocation(Reference.MODID + ":wallsign"));
		GameRegistry.registerTileEntity(TileEntityRegister.class, new ResourceLocation(Reference.MODID + ":sco_pos"));
		GameRegistry.registerTileEntity(ShelvingTileEntity.class, new ResourceLocation(Reference.MODID + ":shelving"));
		GameRegistry.registerTileEntity(TileEntityTaggingStation.class, new ResourceLocation(Reference.MODID + ":taggingstation"));
		GameRegistry.registerTileEntity(TileEntitySoundEmitter.class, new ResourceLocation(Reference.MODID + ":soundblock"));
		GameRegistry.registerTileEntity(TileEntityPhoneStand.class, new ResourceLocation(Reference.MODID + ":itemstand"));
		GameRegistry.registerTileEntity(TileEntityWirelessChargingPad.class, new ResourceLocation(Reference.MODID + ":charger"));
		GameRegistry.registerTileEntity(TileEntitySmoker.class, new ResourceLocation(Reference.MODID + ":smoker_block"));
		GameRegistry.registerTileEntity(TileEntityATM.class, new ResourceLocation(Reference.MODID + ":atm"));
		GameRegistry.registerTileEntity(TileEntityFluidMeter.class, new ResourceLocation(Reference.MODID + ":fluidmeter"));
		GameRegistry.registerTileEntity(TileEntityShoppingBasketHolder.class, new ResourceLocation(Reference.MODID + ":shoppingbasketholder"));
	}
}
