package pascal_parser;

import pascal_parser.analysis.DepthFirstAdapter;

public class SemanticCheckingVerifier extends DepthFirstAdapter {

    private final ParserConfig config;

    public SemanticCheckingVerifier(ParserConfig config) {
        this.config = config;
    }


}
