package src;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    public void parse() {
        parseMain();
    }

    private void parseMain() {
        consume(TokenType.MAIN, "Expect 'main'");
        consume(TokenType.LEFT_PAREN, "Expect '(' after 'main'");
        consume(TokenType.RIGHT_PAREN, "Expect ')' after '('");
        consume(TokenType.LEFT_BRACE, "Expect '{' after ')'");
        parseA();
        consume(TokenType.RIGHT_BRACE, "Expect '}' after '{A}'");
    }

    private void parseA() {
        if(peek().type == TokenType.STRING || peek().type == TokenType.INT || peek().type == TokenType.BOOL || peek().type ==TokenType.DOUBLE) {
            parseB();
            consume(TokenType.IDENTIFIER, "Expect identifier after type");
            verfySemiColon(TokenType.SEMICOLON, "Expect ';' after identifier");
            parseA();
        }else if(peek().type == TokenType.IF || peek().type == TokenType.FOR){
            parseJ();
            parseA();
        }
        
    }

    private void parseB() {
        if (!match(TokenType.STRING, TokenType.INT, TokenType.BOOL, TokenType.DOUBLE)) {
            throw error(peek(), "Expect type (string, int, bool, double)");
        }
    }

    private void parseC() {
        parseG();
        parseH();
        parseI();
    }

    private void parseD() {
        consume(TokenType.INT, "Expect 'int' in for loop");
        consume(TokenType.IDENTIFIER, "Expect identifier after 'int'");
        consume(TokenType.SEMICOLON, "Expect ';' after identifier in for loop");
        consume(TokenType.IDENTIFIER, "Expect identifier after ';'");
        parseE();
        consume(TokenType.NUMBER, "Expect number after comparison operator ");
        consume(TokenType.SEMICOLON, "Expect ';' after expression in for loop");
        consume(TokenType.IDENTIFIER, "Expect identifier after ';'");
        parseF();
    }

    private void parseE() {
        if (!match(TokenType.GEQ, TokenType.GREATER, TokenType.LEQ, TokenType.LESS)) {
            throw error(peek(), "Expect comparison operator (>=, >, <=, <) in for loop condition");
        }
    }

    private void parseF() {
        if (!match(TokenType.INCREMENT, TokenType.DECREMENT)) {
            throw error(peek(), "Expect increment (++) or decrement (--) in for loop update");
        }
    }

    private void parseG() {
        if (!match(TokenType.IDENTIFIER, TokenType.NUMBER)) {
            throw error(peek(), "Expect identifier or number in condition");
        }
    }
    
    private void parseH() {
        if (!match(TokenType.EQUALS, TokenType.NEQ, TokenType.GEQ, TokenType.GREATER, TokenType.LEQ, TokenType.LESS)) {
            throw error(peek(), "Expect ('==', '!=', >=, >, <=, <) in condition");
        }
    }
    
    private void parseI() {
        if (!match(TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.TRUE, TokenType.FALSE)) {
            throw error(peek(), "Expect identifier or boolean literal in condition");
        }
    }

    private void parseJ() {
        if (peek().type == TokenType.IF) {
            consume(TokenType.IF, "Expect 'if'");
            consume(TokenType.LEFT_PAREN, "Expect '(' after 'if'");
            parseC();
            consume(TokenType.RIGHT_PAREN, "Expect ')' after condition");
            consume(TokenType.LEFT_BRACE, "Expect '{' after ')'");
            parseA();
            consume(TokenType.RIGHT_BRACE, "Expect '}' after block");
        } else if (peek().type == TokenType.FOR) {
            consume(TokenType.FOR, "Expect 'for'");
            consume(TokenType.LEFT_PAREN, "Expect '(' after 'for'");
            parseD();
            consume(TokenType.RIGHT_PAREN, "Expect ')' after for loop");
            consume(TokenType.LEFT_BRACE, "Expect '{' after ')'");
            parseA();
            consume(TokenType.RIGHT_BRACE, "Expect '}' after block");
        }
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }

    private Token verfySemiColon(TokenType type, String message) {
        if (check(type)) return advance();
        System.out.println("[line " + peek().line + ", column " + peek().column + "] Error at " + peek().lexeme + " - " + message);
        return null;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private ParseError error(Token token, String message) {
        System.err.println("[line " + token.line + ", column " + token.column + "] Error at " + token.lexeme + " - " + message);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {}
}
