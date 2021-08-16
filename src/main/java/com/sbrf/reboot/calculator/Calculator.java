package com.sbrf.reboot.calculator;

public class Calculator {

    public static int getAddition(int a, int b) {
        return a + b;
    }

    public static int getSubtraction(int a, int b) {
        return a - b;
    }

    public static long getMultiplication(int a, int b) {
        return a * b;
    }

    public static int getDivision(int a, int b) {
        return a / b;
    }

    public static void infoMessage() {
        System.out.print("Теперь я знаю, что такое форк");
    }

    public static long getSquare(int a) {
        return a * a;
    }

    public static int getMod(int a, int b) {
        return a % b;
    }
}
