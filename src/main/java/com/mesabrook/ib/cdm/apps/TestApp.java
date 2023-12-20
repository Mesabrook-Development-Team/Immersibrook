package com.mesabrook.ib.cdm.apps;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.listener.ClickListener;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class TestApp extends Application
{
    @Override
    public void init()
    {
        Label helloWorld = new Label("Hello World!", 3, 5);
        Button helloButton = new Button(3, 20, "Hello!");

        helloButton.setClickListener(new ClickListener()
        {
            @Override
            public void onClick(int i, int i1, int i2)
            {
                helloWorld.setText("Button Clicked!");
                helloWorld.setTextColor(Color.BLUE);
            }
        });


        addComponent(helloWorld);
        addComponent(helloButton);
    }

    @Override
    public void load(NBTTagCompound nbtTagCompound)
    {

    }

    @Override
    public void save(NBTTagCompound nbtTagCompound)
    {

    }
}
