package rz.mesabrook.wbtc.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.items.armor.SafetyVestModel;

public class VestTogglePacket implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	public static class Handler implements IMessageHandler<VestTogglePacket, IMessage>
	{

		@Override
		public IMessage onMessage(VestTogglePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(ctx));
			return null;
		}
		
		private void handle(MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			ItemStack stack = player.inventory.armorItemInSlot(2); // 2 = chest
			if (!(stack.getItem() instanceof SafetyVestModel))
			{
				return;
			}
			
			NBTTagCompound tag = stack.getTagCompound();
			if (tag == null)
			{
				tag = new NBTTagCompound();
				stack.setTagCompound(tag);
			}
			
			if (!tag.hasKey("glowing"))
			{
				tag.setBoolean("glowing", true);
			}
			
			tag.setBoolean("glowing", !tag.getBoolean("glowing"));
		}
	}
}
