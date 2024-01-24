package com.mesabrook.ib.client.category;

import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import net.minecraft.item.ItemStack;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 *
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */

public class CategoryTech extends AbstractCategory
{
    public CategoryTech()
    {
        super("im.filter.tech", new ItemStack(ModItems.PHONE_SPECIAL));
    }

    @Override
    public void init()
    {
        add(ModBlocks.PHONE_CHARGING_PAD);
        add(ModItems.RADIO);
        add(ModItems.PHONE_SPECIAL);
        add(ModItems.PHONE_MESABROOK);
        add(ModItems.PHONE_RC);
        add(ModItems.PHONE_ZOE);
        add(ModItems.PHONE_FR);
        add(ModItems.BOX_WHITE);
        add(ModItems.BOX_ORANGE);
        add(ModItems.BOX_MAGENTA);
        add(ModItems.BOX_LBLUE);
        add(ModItems.BOX_YELLOW);
        add(ModItems.BOX_LIME);
        add(ModItems.BOX_PINK);
        add(ModItems.BOX_GRAY);
        add(ModItems.BOX_SILVER);
        add(ModItems.BOX_CYAN);
        add(ModItems.BOX_PURPLE);
        add(ModItems.BOX_BLUE);
        add(ModItems.BOX_BROWN);
        add(ModItems.BOX_GREEN);
        add(ModItems.BOX_RED);
        add(ModItems.BOX_BLACK);
        add(ModItems.OT_CHARGER);

        add(ModItems.LAPTOP_BOX_BLACK);
        add(ModItems.LAPTOP_BOX_WHITE);
        add(ModItems.LAPTOP_BOX_ORANGE);
        add(ModItems.LAPTOP_BOX_MAGENTA);
        add(ModItems.LAPTOP_BOX_LBLUE);
        add(ModItems.LAPTOP_BOX_YELLOW);
        add(ModItems.LAPTOP_BOX_LIME);
        add(ModItems.LAPTOP_BOX_PINK);
        add(ModItems.LAPTOP_BOX_GRAY);
        add(ModItems.LAPTOP_BOX_SILVER);
        add(ModItems.LAPTOP_BOX_CYAN);
        add(ModItems.LAPTOP_BOX_PURPLE);
        add(ModItems.LAPTOP_BOX_BROWN);
        add(ModItems.LAPTOP_BOX_GREEN);
        add(ModItems.LAPTOP_BOX_RED);
        add(ModItems.LAPTOP_BOX_BLUE);

        add(ModItems.ROUTER_BOX_WHITE);
        add(ModItems.ROUTER_BOX_ORANGE);
        add(ModItems.ROUTER_BOX_MAGENTA);
        add(ModItems.ROUTER_BOX_LBLUE);
        add(ModItems.ROUTER_BOX_YELLOW);
        add(ModItems.ROUTER_BOX_LIME);
        add(ModItems.ROUTER_BOX_PINK);
        add(ModItems.ROUTER_BOX_GRAY);
        add(ModItems.ROUTER_BOX_SILVER);
        add(ModItems.ROUTER_BOX_CYAN);
        add(ModItems.ROUTER_BOX_BLUE);
        add(ModItems.ROUTER_BOX_PURPLE);
        add(ModItems.ROUTER_BOX_BROWN);
        add(ModItems.ROUTER_BOX_GREEN);
        add(ModItems.ROUTER_BOX_RED);
        add(ModItems.ROUTER_BOX_BLACK);

        add(ModItems.PRINTER_BOX_WHITE);
        add(ModItems.PRINTER_BOX_ORANGE);
        add(ModItems.PRINTER_BOX_MAGENTA);
        add(ModItems.PRINTER_BOX_LBLUE);
        add(ModItems.PRINTER_BOX_YELLOW);
        add(ModItems.PRINTER_BOX_LIME);
        add(ModItems.PRINTER_BOX_PINK);
        add(ModItems.PRINTER_BOX_GRAY);
        add(ModItems.PRINTER_BOX_SILVER);
        add(ModItems.PRINTER_BOX_CYAN);
        add(ModItems.PRINTER_BOX_BLUE);
        add(ModItems.PRINTER_BOX_PURPLE);
        add(ModItems.PRINTER_BOX_BROWN);
        add(ModItems.PRINTER_BOX_GREEN);
        add(ModItems.PRINTER_BOX_RED);
        add(ModItems.PRINTER_BOX_BLACK);

        add(ModItems.CELL_TRANSMITTER);
        add(ModBlocks.CELL_ANTENNA);
        add(ModItems.SIM_CARD);
        add(ModBlocks.SOUND_EMITTER_WALL);
        add(ModBlocks.SOUND_EMITTER_BLOCK);

        add(ModBlocks.DISH_BELL);
        add(ModBlocks.DISH_MSAC);
        add(ModBlocks.DISH_BLANK);

        add(ModBlocks.SMOKER);

        add(ModBlocks.FLUID_METER);
        add(ModItems.SMARTPHONE_BATTERY);
        add(ModItems.TECH_BLOCK);
        add(ModBlocks.ATS);
    }
}
