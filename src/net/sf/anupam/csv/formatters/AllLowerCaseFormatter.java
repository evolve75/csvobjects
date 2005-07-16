/*
 * AllLowerCaseFormatter.java
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

/**
 * A {@link CSVFieldFormatter Formatter} which transforms the input into all
 * lower-case and returns the lower-case version.
 * <p>
 * The declarative name of this formatter is <code>allLowerCase</code>.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see AllUpperCaseFormatter
 * @csv.formatter-mapping name="allLowerCase"
 */
final class AllLowerCaseFormatter
        implements CSVFieldFormatter {

    /**
     * Constructor for AllLowerCaseFormatter.
     */
    public AllLowerCaseFormatter() {
        super();
    }

    /**
     * @see net.sf.anupam.csv.formatters.CSVFieldFormatter#format(java.lang.String)
     */
    public String format(final String value) {

        if (value == null) {
            return null;
        }
        return value.toLowerCase().trim();
    }

}
