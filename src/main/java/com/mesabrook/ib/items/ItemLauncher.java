package com.mesabrook.ib.items;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemLauncher extends Item implements IHasModel
{
    public ItemLauncher(String name)
    {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        setMaxDamage(64);

        ModItems.ITEMS.add(this);
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(new TextComponentString(TextFormatting.GREEN + "This nifty gadget takes an item from your inventory and launches it!").getFormattedText());
            tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Careful, it can launch your armor too!").getFormattedText());
        }
        else
        {
            tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Press [SHIFT] for more info.").getFormattedText());
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            List<ItemStack> launchableItems = new ArrayList<>();
            ItemStack launcherStack = player.getHeldItem(hand);

            // Add main inventory (hotbar + inventory)
            for (ItemStack stack : player.inventory.mainInventory) {
                if (!stack.isEmpty() && !(stack.getItem() instanceof ItemLauncher)) {
                    launchableItems.add(stack);
                }
            }

            // Add armor inventory
            for (ItemStack stack : player.inventory.armorInventory) {
                if (!stack.isEmpty() && !(stack.getItem() instanceof ItemLauncher)) {
                    launchableItems.add(stack);
                }
            }

            // Add offhand
            for (ItemStack stack : player.inventory.offHandInventory) {
                if (!stack.isEmpty() && !(stack.getItem() instanceof ItemLauncher)) {
                    launchableItems.add(stack);
                }
            }

            if (!launchableItems.isEmpty()) {
                // Pick a random item
                ItemStack originalStack = launchableItems.get(world.rand.nextInt(launchableItems.size()));

                // Make a copy to launch
                ItemStack launchStack = originalStack.copy();
                launchStack.setCount(1);

                // Remove one item unless in Creative
                if (!player.capabilities.isCreativeMode) {
                    originalStack.shrink(1);
                    launcherStack.damageItem(1, player);
                }

                // Launch the item as an entity
                EntityItem thrownItem = new EntityItem(
                    world,
                    player.posX,
                    player.posY + player.getEyeHeight(),
                    player.posZ,
                    launchStack
                );

                Vec3d look = player.getLookVec();
                double velocityScale = 1.75;
                double upwardBoost = 0.25;

                thrownItem.motionX = look.x * velocityScale;
                thrownItem.motionY = look.y * velocityScale + upwardBoost;
                thrownItem.motionZ = look.z * velocityScale;

                thrownItem.rotationYaw = world.rand.nextFloat() * 360.0F;
                thrownItem.rotationPitch = world.rand.nextFloat() * 360.0F;
                thrownItem.setPickupDelay(60);

                world.spawnEntity(thrownItem);

                // Play custom sound
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.modID = "minecraft";
                packet.soundName = "entity.lightning.impact";
                packet.pitch = 1.25F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));

                // Set cooldown (1 second = 20 ticks)
                player.getCooldownTracker().setCooldown(this, 20);
            } else {
                player.sendStatusMessage(new TextComponentString("No launchable items in inventory!"), true);

                // Play duck anyway for comedic effect
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "woosh";
                packet.pitch = 1.75F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }




    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
