package pascal_parser;

/*

   Copyright (C) 2016  Sébastien Le Callonnec

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software Foundation,
   Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA

*/

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
