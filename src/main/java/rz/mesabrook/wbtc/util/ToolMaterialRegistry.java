package rz.mesabrook.wbtc.util;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ToolMaterialRegistry
{
    public static final Item.ToolMaterial SOD_WOOD = EnumHelper.addToolMaterial("wbtc_wood", 0, 30, 1.0F, -2.0F, 8);
    public static final Item.ToolMaterial SOD_STONE = EnumHelper.addToolMaterial("wbtc_stone", 1, 66, 2.0F, -0.5F, 5);
    public static final Item.ToolMaterial SOD_IRON = EnumHelper.addToolMaterial("wbtc_iron", 2, 125, 3.0F, -1.0F, 8);
    public static final Item.ToolMaterial SOD_GOLD = EnumHelper.addToolMaterial("wbtc_gold", 0, 16, 1.0F, -2.0F, 11);
    public static final Item.ToolMaterial SOD_DIAMOND = EnumHelper.addToolMaterial("wbtc_diamond", 3, 780, 4.0F, -0.5F, 5);
    public static final Item.ToolMaterial SOD_ALUMINUM = EnumHelper.addToolMaterial("wbtc_aluminum_sod", 2, 100, 3.0F, -1.2F, 3);
    public static final Item.ToolMaterial SWORD_ALUMINUM = EnumHelper.addToolMaterial("wbtc_aluminum", 2, 200, 5.0F, 1.2F, 8);
    public static final Item.ToolMaterial ZOE_CANE_MAT = EnumHelper.addToolMaterial("wbtc_zoe_cane_material", 3, 420, 1000.0F, 10000.0F, 10);
    public static final Item.ToolMaterial LEVI_HAMMER_MAT = EnumHelper.addToolMaterial("wbtc_levi_ban_hammer_material", 4, 69420, 1000.0F, 10000.0F, 10);
}
