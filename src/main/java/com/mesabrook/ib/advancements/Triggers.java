package com.mesabrook.ib.advancements;

import com.google.common.collect.Lists;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class Triggers
{
    private static final List<BasicTrigger> TRIGGERS = Lists.newArrayList();

    // Misc
    public static final BasicTrigger WELCOME = register("imb_welcome");
    public static final BasicTrigger OWO = register("imb_owo_click");
    public static final BasicTrigger FIRST_AID = register("imb_first_aid");
    public static final BasicTrigger FIRST_AID_OTHER_PLAYER = register("imb_heal_other");
    public static final BasicTrigger FIRST_AID_HOSTILES = register("imb_heal_monsters");
    public static final BasicTrigger BONKED = register("imb_bonked");
    public static final BasicTrigger MESARANG = register("imb_mesarang");
    public static final BasicTrigger DOOT = register("imb_doot");
    public static final BasicTrigger STAMPBOOK = register("imb_stampbook");

    // Plastic
    public static final BasicTrigger MAKE_PLASTIC_INGOTS = register("imb_ingot");
    public static final BasicTrigger MAKE_PLASTIC_CUBE = register("imb_pcube");
    public static final BasicTrigger WEAR_VEST = register("imb_wear_vest");
    public static final BasicTrigger WEAR_NV = register("imb_wear_nv");

    // Food
    public static final BasicTrigger MAKE_FOODCUBE = register("imb_make_foodcube");
    public static final BasicTrigger PUFFERFISH = register("imb_pufferfish");
    public static final BasicTrigger CURSED_PIE = register("imb_cursed_pie");
    public static final BasicTrigger SHEPARD = register("imb_commander");
    public static final BasicTrigger CANDY = register("imb_candyman");
    public static final BasicTrigger CHOCOLATE = register("imb_chocolate");

    // Bin
    public static final BasicTrigger BIN_PLACE = register("imb_place_bin");
    public static final BasicTrigger BIN_USE = register("imb_bin_use");

    // Tech
    public static final BasicTrigger PHONE_USE = register("imb_phone_use");

    private static BasicTrigger register(String name)
    {
        BasicTrigger trigger = new BasicTrigger(new ResourceLocation(Reference.MODID, name));
        TRIGGERS.add(trigger);
        return trigger;
    }

    public static void trigger(IModTrigger trigger, EntityPlayer player)
    {
        if(player instanceof EntityPlayerMP)
        {
            trigger.trigger((EntityPlayerMP) player);
        }
    }

    @SuppressWarnings("deprecation")
    public static void init()
    {
        Method method;
        try
        {
            method = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
            method.setAccessible(true);
            for(BasicTrigger TRIGGER : TRIGGERS)
            {
                method.invoke(null, TRIGGER);
            }
        }
        catch(SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            Main.logger.error(e);
        }
    }
}
