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

import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;

public class OptionProcessor {

    private final Options options = new Options();

    public OptionProcessor() {
        options.addOption("h", "help", false, "Display this ");
        options.addOption(null, "ast", false, "Dumps AST.");
    }

    public ParserConfig processOptions(String[] args) {
        ParserConfig config = new ParserConfig();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine line = parser.parse(options, args);
            config.setDumpAst(line.hasOption("ast"));
            config.setArgs(line.getArgs());
        } catch(ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }

        return config;
    }
}
