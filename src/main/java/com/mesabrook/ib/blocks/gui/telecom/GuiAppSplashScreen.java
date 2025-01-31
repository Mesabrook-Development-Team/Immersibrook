package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.util.IndependentTimer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GuiAppSplashScreen<NextApp extends GuiPhoneBase> extends GuiPhoneBase
{
    ImageButton logo;
    private String logoPath;
    private ResourceLocation logoResourceLocation;
    private String appName;
    private String splashColor;
    int progress = 0;
    IndependentTimer timer;
    private Class<NextApp> nextAppClazz;
    private Runnable postOpen;

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

    public ResourceLocation getLogoResourceLocation() {
		return logoResourceLocation;
	}

	public void setLogoResourceLocation(ResourceLocation logoResourceLocation) {
		this.logoResourceLocation = logoResourceLocation;
	}

	public String getAppName()
    {
        return appName;
    }

    public String setAppName(String appNameIn)
    {
        return this.appName = appNameIn;
    }

    public GuiAppSplashScreen(ItemStack phoneStack, EnumHand hand, Class<NextApp> nextAppClazz, Runnable postOpen)
    {
        super(phoneStack, hand);
        this.nextAppClazz = nextAppClazz;
        this.postOpen = postOpen;
    }
    
    public GuiAppSplashScreen(ItemStack phoneStack, EnumHand hand, Class<NextApp> nextAppClazz)
    {
    	this(phoneStack, hand, nextAppClazz, null);
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
    	if (getLogoResourceLocation() != null)
    	{
    		logo = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 16, INNER_Y + 70, 32, 32, getLogoResourceLocation(), 32, 32, 32, 32);
    	}
    	else
    	{
    		logo = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 16, INNER_Y + 70, 32, 32, phoneStackData.getIconTheme() + "/" + getLogoPath(), 32, 32);
    	}
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
        	NextApp phoneBase;
        	try
        	{
        		phoneBase = nextAppClazz.getConstructor(ItemStack.class, EnumHand.class).newInstance(phoneStack, hand);
        	}
        	catch(Exception ex)
        	{
        		Main.logger.error("An error occurred while trying to go to next app", ex);
                timer.stop();
                timer.reset();
        		return;
        	}
        	
        	mc.displayGuiScreen(phoneBase);
            
            if (postOpen != null)
            {
            	postOpen.run();
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
