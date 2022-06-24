package com.mesabrook.ib.util;

import java.util.List;

import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager.WirelessEmergencyAlert.Coordinate;

public class Math
{
    public static float num1;
    public static float num2;
    public static char operator;
    public static float answer;

    /**
     * Allows for simple calculations (addition, subtraction, multiplication, and division)
     * Parameters are passed in the following order: num1 operator num2.
     * Example: 1 + 1
     */
    public static float calculate(float num1In, char operatorIn, float num2In)
    {
        num1 = num1In;
        num2 = num2In;
        operator = operatorIn;

        switch (operator)
        {
            case '+':
                answer = num1 + num2;
                break;
            case '-':
                answer = num1 - num2;
                break;
            case '*':
                answer = num1 * num2;
                break;
            case '/':
                answer = num1 / num2;
                break;
        }
        
        return answer;
    }
    
    public static boolean isCoordinateInPolygon(Coordinate coordinate, List<Coordinate> polygon)
    {
        boolean result = false;
        int j = polygon.size() - 1;
        for (int i = 0; i < polygon.size(); i++)
        {
            if (polygon.get(i).getZ() < coordinate.getZ() && polygon.get(j).getZ() >= coordinate.getZ() || polygon.get(j).getZ() < coordinate.getZ() && polygon.get(i).getZ() >= coordinate.getZ())
            {
                if (polygon.get(i).getX() + (coordinate.getZ() - polygon.get(i).getZ()) / (polygon.get(j).getZ() - polygon.get(i).getZ()) * (polygon.get(j).getX() - polygon.get(i).getX()) < coordinate.getX())
                {
                    result = !result;
                }
            }
            j = i;
        }
        return result;
    }
}
