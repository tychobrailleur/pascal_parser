package pascal_parser;

import org.junit.Test;
import pascal_parser.node.Node;

public class ParsingTest {

    private PascalParser parser = new PascalParser();

    public void testParseExample1() throws Exception {
        Node node = parser.parse("src/test/resources/test1.pas");
    }

    @Test
    public void testParseExample2() throws Exception {
        parser.parse("src/test/resources/test2.pas");
    }

    @Test
    public void testParseExample3() throws Exception {
        parser.parse("src/test/resources/test3.pas");
    }
}
