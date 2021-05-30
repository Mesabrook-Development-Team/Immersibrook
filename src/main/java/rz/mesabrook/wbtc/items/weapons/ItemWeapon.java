package rz.mesabrook.wbtc.items.weapons;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
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

public class ItemWeapon extends ItemSword implements IHasModel
{
	public ItemWeapon(String name, ToolMaterial material)
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
			if(this.getUnlocalizedName().contains("sod"))
			{
				tooltip.add(TextFormatting.RED + "noo you can't just break a sword in half and call it a sod!");
				tooltip.add(TextFormatting.AQUA + "hehe sod go swish slash");
				super.addInformation(stack, world, tooltip, flag);
			}
			else if(this.getUnlocalizedName().contains("levi_hammer"))
			{
				tooltip.add(TextFormatting.AQUA + "An LVN Product");
				super.addInformation(stack, world, tooltip, flag);
			}
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(this.getUnlocalizedName().contains("levi_hammer"))
		{
			try
			{
				World worldIn = player.world;
				if(!worldIn.isRemote)
				{
					PlaySoundPacket packet = new PlaySoundPacket();
					packet.pos = player.getPosition();
					packet.soundName = "beaned";
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
					entity.setFire(10);
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
