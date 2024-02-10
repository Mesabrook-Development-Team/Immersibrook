package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.UniversalDeathSource;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class MiscItem extends Item implements IHasModel
{
	public static final DamageSource KEKW = new UniversalDeathSource("kekw", "im.death.kekw");
	private final TextComponentTranslation color = new TextComponentTranslation("im.color");
	private final TextComponentTranslation firecloth = new TextComponentTranslation("im.firecloth");
	public MiscItem(String name, int stack, CreativeTabs creativeTab)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(stack);
		setCreativeTab(creativeTab);
		
		ModItems.ITEMS.add(this);

		color.getStyle().setBold(true);
		color.getStyle().setColor(TextFormatting.YELLOW);
		firecloth.getStyle().setColor(TextFormatting.GRAY);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(GuiScreen.isShiftKeyDown())
		{
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
			if(stack.getItem() == ModItems.HFCS)
			{
				tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.hfcs").getFormattedText());
			}
			if(stack.getItem() == ModItems.OT_CHARGER)
			{
				tooltip.add(TextFormatting.YELLOW + "A single-use charger for Minedroid smartphones. To use it, combine this item with your phone in a Crafting Table.");
			}
			if(stack.getItem() == ModItems.PLEATHER)
			{
				tooltip.add(TextFormatting.YELLOW + "100% Plastic, 0% Leather");
			}
			else
			{
				tooltip.add(TextFormatting.YELLOW + "Crafting Ingredient");
			}
		}
		else
		{
			tooltip.add(TextFormatting.YELLOW + "Press [SHIFT] for more info.");
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
				ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
				packet.pos = playerIn.getPosition();
				packet.soundName = "kekw";
				PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
				playerIn.attackEntityFrom(KEKW, 1000F);
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
