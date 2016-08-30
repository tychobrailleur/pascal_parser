package pascal_parser;


import pascal_parser.analysis.DepthFirstAdapter;
import pascal_parser.node.Node;

public class AstDump extends DepthFirstAdapter {

    private final static int SIZE = 2;
    private int level;

    public void defaultIn(Node node) {
        for (int i = 0; i < level*SIZE; i++) {
            System.out.print("-");
        }
        level++;
        System.out.println(node.getClass());
    }

    public void defaultOut(Node node) {
        level--;
    }
}
