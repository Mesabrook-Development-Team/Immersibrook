package com.mesabrook.ib.net;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.mesabrook.ib.blocks.gui.GuiAboutImmersibrook;

public class AboutGUIPacket implements IMessage, IMessageHandler<AboutGUIPacket, IMessage>
{
    public AboutGUIPacket()
    {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(AboutGUIPacket message, MessageContext ctx)
    {
        ClientSideHandlers.openAboutGUI();
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }
}
