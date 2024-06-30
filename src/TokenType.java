package src;

public enum TokenType {
    // Palavras-chave
    MAIN,       // main
    IF,         // if
    ELSE,       // else
    FOR,        // for
    WHILE,      // while
    DO,         // do
    BREAK,      // break
    CONTINUE,   // continue
    RETURN,     // return
    TRUE,       // true
    FALSE,      // false
    // Tipos de dados
    STRING,     // string
    INT,        // int
    BOOL,       // bool
    DOUBLE,     // double
    // Operadores relacionais
    GEQ,        // >=
    GREATER,    // >
    LEQ,        // <=
    LESS,       // <
    EQUALS,     // ==
    NEQ,        // !=
    // Operadores aritméticos
    PLUS,       // +
    MINUS,      // -
    MULTIPLY,   // *
    DIVIDE,     // /
    // Operadores de atribuição
    ASSIGN,     // =
    PLUS_ASSIGN,// +=
    MINUS_ASSIGN,// -=
    // Delimitadores
    SEMICOLON,  // ;
    COMMA,      // ,
    LEFT_PAREN, // (
    RIGHT_PAREN,// )
    LEFT_BRACE, // {
    RIGHT_BRACE,// }
    // Outros símbolos
    IDENTIFIER, // identificadores
    NUMBER,     // números inteiros ou decimais
    STRING_LITERAL, // literais de string
    
    // Operadores de incremento e decremento
    INCREMENT,  // ++
    DECREMENT,  // --
    
    // Fim de arquivo
    EOF         // fim de arquivo
}
