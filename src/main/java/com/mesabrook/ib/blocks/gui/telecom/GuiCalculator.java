package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.util.Math;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class GuiCalculator extends GuiPhoneBase
{
    // Text field
    GuiTextField calcText;

    // Numbers
    ImageButton btn0;
    ImageButton btn1;
    ImageButton btn2;
    ImageButton btn3;
    ImageButton btn4;
    ImageButton btn5;
    ImageButton btn6;
    ImageButton btn7;
    ImageButton btn8;
    ImageButton btn9;

    // Operators
    ImageButton btnEquals;
    ImageButton btnAdd;
    ImageButton btnSubtract;
    ImageButton btnMultiply;
    ImageButton btnDivide;
    ImageButton btnPeriod;

    // Others
    ImageButton btnClear;

    // Math
    float num1 = Float.MIN_VALUE;
    float num2 = Float.MIN_VALUE;
    char operator = ' ';

    public GuiCalculator(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/app_screen_no_bar.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();
        calcText = new GuiTextField(30, fontRenderer, INNER_X + 5, INNER_Y + 35, INNER_TEX_WIDTH - 10, 15);

        btn0 = new ImageButton(0, INNER_X + 40, INNER_Y + 160, 32, 32, "calcbtn_0.png", 32, 32);
        btn1 = new ImageButton(1, INNER_X + 5, INNER_Y + 125, 32, 32, "calcbtn_1.png", 32, 32);
        btn2 = new ImageButton(2, INNER_X + 40, INNER_Y + 125, 32, 32, "calcbtn_2.png", 32, 32);
        btn3 = new ImageButton(3, INNER_X + 75, INNER_Y + 125, 32, 32, "calcbtn_3.png", 32, 32);
        btn4 = new ImageButton(4, INNER_X + 5, INNER_Y + 90, 32, 32, "calcbtn_4.png", 32, 32);
        btn5 = new ImageButton(5, INNER_X + 40, INNER_Y + 90, 32, 32, "calcbtn_5.png", 32, 32);
        btn6 = new ImageButton(6, INNER_X + 75, INNER_Y + 90, 32, 32, "calcbtn_6.png", 32, 32);
        btn7 = new ImageButton(7, INNER_X + 5, INNER_Y + 55, 32, 32, "calcbtn_7.png", 32, 32);
        btn8 = new ImageButton(8, INNER_X + 40, INNER_Y + 55, 32, 32, "calcbtn_8.png", 32, 32);
        btn9 = new ImageButton(9, INNER_X + 75, INNER_Y + 55, 32, 32, "calcbtn_9.png", 32, 32);

        btnAdd = new ImageButton(10, INNER_X + 125, btn1.y + 15, 16, 16, "calcbtn_add.png", 32, 32);
        btnSubtract = new ImageButton(11, INNER_X + 142, btn1.y + 15, 16, 16, "calcbtn_subtract.png", 32, 32);
        btnMultiply = new ImageButton(12, INNER_X + 125, btn1.y - 3, 16, 16, "calcbtn_multiply.png", 32, 32);
        btnDivide = new ImageButton(13, INNER_X + 142, btn1.y - 3, 16, 16, "calcbtn_divide.png", 32, 32);

        btnEquals = new ImageButton(14, INNER_X + 125, INNER_Y + 160, 32, 32, "calcbtn_equal.png", 32, 32);
        btnClear = new ImageButton(15, INNER_X + 75, INNER_Y + 160, 32, 32, "calcbtn_clear.png", 32, 32);
        btnPeriod = new ImageButton(16, INNER_X + 5, INNER_Y + 160, 32, 32, "calcbtn_dot.png", 32, 32);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(btn0)
                .add(btn1)
                .add(btn2)
                .add(btn3)
                .add(btn4)
                .add(btn5)
                .add(btn6)
                .add(btn7)
                .add(btn8)
                .add(btn9)
                .add(btnAdd)
                .add(btnSubtract)
                .add(btnMultiply)
                .add(btnDivide)
                .add(btnEquals)
                .add(btnClear)
                .add(btnPeriod)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        calcText.drawTextBox();
        
        String strNum1 = "";
        if (num1 != Float.MIN_VALUE)
        {
        	strNum1 = Float.toString(num1);
        }
        
        String strNum2 = "";
        if (num2 != Float.MIN_VALUE)
        {
        	strNum2 = Float.toString(num2);
        }
        
        if (strNum1 != "" || strNum2 != "")
        {
	        String currentCalc = String.format("%s %s %s", strNum1, operator, strNum2);
	        fontRenderer.drawString(currentCalc, INNER_X + 5, INNER_Y + 20, 0xFFFFFF);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        calcText.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        
        if (typedChar == '+')
        {
        	actionPerformed(btnAdd);
        	return;
        }
        else if (typedChar == '-' && !calcText.getText().isEmpty())
        {
        	actionPerformed(btnSubtract);
        	return;
        }
        else if (typedChar == '/')
        {
        	actionPerformed(btnDivide);
        	return;
        }
        else if (typedChar == '*')
        {
        	actionPerformed(btnMultiply);
        	return;
        }
        else if (keyCode == Keyboard.KEY_NUMPADENTER || keyCode == Keyboard.KEY_RETURN)
        {
        	actionPerformed(btnEquals);
        	return;
        }
        
        calcText.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        // Numbers
        if(button.id >= 0 && button.id <= 9)
        {
        	calcText.setText(calcText.getText() + Integer.toString(button.id));
        }
        
        // Operator buttons
        if (button.id >= 10 && button.id <= 13)
        {
        	if (num1 == Float.MIN_VALUE)
        	{
        		num1 = tryParseFloat(calcText.getText());
        		if (num1 != Float.MIN_VALUE)
        		{
        			calcText.setText("");
        		}
        	}
        	else
        	{
        		num2 = tryParseFloat(calcText.getText());
        		if (num2 == Float.MIN_VALUE)
        		{
        			return;
        		}
        		
        		num1 = Math.calculate(num1, operator, num2);
        		num2 = -1;
        		calcText.setText("");
        	}
        }

        // Addition
        if(button == btnAdd)
        {
            operator = '+';
        }

        // Subtraction
        if(button == btnSubtract)
        {
            operator = '-';
        }

        // Multiplication
        if(button == btnMultiply)
        {
            operator = '*';
        }

        // Division
        if(button == btnDivide)
        {
            operator = '/';
        }

        // Equals
        if(button == btnEquals)
        {
        	num2 = tryParseFloat(calcText.getText());
        	if (num2 == Float.MIN_VALUE)
        	{
        		return;
        	}
        	
            try
            {
                float answer = Math.calculate(num1, operator, num2);
                num1 = Float.MIN_VALUE;
                num2 = Float.MIN_VALUE;
                operator = ' ';
                calcText.setText(Float.toString(answer));
            }
            catch(Exception ex)
            {
                calcText.setText("Math Error (" + ex + ")");
            }
        }
        
        // Clear
        if (button == btnClear)
        {
        	num1 = Float.MIN_VALUE;
            num2 = Float.MIN_VALUE;
            operator = ' ';
            calcText.setText("");
        }
        
        // Period
        if (button == btnPeriod)
        {
        	calcText.setText(calcText.getText() + ".");
        }
    }
    
    private float tryParseFloat(String fromString)
    {
    	float result = Float.MIN_VALUE;
    	try
		{
			result = Float.parseFloat(calcText.getText());
		}
		catch(NumberFormatException ex)
		{
			Toaster.forPhoneNumber(getCurrentPhoneNumber()).queueToast(new Toast("Not a number!", 0xFF0000));
			return Float.MIN_VALUE;
		}
    	
    	return result;
    }
}
