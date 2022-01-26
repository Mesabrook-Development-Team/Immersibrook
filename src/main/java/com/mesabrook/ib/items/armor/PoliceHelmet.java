package com.mesabrook.ib.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.PlaySoundPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;

public class PoliceHelmet extends Item implements IHasModel
{
    public PoliceHelmet(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModItems.ITEMS.add(this);
    }

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

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack item = player.getHeldItem(hand);
        ItemStack armorSlot = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

        if(armorSlot.isEmpty())
        {
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, item.copy());
            item.setCount(0);
            if(!world.isRemote)
            {
                PlaySoundPacket packet = new PlaySoundPacket();
                packet.pos = player.getPosition();
                packet.soundName = "police_helmet";
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        boolean effectsActive = true;

        if(tag != null)
        {
            effectsActive = tag.getBoolean("policeeffects");
        }

        if(player instanceof EntityPlayer && effectsActive)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10, 1, true, false));
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 10, 1, true, false));
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10, 2, true, false));
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10, 1, true, false));
            player.heal(0.1F);
        }
        else if(player instanceof EntityPlayer && !effectsActive)
        {
            player.removePotionEffect(MobEffects.RESISTANCE);
            player.removePotionEffect(MobEffects.JUMP_BOOST);
            player.removePotionEffect(MobEffects.SPEED);
            player.removePotionEffect(MobEffects.STRENGTH);
        }
    }
}
