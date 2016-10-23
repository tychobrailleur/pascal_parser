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
import java.util.Iterator;
import java.util.List;
import pascal_parser.analysis.DepthFirstAdapter;
import pascal_parser.node.*;
import pascal_parser.utils.StringUtils;

public class Emitter extends DepthFirstAdapter {

    private final PrintStream out = System.out;

    private int level = 0;
    private String space = "  ";
    private boolean inhibitSpaces = false;

    public void inAPascalProgram(APascalProgram node) {
        out.println("program ");
        out.println(node.getName().getText());
        out.println(";");
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
    public void caseAProcCallExpression(AProcCallExpression node) {
        out.print(node.getName().getText());
        if (node.getArgs() != null && !node.getArgs().isEmpty()) {
            out.print("(");
            joinApplied(node.getArgs(), ", ");
            out.print(")");
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
        out.print(node.getVar().getText());
        out.print(" := ");
        node.getInitial().apply(this);
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
        out.print(node.getVar().getText());
        out.print(" := ");
        node.getInitial().apply(this);
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
            inhibitSpaces = true;
            List<PFormalParameter> copy = new ArrayList<>(node.getArgs());
            for (PFormalParameter e : copy) {
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
            inhibitSpaces = true;

            Iterator<PFormalParameter> params = node.getArgs().iterator();
            while (params.hasNext()) {
                PFormalParameter param = params.next();
                param.apply(this);
                if (params.hasNext()) {
                    out.print(", ");
                }
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
            joinApplied(node.getExpression(), ", ");
            out.print(")");
        }
        out.println(";");
    }

    @Override
    public void caseAVarFormalParameter(AVarFormalParameter parameter) {
        Iterator<TIdentifier> names = parameter.getName().iterator();
        while (names.hasNext()) {
            TIdentifier name = names.next();
            out.print(name.getText());
            if (names.hasNext()) {
                out.print(", ");
            }
        }
        out.print(":");
        out.print(parameter.getType().toString().trim());
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

    private <T extends PExpression> void joinApplied(List<T> entries, String separator) {
        if (entries != null && !entries.isEmpty()) {
            Iterator<T> iterEntries = entries.iterator();
            while (iterEntries.hasNext()) {
                T entry = iterEntries.next();
                entry.apply(this);
                if (iterEntries.hasNext()) {
                    out.print(separator);
                }
            }
        }
    }
}
