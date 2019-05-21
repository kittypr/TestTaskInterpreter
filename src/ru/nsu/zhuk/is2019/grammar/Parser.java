package ru.nsu.zhuk.is2019.grammar;

import java.io.IOException;

import ru.nsu.zhuk.is2019.exceptions.SyntaxErrorException;
import ru.nsu.zhuk.is2019.exceptions.RuntimeErrorException;


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
        throw new SyntaxErrorException();
    }

    private int parseIfExpr() throws IOException, SyntaxErrorException{
        if (currentLexeme.getType() == LexemeType.OPEN_BRACKET){ // checking correct start of if-expression
            currentLexeme = lexer.getLexeme();
            int ifRes = parseExpr();

            // reading "]?{"
            if (currentLexeme.getType() != LexemeType.CLOSE_BRACKET) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.QMARK) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.OPEN_BRACE) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();


            // calculating both of if-expression branches in case of syntax error
            int thenRes = 0;
            try{
                thenRes = parseExpr();
            } catch (RuntimeErrorException err){ // ignoring runtime errors if we don't use the result of this branch
                if (ifRes != 0) throw err;
            }

            // reading "}:{"
            if (currentLexeme.getType() != LexemeType.CLOSE_BRACE) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.COLON) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();
            if (currentLexeme.getType() != LexemeType.OPEN_BRACE) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();


            // calculating both of if-expression branches in case of syntax error
            int elseRes = 0;
            try {
                elseRes = parseExpr();
            } catch (RuntimeErrorException err){ // ignoring runtime errors if we don't use the result of this branch
                if (ifRes == 0) throw err;
            }

            // checking correct end of if-expression
            if (currentLexeme.getType() != LexemeType.CLOSE_BRACE) throw new SyntaxErrorException();
            currentLexeme = lexer.getLexeme();

            return ifRes != 0 ? thenRes : elseRes;
        }
        throw new SyntaxErrorException();
    }

    private int parseBinExpr() throws IOException, IllegalArgumentException {
        int divider;
        boolean runtimeError = false;
        if (currentLexeme.getType() == LexemeType.OPEN_PAREN){// checking correct start of bin-expression
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
                    divider =  parseExpr();
                    if (divider == 0) {
                        runtimeError = true; // only indicating RunTime Exception for continuation of syntax checking
                    } else {
                        exprRes = exprRes / divider;
                    }
                    break;
                case MOD:
                    currentLexeme = lexer.getLexeme();
                    divider =  parseExpr();
                    if (divider == 0) {
                        runtimeError = true; // only indicating RunTime Exception for continuation of syntax checking
                    }
                    else {
                        exprRes = exprRes % divider;
                    }
                    break;
                case GRT:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes > parseExpr() ? 1 : 0;
                    break;
                case LTR:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes < parseExpr() ? 1 : 0;
                    break;
                case EQ:
                    currentLexeme = lexer.getLexeme();
                    exprRes = exprRes == parseExpr() ? 1 : 0;
                    break;
            }
            if (currentLexeme.getType() == LexemeType.CLOSE_PAREN){// checking correct end of bin-expression
                currentLexeme = lexer.getLexeme();
                if (runtimeError) throw new RuntimeErrorException();
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
        } else throw new SyntaxErrorException();
    }
}
