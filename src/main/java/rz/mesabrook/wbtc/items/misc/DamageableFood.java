package rz.mesabrook.wbtc.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

import javax.annotation.Nullable;
import javax.xml.soap.Text;
import java.util.List;
import java.util.Random;

public class DamageableFood extends Item implements IHasModel
{
    private final float saturation;
    private final TextComponentTranslation sugarCrash = new TextComponentTranslation("im.sugarcrash");
    private final TextComponentTranslation sugarRush = new TextComponentTranslation("im.sugarrush");

    public DamageableFood(String name, int stackSize, int amount, float saturation, boolean canFeedDoggos)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setMaxDamage(8);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(stackSize);
        this.saturation = saturation;

        ModItems.ITEMS.add(this);

        sugarCrash.getStyle().setBold(true);
        sugarCrash.getStyle().setColor(TextFormatting.RED);
        sugarRush.getStyle().setBold(true);
        sugarRush.getStyle().setColor(TextFormatting.GREEN);
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
        if(!player.isCreative() && worldIn.isRemote)
        {
            player.getFoodStats().setFoodLevel(20);
            player.getFoodStats().setFoodSaturationLevel(this.saturation);
            stack.damageItem(1, player);
        }
        if(!worldIn.isRemote)
        {
            if(this.getUnlocalizedName().contains("serpent"))
            {
                if(!worldIn.isRemote)
                {
                    Random chooser = new Random();
                    int effect;
                    effect = chooser.nextInt(4);

                    switch(effect)
                    {
                        case 0:
                            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 500, 20, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 500, 2, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 500, 4, true, false));
                            player.sendMessage(sugarRush);
                            break;
                        case 1:
                            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 500, 20, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 500, 2, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 500, 4, true, false));
                            player.sendMessage(sugarRush);
                            break;
                        case 2:
                            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 500, 20, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 500, 2, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 500, 4, true, false));
                            player.sendMessage(sugarRush);
                            break;
                        case 3:
                            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 500, 1, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 500, 25, true, false));
                            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 500, 55, true, false));
                            player.sendMessage(sugarCrash);
                            break;
                    }
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
        if(this.getUnlocalizedName().contains("serpent"))
        {
            tooltip.add(TextFormatting.RED + new TextComponentTranslation("im.sg").getFormattedText());
            tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.warning").getFormattedText());
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
