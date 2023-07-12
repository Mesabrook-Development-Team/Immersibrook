package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.blocks.te.*;
import com.mesabrook.ib.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityTrashBin.class, new ResourceLocation(Reference.MODID + ":trash_bin"));
		GameRegistry.registerTileEntity(TileEntityPlaque.class, new ResourceLocation(Reference.MODID + ":plaque"));
		GameRegistry.registerTileEntity(TileEntityFoodBox.class, new ResourceLocation(Reference.MODID + ":foodbox"));
		GameRegistry.registerTileEntity(TileEntityWallSign.class, new ResourceLocation(Reference.MODID + ":wallsign"));
		GameRegistry.registerTileEntity(TileEntityRegister.class, new ResourceLocation(Reference.MODID + ":sco_pos"));
	}
}
