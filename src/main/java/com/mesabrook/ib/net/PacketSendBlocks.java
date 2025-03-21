package com.mesabrook.ib.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

public class PacketSendBlocks implements IMessage
{
	private List<String> blocks;
	private String modID = "";
	
	public PacketSendBlocks() 
	{
        // Required empty constructor
    }
	
	public PacketSendBlocks(List<String> blocks, String modID) 
	{
		this.blocks = blocks;
		this.modID = modID;
    }
	
	@Override
    public void fromBytes(ByteBuf buf) 
	{
        int size = buf.readInt();
        blocks = new ArrayList<>();
        for (int i = 0; i < size; i++) 
        {
            int length = buf.readInt();
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            blocks.add(new String(bytes));
        }
        modID = ByteBufUtils.readUTF8String(buf);
    }
	
	@Override
    public void toBytes(ByteBuf buf) 
	{
        buf.writeInt(blocks.size());
        for (String block : blocks) 
        {
            byte[] bytes = block.getBytes();
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        }
        ByteBufUtils.writeUTF8String(buf, modID);
    }
	
	public static class Handler implements IMessageHandler<PacketSendBlocks, IMessage>
	{
		 @Override
	     public IMessage onMessage(PacketSendBlocks message, MessageContext ctx) 
		 {
	            net.minecraft.client.Minecraft.getMinecraft().addScheduledTask(() -> 
	            {
	                ClientSideHandlers.processBlockList(message.blocks, message.modID);
	            });
	            return null;
	     }
	}
}
