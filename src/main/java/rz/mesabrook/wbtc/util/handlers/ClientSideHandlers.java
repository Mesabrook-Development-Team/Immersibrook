package rz.mesabrook.wbtc.util.handlers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiPhoneActivate;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiPhoneActivate.ActivationScreens;
import rz.mesabrook.wbtc.blocks.gui.telecom.GuiPhoneBase;
import rz.mesabrook.wbtc.blocks.gui.telecom.SignalStrengths;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.Reference;

@SideOnly(Side.CLIENT)
public class ClientSideHandlers 
{
	private static HashMap<Long, PositionedSoundRecord> soundsByBlockPos = new HashMap<>();
	public static void playSoundHandler(PlaySoundPacket message, MessageContext ctx)
	{
		SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
		
		// Clear our cache of playing sounds
		// Since we do this each time the packet is received, this
		// shouldn't take a whole lot of extra processing time
		HashSet<Long> keysToRemove = new HashSet<>();
		for(Entry<Long, PositionedSoundRecord> kvp : soundsByBlockPos.entrySet())
		{
			if (!soundHandler.isSoundPlaying(kvp.getValue()))
			{
				keysToRemove.add(kvp.getKey());
			}
		}
		
		for(long key : keysToRemove)
		{
			soundsByBlockPos.remove(key);
		}
		
		PositionedSoundRecord existingSoundAtPosition = soundsByBlockPos.get(message.pos.toLong());
		if (existingSoundAtPosition != null)
		{
			return;
		}
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		WorldClient world = Minecraft.getMinecraft().world;

		ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, message.soundName);
		IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
		SoundEvent sound = soundRegistry.getValue(soundLocation);
		
		PositionedSoundRecord record = new PositionedSoundRecord(sound, SoundCategory.BLOCKS, 1F, 1F, message.pos);
		
		soundsByBlockPos.put(message.pos.toLong(), record);
		
		Minecraft.getMinecraft().getSoundHandler().playSound(record);
	}
	
	// This little piece of shit is what actually loads the super duper poggy woggy creative menu for Immersibrook.
	public static void loadCreativeGUI()
	{
		MinecraftForge.EVENT_BUS.register(new CreativeGuiDrawHandler());
	}

	public static class TelecomClientHandlers
	{
		public static void onNoReception()
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneActivate)
			{
				GuiPhoneActivate activate = (GuiPhoneActivate)Minecraft.getMinecraft().currentScreen;
				activate.setMessage("Failed to activate: out of range");
				activate.setActivationScreen(ActivationScreens.Message);
			}
		}
		
		public static void onChooseNumber(int[] options, boolean isResend)
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneActivate)
			{
				GuiPhoneActivate activate = (GuiPhoneActivate)Minecraft.getMinecraft().currentScreen;
				activate.setSelectablePhoneNumbers(options, isResend);
				activate.setMessage("");
				activate.setActivationScreen(ActivationScreens.ChooseNumber);
			}
		}
		
		public static void onActivationComplete()
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneActivate)
			{
				GuiPhoneActivate activate = (GuiPhoneActivate)Minecraft.getMinecraft().currentScreen;
				activate.goToMainScreen();
				
			}
		}
	
		public static void onReceptionReceived(double reception)
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneBase)
			{
				GuiPhoneBase phone = (GuiPhoneBase)Minecraft.getMinecraft().currentScreen;
				phone.setSignalStrength(SignalStrengths.getForReceptionAmount(reception));
			}
		}
	}
}
