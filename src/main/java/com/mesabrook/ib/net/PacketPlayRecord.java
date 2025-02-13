package com.mesabrook.ib.net;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketPlayRecord implements IMessage
{
	private BlockPos pos;
    private ItemStack record;
    
 // Default constructor for deserialization
    public PacketPlayRecord() {}

    public PacketPlayRecord(BlockPos pos, ItemStack record)
    {
        this.pos = pos;
        this.record = record;
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeBlockPos(pos);
        packetBuffer.writeItemStack(record);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        this.pos = packetBuffer.readBlockPos();
        try {
			this.record = packetBuffer.readItemStack();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static class Handler implements IMessageHandler<PacketPlayRecord, IMessage>
    {
    	@SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketPlayRecord message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                // Play the record sound at the specified position
                Minecraft.getMinecraft().world.playEvent(1010, message.pos, Item.getIdFromItem(message.record.getItem()));
            });
            return null;
        }
    }
}
