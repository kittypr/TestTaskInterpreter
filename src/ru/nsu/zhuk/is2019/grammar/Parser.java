package ru.nsu.zhuk.is2019.grammar;

import java.io.IOException;


public class Parser {
    private Lexer lexer;
    private Lexeme currentLexeme;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        currentLexeme = lexer.getLexeme();
    }

    public int calculate() throws IOException {
        int result = parseBinExpr();
        if (currentLexeme.getType() == LexemeType.EOF){
            return result;
        } else {
            throw new IOException("Syntax error.");
        }
    }

    private int parseBinExpr() throws IOException {
        if (currentLexeme.getType() == LexemeType.MINUS || currentLexeme.getType() == LexemeType.NUMBER){
            return parseConstExpr();
        }

        if (currentLexeme.getType() == LexemeType.LEFT_PAREN){
            currentLexeme = lexer.getLexeme();
            int exprRes = parseBinExpr();
            switch (currentLexeme.getType()){
                case PLUS:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes + parseBinExpr();
                    break;
                case MINUS:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes - parseBinExpr();
                    break;
                case ASTERISK:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes * parseBinExpr();
                    break;
                case DIV:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes / parseBinExpr();
                    break;
            }
            if (currentLexeme.getType() == LexemeType.RIGHT_PAREN){
                currentLexeme = lexer.getLexeme();
                return exprRes;
            }
        }
        throw new IOException();
    }

    private int parseConstExpr() throws IOException {
        int sign = 1;
        if (currentLexeme.getType() == LexemeType.MINUS) {
            currentLexeme = lexer.getLexeme();
            sign = -1;
        }
        if (currentLexeme.getType() == LexemeType.NUMBER){
            int result = sign * Integer.parseInt(currentLexeme.getLexemeText());
            currentLexeme = lexer.getLexeme();
            return result;
        }
        else throw new IOException();
    }
}

