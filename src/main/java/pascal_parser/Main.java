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

import pascal_parser.node.Node;
import pascal_parser.node.Switch;


public class Main {

    private final static PascalParser parser = new PascalParser();
    private final static OptionProcessor options = new OptionProcessor();

    public static void main(String args[]) {

        ParserConfig config = options.processOptions(args);

        if (config.getArgs().length < 1) {
            System.exit(1);
        }

        for (String arg: config.getArgs()) {
            try {
                Node tree = parser.parse(arg);

                Switch visitor = new Emitter();
                if (config.isDumpAst()) {
                    System.out.println("Dumping AST...");
                    visitor = new AstDump();
                }

                tree.apply(visitor);

                final SemanticCheckingVerifier semanticCheckingVerifier = new SemanticCheckingVerifier(config);
                tree.apply(semanticCheckingVerifier);


            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("\n" + e.getMessage());
            }
        }
    }
}
