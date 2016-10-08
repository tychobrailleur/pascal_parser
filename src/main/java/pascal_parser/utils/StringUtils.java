package pascal_parser.utils;

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
import java.util.List;

/**
 * String utilities.
 *
 * @author Sébastien Le Callonnec
 */
public final class StringUtils {
    private StringUtils() {}

    public static <T> String join(List<T> entries, String separator) {
        if (entries == null) {
            return null;
        }

        StringBuilder output = new StringBuilder();
        Iterator<T> iterEntries = entries.iterator();
        while (iterEntries.hasNext()) {
            T entry = iterEntries.next();
            if (entry != null) {
                output.append(entry.toString().trim());
                if (iterEntries.hasNext() && separator != null) {
                    output.append(separator);
                }
            }
        }

        return output.toString();
    }
}
