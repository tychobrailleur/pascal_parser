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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SymbolTable {

    private LinkedList<Map<String, Binding>> scopes = new LinkedList<>();


    public void enterScope() {
        scopes.push(new ConcurrentHashMap<String, Binding>());
    }

    public void leaveScope() {
        scopes.pop();
    }

    public void addBinding(String name, Binding binding) {
        Map<String, Binding> currentScope = scopes.peek();

        if (currentScope.get(name) != null) {
            throw new AlreadyExistsException(name + " is already declared in this scope.");
        }

        currentScope.put(name, binding);
    }

    public Binding findBinding(String name) {

        Iterator<Map<String, Binding>> iterScopes = scopes.iterator();
        while (iterScopes.hasNext()) {

            Map<String, Binding> scope = iterScopes.next();
            Binding binding = scope.get(name);
            if (binding != null) {
                return binding;
            }
        }

        return null;
    }

    public boolean empty() {
        return scopes.isEmpty();
    }

    public void dumpScope() {
        Iterator<Map<String, Binding>> iterScopes = scopes.iterator();
        while (iterScopes.hasNext()) {
            Map<String, Binding> scope = iterScopes.next();
            System.out.println("=======");
            System.out.println(scope);
            System.out.println("=======");
        }
    }
}
