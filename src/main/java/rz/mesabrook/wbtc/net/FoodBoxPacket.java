package rz.mesabrook.wbtc.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.items.misc.FoodBoxItemBlock;

public class FoodBoxPacket implements IMessage
{
    public String boxID = "";
    public String company = "";
    public EnumHand hand;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        boxID = ByteBufUtils.readUTF8String(buf);
        company = ByteBufUtils.readUTF8String(buf);
        hand = EnumHand.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, boxID);
        ByteBufUtils.writeUTF8String(buf, company);
        buf.writeInt(hand.ordinal());
    }

    public static class Handler implements IMessageHandler<FoodBoxPacket, IMessage>
    {
        @Override
        public IMessage onMessage(FoodBoxPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(FoodBoxPacket message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            ItemStack heldStack = player.getHeldItem(message.hand);
            if(!(heldStack.getItem() instanceof FoodBoxItemBlock))
            {
                return;
            }

            NBTTagCompound compound = heldStack.getTagCompound();
            if(compound == null)
            {
                compound = new NBTTagCompound();
                heldStack.setTagCompound(compound);
            }

            compound.setString("boxID", message.boxID);
            compound.setString("company", message.company);
        }
    }
}
