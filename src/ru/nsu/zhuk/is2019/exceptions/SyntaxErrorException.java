package ru.nsu.zhuk.is2019.exceptions;

public class SyntaxErrorException extends IllegalArgumentException {

    static private final String MESSAGE = "SYNTAX ERROR";

    public SyntaxErrorException(){
        super(MESSAGE);
    }
}
