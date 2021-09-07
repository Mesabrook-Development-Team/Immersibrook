package rz.mesabrook.wbtc.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
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
import javax.xml.soap.Text;
import java.util.List;

public class ImmersiFood extends ItemFood implements IHasModel
{
    private final TextComponentTranslation burp = new TextComponentTranslation("im.sparkling");
    private final TextComponentTranslation milk = new TextComponentTranslation("im.truffle.milk");
    private final TextComponentTranslation white = new TextComponentTranslation("im.truffle.white");

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
        if(itemStack.getItem() == ModItems.SPARKLING_PINK_LEMONADE)
        {
            return EnumAction.DRINK;
        }
        else
        {
            return EnumAction.EAT;
        }
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if(this.getUnlocalizedName().contains("lollipop") && !player.isCreative())
        {
            stack.damageItem(1, player);
            player.addItemStackToInventory(new ItemStack(ModItems.PAPER_STICK));
        }

        if(stack.getItem() == ModItems.SPARKLING_PINK_LEMONADE && !player.isCreative())
        {
            stack.damageItem(1, player);
            player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
        }

        if(stack.getItem() == ModItems.SPARKLING_PINK_LEMONADE)
        {
            if(!player.isCreative())
            {
                stack.damageItem(1, player);
                player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
                if(!worldIn.isRemote)
                {
                    PlaySoundPacket packet = new PlaySoundPacket();
                    packet.pos = player.getPosition();
                    packet.soundName = "burp";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
                    player.sendMessage(new TextComponentString(burp.getFormattedText()));
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(stack.getItem() == ModItems.MILK_TRUFFLE)
        {
            tooltip.add(milk.getFormattedText());
        }
        if(stack.getItem() == ModItems.WHITE_TRUFFLE)
        {
            tooltip.add(white.getFormattedText());
        }
    }
}
