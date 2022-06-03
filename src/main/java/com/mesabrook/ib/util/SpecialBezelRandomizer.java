package com.mesabrook.ib.util;

import java.util.Random;

public class SpecialBezelRandomizer
{
    public static String bezel;

    public static void RandomBezel()
    {
        Random color = new Random();
        int colors;
        colors = color.nextInt(16);

        switch(colors)
        {
            case 0:
                bezel = "white";
                break;
            case 1:
                bezel = "orange";
                break;
            case 2:
                bezel = "magenta";
                break;
            case 3:
                bezel = "lblue";
                break;
            case 4:
                bezel = "yellow";
                break;
            case 5:
                bezel = "lime";
                break;
            case 6:
                bezel = "pink";
                break;
            case 7:
                bezel = "gray";
                break;
            case 8:
                bezel = "silver";
                break;
            case 9:
                bezel = "cyan";
                break;
            case 10:
                bezel = "blue";
                break;
            case 11:
                bezel = "purple";
                break;
            case 12:
                bezel = "brown";
                break;
            case 13:
                bezel = "green";
                break;
            case 14:
                bezel = "red";
                break;
            case 15:
                bezel = "black";
                break;
        }
    }
}
