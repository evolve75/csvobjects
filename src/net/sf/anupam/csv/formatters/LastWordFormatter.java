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
 * A {@link CSVFieldFormatter formatter} that returns the last word
 * of the specified CSV value. This is useful in situations such as
 * extracting the last name.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @csv.formatter-mapping name="lastWord"
 * @see FirstWordFormatter
 * @since 1.5
 */
final class LastWordFormatter
        implements CSVFieldFormatter {

    /**
     * Constructor for FirstWordFormatter.
     */
    public LastWordFormatter() {
        super();
    }

    /**
     * Formats the value and returns the last word.
     *
     * @param value the value to be transformed
     * @return the last word from the input value
     * @see CSVFieldFormatter#format(String)
     */
    public String format(final String value) {

        if (value == null) {
            return null;
        }
        final String [] splitValues = value.split(" ");

        return StringUtils.trim(splitValues[splitValues.length - 1]);
    }

}
