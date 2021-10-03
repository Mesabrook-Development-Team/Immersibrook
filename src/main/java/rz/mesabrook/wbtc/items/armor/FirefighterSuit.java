package rz.mesabrook.wbtc.items.armor;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
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

public class FirefighterSuit extends ItemArmor implements IHasModel
{
    private TextComponentTranslation brand = new TextComponentTranslation("im.ff.brand");
    private TextComponentTranslation material = new TextComponentTranslation("im.ff.mat");
    public FirefighterSuit(String name, ArmorMaterial mat, EntityEquipmentSlot equipSlot, int renderIndexIn)
    {
        super(mat, renderIndexIn, equipSlot);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);

        brand.getStyle().setColor(TextFormatting.RED);
        material.getStyle().setColor(TextFormatting.RED);
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
        // Labeled Jackets
        if(stack.getItem() == ModItems.FDRC_CHEST)
        {
            tooltip.add(brand.getFormattedText() + new TextComponentString(TextFormatting.YELLOW + " Fire Department of Ravenholm City (FDRC)").getFormattedText());
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Black Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.CBFD_CHEST)
        {
            tooltip.add(brand.getFormattedText() + new TextComponentString(TextFormatting.YELLOW + " Crystal Beach Fire Department (CBFD)").getFormattedText());
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Light Tan Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.AVFD_CHEST)
        {
            tooltip.add(brand.getFormattedText() + new TextComponentString(TextFormatting.YELLOW + " Autumn Valley Fire Department (AVFD)").getFormattedText());
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Light Tan Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.IRFD_CHEST)
        {
            tooltip.add(brand.getFormattedText() + new TextComponentString(TextFormatting.YELLOW + " Iron River Fire Department (IRFD)").getFormattedText());
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Dark Tan Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.SCFD_CHEST)
        {
            tooltip.add(brand.getFormattedText() + new TextComponentString(TextFormatting.YELLOW + " Sodor City Fire Department (SCFD)").getFormattedText());
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Dark Tan Firecloth").getFormattedText());
        }

        // Generic Jackets
        if(stack.getItem() == ModItems.BLACK_GENERIC)
        {
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Black Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.DT_GENERIC)
        {
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Dark Tan Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.LT_GENERIC)
        {
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Light Tan Firecloth").getFormattedText());
        }

        // Pants
        if(stack.getItem() == ModItems.FF_PANTS_BLACK)
        {
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Black Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.FF_PANTS_LT)
        {
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Light Tan Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.FF_PANTS_DT)
        {
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Dark Tan Firecloth").getFormattedText());
        }
        if(stack.getItem() == ModItems.FF_PANTS_BLACK_WS)
        {
            tooltip.add(material.getFormattedText() + new TextComponentString(TextFormatting.DARK_GRAY + " Black Firecloth - White Stripes").getFormattedText());
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {

    }
}
