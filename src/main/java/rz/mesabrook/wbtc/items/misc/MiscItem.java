package rz.mesabrook.wbtc.items.misc;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;
import javax.xml.soap.Text;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.DamageSourceAprilFools;
import rz.mesabrook.wbtc.util.DamageSourceDuckBoom;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.SoundRandomizer;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

public class MiscItem extends Item implements IHasModel
{
	public static final DamageSource KEKW = new DamageSourceAprilFools("kekw");
	private final TextComponentTranslation color = new TextComponentTranslation("im.color");
	private final TextComponentTranslation firecloth = new TextComponentTranslation("im.firecloth");
	public MiscItem(String name, int stack)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(stack);
		
		ModItems.ITEMS.add(this);

		color.getStyle().setBold(true);
		color.getStyle().setColor(TextFormatting.YELLOW);
		firecloth.getStyle().setColor(TextFormatting.GRAY);
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(stack.getItem() == ModItems.NV_LENS || stack.getItem() == ModItems.NV_BODY || stack.getItem() == ModItems.NV_INTERNALS || stack.getItem() == ModItems.NV_STRAPS || this.getUnlocalizedName().contains("shell") || stack.getItem() == ModItems.PHONE_SCREEN)
		{
			tooltip.add(TextFormatting.GOLD + "Crafting Ingredient");
		}

		if(stack.getItem() == ModItems.DYE_RED || stack.getItem() == ModItems.SUGAR_RED)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.RED + "RED");
		}
		if(stack.getItem() == ModItems.DYE_GREEN || stack.getItem() == ModItems.SUGAR_GREEN)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.DARK_GREEN + "GREEN");
		}
		if(stack.getItem() == ModItems.DYE_BLUE || stack.getItem() == ModItems.SUGAR_BLUE)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.BLUE + "BLUE");
		}
		if(stack.getItem() == ModItems.DYE_WHITE)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.WHITE + "WHITE");
		}
		if(stack.getItem() == ModItems.DYE_YELLOW)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.YELLOW + "YELLOW");
		}
		if(stack.getItem() == ModItems.SUGAR_ORANGE)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.GOLD + "ORANGE");
		}
		if(stack.getItem() == ModItems.SUGAR_PURPLE)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.DARK_PURPLE + "PURPLE");
		}
		if(stack.getItem() == ModItems.SUGAR_BROWN)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.DARK_RED + "BROWN");
		}
		if(stack.getItem() == ModItems.SUGAR_LIME)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.GREEN + "LIME");
		}
		if(stack.getItem() == ModItems.SUGAR_PINK)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.LIGHT_PURPLE + "PINK");
		}
		if(stack.getItem() == ModItems.SUGAR_YELLOW)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.YELLOW + "YELLOW");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_RED)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.LIGHT_PURPLE + "Strawberry");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_LIME)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.GREEN + "Lime");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_BLUE)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.BLUE + "Blueberry");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_ORANGE)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.GOLD + "Orange");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_GRAPE)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.DARK_PURPLE + "Grape");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_RB)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.DARK_RED + "Root Beer");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_CHOC)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.DARK_RED + "Chocolate");
		}

		if(stack.getItem() == ModItems.RAW_CANDY_PL)
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.LIGHT_PURPLE + "Pink Lemonade");
		}

		if(stack.getItem() == ModItems.FIRECLOTH_1 || stack.getItem() == ModItems.FIRECLOTH_2 || stack.getItem() == ModItems.FIRECLOTH_3 || stack.getItem() == ModItems.FIRECLOTH_4)
		{
			tooltip.add(firecloth.getFormattedText());
		}

		if(stack.getItem() == ModItems.REFLECTIVE_WHITE)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.WHITE + "WHITE");
		}
		if(stack.getItem() == ModItems.REFLECTIVE_YELLOW)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.YELLOW + "YELLOW");
		}
		if(stack.getItem() == ModItems.REFLECTIVE_PINK)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.RED + "PINK");
		}
		if(stack.getItem() == ModItems.REFLECTIVE_GREEN)
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.GREEN + "GREEN");
		}
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		if(this.getUnlocalizedName().contains("raw_candy"))
		{
			if(playerIn instanceof EntityPlayer)
			{
				Triggers.trigger(Triggers.CANDY, playerIn);
			}
		}

		if(this.getUnlocalizedName().contains("chocolate_bar"))
		{
			if(playerIn instanceof EntityPlayer)
			{
				Triggers.trigger(Triggers.CHOCOLATE, playerIn);
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if(itemstack.getItem() == ModItems.KEKW)
		{
			if(!worldIn.isRemote)
			{
				itemstack.setCount(0);
				SoundRandomizer.RandomizeSound();
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = playerIn.getPosition();
				packet.soundName = "kekw";
				PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
				playerIn.attackEntityFrom(KEKW, 1000F);
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
