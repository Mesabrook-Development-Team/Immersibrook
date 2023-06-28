package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;

public class GuiAppSplashScreen extends GuiPhoneBase
{
    ImageButton logo;
    private String logoPath;
    private String appName;
    private String splashColor;
    int progress = 0;

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
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, getAppName() + " " + Reference.MINEDROID_VERSION, INNER_X + 80, INNER_Y + 150, 0xFFFFFF);

        progress++;
        if(progress > 135)
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
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
