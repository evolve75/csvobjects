/*
 * FirstWordFormatterTest.java 
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

import junit.framework.TestCase;

/**
 * LastWordFormatterTest.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 */
public class LastWordFormatterTest
        extends TestCase {

    /**
     * Constructor for LastWordFormatterTest.
     *
     * @param name name of the test
     */
    public LastWordFormatterTest(final String name) {
        super(name);
    }

    /**
     * Main method to perform the tests.
     *
     * @param args Program arguments
     */
    public static void main(final String [] args) {
        junit.textui.TestRunner.run(LastWordFormatterTest.class);
    }

    /**
     * Test method for
     * 'com.tcs.mis.csv.utilities.FirstWordFormatter.format(String)'.
     */
    public void testFormat() {
        final String name = "Anupam B Sengupta";
        final CSVFieldFormatter formatter = new LastWordFormatter();
        assertNotNull("The CSV Field formatter cannot be null", formatter);
        final String result = (String) formatter.format(name);
        assertEquals("Should have returned the last name", "Sengupta", result);
    }

    /**
     * Test method for
     * 'com.tcs.mis.csv.utilities.FirstWordFormatter.format(String)'.
     */
    public void testFormatWhenNull() {
        final CSVFieldFormatter formatter = new LastWordFormatter();
        assertNotNull("The CSV Field formatter cannot be null", formatter);

        final String nullResult = (String) formatter.format(null);
        assertNull("Should have returned null", nullResult);

    }

    /**
     * Test method for
     * 'com.tcs.mis.csv.utilities.FirstWordFormatter.format(String)'.
     */
    public void testFormatWhenEmpty() {
        final CSVFieldFormatter formatter = new LastWordFormatter();
        assertNotNull("The CSV Field formatter cannot be null", formatter);

        final String emptyResult = (String) formatter.format("");
        assertEquals("Should have returned an empty string", "", emptyResult);

    }
}
