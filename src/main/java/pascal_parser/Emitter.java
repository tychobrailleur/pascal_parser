package pascal_parser;


import pascal_parser.analysis.DepthFirstAdapter;
import pascal_parser.node.ALabelStatement;
import pascal_parser.node.APascalProgram;
import pascal_parser.node.AProgramHeader;

import java.util.Iterator;

public class Emitter extends DepthFirstAdapter {

    int level = 0;
    String space = "    ";

    @Override
    public void inAProgramHeader(AProgramHeader node) {
        System.out.println("program " + node.getIdentifier().getText() + ";");
        System.out.println("begin");
        level++;
    }

    @Override
    public void outAPascalProgram(APascalProgram node) {
        System.out.println("end.");
    }

    @Override
    public void inALabelStatement(ALabelStatement node) {
        printSpaces();
        System.out.print("label ");

        StringBuilder labels = new StringBuilder();
        Iterator iterNumber = node.getNumber().iterator();
        while (iterNumber.hasNext()) {
            labels.append(iterNumber.next().toString().trim());
            if (iterNumber.hasNext()) {
                labels.append(", ");
            }
        }

        System.out.print(labels.toString());
        System.out.println(";");
    }

    private void printSpaces() {
        for (int i = 0; i < level; i++) {
            System.out.print(space);
        }
    }
}
