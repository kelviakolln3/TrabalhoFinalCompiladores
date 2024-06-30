package src;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <source file>");
            System.exit(1);
        }

        String sourceFilePath = "input/program.txt";

        try {
            String source = new String(Files.readAllBytes(Paths.get(sourceFilePath)),StandardCharsets.UTF_8);

            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.tokenize();

            for (Token token : tokens) {
                System.out.println("Type: " + token.type + " Lexema: " + "'" + token.lexeme + "'");
            }

            Parser parser = new Parser(tokens);
            parser.parse();

            System.out.println("Parsing completed successfully!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
