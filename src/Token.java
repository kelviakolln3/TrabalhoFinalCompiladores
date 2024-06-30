package src;

// Representa um token na linguagem
public class Token {
    public TokenType type; // Tipo do token
    public String lexeme;  // Lexema (texto) do token
    public int line;       // Linha onde o token foi encontrado
    public int column;     // Coluna onde o token foi encontrado

    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("Token{type=%s, lexeme='%s', line=%d, column=%d}", type, lexeme, line, column);
    }
}