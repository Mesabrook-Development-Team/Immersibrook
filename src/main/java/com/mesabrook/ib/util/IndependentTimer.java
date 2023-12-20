package com.mesabrook.ib.util;

public class IndependentTimer
{
    private long startTime;
    private long elapsedTime;

    public IndependentTimer()
    {
        reset();
    }

    public void reset()
    {
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
    }

    public void update()
    {
        long currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - startTime;
    }

    public void stop()
    {
        long stoppedTime = elapsedTime;
        elapsedTime = stoppedTime;
    }

    public long getElapsedTime()
    {
        return elapsedTime;
    }

    public long setElapsedTime(long elapsedTimeIn)
    {
        return elapsedTime = elapsedTimeIn;
    }
}
