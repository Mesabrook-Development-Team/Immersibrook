package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
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

import java.io.IOException;

public class GuiPhoneSetupStart extends GuiPhoneBase
{
    MinedroidButton beginSetup;
    ImageButton mux;
    private PositionedSoundRecord setupMusic = null;
    
    public GuiPhoneSetupStart(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen_setup.png";
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }
    
    @Override
    protected boolean renderTopBar() {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        beginSetup = new MinedroidButton(1, INNER_X + 100, INNER_Y + 180, 50, "Begin >", 0xFFFFFF);
        mux = new ImageButton(2, INNER_X + 15, INNER_Y + 27, 25, 25, "icn_mux.png", 32, 32);
        buttonList.add(beginSetup);
        buttonList.add(mux);

        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "minedroid_startup";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
        
        SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "md_setup");
        IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
        SoundEvent soundEvent = soundRegistry.getValue(soundLocation);

        setupMusic = PositionedSoundRecord.getMasterRecord(soundEvent, 1F);
        handler.playSound(setupMusic);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        int stringWidth = fontRenderer.getStringWidth("Hi!");

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString("Hi!", scale(INNER_X + 29, dBigFont) - stringWidth / 2, scale(INNER_Y + 60, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);

        fontRenderer.drawString("Welcome to Minedroid!", INNER_X + 15, INNER_Y + 105, 0xFFFFFF);
        fontRenderer.drawString("Click Begin to start setup.", INNER_X + 15, INNER_Y + 125, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == beginSetup)
        {
        	SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        	handler.stopSound(setupMusic);
        	
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStepPersonalization(phoneStack, hand));
        }
    }
    
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
    	SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
    	handler.stopSound(setupMusic);
	}
}
