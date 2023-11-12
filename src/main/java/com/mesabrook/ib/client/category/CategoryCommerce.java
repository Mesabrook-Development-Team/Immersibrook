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
        add(ModItems.ONE_DOLLAR);
        add(ModItems.FIVE_DOLLARS);
        add(ModItems.TEN_DOLLARS);
        add(ModItems.TWENTY_DOLLARS);
        add(ModItems.FIFTY_DOLLARS);
        add(ModItems.ONE_HUNDRED_DOLLARS);
        add(ModItems.PENNY);
        add(ModItems.FIVE_CENTS);
        add(ModItems.TEN_CENTS);
        add(ModItems.TWENTY_FIVE_CENTS);
        add(ModItems.DOLLAR_COIN);

        // SCO
        add(ModBlocks.SCO_POS);
        add(ModBlocks.SCO_SCANNER);
        add(ModBlocks.SCO_BAGGING);

        // Shelving
        add(ModBlocks.SHELF_ONE_LEVEL_TWO_PEGHOOKS);
        add(ModBlocks.SHELF_FOUR_PEGHOOKS);
        add(ModBlocks.SHELF_TWO_LEVELS_NO_PEGHOOKS);
        add(ModBlocks.RETAIL_FREEZER_UPRIGHT);
        add(ModBlocks.RETAIL_FREEZER_DEEP);
        add(ModBlocks.RETAIL_DRINK_COOLER);

        // Security Station
        add(ModBlocks.SECURITY_STATION);
        add(ModBlocks.SIM_STATION);
        add(ModBlocks.ITEM_STAND);
        add(ModBlocks.ITEM_STAND_ORANGE);
        add(ModBlocks.ITEM_STAND_MAGENTA);
        add(ModBlocks.ITEM_STAND_LBLUE);
        add(ModBlocks.ITEM_STAND_YELLOW);
        add(ModBlocks.ITEM_STAND_LIME);
        add(ModBlocks.ITEM_STAND_PINK);
        add(ModBlocks.ITEM_STAND_GRAY);
        add(ModBlocks.ITEM_STAND_SILVER);
        add(ModBlocks.ITEM_STAND_CYAN);
        add(ModBlocks.ITEM_STAND_PURPLE);
        add(ModBlocks.ITEM_STAND_BLUE);
        add(ModBlocks.ITEM_STAND_BROWN);
        add(ModBlocks.ITEM_STAND_GREEN);
        add(ModBlocks.ITEM_STAND_RED);
        add(ModBlocks.ITEM_STAND_BLACK);

        // ATM
        add(ModBlocks.ATM);
    }
}
