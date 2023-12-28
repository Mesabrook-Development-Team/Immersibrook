package com.mesabrook.ib.blocks.sco;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.TreeMap;

import com.mesabrook.ib.blocks.ImmersiblockRotationalManyBB;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.items.commerce.ItemMoney.MoneyType;
import com.mesabrook.ib.items.commerce.ItemWallet;
import com.mesabrook.ib.util.ModUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockScanner extends ImmersiblockRotationalManyBB {

	private static final AxisAlignedBB SCANNER = ModUtils.getPixelatedAABB(4, 16.5, -4, 12, 16.75, 2);
	private static final AxisAlignedBB BILL_ACCEPTER = ModUtils.getPixelatedAABB(3.75, 11.5, -4.75, 5.75, 13.75, -2.5);
	private static final AxisAlignedBB COIN_SLOT = ModUtils.getPixelatedAABB(1, 10.75, -5, 3, 13.75, -2.5);
	private static final AxisAlignedBB BASE_BOX = ModUtils.getPixelatedAABB(0, 0, -4, 16, 16.5, 13);
	public BlockScanner()
	{
		super("sco_scanner", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, true, SCANNER, BILL_ACCEPTER, COIN_SLOT, BASE_BOX);
	}
	
	@Override
	public boolean onSubBoundingBoxActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, AxisAlignedBB subBoundingBox) {
		if (worldIn.getBlockState(pos.up()).getBlock() != ModBlocks.SCO_POS)
		{
			return super.onSubBoundingBoxActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ, subBoundingBox);
		}
		
		TileEntity te = worldIn.getTileEntity(pos.up());
		if (!(te instanceof TileEntityRegister))
		{
			return false;
		}
		
		TileEntityRegister register = (TileEntityRegister)te;
		if (register.getRegisterStatus() != RegisterStatuses.Online &&
				register.getRegisterStatus() != RegisterStatuses.InSession && 
				register.getRegisterStatus() != RegisterStatuses.PaymentSelect &&
				register.getRegisterStatus() != RegisterStatuses.PaymentCash)
		{
			return false;
		}
		
		if (worldIn.isRemote)
		{
			return subBoundingBox == BILL_ACCEPTER || subBoundingBox == COIN_SLOT;
		}
		
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (subBoundingBox == SCANNER && !playerIn.getHeldItem(hand).isEmpty()) // Boop
		{
			if (!register.insertItemInFirstAvailableSlot(heldItem, true))
			{
				playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You cannot scan any more items. Please complete this transaction and try again."));
			}
			
			if (heldItem.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, facing))
			{
				if (heldItem.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, facing).getLocationIDOwner() != register.getLocationIDOwner())
				{
					playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This item cannot be rung up at this register"));
					return false;
				}
				
				if (!register.hasSecurityBoxCapacityForStack(heldItem.copy().splitStack(1)))
				{
					playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This register's internal security box inventory is full or will be full after this current session is complete."));
					return false;
				}
				
				heldItem = heldItem.splitStack(1);
			}
			register.insertItemInFirstAvailableSlot(heldItem.copy());
			register.setRegisterStatus(RegisterStatuses.InSession);
			heldItem.shrink(heldItem.getCount());
			worldIn.notifyBlockUpdate(register.getPos(), worldIn.getBlockState(register.getPos()), worldIn.getBlockState(register.getPos()), 3);
			
			return true;
		}
		else if (subBoundingBox == BILL_ACCEPTER && heldItem.getItem() instanceof ItemMoney && ((ItemMoney)heldItem.getItem()).getMoneyType() == MoneyType.Bill && register.hasItemsForSession()) // Brrr
		{
			BigDecimal amountForRegister = new BigDecimal(((ItemMoney)heldItem.getItem()).getValue()).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
			heldItem.shrink(1);
			
			register.applyCashTender(amountForRegister);
			
			return true;
		}
		else if (subBoundingBox == COIN_SLOT && heldItem.getItem() instanceof ItemMoney && ((ItemMoney)heldItem.getItem()).getMoneyType() == MoneyType.Coin && register.hasItemsForSession()) // Ka-ching
		{
			BigDecimal amountForRegister = new BigDecimal(((ItemMoney)heldItem.getItem()).getValue() * heldItem.getCount()).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
			heldItem.shrink(heldItem.getCount());
			
			register.applyCashTender(amountForRegister);
			
			return true;
		}
		else if ((subBoundingBox == BILL_ACCEPTER || subBoundingBox == COIN_SLOT) && heldItem.getItem() instanceof ItemWallet && heldItem.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing) && register.hasItemsForSession())
		{			
			IItemHandler walletInventory = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			BigDecimal amountRemaining = register.getDueAmount();
			final BigDecimal zero = new BigDecimal(0);
			ItemMoney currentDenomination = ItemMoney.SORTED_MONEY.last();
			while(amountRemaining.compareTo(zero) > 0)
			{
				for(int i = 0; i < walletInventory.getSlots(); i++)
				{
					ItemStack slotStack = walletInventory.getStackInSlot(i);
					if (slotStack.getItem() != currentDenomination)
					{
						continue;
					}
					
					int stacksToPop = 0;
					for(int j = 0; j < slotStack.getCount(); j++)
					{
						stacksToPop++;
						amountRemaining = amountRemaining.subtract(new BigDecimal(currentDenomination.getValue()).divide(new BigDecimal(100)));
						if (amountRemaining.compareTo(zero) <= 0)
						{
							break;
						}
					}
					
					walletInventory.extractItem(i, stacksToPop, false);
					
					if (amountRemaining.compareTo(zero) <= 0)
					{
						break;
					}
				}
				
				currentDenomination = ItemMoney.SORTED_MONEY.lower(currentDenomination);
				if (currentDenomination == null)
				{
					break;
				}
			}
			
			register.applyCashTender(register.getDueAmount().subtract(amountRemaining));
			
			return true;
		}
		
		return false;
	}
}
