package rz.mesabrook.wbtc.items.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.SoundRandomizer;
import rz.mesabrook.wbtc.util.config.ModConfig;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ToolPickaxeBase extends ItemPickaxe implements IHasModel
{
    public ToolPickaxeBase(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModItems.ITEMS.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(ModConfig.funnyTooltips)
        {
            if(this.getUnlocalizedName().contains("levi_hammer"))
            {
                tooltip.add(TextFormatting.AQUA + "An LVN Product");
                super.addInformation(stack, world, tooltip, flag);
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        SoundRandomizer.HammerRandomizer();
        if(this.getUnlocalizedName().contains("levi_hammer"))
        {
            try
            {
                World worldIn = player.world;
                if(!worldIn.isRemote)
                {
                    SoundRandomizer.HammerRandomizer();
                    PlaySoundPacket packet = new PlaySoundPacket();
                    packet.pos = player.getPosition();
                    packet.soundName = SoundRandomizer.hammerResult;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 25));
                    entity.setFire(100);
                }
            }
            catch(Exception ex)
            {
                System.out.println(ex);
                return false;
            }
        }
        else
        {
            return false;
        }
        return false;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
