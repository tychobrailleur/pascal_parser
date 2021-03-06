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
