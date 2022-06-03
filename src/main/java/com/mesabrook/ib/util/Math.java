package com.mesabrook.ib.util;

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
    public static void calculate(float num1In, char operatorIn, float num2In)
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
    }
}
