package pascal_parser;


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
