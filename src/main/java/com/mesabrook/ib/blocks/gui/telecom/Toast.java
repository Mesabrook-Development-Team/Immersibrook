package com.mesabrook.ib.blocks.gui.telecom;

public class Toast {
	private int fadeInFactor;
	private int displayTicks;
	private int fadeOutFactor;
	private String text;
	private int color;
	
	public Toast(int fadeInFactor, int displayTicks, int fadeOutFactor, String text, int color)
	{
		this.fadeInFactor = fadeInFactor;
		this.displayTicks = displayTicks;
		this.fadeOutFactor = fadeOutFactor;
		this.text = text;
		this.color = color;
	}
	
	public Toast(String text)
	{
		this(text, 0xFFFFFF);
	}
	
	public Toast(String text, int color)
	{
		this(5, 80, 5, text, color);
	}
	
	public int getFadeInFactor() {
		return fadeInFactor;
	}
	
	public void setFadeInFactor(int fadeInFactor) {
		this.fadeInFactor = fadeInFactor;
	}
	
	public int getDisplayTicks() {
		return displayTicks;
	}
	
	public void setDisplayTicks(int displayTicks) {
		this.displayTicks = displayTicks;
	}
	
	public int getFadeOutFactor() {
		return fadeOutFactor;
	}
	
	public void setFadeOutFactor(int fadeOutFactor) {
		this.fadeOutFactor = fadeOutFactor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
