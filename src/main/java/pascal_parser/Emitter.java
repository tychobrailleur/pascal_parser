package pascal_parser;


import pascal_parser.analysis.DepthFirstAdapter;
import pascal_parser.node.AConstProgramDeclItem;
import pascal_parser.node.ALabelDecl;
import pascal_parser.node.AProgramBody;
import pascal_parser.node.AProgramHeader;

public class Emitter extends DepthFirstAdapter {

    int level = 0;
    String space = "    ";

    @Override
    public void inAProgramHeader(AProgramHeader node) {
        System.out.println("program " + node.getProgramName().getText() + ";");
        level++;
    }

    public void inAProgramBody(AProgramBody node) {
        printSpaces();
        System.out.println("begin");
        level++;
    }

    public void outAProgramBody(AProgramBody node) {
        level--;
        printSpaces();
        System.out.println("end.");
    }

    public void inALabelDecl(ALabelDecl node) {
        printSpaces();
        System.out.print("label ");

    }

    @Override
    public void inAConstProgramDeclItem(AConstProgramDeclItem node) {
        printSpaces();
        System.out.println("const");
    }


    private void printSpaces() {
        for (int i = 0; i < level; i++) {
            System.out.print(space);
        }
    }
}
