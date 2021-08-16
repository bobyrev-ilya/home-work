package com.sbrf.reboot.calculator;

import org.junit.After;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream stream = new PrintStream(outputStream);

    @Test
    void getAddition() {
        assertEquals(9, Calculator.getAddition(4, 5));
    }

    @Test
    void getSubtraction() {
        assertEquals(-1, Calculator.getSubtraction(4, 5));
    }

    @Test
    void getMultiplication() {
        assertEquals(20, Calculator.getMultiplication(4, 5));
    }

    @Test
    void getSquare() {
        assertEquals(36, Calculator.getSquare(6));
    }

    @Test
    void getMod() {
        assertEquals(1, Calculator.getMod(7, 3));
    }

    @Test
    void getDivision() {
        assertEquals(3, Calculator.getDivision(9, 3));
    }

    @Test
    void classHasSevenMethods() {
        assertEquals(7, Calculator.class.getMethods().length - Object.class.getMethods().length);
    }

    @Test
    void checkInfoMessage(){
        System.setOut(stream);
        Calculator.infoMessage();
        assertEquals("Теперь я знаю, что такое форк",outputStream.toString());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}