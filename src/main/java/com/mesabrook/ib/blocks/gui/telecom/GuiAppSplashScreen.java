package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;

public class GuiAppSplashScreen extends GuiPhoneBase
{
    ImageButton logo;
    private String logoPath;
    private String appName;
    private String splashColor;
    int progress = 0;
    IndependentTimer timer;

    public String getSplashColor()
    {
        return splashColor;
    }

    public String setSplashColor(String colorIn)
    {
        return this.splashColor = "system/app_splash_" + colorIn + ".png";
    }

    public String getLogoPath()
    {
        return logoPath;
    }

    public String setLogoPath(String logoPathIn)
    {
        return this.logoPath = logoPathIn;
    }

    public String getAppName()
    {
        return appName;
    }

    public String setAppName(String appNameIn)
    {
        return this.appName = appNameIn;
    }

    public GuiAppSplashScreen(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
       try
       {
           return splashColor;
       }
       catch(Exception ex)
       {
           return "system/app_screen_no_bar.png";
       }
    }

    @Override
    public void initGui()
    {
    	super.initGui();
        logo = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 16, INNER_Y + 70, 32, 32, phoneStackData.getIconTheme() + "/" + getLogoPath(), 32, 32);
        buttonList.add(logo);
        timer = new IndependentTimer();
        timer.update();
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.BOLD + getAppName()).getFormattedText(), INNER_X + 80, INNER_Y + 150, 0xFFFFFF);

        timer.update();
        if(timer.getElapsedTime() > 300)
        {
            if(getLogoPath().contains("icn_calc"))
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiCalculator(phoneStack, hand));
            }
            if(getLogoPath().contains("icn_musicplayer"))
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiSoundPlayer(phoneStack, hand));
            }
            if(getLogoPath().contains("icn_contacts"))
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBook(phoneStack, hand));
            }
            if(getLogoPath().contains("icn_settings"))
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
            }
            if(getLogoPath().contains("phone"))
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiEmptyPhone(phoneStack, hand));

                PhoneQueryPacket queryPacket = new PhoneQueryPacket();
                queryPacket.forNumber = getCurrentPhoneNumber();

                int nextID = ClientSideHandlers.TelecomClientHandlers.getNextHandlerID();

                ClientSideHandlers.TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, ClientSideHandlers.TelecomClientHandlers::onPhoneQueryResponseForPhoneApp);
                queryPacket.clientHandlerCode = nextID;
                PacketHandler.INSTANCE.sendToServer(queryPacket);
            }
            timer.stop();
            timer.reset();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
