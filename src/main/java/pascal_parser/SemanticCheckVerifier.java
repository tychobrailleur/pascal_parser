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

import pascal_parser.analysis.DepthFirstAdapter;
import pascal_parser.node.*;

import java.util.ArrayList;
import java.util.List;

public class SemanticCheckVerifier extends DepthFirstAdapter {

    private final ParserConfig config;
    private List<ParsingError> errors;

    private SymbolTable symbolTable = new SymbolTable();


    public SemanticCheckVerifier(ParserConfig config) {
        this.config = config;
        this.errors = new ArrayList<>();
    }

    @Override
    public void caseAVarStatement(AVarStatement node) {
        for (TIdentifier name: node.getName()) {

            Binding binding = symbolTable.findBinding(name.getText());
            if (binding == null) {
                binding = new Binding();
                binding.setType(node.getType().toString());
                symbolTable.addBinding(name.getText(), binding);
            } else {
                symbolTable.dumpScope();
                errors.add(new ParsingError(name.getLine(), name.getPos(), name.getText() + " already defined."));
            }
        }
    }

    @Override
    public void inAPascalProgram(APascalProgram node) {
        symbolTable.enterScope();
    }

    @Override
    public void outAPascalProgram(APascalProgram node) {
        symbolTable.leaveScope();
        assert symbolTable.empty();
    }

    @Override
    public void inAProcDeclStatement(AProcDeclStatement node) {
        symbolTable.enterScope();
    }

    @Override
    public void outAProcDeclStatement(AProcDeclStatement node) {
        symbolTable.leaveScope();
    }

    @Override
    public void inAFuncDeclStatement(AFuncDeclStatement node) {
        symbolTable.enterScope();
    }

    @Override
    public void outAFuncDeclStatement(AFuncDeclStatement node) {
        symbolTable.leaveScope();
    }

    @Override
    public void inABlockStatement(ABlockStatement node) {
        symbolTable.enterScope();
    }

    @Override
    public void outABlockStatement(ABlockStatement node) {
        symbolTable.leaveScope();
    }

    @Override
    public void caseAAssignStatement(AAssignStatement node) {
        super.caseAAssignStatement(node);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ParsingError> getErrors() {
        return errors;
    }
}
