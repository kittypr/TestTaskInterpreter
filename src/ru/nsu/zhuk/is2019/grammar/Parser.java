package ru.nsu.zhuk.is2019.grammar;

import java.io.IOException;


public class Parser {
    private Lexer lexer;
    private Lexeme currentLexeme;

    public Parser(Lexer lexer) throws IOException, IllegalArgumentException {
        this.lexer = lexer;
        currentLexeme = lexer.getLexeme();
    }

    public int calculate() throws IOException, IllegalArgumentException {
        int result = parseExpr();
        if (currentLexeme.getType() == LexemeType.EOF){
            return result;
        } else {
            throw new IOException("Syntax error.");
        }
    }

    private int parseExpr() throws IOException, IllegalArgumentException{
        LexemeType curType = currentLexeme.getType();
        if (curType == LexemeType.OPEN_PAREN){
            int result = parseBinExpr();
            currentLexeme = lexer.getLexeme();
            return result;
        } else if (curType == LexemeType.MINUS || curType == LexemeType.NUMBER){
            int result = parseConstExpr();
            currentLexeme = lexer.getLexeme();
            return result;
        } else if (curType == LexemeType.OPEN_BRACKET){
            int result = parseIfExpr();
            currentLexeme = lexer.getLexeme();
            return result;
        }
        throw new IOException();
    }

    private int parseIfExpr(){return 0;}

    private int parseBinExpr() throws IOException, IllegalArgumentException {
        if (currentLexeme.getType() == LexemeType.OPEN_PAREN){
            currentLexeme = lexer.getLexeme();
            int exprRes = parseExpr();
            switch (currentLexeme.getType()){
                case PLUS:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes + parseExpr();
                    break;
                case MINUS:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes - parseExpr();
                    break;
                case ASTERISK:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes * parseExpr();
                    break;
                case DIV:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes / parseExpr();
                    break;
            }
            if (currentLexeme.getType() == LexemeType.CLOSE_PAREN){
                currentLexeme = lexer.getLexeme();
                return exprRes;
            }
        }
        throw new IOException();
    }

    private int parseConstExpr() throws IOException, IllegalArgumentException {
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

