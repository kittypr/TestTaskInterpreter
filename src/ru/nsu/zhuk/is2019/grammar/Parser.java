package ru.nsu.zhuk.is2019.grammar;

import java.io.IOException;

import ru.nsu.zhuk.is2019.exceptions.SyntaxErrorException;


public class Parser {
    private Lexer lexer;
    private Lexeme currentLexeme;

    public Parser(Lexer lexer) throws IOException, IllegalArgumentException {
        this.lexer = lexer;
        currentLexeme = lexer.getLexeme();
    }

    public int calculate() throws IOException, IllegalArgumentException {
        int result = parseExpr();
        currentLexeme = lexer.getLexeme();
        if (currentLexeme.getType() == LexemeType.EOF){
            return result;
        } else {
            throw new SyntaxErrorException();
        }
    }

    private int parseExpr() throws IOException, IllegalArgumentException{
        LexemeType curType = currentLexeme.getType();
        if (curType == LexemeType.OPEN_PAREN){
            return parseBinExpr();
        } else if (curType == LexemeType.MINUS || curType == LexemeType.NUMBER){
            return parseConstExpr();
        } else if (curType == LexemeType.OPEN_BRACKET){
            return parseIfExpr();
        }
        throw new IOException();
    }

    // <if-expression> ::= "[" <expression> "]?(" <expression> "):("<expression>")"
    private int parseIfExpr() throws IOException, SyntaxErrorException{
        if (currentLexeme.getType() == LexemeType.OPEN_BRACKET){
            currentLexeme = lexer.getLexeme();
            int ifRes = parseExpr();

            // read "]?("
            if (currentLexeme.getType() != LexemeType.CLOSE_BRACKET) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.QMARK) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.OPEN_PAREN) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            int thenRes = parseExpr();


            if (currentLexeme.getType() != LexemeType.CLOSE_PAREN) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.COLON) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.OPEN_PAREN) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            int elseRes = parseExpr();


            if (currentLexeme.getType() != LexemeType.CLOSE_PAREN) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();

            return ifRes >=1 ? thenRes : elseRes;
        }
        throw new SyntaxErrorException();
    }

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
        throw new SyntaxErrorException();
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
        } else throw new IllegalArgumentException();
    }
}
