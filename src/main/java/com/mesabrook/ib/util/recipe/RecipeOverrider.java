package com.mesabrook.ib.util.recipe;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@EventBusSubscriber
public class RecipeOverrider
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        try
        {
            IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
            Main.logger.info("[" + Reference.MODNAME + "] JSON Recipe Remover, ACTIVATE!");

            // Resource Locations for JSON-based recipes goes here.
            ResourceLocation vanillaPaper = new ResourceLocation("minecraft:paper");

            // Resource Locations for other mods.
            if(Main.THERCMOD)
            {
                if(ModConfig.overrideRCModPlastic)
                {
                    ResourceLocation plastic = new ResourceLocation("thercmod:plastic");
                    modRegistry.remove(plastic);
                    Main.logger.info("[" + Reference.MODNAME + "] wbtc.cfg/overrideRCModPlastic = true");
                    Main.logger.info("[" + Reference.MODNAME + "] The RC Mod's plastic recipe has been disabled. Use ours instead.");
                }
                else
                {
                    Main.logger.info("[" + Reference.MODNAME + "] wbtc.cfg/overrideRCModPlastic = false");
                    Main.logger.info("[" + Reference.MODNAME + "] The RC Mod's plastic recipe has NOT been disabled.");
                }
            }

            if(ModConfig.makePaperProductionMoreRealistic)
            {
                modRegistry.remove(vanillaPaper);
                Main.logger.info("[" + Reference.MODNAME + "] wbtc.cfg/makePaperProductionMoreRealistic = true");
                Main.logger.info("[" + Reference.MODNAME + "] Paper Recipe has been disabled.");
            }
            else
            {
                Main.logger.info("[" + Reference.MODNAME + "] wbtc.cfg/makePaperProductionMoreRealistic = false");
                Main.logger.info("[" + Reference.MODNAME + "] Config option to disable Paper recipe is set to false. Recipe still active.");
            }
        }
        catch(Exception ex)
        {
            Main.logger.error("[" + Reference.MODNAME + "] ERROR IN RECIPE REMOVER!!!");
            Main.logger.error(ex);
        }
    }
}
