package com.mesabrook.ib.items.armor;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class NightVisionGoggles extends Item implements IHasModel, ICapabilityProvider
{
    private static final int ENERGY_CONSUMPTION = 1;
    private static final int INITIAL_ENERGY = 150000;
    private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(INITIAL_ENERGY);

    @CapabilityInject(IEnergyStorage.class)
    public static Capability<IEnergyStorage> ENERGY;

    public NightVisionGoggles(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
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

        if (armorSlot.isEmpty() && item.getItem() == ModItems.NV_GOGGLES)
        {
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, item.copy());
            item.setCount(0);
            if (!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "nv";
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
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        if (playerIn instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.WEAR_NV, playerIn);
        }
        // Set initial energy
        IEnergyStorage energyStorage = stack.getCapability(ENERGY, null);
        if (energyStorage instanceof CustomEnergyStorage)
        {
            ((CustomEnergyStorage) energyStorage).setEnergy(INITIAL_ENERGY);
        }
    }
    
    @Override
    public void registerModels() 
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        boolean nightvision = true;

        if (tag != null)
        {
            nightvision = tag.getBoolean("nightvision");
        }

        IEnergyStorage energyStorage = itemStack.getCapability(ENERGY, null);

        if (energyStorage != null)
        {
            if (nightvision && energyStorage.extractEnergy(ENERGY_CONSUMPTION, true) >= ENERGY_CONSUMPTION)
            {
                energyStorage.extractEnergy(ENERGY_CONSUMPTION, false);
                player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 210, 1, true, false));
            }
            else if (nightvision && energyStorage.getEnergyStored() < ENERGY_CONSUMPTION)
            {
                // Remove Night Vision effect and send message
                player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
                if (!world.isRemote)
                {
                    player.sendMessage(new TextComponentString("Your Night Vision Goggles have run out of energy!"));
                }
                // Set nightvision to false in NBT to prevent further potion effects until recharged
                if (tag == null)
                {
                    tag = new NBTTagCompound();
                }
                tag.setBoolean("nightvision", false);
                itemStack.setTagCompound(tag);
            }
            else if (!nightvision)
            {
                player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        NBTTagCompound tag = stack.getTagCompound();
        boolean triggerEffect;
        if (tag != null)
        {
            triggerEffect = tag.getBoolean("nightvision");
            return triggerEffect;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        IEnergyStorage energyStorage = stack.getCapability(ENERGY, null);
        NBTTagCompound tag = stack.getTagCompound();
        if (energyStorage != null)
        {
            int energy = energyStorage.getEnergyStored();
            
            if(energy <= 0)
            {
            	tooltip.add(TextFormatting.RED + "Energy: " + energy + " FE");
            	tooltip.add(TextFormatting.RED + "Recharge Required.");
            }
            else
            {
                tooltip.add("Energy: " + energy + " FE");	
            }
            
            if(tag != null)
            {
            	boolean status = tag.getBoolean("nightvision");
            	if(status)
            	{
            		tooltip.add("Status: " + TextFormatting.ITALIC + "Active");
            	}
            	else
            	{
            		tooltip.add("Status: " + TextFormatting.ITALIC + "Inactive");
            	}
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return this;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == ENERGY;
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == ENERGY)
        {
            return ENERGY.cast(energyStorage);
        }
        return null;
    }

    public static class CustomEnergyStorage implements IEnergyStorage
    {
        private int energy;
        private int capacity;

        public CustomEnergyStorage(int capacity)
        {
            this.capacity = capacity;
            this.energy = 0;
        }

        public void setEnergy(int energy)
        {
            this.energy = energy;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            int energyReceived = Math.min(capacity - energy, maxReceive);
            if (!simulate)
            {
                energy += energyReceived;
            }
            return energyReceived;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            int energyExtracted = Math.min(energy, maxExtract);
            if (!simulate)
            {
                energy -= energyExtracted;
            }
            return energyExtracted;
        }

        @Override
        public int getEnergyStored()
        {
            return energy;
        }

        @Override
        public int getMaxEnergyStored()
        {
            return capacity;
        }

        @Override
        public boolean canExtract()
        {
            return true;
        }

        @Override
        public boolean canReceive()
        {
            return true;
        }
    }
}
