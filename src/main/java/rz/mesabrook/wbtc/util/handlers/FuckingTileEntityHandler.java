package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rz.mesabrook.wbtc.blocks.te.TileEntityFoodBox;
import rz.mesabrook.wbtc.blocks.te.TileEntityPlaque;
import rz.mesabrook.wbtc.blocks.te.TileEntityTrashBin;
import rz.mesabrook.wbtc.util.Reference;

public class FuckingTileEntityHandler 
{
	public static void registerTileEntites()
	{
		GameRegistry.registerTileEntity(TileEntityTrashBin.class, new ResourceLocation(Reference.MODID + ":trash_bin"));
		GameRegistry.registerTileEntity(TileEntityPlaque.class, new ResourceLocation(Reference.MODID + ":plaque"));
		GameRegistry.registerTileEntity(TileEntityFoodBox.class, new ResourceLocation(Reference.MODID + ":foodbox"));
	}
}
