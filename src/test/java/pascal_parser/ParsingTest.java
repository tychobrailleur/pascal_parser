package pascal_parser;

import org.junit.Test;
import pascal_parser.node.Node;

public class ParsingTest {

    private PascalParser parser = new PascalParser();

    @Test
    public void testParseExamples() throws Exception {
        for (int i = 1; i <= 10; i++) {
            parser.parse("src/test/resources/test" + i + ".pas");
        }
    }
}
