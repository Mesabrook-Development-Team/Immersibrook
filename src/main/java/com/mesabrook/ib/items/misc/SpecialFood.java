package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

public class SpecialFood extends Item implements IHasModel
{
    private final float saturation;
    private final int amount;
    private final TextComponentTranslation sugarCrash = new TextComponentTranslation("im.sugarcrash");
    private final TextComponentTranslation sugarRush = new TextComponentTranslation("im.sugarrush");
    private final TextComponentTranslation peanutAllergy = new TextComponentTranslation("im.peanut");
    private final TextComponentTranslation cheeseEat = new TextComponentTranslation("im.truss.eat");
    private static Field setSaturationField = null;

    public SpecialFood(String name, int amount, float saturation, boolean canFeedDoggos)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setMaxDamage(8);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        this.saturation = saturation;
        this.amount = amount;

        ModItems.ITEMS.add(this);

        sugarCrash.getStyle().setBold(true);
        sugarCrash.getStyle().setColor(TextFormatting.RED);
        sugarRush.getStyle().setBold(true);
        sugarRush.getStyle().setColor(TextFormatting.GREEN);
        peanutAllergy.getStyle().setBold(true);
        peanutAllergy.getStyle().setColor(TextFormatting.RED);
        cheeseEat.getStyle().setColor(TextFormatting.YELLOW);
        
        if (setSaturationField == null)
        {
        	setSaturationField = ReflectionHelper.findField(FoodStats.class, "foodSaturationLevel", "field_75125_b");
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.EAT;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        EntityPlayer player = (EntityPlayer) entityLiving;
        if(!worldIn.isRemote)
        {
        	if (!player.isCreative())
        	{
                player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + this.amount);
                stack.damageItem(1, player);
                
                try {
					setSaturationField.setFloat(player.getFoodStats(), 20F);
				} catch (IllegalArgumentException e) {
					Main.logger.error("An error occurred setting food saturation", e);
				} catch (IllegalAccessException e) {
					Main.logger.error("An error occurred setting food saturation", e);
				}
        	}
        	
        }
        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(playerIn.getFoodStats().needFood())
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(this.getUnlocalizedName().contains("klussbar") || this.getUnlocalizedName().contains("nut") || this.getUnlocalizedName().contains("krisp"))
        {
        	tooltip.add(peanutAllergy.getFormattedText());
        }

        if(stack.getItem() == ModItems.FOOD_TRUSS)
        {
            tooltip.add(cheeseEat.getFormattedText());
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
