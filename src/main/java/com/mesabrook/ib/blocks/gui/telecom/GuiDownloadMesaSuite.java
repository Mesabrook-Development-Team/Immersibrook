package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.util.ModUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiDownloadMesaSuite extends GuiPhoneBase
{
	ImageButton mesaSuiteIcon;
	MinedroidButton download;
	MinedroidButton cancel;
	public GuiDownloadMesaSuite(ItemStack phoneStack, EnumHand hand)
	{
		super(phoneStack, hand);
	}
	
    @Override
    protected boolean renderControlBar() 
    {
        return false;
    }
	
    @Override
    protected String getInnerTextureFileName()
    {
        return phoneStackData.getIconTheme() + "/app_screen_setup.png";
    }
    
    @Override
    public void initGui()
    {
    	super.initGui();
    	
    	int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
    	mesaSuiteIcon = new ImageButton(0, INNER_X + 8, INNER_Y + 3, 68, 68, phoneStackData.getIconTheme() + "/ms_logo.png", 32, 32);
    	download = new MinedroidButton(1, INNER_X + 48, lowerControlsY - 3, 105, "Download MesaSuite", 0xFFFFFF);
    	cancel = new MinedroidButton(2, INNER_X + 15, lowerControlsY - 3, 25, "No", 0xFFFFFF);
    
        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(mesaSuiteIcon)
                .add(download) 
                .add(cancel)
                .build());
    }
    
    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
    	super.doDraw(mouseX, mouseY, partialticks);
    	fontRenderer.drawString("MesaSuite Required!", INNER_X + 9, INNER_Y + 50, 0xFFFFFF); 
    	fontRenderer.drawSplitString("In order to access this feature, you need to download MesaSuite.", INNER_X + 10, INNER_Y + 70, INNER_TEX_WIDTH - 12, 0xFFFFFF);
    	
    	fontRenderer.drawSplitString("Do you want to download it now?", INNER_X + 10, INNER_Y + 120, INNER_TEX_WIDTH - 12, 0xFFFFFF);
    	fontRenderer.drawSplitString("(Only available for Windows)", INNER_X + 10, INNER_Y + 160, INNER_TEX_WIDTH - 12, 0xFFFFFF);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        
        if(button == cancel)
        {
        	Minecraft.getMinecraft().displayGuiScreen(new GuiHome(phoneStack, hand));
        } 
        if(button == download)
        {
        	ModUtils.openWebLinkThroughMC("https://mesabrook.com/downloads.html");
        }
    }
}
