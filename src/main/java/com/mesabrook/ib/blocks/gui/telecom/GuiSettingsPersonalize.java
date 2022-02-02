package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.telecom.PersonalizationPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class GuiSettingsPersonalize extends GuiPhoneBase {

	LabelButton back;
	LabelButton reset;
	LabelButton apply;
	LabelButton lockPrev;
	LabelButton lockNext;
	LabelButton homePrev;
	LabelButton homeNext;
	LabelButton chatPrev;
	LabelButton chatNext;
	LabelButton ringPrev;
	LabelButton ringNext;
	
	private int currentHome;
	private int currentLock;
	private int currentChatTone;
	private int currentRingTone;
	
	private PositionedSoundRecord ringtonePreview = null;
	private PositionedSoundRecord chatPreview = null;
	
	public GuiSettingsPersonalize(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
		
		currentHome = phoneStackData.getHomeBackground();
		currentLock = phoneStackData.getLockBackground();
		currentChatTone = phoneStackData.getChatTone();
		currentRingTone = phoneStackData.getRingTone();
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}

	@Override
	public void initGui() {
		super.initGui();

		
		back = new LabelButton(4, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);
		
		lockPrev = new LabelButton(5, INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 87, "<", 0xFFFFFF);
		lockNext = new LabelButton(6, INNER_X + (INNER_TEX_WIDTH / 2) + 35, INNER_Y + 87, ">", 0xFFFFFF);
		homePrev = new LabelButton(7, INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 150, "<", 0xFFFFFF);
		homeNext = new LabelButton(8, INNER_X + (INNER_TEX_WIDTH / 2) + 35, INNER_Y + 150, ">", 0xFFFFFF);
		chatPrev = new LabelButton(9, INNER_X + 90, INNER_Y + 162, "<", 0xFFFFFF);
		chatNext = new LabelButton(10, INNER_X + 110, INNER_Y + 162, ">", 0xFFFFFF);
		ringPrev = new LabelButton(11, INNER_X + 50, INNER_Y + 174, "<", 0xFFFFFF);
		ringNext = new LabelButton(12, INNER_X + 70, INNER_Y + 174, ">", 0xFFFFFF);
		
		reset = new LabelButton(2, INNER_X + (INNER_TEX_WIDTH / 4), INNER_Y + INNER_TEX_HEIGHT - 33, "Reset", 0x0000FF);
		apply = new LabelButton(3, INNER_X + INNER_TEX_WIDTH - (INNER_TEX_WIDTH / 4), INNER_Y + INNER_TEX_HEIGHT - 33, "Apply", 0x0000FF);
		apply.x = apply.x - apply.width;
		
		buttonList.addAll(ImmutableList.<GuiButton>builder()
				.add(reset)
				.add(apply)
				.add(back)
				.add(lockPrev)
				.add(lockNext)
				.add(homeNext)
				.add(homePrev)
				.add(chatPrev)
				.add(chatNext)
				.add(ringPrev)
				.add(ringNext)
				.build());
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString("Personalize", INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
		
		fontRenderer.drawString("Lock Screen:", INNER_X + 3, INNER_Y + 36, 0x4444FF);
		int fontWidth = fontRenderer.getStringWidth(String.valueOf(currentLock));
		fontRenderer.drawString(String.valueOf(currentLock), INNER_X + (INNER_TEX_WIDTH / 2) + 20 - (fontWidth / 2), INNER_Y + 87, 0xFFFFFF);
		
		fontRenderer.drawString("Home Screen:", INNER_X + 3, INNER_Y + 99, 0x4444FF);
		fontWidth = fontRenderer.getStringWidth(String.valueOf(currentHome));
		fontRenderer.drawString(String.valueOf(currentHome), INNER_X + (INNER_TEX_WIDTH / 2) + 20 - (fontWidth / 2), INNER_Y + 150, 0xFFFFFF);
		
		fontRenderer.drawString("Chat Notification:", INNER_X + 3, INNER_Y + 162, 0x4444FF);
		fontWidth = fontRenderer.getStringWidth(String.valueOf(currentChatTone));
		fontRenderer.drawString(String.valueOf(currentChatTone), INNER_X + 103 - (fontWidth / 2), INNER_Y + 162, 0xFFFFFF);
		
		fontRenderer.drawString("Ringtone:", INNER_X + 3, INNER_Y + 174, 0x4444FF);
		fontWidth = fontRenderer.getStringWidth(String.valueOf(currentRingTone));
		fontRenderer.drawString(String.valueOf(currentRingTone), INNER_X + 63 - (fontWidth / 2), INNER_Y + 174, 0xFFFFFF);
		
		GlStateManager.color(1, 1, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/gui_phone_bg_" + currentLock + ".png"));
		drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 36, 0, 0, 323, 414, 39, 50, 512, 512);	
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/gui_phone_bg_" + currentHome + ".png"));
		drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 99, 0, 0, 323, 414, 39, 50, 512, 512);	
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
		}
		
		if (button == lockPrev)
		{
			currentLock--;
			
			if (currentLock < 1)
			{
				currentLock = Reference.MAX_PHONE_BACKGROUNDS;
			}
		}
		
		if (button == lockNext)
		{
			currentLock++;
			
			if (currentLock > Reference.MAX_PHONE_BACKGROUNDS)
			{
				currentLock = 1;
			}
		}
		
		if (button == homeNext)
		{
			currentHome++;
			
			if (currentHome > Reference.MAX_PHONE_BACKGROUNDS)
			{
				currentHome = 1;
			}
		}
		
		if (button == homePrev)
		{
			currentHome--;
			
			if (currentHome < 1)
			{
				currentHome = Reference.MAX_PHONE_BACKGROUNDS;
			}
		}
		
		if (button == chatPrev)
		{
			currentChatTone--;
			
			if (currentChatTone < 1)
			{
				currentChatTone = Reference.MAX_CHAT_NOTIFICATIONS;
			}
		}
		
		if (button == chatNext)
		{
			currentChatTone++;
			
			if (currentChatTone > Reference.MAX_CHAT_NOTIFICATIONS)
			{
				currentChatTone = 1;
			}
		}
		
		if (button == ringPrev)
		{
			currentRingTone--;
			
			if (currentRingTone < 1)
			{
				currentRingTone = Reference.MAX_RINGTONES;
			}
		}
		
		if (button == ringNext)
		{
			currentRingTone++;
			
			if (currentRingTone > Reference.MAX_RINGTONES)
			{
				currentRingTone = 1;
			}
		}
		
		SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
		handler.stopSound(ringtonePreview);
		handler.stopSound(chatPreview);
		
		if (button == ringNext || button == ringPrev)
		{
			ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "ring_" + Integer.toString(currentRingTone));
			IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
			SoundEvent soundEvent = soundRegistry.getValue(soundLocation);
			
			ringtonePreview = PositionedSoundRecord.getMasterRecord(soundEvent, 1F);
			handler.playSound(ringtonePreview);
		}
		
		if (button == chatNext || button == chatPrev)
		{
			ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "ding_" + Integer.toString(currentChatTone));
			IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
			SoundEvent soundEvent = soundRegistry.getValue(soundLocation);
			
			chatPreview = PositionedSoundRecord.getMasterRecord(soundEvent, 1F);
			handler.playSound(chatPreview);
		}
		
		if (button == apply)
		{
			PersonalizationPacket packet = new PersonalizationPacket();
			packet.hand = hand.ordinal();
			packet.homeBackground = currentHome;
			packet.lockBackground = currentLock;
			packet.chatTone = currentChatTone;
			packet.ringTone = currentRingTone;
			packet.guiClassName = GuiSettingsPersonalize.class.getName();
			PacketHandler.INSTANCE.sendToServer(packet);
			
			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Settings Applied", 0xFFFFFF));
		}
		
		if (button == reset)
		{
			currentHome = phoneStackData.getHomeBackground();
			currentLock = phoneStackData.getLockBackground();
			currentChatTone = phoneStackData.getChatTone();
			currentRingTone = phoneStackData.getRingTone();
		}
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		
		SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
		handler.stopSound(chatPreview);
		handler.stopSound(ringtonePreview);
	}
}
