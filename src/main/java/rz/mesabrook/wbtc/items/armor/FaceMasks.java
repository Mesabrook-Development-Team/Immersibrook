package rz.mesabrook.wbtc.items.armor;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class FaceMasks extends ItemArmor implements IHasModel
{
    private final TextComponentTranslation noProtection = new TextComponentTranslation("im.noprotect");
    public FaceMasks(String name, ArmorMaterial mat, EntityEquipmentSlot equipmentSlot)
    {
        super(mat, 1, equipmentSlot);
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModItems.ITEMS.add(this);

        noProtection.getStyle().setColor(TextFormatting.RED);
;    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        if(playerIn instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.WEAR_VEST, playerIn);
        }
    }

//    @Override
//    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
//    {
//        NBTTagCompound tag = itemStack.getTagCompound();
//        int coughGender = ModConfig.coughingSound;
//
//        if(tag != null)
//        {
//            coughGender = tag.getInteger("coughgender");
//        }
//
//        if(player instanceof EntityPlayer && coughGender == 1)
//        {
//            // Male Cough
//            PlaySoundPacket packet = new PlaySoundPacket();
//            packet.pos = player.getPosition();
//            packet.soundName = "c1";
//            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 5));
//        }
//        else if(player instanceof EntityPlayer && coughGender == 2)
//        {
//            // Female Cough
//            PlaySoundPacket packet = new PlaySoundPacket();
//            packet.pos = player.getPosition();
//            packet.soundName = "c2";
//            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 5));
//        }
//    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(noProtection.getFormattedText());
    }
}
