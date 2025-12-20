package com.mesabrook.ib.items.misc;

import java.util.List;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.entity.EntityDiscGolf;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDiscGolf extends Item implements IHasModel
{
	private int speed;
	private int glide;
	private int turn;
	private int fade;
	
    public ItemDiscGolf(String name, int speed, int glide, int turn, int fade)
    {
    	this.speed = speed;
    	this.glide = glide;
    	this.turn = turn;
    	this.fade = fade;
    	
        setMaxStackSize(1);
        setMaxDamage(212);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModItems.ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    	if (!worldIn.isRemote)
        {
            if(entityLiving instanceof EntityPlayer)
            {
                Triggers.trigger(Triggers.MESARANG, (EntityPlayer)entityLiving);
            }

            ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
            packet.pos = entityLiving.getPosition();
            packet.soundName = "woosh";
            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(entityLiving.dimension, entityLiving.posX, entityLiving.posY, entityLiving.posZ, 25));

            EntityDiscGolf mesarang = new EntityDiscGolf(worldIn, entityLiving, stack.getItemDamage());
            int timeHeld = getMaxItemUseDuration(stack) - timeLeft;
            mesarang.shootDisc(this, entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, getCurrentThrowPower(timeHeld));
            worldIn.spawnEntity(mesarang);
            
            stack.setCount(0);
        }
    }
    
    public float getCurrentThrowPower(int timeHeld)
    {
    	float currentPower = 0.75F;
    	if (timeHeld < 20)
		{
			currentPower = (float)timeHeld / 20F;
		}

		if (timeHeld >= 20 && timeHeld < 30)
		{
			currentPower = .75F + .25F *
							(
								1F - 
									(((float)timeHeld - 20F) / 10F)
							);
		}
		
		return currentPower;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
    	return EnumAction.BOW;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
    	return 72000;
    }

	public int getSpeed() {
		return speed;
	}

	public int getGlide() {
		return glide;
	}

	public int getTurn() {
		return turn;
	}

	public int getFade() {
		return fade;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextFormatting.GREEN + "Flight Rating:");
		tooltip.add(TextFormatting.GREEN + String.format("%d Speed", speed));
		tooltip.add(TextFormatting.GREEN + String.format("%d Glide", glide));
		tooltip.add(TextFormatting.GREEN + String.format("%d Turn", turn));
		tooltip.add(TextFormatting.GREEN + String.format("%d Fade", fade));
	}
}
