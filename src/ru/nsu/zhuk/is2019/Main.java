package ru.nsu.zhuk.is2019;

import ru.nsu.zhuk.is2019.grammar.Parser;
import ru.nsu.zhuk.is2019.grammar.Lexer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {

    public static String calculate(Reader in){
        try {
            Parser parser = new Parser(new Lexer(in));
            return String.valueOf(parser.calculate());
        } catch (IOException err){
            err.printStackTrace();
            return "ERROR";
        } catch (IllegalArgumentException err){
            return err.getMessage();
        }

    }

    public static void main(String[] args) {
        try (InputStreamReader in = new InputStreamReader(System.in)) {
            System.out.println(calculate(in));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
