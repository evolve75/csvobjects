/*
 * FirstWordFormatter.java
 * 
 * Copyright (C) 2005 Anupam Sengupta (anupamsg@users.sourceforge.net) 
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
 *
 * Version: $Revision$
 */
package net.sf.anupam.csv.formatters;

import org.apache.commons.lang.StringUtils;

/**
 * A {@link CSVFieldFormatter formatter} that returns the first word
 * of the specified CSV value. This is useful in situtations such as 
 * extracting the first name.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see LastWordFormatter
 * @csv.formatter-mapping name="firstWord"
 */
final class FirstWordFormatter
        implements CSVFieldFormatter {

    /**
     * Constructor for FirstWordFormatter.
     */
    public FirstWordFormatter() {
        super();
    }

    /**
     * @see net.sf.anupam.csv.formatters.CSVFieldFormatter#format(String)
     */
    public String format(final String value) {

        if (value == null) {
            return null;
        }
        return StringUtils.trim(value.split(" ")[0]);
    }

}