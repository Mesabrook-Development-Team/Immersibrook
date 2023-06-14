package com.mesabrook.ib.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
public class MottoRandomizer
{
    public static String MOTTO;

    public List<String> loadMottos()
    {
        List<String> mottos = new ArrayList<>();

        try
        {
            InputStream inputStream = getClass().getResourceAsStream("/assets/wbtc/mottos.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while((line = reader.readLine()) != null)
            {
                mottos.add(line);
            }

            reader.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return mottos;
    }

    public String getRandomMotto(List<String> mottos)
    {
        Random random = new Random();
        int index = random.nextInt(mottos.size());
        return mottos.get(index);
    }

    public static void RandomMotto()
    {
        Random rand = new Random();
        int choices;
        choices = rand.nextInt(10);

        switch(choices)
        {
            case 0:
                MOTTO = "Immersion go brr!";
                break;
            case 1:
                MOTTO = "Slava Ukraini!";
                break;
            case 2:
                MOTTO = "F--k the Supreme Court!";
                break;
            case 3:
                MOTTO = "An excellent source of Omega-3 fatty acids.";
                break;
            case 4:
                MOTTO = "It says gullible on the ceiling.";
                break;
            case 5:
                MOTTO = "Oh, it doe--you stole my lungs.";
                break;
            case 6:
                MOTTO = "2+2 = Goldfish";
                break;
            case 7:
                MOTTO = "I'm Immersibrook, and I'm a crashaholic...";
                break;
            case 8:
                MOTTO = "Minedroid is not a fork of Android(TM)";
                break;
            case 9:
                MOTTO = "View live Mesabrook storm data on our dynmap!";
                break;
        }
    }
}
