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
        add(ModItems.CELL_TRANSMITTER);
        add(ModBlocks.CELL_ANTENNA);
        add(ModItems.SIM_CARD);
        add(ModBlocks.SOUND_EMITTER_WALL);
        add(ModBlocks.SOUND_EMITTER_BLOCK);
    }
}
