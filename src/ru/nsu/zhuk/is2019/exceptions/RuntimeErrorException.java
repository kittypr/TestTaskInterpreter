package ru.nsu.zhuk.is2019.exceptions;

public class RuntimeErrorException extends ArithmeticException{

    static final private String MESSAGE = "RUNTIME ERROR";

    private String expression;
    private int line;

    public RuntimeErrorException(){
        super(MESSAGE);
    }

    public RuntimeErrorException(String expression, int line){
        this.expression = expression;
        this.line = line;
    }

    public String getErrorExpression(){
        return expression + " : " + String.valueOf(line);
    }
}
