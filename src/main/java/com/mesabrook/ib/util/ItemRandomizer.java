package com.mesabrook.ib.util;

import com.pam.harvestcraft.item.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;

import java.util.Random;

public class ItemRandomizer
{
    public static ItemStack giftItem;
    public static ItemStack presentItem;

    public static void RandomizePresent()
    {
        Random rand = new Random();
        int choices;
        choices = rand.nextInt(3);

        switch(choices)
        {
            case 0:
                presentItem = new ItemStack(ModItems.PRESENT_TEST,1);
                break;
            case 1:
                presentItem = new ItemStack(ModItems.PRESENT_GREEN, 1);
                break;
            case 2:
                presentItem = new ItemStack(ModItems.PRESENT_BLUE, 1);
                break;
        }
    }

    public static void RandomizeItem()
    {
        Random rand = new Random();
        int choices;
        choices = rand.nextInt(30);

        switch(choices)
        {
            case 0:
                giftItem = new ItemStack(ModItems.CANDY_CANE, 1);
                break;
            case 1:
                giftItem = new ItemStack(Items.DIAMOND,1);
                break;
            case 2:
                giftItem = new ItemStack(ModItems.WHITE_CHOCOLATE,1);
                break;
            case 3:
                giftItem = new ItemStack(ModBlocks.MILK_CHOC_TRUFFLE_BOX, 1);
                break;
            case 4:
                giftItem = new ItemStack(ItemRegistry.chocolatebarItem,1);
                break;
            case 5:
                giftItem = new ItemStack(Items.COAL,1);
                break;
            case 6:
                giftItem = new ItemStack(ModItems.RAVEN_BAR,1);
                break;
            case 7:
                giftItem = new ItemStack(ModItems.SERPENT_BAR,1);
                break;
            case 8:
                giftItem = new ItemStack(ModItems.KRISP_BAR,1);
                break;
            case 9:
                giftItem = new ItemStack(ModItems.STRAWBERRY_BAR,1);
                break;
            case 10:
                giftItem = new ItemStack(ModBlocks.WHITE_CHOC_TRUFFLE_BOX, 1);
                break;
            case 11:
                giftItem = new ItemStack(ModItems.ALUMINUM_SWORD,1);
                break;
            case 12:
                giftItem = new ItemStack(ModItems.DOOTER,1);
                break;
            case 13:
                giftItem = new ItemStack(ModItems.POPPER_RED,1);
                break;
            case 14:
                giftItem = new ItemStack(ModItems.POPPER_GREEN,1);
                break;
            case 15:
                giftItem = new ItemStack(ModItems.POPPER_BLUE,1);
                break;
            case 16:
                giftItem = new ItemStack(ModBlocks.CHOC_PB_TRUFFLE_BOX,1);
                break;
            case 17:
                giftItem = new ItemStack(ModBlocks.CHOC_CARAMEL_TRUFFLE_BOX,1);
                break;
            case 18:
                giftItem = new ItemStack(ModBlocks.CHOC_STRAWBERRY_TRUFFLE_BOX,1);
                break;
            case 19:
                giftItem = new ItemStack(Items.DIAMOND_SWORD,1);
                break;
            case 20:
                giftItem = new ItemStack(ModBlocks.CHOC_BLUEBERRY_TRUFFLE_BOX,1);
                break;
            case 21:
                giftItem = new ItemStack(ModBlocks.CHOC_GRAPE_TRUFFLE_BOX,1);
                break;
            case 22:
                giftItem = new ItemStack(ModItems.SPARKLING_PINK_LEMONADE,1);
                break;
            case 23:
                giftItem = new ItemStack(ItemRegistry.peppermintItem,9);
                break;
            case 24:
                giftItem = new ItemStack(Items.SNOWBALL,64);
                break;
            case 25:
                giftItem = new ItemStack(ModBlocks.CUBE_APPLE_PIE,1);
                break;
            case 26:
                giftItem = new ItemStack(ModBlocks.CUBE_CHEESE,1);
                break;
            case 27:
                giftItem = new ItemStack(ModItems.CANDY_CHOCOLATE,1);
                break;
            case 28:
                giftItem = new ItemStack(ModItems.PHONE_RED,1);
                break;
            case 29:
                giftItem = new ItemStack(ModItems.PHONE_GREEN,1);
                break;
        }
    }
}
