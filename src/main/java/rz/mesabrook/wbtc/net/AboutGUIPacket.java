package rz.mesabrook.wbtc.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.blocks.gui.GuiAboutImmersibrook;

public class AboutGUIPacket implements IMessage, IMessageHandler<AboutGUIPacket, IMessage>
{
    public AboutGUIPacket()
    {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(AboutGUIPacket message, MessageContext ctx)
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiAboutImmersibrook());
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
