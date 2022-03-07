package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.blocks.te.TileEntityFoodBox;
import com.mesabrook.ib.blocks.te.TileEntityPlaque;
import com.mesabrook.ib.blocks.te.TileEntityTrashBin;
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
	}
}
