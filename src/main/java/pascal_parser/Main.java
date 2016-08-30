package pascal_parser;

import pascal_parser.lexer.Lexer;
import pascal_parser.node.Start;
import pascal_parser.node.Switch;
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
            Lexer l = new Lexer(new PushbackReader(new BufferedReader(infile), 1024));
            Parser p = new Parser(l);
            Start tree = p.parse();

            Switch visitor = new Emitter();
            if (args.length == 2) {
                if (args[1].equals("--ast")) {
                    visitor = new AstDump();
                }
            }

            tree.apply(visitor);
        } catch (Exception e) {
            throw new RuntimeException("\n" + e.getMessage());
        }
    }
}
