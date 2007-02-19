/*
 * CSVOException.java
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
package net.sf.anupam.csv.exceptions;

/**
 * A checked exception for CSV Objects framework. This exception is used internally
 * within the framework.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 */
public class CSVOException extends Exception {

	/**
	 * Declared the serial version UID per Serialization guidelines.
	 */
	private static final long serialVersionUID = 2000896023138435841L;
	
    /**
     * Default Constructor.
     */
    public CSVOException() {
        super();
    }

    /**
     * Constructor accepting a message string.
     *
     * @param message the error message
     */
    public CSVOException(final String message) {
        super(message);
    }

    /**
     * Constructor accepting a message and a root cause exception to embed.
     *
     * @param message the error message
     * @param cause   the root cause exception
     */
    public CSVOException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor accepting the root cause exception to embed.
     *
     * @param cause the root cause exception
     */
    public CSVOException(final Throwable cause) {
        super(cause);
    }
}
