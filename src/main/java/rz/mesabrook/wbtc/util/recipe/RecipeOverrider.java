package rz.mesabrook.wbtc.util.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.util.Reference;

@EventBusSubscriber
public class RecipeOverrider
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        try
        {
            IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
            Main.logger.info("[" + Reference.MODNAME + "] Disabling Recipes.");

            // Resource Locations for JSON-based recipes goes here.
            ResourceLocation vanillaPaper = new ResourceLocation("minecraft:paper");

            // Add remove calls here
            modRegistry.remove(vanillaPaper);
        }
        catch(Exception ex)
        {
            Main.logger.error("[" + Reference.MODNAME + "] ERROR IN RECIPE REMOVER!!!");
            Main.logger.error(ex);
        }
    }
}
