/*
 * TestCSVReader.java
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
 * Version $Revision$
 */
package net.sf.anupam.csv;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * TestCSVReader.
 *
 * @author Tata Consultancy Services
 * @version $Revision$
 */
public class CSVReaderTest
        extends TestCase {

    /**
     * The sample data file to use for the test.
     */
    private static final String sampleCSVFileName = "test/net/sf/anupam/csv/beans/sample.csv";

    /**
     * Logger to use.
     */
    private static final Log LOG = LogFactory
            .getLog(CSVReaderTest.class);

    /**
     * The CSV reader to use in the test.
     */
    private transient Reader csvReader;

    // ~ Constructors
    // -----------------------------------------------------------

    /**
     * Constructor for TestCSVReader.
     *
     * @param testName name of the test
     */
    public CSVReaderTest(final String testName) {
        super(testName);
    }

    // ~ Methods
    // ----------------------------------------------------------------

    /**
     * Main method to run the test.
     *
     * @param args Program arguments
     */
    public static void main(final String [] args) {
        junit.textui.TestRunner.run(CSVReaderTest.class);
    }

    /**
     * Reads the sample CSV file for this test.
     *
     * @throws Exception if the file IO fails
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        csvReader = new InputStreamReader(ClassLoader
                .getSystemResourceAsStream(sampleCSVFileName));
        LOG.info("Loaded the test CSV file");

    }

    /**
     * Closes the CSV reader.
     *
     * @throws Exception if an IO error occurs
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (csvReader != null) {
            csvReader.close();
        }
    }

    /**
     * Test the reader's construction method.
     */
    public final void testCSVReader() {
        final CSVReader reader = new CSVReader(csvReader, true);
        assertNotNull("The CSV Reader cannot be null", reader);
        reader.close();
    }

    /**
     * Test the reader's iterator() method.
     */
    public final void testIterator() {
        final CSVReader reader = new CSVReader(csvReader, true);
        assertNotNull("The CSV Reader cannot be null", reader);
        for (List<String> l : reader) {
            for (String value : l) {
                assertNotNull("The parsed CSV fields cannot be null", value);
                LOG.info(value);
            }
        }
        final Iterator iter = reader.iterator();

        assertFalse("The CSV parsed iterator should have returned at least one line", iter.hasNext());
        try {
            iter.next();
            fail("Should have thrown an exception");
        } catch (final NoSuchElementException e) {
            // Do nothing
        }
        reader.close();
    }

}
