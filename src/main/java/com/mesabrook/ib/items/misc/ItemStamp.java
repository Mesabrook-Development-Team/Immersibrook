package com.mesabrook.ib.items.misc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class ItemStamp extends Item implements IHasModel
{
	private StampTypes stampType;
    public ItemStamp(String name, StampTypes stampType)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(6);
        setStampType(stampType);

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
            if (!(stack.getItem() instanceof ItemStamp))
            {
            	return;
            }
            
            ItemStamp stampItem = (ItemStamp)stack.getItem();
            if(stampItem.getStampType() == StampTypes.IronRiver)
            {
                tooltip.add(TextFormatting.GOLD + new TextComponentTranslation("im.tooltip.stamp.ir").getFormattedText());
            }

            if(stampItem.getStampType() == StampTypes.RavenholmCity)
            {
                tooltip.add(TextFormatting.BLUE + new TextComponentTranslation("im.tooltip.stamp.rc").getFormattedText());
            }

            if(stampItem.getStampType() == StampTypes.SodorCity)
            {
                tooltip.add(TextFormatting.GREEN + new TextComponentTranslation("im.tooltip.stamp.sc").getFormattedText());
            }

            if(stampItem.getStampType() == StampTypes.CrystalBeach)
            {
                tooltip.add(TextFormatting.AQUA + new TextComponentTranslation("im.tooltip.stamp.cb").getFormattedText());
            }

            if(stampItem.getStampType() == StampTypes.AutumnValley)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.stamp.av").getFormattedText());
            }

            if(stampItem.getStampType() == StampTypes.Clayton)
            {
                tooltip.add(TextFormatting.DARK_PURPLE + new TextComponentTranslation("im.tooltip.stamp.cl").getFormattedText());
            }
        }
        else
        {
            tooltip.add(TextFormatting.WHITE + new TextComponentTranslation("im.tooltip.hidden").getFormattedText());
        }
    }

    public StampTypes getStampType() {
		return stampType;
	}

	public ItemStamp setStampType(StampTypes stampType) {
		this.stampType = stampType;
		return this;
	}

	public enum StampTypes
    {
    	IronRiver,
    	RavenholmCity,
    	SodorCity,
    	CrystalBeach,
    	AutumnValley,
    	Clayton
    }
}
