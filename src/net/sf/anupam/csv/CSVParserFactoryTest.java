/*
 * CSVParserFactoryTest.java 
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
package net.sf.anupam.csv;

import java.io.FileNotFoundException;

import net.sf.anupam.csv.mapping.CSVBeanMapping;
import net.sf.anupam.csv.mapping.CSVFieldMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

/**
 * CSVParserFactoryTest.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 */
public class CSVParserFactoryTest
        extends TestCase {

    /**
     * The sample data file to use for the test.
     */
    private static final String SAMPLE_CSV_FILE = "test/net/sf/anupam/csv/beans/sample.csv";

    /**
     * The logger to use.
     */
    private Log                 log             = LogFactory
                                                        .getLog(CSVParserFactoryTest.class);

    /**
     * Constructor for CSVParserFactoryTest.
     * 
     * @param name
     *            name of the test
     */
    public CSVParserFactoryTest(final String name) {
        super(name);
    }

    /**
     * Main method to run the test.
     * 
     * @param args
     *            program arguments
     */
    public static void main(final String [] args) {
        junit.textui.TestRunner.run(CSVParserFactoryTest.class);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    /**
     * Test the getBeanMapping method.
     */
    public void testGetBeanMapping() {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(parserFactory);

        final CSVBeanMapping beanMapping = parserFactory
                .getBeanMapping("employeeBean");
        assertNotNull(beanMapping);
        log.debug(beanMapping);
        for (CSVFieldMapping fieldMapping : beanMapping) {
            assertNotNull(fieldMapping);
            log.debug(fieldMapping);
        }
    }

    /**
     * Tests the getCSVParser method.
     */
    public void testGetCSVParser() {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(parserFactory);

        try {
            parserFactory.getCSVParser("employeeBean", SAMPLE_CSV_FILE, true,
                    true);
        } catch (final FileNotFoundException e) {
            fail("Unexpected exception "
                    + e.getLocalizedMessage());
        }
    }

    /**
     * Tests the getCSVParser method.
     */
    public void testGetCSVParserForException() {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(parserFactory);

        try {
            parserFactory.getCSVParser("employeeBean", "dummy", true, true);
            fail("Should have thrown a FileNotFoundException");
        } catch (final FileNotFoundException e) {
            // Do nothing
        }
    }

    /**
     * Tests the getCSVParser method.
     */
    public void testGetCSVParserForEmptyBean() {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(parserFactory);

        try {
            parserFactory.getCSVParser("", SAMPLE_CSV_FILE, true, true);
            fail("Should have thrown a FileNotFoundException");
        } catch (final Throwable e) {
            // Do nothing
        }
    }
}
