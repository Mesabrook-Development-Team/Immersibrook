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
        add(ModItems.RADIO);
        add(ModItems.PHONE_WHITE);
        add(ModItems.PHONE_ORANGE);
        add(ModItems.PHONE_MAGENTA);
        add(ModItems.PHONE_LBLUE);
        add(ModItems.PHONE_YELLOW);
        add(ModItems.PHONE_LIME);
        add(ModItems.PHONE_PINK);
        add(ModItems.PHONE_GRAY);
        add(ModItems.PHONE_SILVER);
        add(ModItems.PHONE_CYAN);
        add(ModItems.PHONE_BLUE);
        add(ModItems.PHONE_BROWN);
        add(ModItems.PHONE_GREEN);
        add(ModItems.PHONE_PURPLE);
        add(ModItems.PHONE_RED);
        add(ModItems.PHONE_BLACK);
        add(ModItems.PHONE_SPECIAL);
        add(ModItems.PHONE_MESABROOK);
        add(ModItems.PHONE_RC);
        add(ModItems.PHONE_ZOE);
        add(ModItems.PHONE_FR);
        add(ModItems.CELL_TRANSMITTER);
        add(ModBlocks.CELL_ANTENNA);
        add(ModItems.SIM_CARD);
    }
}
