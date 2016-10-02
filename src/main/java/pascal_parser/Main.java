package pascal_parser;

import pascal_parser.node.Node;
import pascal_parser.node.Switch;


public class Main {

    private final static PascalParser parser = new PascalParser();

    public static void main(String args[]) {

        if (args.length < 1) {
            System.exit(1);
        }

        try {
            Node tree = parser.parse(args[0]);

            Switch visitor = new Emitter();
            System.out.println(args);
            if (args.length == 2) {
                if (args[1].equals("--ast")) {
                    System.out.println("Dumping AST...");
                    visitor = new AstDump();
                }
            }

            tree.apply(visitor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("\n" + e.getMessage());
        }
    }
}
