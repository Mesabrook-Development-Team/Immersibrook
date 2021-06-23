package rz.mesabrook.wbtc.advancements;

import com.google.common.collect.Lists;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.util.Reference;
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

    public static final BasicTrigger WELCOME = register("imb_welcome");

    // Plastic
    public static final BasicTrigger GET_PLASTIC = register("imb_plastic_get");
    public static final BasicTrigger WEAR_VEST = register("imb_wear_vest");
    public static final BasicTrigger WEAR_NV = register("imb_wear_nv");

    // Food
    public static final BasicTrigger MAKE_FOODCUBE = register("imb_make_foodcube");
    public static final BasicTrigger PUFFERFISH = register("imb_pufferfish");


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
