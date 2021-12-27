package rz.mesabrook.wbtc.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ImmersiFood extends ItemFood implements IHasModel
{
    private final TextComponentTranslation burp = new TextComponentTranslation("im.sparkling");
    private final TextComponentTranslation milk = new TextComponentTranslation("im.truffle.milk");
    private final TextComponentTranslation white = new TextComponentTranslation("im.truffle.white");
    private final TextComponentTranslation caramel = new TextComponentTranslation("im.truffle.caramel");
    private final TextComponentTranslation pb = new TextComponentTranslation("im.truffle.pb");
    private final TextComponentTranslation strawberry = new TextComponentTranslation("im.truffle.sb");
    private final TextComponentTranslation bb = new TextComponentTranslation("im.truffle.bb");
    private final TextComponentTranslation grape = new TextComponentTranslation("im.truffle.grape");

    public ImmersiFood(String name, int stackSize, int damage, int amount, float saturation, boolean canFeedDoggos)
    {
        super(amount, saturation, canFeedDoggos);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(stackSize);
        setMaxDamage(damage);

        ModItems.ITEMS.add(this);
        burp.getStyle().setItalic(true);
        burp.getStyle().setColor(TextFormatting.RED);
        milk.getStyle().setColor(TextFormatting.DARK_RED);
        white.getStyle().setColor(TextFormatting.WHITE);
        caramel.getStyle().setColor(TextFormatting.GOLD);
        pb.getStyle().setColor(TextFormatting.GOLD);
        strawberry.getStyle().setColor(TextFormatting.RED);
        bb.getStyle().setColor(TextFormatting.BLUE);
        grape.getStyle().setColor(TextFormatting.DARK_PURPLE);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        if(this == ModItems.SPARKLING_PINK_LEMONADE || this == ModItems.ALMOND_WATER || this == ModItems.PINK_LEMONADE_DRINK)
        {
            return EnumAction.DRINK;
        }
        else
        {
            return EnumAction.EAT;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if(this.getUnlocalizedName().contains("lollipop") && !player.isCreative())
        {
            stack.damageItem(1, player);
            player.addItemStackToInventory(new ItemStack(ModItems.PAPER_STICK));
        }

        if(stack.getItem() == ModItems.SPARKLING_PINK_LEMONADE)
        {
            if(!player.isCreative())
            {
                stack.damageItem(1, player);
                player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE, 1));
                if(!worldIn.isRemote)
                {
                    PlaySoundPacket packet = new PlaySoundPacket();
                    packet.pos = player.getPosition();
                    packet.soundName = "burp";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
                    player.sendStatusMessage(new TextComponentString(burp.getFormattedText()), true);
                }
            }
        }

        if(this == ModItems.ALMOND_WATER)
        {
            if(!player.isCreative())
            {
                stack.damageItem(1, player);
                player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE, 1));

                if(!worldIn.isRemote)
                {
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1000, 3, false, true));
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1000, 3, false, true));
                    player.heal(10F);
                }
            }
        }
        else
        {
            if(!player.isCreative())
            {
                stack.damageItem(1, player);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(this == ModItems.MILK_TRUFFLE)
        {
            tooltip.add(milk.getFormattedText());
        }
        if(this == ModItems.WHITE_TRUFFLE)
        {
            tooltip.add(white.getFormattedText());
        }
        if(this == ModItems.TRUFFLE_CARAMEL)
        {
            tooltip.add(milk.getFormattedText());
            tooltip.add(caramel.getFormattedText());
        }
        if(this == ModItems.TRUFFLE_PB)
        {
            tooltip.add(milk.getFormattedText());
            tooltip.add(pb.getFormattedText());
        }
        if(this == ModItems.TRUFFLE_STRAWBERRY)
        {
            tooltip.add(milk.getFormattedText());
            tooltip.add(strawberry.getFormattedText());
        }
        if(this == ModItems.TRUFFLE_WHITE_BB)
        {
            tooltip.add(white.getFormattedText());
            tooltip.add(bb.getFormattedText());
        }
        if(this == ModItems.TRUFFLE_WHITE_GRAPE)
        {
            tooltip.add(white.getFormattedText());
            tooltip.add(grape.getFormattedText());
        }
        if(this == ModItems.PEPPERMINT_BARK)
        {
            tooltip.add(TextFormatting.GREEN + new TextComponentTranslation("im.tooltip.peppermintbark").getFormattedText());
        }
        if(this == ModItems.ALMOND_WATER)
        {
            tooltip.add(TextFormatting.AQUA + new TextComponentTranslation("im.almondwater").getFormattedText());
        }
    }
}