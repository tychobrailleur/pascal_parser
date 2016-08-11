package pascal_parser;


import pascal_parser.analysis.DepthFirstAdapter;
import pascal_parser.node.AProgramHeader;

public class Emitter extends DepthFirstAdapter {

    int level = 0;

    @Override
    public void inAProgramHeader(AProgramHeader node) {
        System.out.println("program " + node.getProgramName() + ";");
        level++;
    }

    public void outAProgramHeader(AProgramHeader node) {
        System.out.println("end.");
        level--;
    }
}
