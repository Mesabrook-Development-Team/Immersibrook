package rz.mesabrook.wbtc.util;

import java.util.Random;

public class PhoneWallpaperRandomizer
{
    public static String wallpaper;
    public static void ShuffleWallpaper()
    {
        Random chooser = new Random();
        int snds;
        snds = chooser.nextInt(4);

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
        }
    }
}
