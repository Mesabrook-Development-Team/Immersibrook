package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class GuiMagicEightBall extends GuiPhoneBase
{
    GuiTextField questionBox;
    MinedroidButton submit;
    ImageButton help;
    
    private static String displayAnswer = "Ask me your questions.";
	private static final List<String> answers = new ArrayList<>();
	
	private int questionBoxX;
	private int questionBoxY;
	private int questionBoxWidth;
	
	private int submitButtonX;
	private int submitButtonY;
	private int submitButtonWidth;
	
	private IndependentTimer timer;
	
	static 
	{
        answers.add("It is certain.");
        answers.add("It is decidedly so.");
        answers.add("Without a doubt.");
        answers.add("Yes � definitely.");
        answers.add("You may rely on it.");
        answers.add("As I see it, yes.");
        answers.add("Most likely.");
        answers.add("Outlook good.");
        answers.add("Yes.");
        answers.add("Signs point to yes.");
        answers.add("Reply hazy, try again.");
        answers.add("Ask again later.");
        answers.add("Better not tell you now.");
        answers.add("Cannot predict now.");
        answers.add("Concentrate and ask again.");
        answers.add("Don't count on it.");
        answers.add("My reply is no.");
        answers.add("My sources say no.");
        answers.add("Outlook not so good.");
        answers.add("Very doubtful.");
    }
	
	public GuiMagicEightBall(ItemStack phoneStack, EnumHand hand) 
	{
		super(phoneStack, hand);
		displayAnswer = "~Ask me your questions~";
	}

	@Override
	protected String getInnerTextureFileName() 
	{
		return phoneStackData.getIconTheme() + "/app_screen_purple.png";
	}
	
	public static String getRandomAnswer() 
	{
        Random random = new Random();
        int index = random.nextInt(answers.size());
        return displayAnswer = new TextComponentString(TextFormatting.LIGHT_PURPLE + "~" + answers.get(index) + "~").getFormattedText();
    }
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 50;
		questionBox = new GuiTextField(1, fontRenderer, INNER_X + 5, INNER_Y + 165, 151, 10);
		submit = new MinedroidButton(2, INNER_X + 61, lowerControlsY + 15, 40, "Ask", 0x000000);
		
		help = new ImageButton(4, INNER_X + 145, INNER_Y + 18, 12, 12, "plex" + "/icn_help.png", 32, 32); 
		
		buttonList.add(submit);
		buttonList.add(help);
		
		Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks)
	{
		super.doDraw(mouseX, mouseY, partialticks);
		fontRenderer.drawString(new TextComponentString("Magic 8 Ball").getFormattedText(), INNER_X + 5, INNER_Y + 20, 0xFFFFFF);
		drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.ITALIC + displayAnswer).getFormattedText(), INNER_X + 80, INNER_Y + 130, 0xFFFFFF);
		
		questionBox.drawTextBox();
		
		fontRenderer.drawString("Ask the Magic 8 Ball!", questionBox.x, questionBox.y - 10, 0xFFFFFF);
		showBall();
		
	}
	
    private void showBall()
    {
    	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/" + "plex" + "/eightball.png"));
    	GlStateManager.color(1, 1, 1);
    	drawScaledCustomSizeModalRect(INNER_X + 45, INNER_Y + 50, 0, 0, 70, 70, 70, 70, 70, 70);
    }
    
	
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        questionBox.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        questionBox.textboxKeyTyped(typedChar, keyCode);
    }
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
		if(button == submit)
		{
			if(questionBox.getText() != "" && questionBox.getText().contains("?"))
			{
				getRandomAnswer();
				Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1F, 1F);
			}
			else
			{
				displayAnswer = "~That is not a question~";
			}
		}
		
		if(button == help)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiAboutMagicEightBall(phoneStack, hand));
		}
	}
}
