package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.Reference;

@SideOnly(Side.CLIENT)
public class ClientSideHandlers 
{
	public static void playSoundHandler(PlaySoundPacket message, MessageContext ctx)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		WorldClient world = Minecraft.getMinecraft().world;

		ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, message.soundName);
		IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
		SoundEvent sound = soundRegistry.getValue(soundLocation);
		
		PositionedSoundRecord record = new PositionedSoundRecord(sound, SoundCategory.BLOCKS, 1F, 1F, message.pos);
		
		Minecraft.getMinecraft().getSoundHandler().playSound(record);
				
		/*if (!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(record))
		{
			world.playSound(player, message.pos, sound, SoundCategory.BLOCKS, 1F, 1F);
		}*/
	}
}
