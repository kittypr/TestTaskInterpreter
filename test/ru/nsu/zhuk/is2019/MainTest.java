package ru.nsu.zhuk.is2019;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;




class MainTest {

    @Test
    void basicCalcTest() {
        try {
            assertEquals("1", Main.calculate(new StringReader("1")));
            assertEquals("2", Main.calculate(new StringReader("(1+1)")));
            assertEquals("2", Main.calculate(new StringReader("(1*2)")));
            assertEquals("1", Main.calculate(new StringReader("(2/2)")));
            assertEquals("6", Main.calculate(new StringReader("(2+(2*2))")));
            assertEquals("3", Main.calculate(new StringReader("(2+(2/2))")));
            assertEquals("2", Main.calculate(new StringReader("(1+1)")));
            assertEquals("8", Main.calculate(new StringReader("((2+2)*2)")));
            assertEquals("-3", Main.calculate(new StringReader("-3")));
            assertEquals("-3", Main.calculate(new StringReader("(1-4)")));
            assertEquals("-3", Main.calculate(new StringReader("(1*-3)")));
            assertEquals("-3", Main.calculate(new StringReader("(-3*1)")));
            assertEquals("2", Main.calculate(new StringReader("(-2+(2*2))")));
        } catch (Exception e) {
            System.err.println("Exception in unexpected place during test execution");
            e.printStackTrace();
        }
    }

    @Test
    void comlexCalcTest(){
        try {
            assertEquals("4", Main.calculate(new StringReader("(2+((3*4)/5))")));
            assertEquals("8", Main.calculate(new StringReader("(2*(2+((3*4)/5)))")));
            assertEquals("-8", Main.calculate(new StringReader("(-2*(2+((3*4)/5)))")));
            assertEquals("-32", Main.calculate(new StringReader("((2+((3*4)/5))*-8)")));
        } catch (Exception e) {
            System.err.println("Exception in unexpected place during test execution");
            e.printStackTrace();
        }
    }
}
