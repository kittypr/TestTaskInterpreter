package ru.nsu.zhuk.is2019.exceptions;

public class SyntaxErrorException extends IllegalArgumentException {

    static final String message = "SYNTAX ERROR";

    public SyntaxErrorException(){
        super(message);
    }
}
