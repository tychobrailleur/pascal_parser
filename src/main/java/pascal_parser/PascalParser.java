package pascal_parser;

import pascal_parser.lexer.Lexer;
import pascal_parser.node.Node;
import pascal_parser.node.Start;
import pascal_parser.parser.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PushbackReader;

public class PascalParser {

    public Node parse(String filename) throws ParseException {
        try {
            FileReader infile = new FileReader(filename);
            Lexer l = new Lexer(new PushbackReader(new BufferedReader(infile), 1024));
            Parser p = new Parser(l);
            Start tree = p.parse();

            return tree;

        } catch (Exception e) {
            throw new ParseException("Error parsing file.", e);
        }
    }
}
