/*
 * DoNothingFormatter.java
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
 * A <code>No-Op</code> {@link CSVFieldFormatter formatter} that acts as the default if no explicit
 * formatter is configured for a CSV field. The formatter performs an Identity
 * operation on the input and returns the same as the <em>"formatted"</em>
 * result.
 * <p>
 * The declarative name of this formatter is <code>none</code>.
 * </p>
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @csv.formatter-mapping name="doNothing"
 */
final class DoNothingFormatter
        implements CSVFieldFormatter {

    /**
     * Constructor for DoNothingFormatter.
     */
    public DoNothingFormatter() {
        super();
    }

    /**
     * @see net.sf.anupam.csv.formatters.CSVFieldFormatter#format(java.lang.String)
     */
    public String format(final String value) {

        return value;
    }

}
