package src;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private final List<Token> tokens;
    private int current;
    private int line;
    private int column;

    public Lexer(String input) {
        this.input = input;
        this.tokens = new ArrayList<>();
        this.current = 0;
        this.line = 1;
        this.column = 1;
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            char c = advance();
            switch (c) {
                case ' ':
                case '\r':
                case '\t':
                    // Ignora espaços em branco
                    break;
                case '\n':
                    line++;
                    column = 1;
                    break;
                case '(':
                    addToken(TokenType.LEFT_PAREN, "(");
                    break;
                case ')':
                    addToken(TokenType.RIGHT_PAREN, ")");
                    break;
                case '{':
                    addToken(TokenType.LEFT_BRACE, "{");
                    break;
                case '}':
                    addToken(TokenType.RIGHT_BRACE, "}");
                    break;
                case ';':
                    addToken(TokenType.SEMICOLON, ";");
                    break;
                case '>':
                    if (peek() == '=') {
                        advance();
                        addToken(TokenType.GEQ, ">=");
                    } else {
                        addToken(TokenType.GREATER, ">");
                    }
                    break;
                case '<':
                    if (peek() == '=') {
                        advance();
                        addToken(TokenType.LEQ, "<=");
                    } else {
                        addToken(TokenType.LESS, "<");
                    }
                    break;
                case '=':
                    if (peek() == '=') {
                        advance();
                        addToken(TokenType.EQUALS, "==");
                    }else {
                        addToken(TokenType.ASSIGN, "=");
                    }
                    break;
                case '!':
                    if (peek() == '=') {
                        advance();
                        addToken(TokenType.NEQ, "!=");
                    } else {
                        throw new RuntimeException("Unexpected character: " + c);
                    }
                    break;
                case '+':
                    if (peek() == '+') {
                        advance();
                        addToken(TokenType.INCREMENT, "++");
                    } else{
                        addToken(TokenType.PLUS, "+");
                    }
                    break;
                case '-':
                    if (peek() == '-') {
                        advance();
                        addToken(TokenType.DECREMENT, "--");
                    }else {
                        addToken(TokenType.MINUS, "-");
                    }
                    break;
                default:
                    if (isDigit(c)) {
                        addToken(TokenType.NUMBER, readNumber());
                    } else if (isAlpha(c)) {
                        String expression = readIdentifier();
                        switch (expression) {
                            case "main":
                                addToken(TokenType.MAIN, "main");
                                break;
                            case "if":
                                addToken(TokenType.IF, "if");
                                break;
                            case "for":
                                addToken(TokenType.FOR, "for");
                                break;
                            case "int":
                                addToken(TokenType.INT, "int");
                                break;
                            case "string":
                                addToken(TokenType.STRING, "string");
                                break;
                            case "bool":
                                addToken(TokenType.BOOL, "bool");
                                break;
                            case "double":
                                addToken(TokenType.DOUBLE, "double");
                                break;
                            default:
                                addToken(TokenType.IDENTIFIER, expression.toString());
                                break;
                        }
                    } else {
                        throw new RuntimeException("Unexpected character: " + c);
                    }
            }
        }
        addToken(TokenType.EOF, "");
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= input.length();
    }

    private char advance() {
        current++;
        column++;
        return input.charAt(current - 1);
    }

    private void addToken(TokenType type, String lexeme) {
        tokens.add(new Token(type, lexeme, line, column));
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private String readNumber() {
        int start = current - 1;
        while (!isAtEnd() && isDigit(peek())) {
            advance();
        }
        return input.substring(start, current);
    }

    private String readIdentifier() {
        int start = current - 1;
        while (!isAtEnd() && (isAlpha(peek()) || isDigit(peek()))) {
            advance();
        }
        return input.substring(start, current);
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return input.charAt(current);
    }
}
