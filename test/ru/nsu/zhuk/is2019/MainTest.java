package ru.nsu.zhuk.is2019;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;


class MainTest {

    @Test
    void basicCalcTest() {
        assertEquals("1", Main.calculate(new StringReader("1")));
        assertEquals("8", Main.calculate(new StringReader("(7+1)")));
        assertEquals("25", Main.calculate(new StringReader("(5*5)")));
        assertEquals("2", Main.calculate(new StringReader("(104/52)")));
        assertEquals("0", Main.calculate(new StringReader("(1>4)")));
        assertEquals("1", Main.calculate(new StringReader("(17>4)")));
        assertEquals("0", Main.calculate(new StringReader("(400<2)")));
        assertEquals("1", Main.calculate(new StringReader("(-400<2)")));
        assertEquals("1", Main.calculate(new StringReader("(5=5)")));
        assertEquals("0", Main.calculate(new StringReader("(50=5)")));
        assertEquals("1", Main.calculate(new StringReader("(-9=-9)")));
        assertEquals("1", Main.calculate(new StringReader("(-3>-62)")));
        assertEquals("-25", Main.calculate(new StringReader("(-5*5)")));
        assertEquals("25", Main.calculate(new StringReader("(-5*-5)")));
        assertEquals("-1", Main.calculate(new StringReader("(-5/5)")));
        assertEquals("-5", Main.calculate(new StringReader("(5/-1)")));
        assertEquals("0", Main.calculate(new StringReader("(10%2)")));
        assertEquals("0", Main.calculate(new StringReader("(-10%2)")));
        assertEquals("0", Main.calculate(new StringReader("(-10%-2)")));
        assertEquals("0", Main.calculate(new StringReader("(10%-2)")));
        assertEquals("5", Main.calculate(new StringReader("(25%-20)")));
        assertEquals("5", Main.calculate(new StringReader("(25%20)")));
        assertEquals("-5", Main.calculate(new StringReader("(-25%-20)")));
        assertEquals("-5", Main.calculate(new StringReader("(-25%20)")));
    }

    @Test
    void complexCalcTest(){
        assertEquals("4", Main.calculate(new StringReader("(2+((3*4)/5))")));
        assertEquals("8", Main.calculate(new StringReader("(2*(2+((3*4)/5)))")));
        assertEquals("-8", Main.calculate(new StringReader("(-2*(2+((3*4)/5)))")));
        assertEquals("-32", Main.calculate(new StringReader("((2+((3*4)/5))*-8)")));
        assertEquals("1", Main.calculate(
                new StringReader("(((((((((2+((3*4)/5))*-8)/-4)+92)>99)*602)%-598)=4)<2)")));
    }

    @Test
    void syntaxErrorTest(){
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("1 + 2")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("1+2")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("(1+ 2)")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("((2+((3*4)/5))*-8")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("dsfsdf")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("(1#2)")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("[]?{}:{}")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("()")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("[1]?{1+1}{}")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("[1]?(1):(15)")));
        assertEquals("SYNTAX ERROR", Main.calculate(new StringReader("(((((())))))")));
    }

    @Test
    void soComplexEvenICanNotCalculateThisTest(){
        assertEquals("0", Main.calculate(
                new StringReader("[((10+20)>(20+10))]?{1}:{0}")));
        assertEquals("50", Main.calculate(
                new StringReader("[((5+4)=([(15/5)]?{1}:{7}))]?{17}:{(200%150)}")));
        assertEquals("-51", Main.calculate(
                new StringReader("[((5+4)=([(15/5)]?{1}:{7}))]?{17}:{[((5+4)=([(15/5)]?{1}:{7}))]?{17}:{(-200%149)}}")));
        assertEquals("13", Main.calculate(
                new StringReader("[-700]?{13}:{2}")));
        assertEquals("-2", Main.calculate(
                new StringReader("[-700]?{((([-700]?{13}:{2}*2)*-1)%6)}:{2}")));
    }

    @Test
    void runtimeErrorTest(){
        assertEquals("RUNTIME ERROR", Main.calculate(new StringReader("(5/0)")));
        assertEquals("RUNTIME ERROR", Main.calculate(new StringReader("(5/(3=60))")));
        assertEquals("2", Main.calculate(new StringReader("[0]?{(5/0)}:{2}")));
        assertEquals("RUNTIME ERROR", Main.calculate(new StringReader("[-5]?{(5/0)}:{2}")));
    }
}
