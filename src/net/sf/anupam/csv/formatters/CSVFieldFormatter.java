/*
 * CSVFieldFormatter.java
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
 * Main Interface for a CSV field formatter. This interface needs to be
 * implemented by the actual formatter implementations in order to be recognized
 * by the Framework.
 * <p>
 * Default implementations are available for common formatting requirements in
 * the Framework, and are present in this package. See the package overview for
 * details.
 * </p>
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see CSVFormatterFactory
 */
public interface CSVFieldFormatter {

    /**
     * Formats the specified CSV field value and returns the formatted result.
     * 
     * @param value
     *            the CSV field value to format
     * @return the formatted result (need not be a String)
     */
    Object format(final String value);
}
