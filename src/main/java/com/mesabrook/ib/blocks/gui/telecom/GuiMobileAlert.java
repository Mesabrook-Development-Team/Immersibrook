package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.ImageButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiMobileAlert extends GuiPhoneBase
{
    MinedroidButton close;
    ImageButton alertIcon;
    public static HashMap<Integer, String> labelsByNumber = new HashMap<>();
    public static HashMap<Integer, String> textByNumber = new HashMap<>();
    private String currentLabel;
    private String currentText;

    public GuiMobileAlert(ItemStack phoneStack, EnumHand hand)
    {
    	super(phoneStack, hand);
    	
    	currentLabel = labelsByNumber.get(phoneStackData.getPhoneNumber());
    	currentText = textByNumber.get(phoneStackData.getPhoneNumber());
    	
    	labelsByNumber.remove(phoneStackData.getPhoneNumber());
    	textByNumber.remove(phoneStackData.getPhoneNumber());
    	
    	currentLabel = currentLabel != null ? currentLabel : "";
    	currentText = currentText != null ? currentText : "";
	}

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/app_screen.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();

        alertIcon = new ImageButton(0, INNER_X + 3, INNER_Y + 18, 13, 13, "icn_alert.png", 32, 32);
        close = new MinedroidButton(4, INNER_X + INNER_TEX_WIDTH - 43, INNER_Y + 17, 39, new TextComponentTranslation("im.alert.close").getFormattedText(), 0xFFFFFF);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(alertIcon)
                .add(close)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString(new TextComponentTranslation("im.alert.name").getFormattedText(), INNER_X + 20, INNER_Y + 20, 0xFFFFFF);
        
        String formattedlabel = currentLabel.toUpperCase();
        int fontWidth = fontRenderer.getStringWidth(formattedlabel);
        
        fontRenderer.drawStringWithShadow(formattedlabel, (INNER_X + (INNER_TEX_WIDTH) / 2) - (fontWidth / 2), INNER_Y + 36, 0xFF0000);
        fontRenderer.drawSplitString(currentText, INNER_X + 2, INNER_Y + 48, INNER_TEX_WIDTH - 4, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == close)
        {
            if(isPhoneUnlocked)
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiHome(phoneStack, hand));
            }
            else
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiLockScreen(phoneStack, hand));
            }
        }
    }
}
