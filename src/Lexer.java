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
                case ' ': // O lexer não faz nada quando encontra um espaço em branco.
                case '\r': // O lexer não faz nada quando encontra o fim de uma linha
                case '\t':
                    // O lexer não faz nada quando encontra um caractere de tabulação.
                    break;
                case '\n':
                    line++;
                    column = 1;
                    break;
                case '(': // Verificar o caracter e adiciona o token e o lexema correspondente
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
                        throw new RuntimeException("Unexpected character: " + c); // Erro se não encontrar o caracter
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
                default: // Se não for nenhum dos outros cassos
                    if (isDigit(c)) { // Verifica se o caracter é do tipo numerico se for ele vai ler o numero e adicionar o token
                        addToken(TokenType.NUMBER, readNumber());
                    } else if (isAlpha(c)) { // Verifica se o caracter é uma palavra
                        String expression = readIdentifier();
                        switch (expression) { // valida se não são as palavras reservadas
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
                            default: // se não for nenhuma palavra reservada ele adiciona o token e o lexema
                                addToken(TokenType.IDENTIFIER, expression.toString());
                                break;
                        }
                    } else {
                        throw new RuntimeException("Unexpected character: " + c); // Erro se não encontrar o caracter
                    }
            }
        }
        addToken(TokenType.EOF, ""); // Adiciona o token de final de código
        return tokens; // Ele retornar a lista de tokens que serão lidos
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
