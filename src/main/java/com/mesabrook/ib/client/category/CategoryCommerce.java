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
public class CategoryCommerce extends AbstractCategory
{
    public CategoryCommerce()
    {
        super("im.filter.money", new ItemStack(ModItems.WALLET_UNI));
    }

    @Override
    public void init()
    {
        add(ModItems.WALLET_UNI);
        add(ModItems.WALLET_FEM);
        add(ModItems.WALLET_MAS);
        add(ModItems.DEBIT_CARD_RED);
        add(ModItems.DEBIT_CARD_GREEN);
        add(ModItems.DEBIT_CARD_BLUE);
    }
}
