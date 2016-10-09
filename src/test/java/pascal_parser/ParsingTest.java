package pascal_parser;

import org.junit.Test;

public class ParsingTest {

    private PascalParser parser = new PascalParser();

    @Test
    public void testParseExamples() throws Exception {
        for (int i = 1; i <= 14; i++) {
            parser.parse("src/test/resources/test" + i + ".pas");
        }
    }
}
