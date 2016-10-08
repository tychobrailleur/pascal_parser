package pascal_parser;

/*

   Copyright (C) 2016  Sébastien Le Callonnec

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software Foundation,
   Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA

*/

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import pascal_parser.analysis.DepthFirstAdapter;
import pascal_parser.node.ALabelStatement;
import pascal_parser.node.APascalProgram;
import pascal_parser.node.AProgramHeader;
import pascal_parser.node.AAddExpression;
import pascal_parser.node.AAndExpression;
import pascal_parser.node.AArrayAccessExpression;
import pascal_parser.node.AArrayTypedef;
import pascal_parser.node.AAssignStatement;
import pascal_parser.node.ABlockStatement;
import pascal_parser.node.ABoolTypedef;
import pascal_parser.node.AByteTypedef;
import pascal_parser.node.ACaseBranchStatement;
import pascal_parser.node.ACaseStatement;
import pascal_parser.node.ACharTypedef;
import pascal_parser.node.AConstBlockStatement;
import pascal_parser.node.AConstStatement;
import pascal_parser.node.ADefTypedef;
import pascal_parser.node.ADivDivExpression;
import pascal_parser.node.ADivExpression;
import pascal_parser.node.AEmptyStatement;
import pascal_parser.node.AEqualExpression;
import pascal_parser.node.AFalseExpression;
import pascal_parser.node.AFileTypedef;
import pascal_parser.node.AFloatExpression;
import pascal_parser.node.AForDownStatement;
import pascal_parser.node.AForUpStatement;
import pascal_parser.node.AFuncDeclStatement;
import pascal_parser.node.AGeqExpression;
import pascal_parser.node.AGotoStatement;
import pascal_parser.node.AGtExpression;
import pascal_parser.node.AIdentifierExpression;
import pascal_parser.node.AIfStatement;
import pascal_parser.node.AIntTypedef;
import pascal_parser.node.ALabelledStatement;
import pascal_parser.node.ALeqExpression;
import pascal_parser.node.ALtExpression;
import pascal_parser.node.AMinusExpression;
import pascal_parser.node.AModExpression;
import pascal_parser.node.AMultExpression;
import pascal_parser.node.ANotEqualDiamondExpression;
import pascal_parser.node.ANotEqualExpression;
import pascal_parser.node.ANotExpression;
import pascal_parser.node.ANumberExpression;
import pascal_parser.node.AOrExpression;
import pascal_parser.node.APackedTypedef;
import pascal_parser.node.APlusExpression;
import pascal_parser.node.APointerAccessExpression;
import pascal_parser.node.APointerTypedef;
import pascal_parser.node.AProcCallExpression;
import pascal_parser.node.AProcCallStatement;
import pascal_parser.node.AProcDeclStatement;
import pascal_parser.node.ARangeExpression;
import pascal_parser.node.ARangeTypedef;
import pascal_parser.node.ARealTypedef;
import pascal_parser.node.ARecordAccessExpression;
import pascal_parser.node.ARecordMemberOptStatement;
import pascal_parser.node.ARecordMemberStatement;
import pascal_parser.node.ARecordTypedef;
import pascal_parser.node.ARepeatStatement;
import pascal_parser.node.ASizedExprExpression;
import pascal_parser.node.AStringExpression;
import pascal_parser.node.ASubExpression;
import pascal_parser.node.ATrueExpression;
import pascal_parser.node.ATypeBlockStatement;
import pascal_parser.node.ATypeDeclStatement;
import pascal_parser.node.AVarBlockStatement;
import pascal_parser.node.AVarStatement;
import pascal_parser.node.AWhileStatement;
import pascal_parser.node.PStatement;
import pascal_parser.utils.StringUtils;

public class Emitter extends DepthFirstAdapter {

    private final PrintStream out = System.out;

    int level = 0;
    String space = "  ";
    private boolean inhibitSpaces = false;

    @Override
    public void inAProgramHeader(AProgramHeader node) {
        out.println("program " + node.getIdentifier().getText() + ";");
        level++;
    }

    @Override
    public void outAPascalProgram(APascalProgram node) {
        out.println(".");
    }

    @Override
    public void inALabelStatement(ALabelStatement node) {
        printSpaces();
        out.print("label ");
        out.print(StringUtils.join(node.getNumber(), ", "));
        out.println(";");
    }

    @Override
    public void inABlockStatement(ABlockStatement node) {
        printSpaces();
        out.println("begin");
        level++;
    }

    @Override
    public void outABlockStatement(ABlockStatement node) {
        level--;
        printSpaces();
        out.print("end");
        if (level > 1) {
            out.println();
        }
    }

    @Override
    public void inANumberExpression(ANumberExpression node) {
        out.print(node.getNumber().getText());
    }

    @Override
    public void caseATypeBlockStatement(ATypeBlockStatement node) {
        printSpaces();
        out.println("type");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        level--;
        out.println();
    }

    @Override
    public void caseAVarBlockStatement(AVarBlockStatement node) {
        printSpaces();
        out.println("var");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        level--;
        out.println();
    }

    @Override
    public void caseAConstBlockStatement(AConstBlockStatement node) {
        printSpaces();
        out.println("const");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        level--;
        out.println();
    }

    @Override
    public void caseATypeDeclStatement(ATypeDeclStatement node) {
        printSpaces();
        out.print(StringUtils.join(node.getName(), ", "));
        out.print(" = ");

        if (node.getType() != null) {
            node.getType().apply(this);
        }

        out.println(";");
    }

    @Override
    public void caseAFalseExpression(AFalseExpression node) {
        out.print("false");
    }

    @Override
    public void outAProcCallExpression(AProcCallExpression node) {
        out.print(")");
    }

    @Override
    public void inAProcCallExpression(AProcCallExpression node) {
        out.print(node.getName().getText());
        if (node.getArgs() != null) {
            out.print("(");
        }
    }

    @Override
    public void caseAPointerAccessExpression(APointerAccessExpression node) {
        out.print(node.getAddress().getText());
        out.print("^");
    }

    @Override
    public void caseARecordAccessExpression(ARecordAccessExpression node) {
        node.getRecord().apply(this);
        out.print(".");
        out.print(node.getField().getText());
    }

    @Override
    public void caseAArrayAccessExpression(AArrayAccessExpression node) {
        out.print(node.getArray().getText());
        out.print("[");
        node.getExpression().apply(this);
        out.print("]");
    }

    @Override
    public void caseAIdentifierExpression(AIdentifierExpression node) {
        out.print(node.getIdentifier().getText());
    }

    @Override
    public void caseATrueExpression(ATrueExpression node) {
        out.print("true");
    }

    @Override
    public void caseANotExpression(ANotExpression node) {
        out.print("not ");
        node.getNot().apply(this);
    }

    @Override
    public void caseAMinusExpression(AMinusExpression node) {
        out.print("-");
        node.getNegative().apply(this);
    }

    @Override
    public void caseAPlusExpression(APlusExpression node) {
        out.print("+");
        node.getPositive().apply(this);
    }

    @Override
    public void caseARangeExpression(ARangeExpression node) {
        if (node.getMin() != null) {
            node.getMin().apply(this);
        }
        out.print("..");
        if (node.getMax() != null) {
            node.getMax().apply(this);
        }
    }

    @Override
    public void caseAFloatExpression(AFloatExpression node) {
        out.print(node.getFloat().getText());
    }

    @Override
    public void caseAStringExpression(AStringExpression node) {
        out.print(node.getStringLiteral().getText());
    }

    @Override
    public void caseASizedExprExpression(ASizedExprExpression node) {
        node.getExpression().apply(this);
        out.print(":");
        out.print(node.getNumber().getText());
    }

    @Override
    public void caseANumberExpression(ANumberExpression node) {
        out.print(node.getNumber().getText());
    }

    @Override
    public void caseAAndExpression(AAndExpression node) {
        node.getLeft().apply(this);
        out.print(" and ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAOrExpression(AOrExpression node) {
        node.getLeft().apply(this);
        out.print(" or ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAGeqExpression(AGeqExpression node) {
        node.getLeft().apply(this);
        out.print(" >= ");
        node.getRight().apply(this);
    }

    @Override
    public void caseALeqExpression(ALeqExpression node) {
        node.getLeft().apply(this);
        out.print(" <= ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAGtExpression(AGtExpression node) {
        node.getLeft().apply(this);
        out.print(" > ");
        node.getRight().apply(this);
    }

    @Override
    public void caseALtExpression(ALtExpression node) {
        node.getLeft().apply(this);
        out.print(" < ");
        node.getRight().apply(this);
    }

    @Override
    public void caseANotEqualDiamondExpression(ANotEqualDiamondExpression node) {
        node.getLeft().apply(this);
        out.print(" <> ");
        node.getRight().apply(this);
    }

    @Override
    public void caseANotEqualExpression(ANotEqualExpression node) {
        node.getLeft().apply(this);
        out.print(" != ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAEqualExpression(AEqualExpression node) {
        node.getLeft().apply(this);
        out.print(" = ");
        node.getRight().apply(this);
    }

    @Override
    public void caseADivDivExpression(ADivDivExpression node) {
        node.getLeft().apply(this);
        out.print(" div ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAModExpression(AModExpression node) {
        node.getLeft().apply(this);
        out.print(" mod ");
        node.getRight().apply(this);
    }

    @Override
    public void caseADivExpression(ADivExpression node) {
        node.getLeft().apply(this);
        out.print(" / ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAMultExpression(AMultExpression node) {
        node.getLeft().apply(this);
        out.print(" * ");
        node.getRight().apply(this);
    }

    @Override
    public void caseASubExpression(ASubExpression node) {
        node.getLeft().apply(this);
        out.print(" - ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAAddExpression(AAddExpression node) {
        node.getLeft().apply(this);
        out.print(" + ");
        node.getRight().apply(this);
    }

    @Override
    public void caseAPointerTypedef(APointerTypedef node) {
        out.print("^");
        node.getTypedef().apply(this);
    }

    @Override
    public void caseAPackedTypedef(APackedTypedef node) {
        out.print("packed ");
        node.getTypedef().apply(this);
    }

    @Override
    public void caseAFileTypedef(AFileTypedef node) {
        out.print("file of ");
        node.getTypedef().apply(this);
    }

    @Override
    public void caseADefTypedef(ADefTypedef node) {
        out.print(node.getIdentifier().getText());
    }

    @Override
    public void caseARangeTypedef(ARangeTypedef node) {
        node.getMin().apply(this);
        out.print("..");
        node.getMax().apply(this);
    }

    @Override
    public void caseARecordTypedef(ARecordTypedef node) {
        out.println("record ");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        printSpaces();
        out.print("end");
        level--;
    }

    @Override
    public void caseAArrayTypedef(AArrayTypedef node) {
        out.print("array[");
        node.getRange().apply(this);
        out.print("] of ");
        node.getType().apply(this);
    }

    @Override
    public void caseAByteTypedef(AByteTypedef node) {
        out.print("byte");
    }

    @Override
    public void caseARealTypedef(ARealTypedef node) {
        out.print("real");
    }

    @Override
    public void caseAIntTypedef(AIntTypedef node) {
        out.print("integer");
    }

    @Override
    public void caseABoolTypedef(ABoolTypedef node) {
        out.print("boolean");
    }

    @Override
    public void caseACharTypedef(ACharTypedef node) {
        out.print("char");
    }

    @Override
    public void caseAEmptyStatement(AEmptyStatement node) {
        out.print(";");
    }

    @Override
    public void caseALabelledStatement(ALabelledStatement node) {
        printSpaces();
        out.print(node.getNumber().getText());
        out.print(": ");
        inhibitSpaces = true;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
    }

    @Override
    public void caseACaseBranchStatement(ACaseBranchStatement node) {
        printSpaces();
        out.print(StringUtils.join(node.getValues(), ", "));
        out.print(": ");
        inhibitSpaces = true;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        out.println();
    }

    @Override
    public void caseACaseStatement(ACaseStatement node) {
        printSpaces();
        out.print("case ");
        node.getCond().apply(this);
        out.println(" of ");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getBranches());
        for (PStatement e : copy) {
            e.apply(this);
        }
        printSpaces();
        out.println("end;");
        level--;
    }

    @Override
    public void caseAAssignStatement(AAssignStatement node) {
        printSpaces();
        node.getVar().apply(this);
        out.print(" := ");
        node.getValue().apply(this);
        out.println(";");
    }

    @Override
    public void caseARecordMemberOptStatement(ARecordMemberOptStatement node) {
        printSpaces();
        node.getValue().apply(this);
        out.print(": ");
        inhibitSpaces = true;
        if (node.getFields() != null && ! node.getFields().isEmpty()) {
            List<PStatement> copy = new ArrayList<>(node.getFields());
            for (PStatement e : copy) {
                e.apply(this);
            }
        }
        out.println(";");
    }

    @Override
    public void caseARecordMemberStatement(ARecordMemberStatement node) {
        printSpaces();
        out.print("case ");
        out.print(node.getName().getText());
        out.println(" of ");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        printSpaces();
        out.println("end;");
    }

    @Override
    public void caseAVarStatement(AVarStatement node) {
        printSpaces();
        out.print(StringUtils.join(node.getName(), ", "));
        out.print(": ");
        node.getType().apply(this);

        if (node.getValue() != null) {
            out.print(" := ");
            node.getValue().apply(this);
        }
        out.println(";");
    }

    @Override
    public void caseAConstStatement(AConstStatement node) {
        printSpaces();
        out.print(node.getIdentifier().getText());
        out.print(" = ");
        node.getExpression().apply(this);
        out.println(";");
    }

    @Override
    public void caseAForDownStatement(AForDownStatement node) {
        printSpaces();
        out.print("for ");
        inhibitSpaces = true;
        node.getAssign().apply(this);
        out.print(" downto ");
        node.getValue().apply(this);
        out.println(" do ");
        level++;

        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        level--;
    }

    @Override
    public void caseAForUpStatement(AForUpStatement node) {
        printSpaces();
        out.print("for ");
        inhibitSpaces = true;
        node.getAssign().apply(this);
        out.print(" to ");
        node.getValue().apply(this);
        out.println(" do ");
        level++;

        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        level--;
    }

    @Override
    public void caseAIfStatement(AIfStatement node) {
        printSpaces();
        out.print("if ");
        node.getCond().apply(this);
        out.println(" then ");
        level++;

        List<PStatement> copy = new ArrayList<>(node.getThen());
        for (PStatement e : copy) {
            e.apply(this);
        }

        level--;
        if (node.getElse() != null && !node.getElse().isEmpty()) {
            printSpaces();
            out.println("else");
            level++;
            List<PStatement> elseCopy = new ArrayList<>(node.getElse());
            for (PStatement e : elseCopy) {
                e.apply(this);
            }
            level--;
        }
    }

    @Override
    public void caseAWhileStatement(AWhileStatement node) {
        printSpaces();
        out.print("while ");
        node.getCond().apply(this);
        out.println(" do ");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }

        level--;
        printSpaces();
        out.println(";");
    }

    @Override
    public void caseARepeatStatement(ARepeatStatement node) {
        printSpaces();
        out.println("repeat");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }

        level--;
        printSpaces();
        out.print("until ");
        node.getCond().apply(this);
        out.println(";");
    }

    @Override
    public void caseAFuncDeclStatement(AFuncDeclStatement node) {
        printSpaces();
        out.print("function ");
        out.print(node.getName().getText());
        if (node.getArgs() != null && !node.getArgs().isEmpty()) {
            out.print("(");
            List<PStatement> copy = new ArrayList<>(node.getArgs());
            for (PStatement e : copy) {
                e.apply(this);
            }
            out.print(")");
        }
        out.print(":");
        node.getReturnType().apply(this);
        out.println(";");
        level++;
        if (node.getBody() != null && !node.getBody().isEmpty()) {
            List<PStatement> copy = new ArrayList<>(node.getBody());
            for (PStatement e : copy) {
                e.apply(this);
            }
        } else {
            printSpaces();
            out.println("forward;");
        }
        level--;
        out.println();
    }

    @Override
    public void caseAProcDeclStatement(AProcDeclStatement node) {
        printSpaces();
        out.print("procedure ");
        out.print(node.getName().getText());
        if (node.getArgs() != null && !node.getArgs().isEmpty()) {
            out.print("(");
            List<PStatement> copy = new ArrayList<>(node.getArgs());
            for (PStatement e : copy) {
                e.apply(this);
            }
            out.print(")");
        }
        out.println(";");
        level++;
        if (node.getBody() != null && !node.getBody().isEmpty()) {
            List<PStatement> copy = new ArrayList<>(node.getBody());
            for (PStatement e : copy) {
                e.apply(this);
            }
        } else {
            printSpaces();
            out.println("forward;");
        }
        level--;
        out.println();
    }

    @Override
    public void caseAProcCallStatement(AProcCallStatement node) {
        printSpaces();
        out.print(node.getName().getText());
        if (node.getExpression() != null && !node.getExpression().isEmpty()) {
            out.print("(");
            out.print(StringUtils.join(node.getExpression(), ", "));
            out.print(")");
        }
        out.println(";");

    }

    @Override
    public void caseABlockStatement(ABlockStatement node) {
        printSpaces();
        out.println("begin");
        level++;
        List<PStatement> copy = new ArrayList<>(node.getStatement());
        for (PStatement e : copy) {
            e.apply(this);
        }
        level--;
        printSpaces();
        out.print("end");
        if (level > 0) {
            out.println();
        }
    }

    @Override
    public void caseAGotoStatement(AGotoStatement node) {
        printSpaces();
        out.print("goto ");
        out.print(node.getNumber().getText());
        out.println(";");
    }

    @Override
    public void caseALabelStatement(ALabelStatement node) {
        printSpaces();
        out.print("label ");
        out.print(StringUtils.join(node.getNumber(), ", "));
        out.print(";");
    }

    private void printSpaces() {
        if (!inhibitSpaces) {
            for (int i = 0; i < level; i++) {
                out.print(space);
            }
        } else {
            inhibitSpaces = false;
        }
    }
}
