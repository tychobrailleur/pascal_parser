package pascal_parser;

import pascal_parser.lexer.Lexer;
import pascal_parser.node.Start;
import pascal_parser.parser.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PushbackReader;


public class Main {

    public static void main(String args[]) {

        if (args.length < 1) {
            System.exit(1);
        }

        try {
            FileReader infile = new FileReader(args[0]);
            Lexer l = new PrintLexer(new PushbackReader(new BufferedReader(infile), 1024));
            Parser p = new Parser(l);
            Start tree = p.parse();

            Emitter emitter = new Emitter();
            tree.apply(emitter);
        } catch (Exception e) {
            throw new RuntimeException("\n" + e.getMessage());
        }
    }
}
