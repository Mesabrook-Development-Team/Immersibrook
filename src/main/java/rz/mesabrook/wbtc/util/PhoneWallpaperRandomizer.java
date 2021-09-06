package rz.mesabrook.wbtc.util;

import java.util.Random;

public class PhoneWallpaperRandomizer
{
    public static String wallpaper;
    public static void ShuffleWallpaper()
    {
        Random chooser = new Random();
        int snds;
        snds = chooser.nextInt(13);

        switch(snds)
        {
            case 0:
                wallpaper = "gui_phone_bg_1.png";
                break;
            case 1:
                wallpaper = "gui_phone_bg_2.png";
                break;
            case 2:
                wallpaper = "gui_phone_bg_3.png";
                break;
            case 3:
                wallpaper = "gui_phone_bg_4.png";
                break;
            case 4:
                wallpaper = "gui_phone_bg_5.png";
                break;
            case 5:
                wallpaper = "gui_phone_bg_6.png";
                break;
            case 6:
                wallpaper = "gui_phone_bg_7.png";
                break;
            case 7:
                wallpaper = "gui_phone_bg_8.png";
                break;
            case 8:
                wallpaper = "gui_phone_bg_9.png";
                break;
            case 9:
                wallpaper = "gui_phone_bg_10.png";
                break;
            case 10:
                wallpaper = "gui_phone_bg_11.png";
                break;
            case 11:
                wallpaper = "gui_phone_bg_12.png";
                break;
            case 12:
                wallpaper = "gui_phone_bg_13.png";
                break;
        }
    }
}
