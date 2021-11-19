package rz.mesabrook.wbtc.items.misc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class ItemStamp extends Item implements IHasModel
{
    public ItemStamp(String name)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(64);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("im.tooltip.stamp.series").getFormattedText());
            if(stack.getItem() == ModItems.IR_STAMP_1 || stack.getItem() == ModItems.IR_STAMP_2 || stack.getItem() == ModItems.IR_STAMP_3)
            {
                tooltip.add(TextFormatting.GOLD + new TextComponentTranslation("im.tooltip.stamp.ir").getFormattedText());
            }

            if(stack.getItem() == ModItems.RC_STAMP_1 || stack.getItem() == ModItems.RC_STAMP_2 || stack.getItem() == ModItems.RC_STAMP_3 || stack.getItem() == ModItems.RC_STAMP_4 || stack.getItem() == ModItems.RC_STAMP_5 || stack.getItem() == ModItems.RC_STAMP_6)
            {
                tooltip.add(TextFormatting.BLUE + new TextComponentTranslation("im.tooltip.stamp.rc").getFormattedText());
            }

            if(stack.getItem() == ModItems.SC_STAMP_1)
            {
                tooltip.add(TextFormatting.GREEN + new TextComponentTranslation("im.tooltip.stamp.sc").getFormattedText());
            }

            if(stack.getItem() == ModItems.CB_STAMP_1 || stack.getItem() == ModItems.CB_STAMP_2 || stack.getItem() == ModItems.CB_STAMP_3 || stack.getItem() == ModItems.CB_STAMP_4)
            {
                tooltip.add(TextFormatting.AQUA + new TextComponentTranslation("im.tooltip.stamp.cb").getFormattedText());
            }

            if(stack.getItem() == ModItems.AV_STAMP_1 || stack.getItem() == ModItems.AV_STAMP_2 || stack.getItem() == ModItems.AV_STAMP_3 || stack.getItem() == ModItems.AV_STAMP_4 || stack.getItem() == ModItems.AV_STAMP_5)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.stamp.av").getFormattedText());
            }

            if(stack.getItem() == ModItems.CL_STAMP_1 || stack.getItem() == ModItems.CL_STAMP_2)
            {
                tooltip.add(TextFormatting.DARK_PURPLE + new TextComponentTranslation("im.tooltip.stamp.cl").getFormattedText());
            }
        }
        else
        {
            tooltip.add(TextFormatting.WHITE + new TextComponentTranslation("im.tooltip.hidden").getFormattedText());
        }
    }
}
