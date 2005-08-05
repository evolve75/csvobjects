/*
 * AllUpperCaseFormatter.java
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
 * A {@link CSVFieldFormatter Formatter} which transforms the input into all upper case and returns the
 * upper case version.
 * <p/>
 * The declarative name of this formatter is <code>allUpperCase</code>.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @csv.formatter-mapping name="allUpperCase"
 * @see AllLowerCaseFormatter
 * @since 1.5
 */
final class AllUpperCaseFormatter
        implements CSVFieldFormatter {

    /**
     * Constructor for AllUpperCaseFormatter.
     */
    public AllUpperCaseFormatter() {
        super();
    }

    /**
     * Formats the value and transforms the result to all UPPER case.
     *
     * @param value the value to be transformed
     * @return the UPPER case transformed value
     * @see CSVFieldFormatter#format(String)
     */
    public String format(final String value) {

        if (value == null) {
            return null;
        }
        return value.toUpperCase().trim();
    }

}
