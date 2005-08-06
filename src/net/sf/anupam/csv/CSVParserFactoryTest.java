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

import junit.framework.TestCase;
import net.sf.anupam.csv.mapping.CSVBeanMapping;
import net.sf.anupam.csv.mapping.CSVFieldMapping;
import net.sf.anupam.csv.exceptions.CSVOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;

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
    private static final Log LOG = LogFactory
            .getLog(CSVParserFactoryTest.class);

    private static final String NOT_NULL_FACTORY_MSG = "The singleton instance of parser factory should not be null";

    /**
     * Constructor for CSVParserFactoryTest.
     *
     * @param name name of the test
     */
    public CSVParserFactoryTest(final String name) {
        super(name);
    }

    /**
     * Main method to run the test.
     *
     * @param args program arguments
     */
    public static void main(final String [] args) {
        junit.textui.TestRunner.run(CSVParserFactoryTest.class);
    }

    /**
     * Test the getBeanMapping method.
     *
     * @throws net.sf.anupam.csv.exceptions.CSVOException
     *          thrown if there is a test failure
     */
    public void testGetBeanMapping() throws CSVOException {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(NOT_NULL_FACTORY_MSG, parserFactory);

        final CSVBeanMapping beanMapping = parserFactory
                .getBeanMapping("employeeBean");
        assertNotNull("The Bean mapping for employeeBean should not be null", beanMapping);
        LOG.debug(beanMapping);
        for (CSVFieldMapping fieldMapping : beanMapping) {
            assertNotNull("The field mapping should not be null", fieldMapping);
            LOG.debug(fieldMapping);
        }
    }

    /**
     * Tests the getCSVParser method.
     *
     * @throws CSVOException
     */
    public void testGetCSVParser() throws CSVOException {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(NOT_NULL_FACTORY_MSG, parserFactory);

        try {
            parserFactory.getCSVParser("employeeBean", SAMPLE_CSV_FILE, true
            );
        } catch (final FileNotFoundException e) {
            fail("Unexpected exception "
                    + e.getLocalizedMessage());
        }
    }

    /**
     * Tests the getCSVParser method.
     *
     * @throws CSVOException
     */
    public void testGetCSVParserForException() throws CSVOException {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(NOT_NULL_FACTORY_MSG, parserFactory);

        try {
            parserFactory.getCSVParser("employeeBean", "dummy", true);
            fail("Should have thrown a FileNotFoundException");
        } catch (final FileNotFoundException e) {
            // Do nothing
        }
    }

    /**
     * Tests the getCSVParser method.
     *
     * @throws CSVOException
     */
    public void testGetCSVParserForEmptyBean() throws CSVOException {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(NOT_NULL_FACTORY_MSG, parserFactory);

        try {
            parserFactory.getCSVParser("", SAMPLE_CSV_FILE, true);
            fail("Should have thrown an IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // Do nothing
        } catch (final FileNotFoundException e) {
            fail("Should have thrown an IllegalArgumentException");
        }
    }

    /**
     * Tests the getCSVParser method.
     *
     * @throws CSVOException
     */
    public void testGetCSVParserForNoFile() throws CSVOException {
        final CSVParserFactory parserFactory = CSVParserFactory.getSingleton();

        assertNotNull(NOT_NULL_FACTORY_MSG, parserFactory);

        try {
            parserFactory.getCSVParser("employeeBean", "DUMMY", true);
            fail("Should have thrown an FileNotFoundException");
        } catch (final IllegalArgumentException e) {
            fail("Should have thrown an FileNotFoundException");
        } catch (final FileNotFoundException e) {
            // Do nothing
        }
    }
}
