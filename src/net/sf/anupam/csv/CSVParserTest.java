/*
 * CSVParserTest.java
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

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.net.sf.anupam.csv.beans.Designation;
import test.net.sf.anupam.csv.beans.Employee;

/**
 * CSVParserTest.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 */
public class CSVParserTest
        extends TestCase {

    /**
     * The sample data file to use for the test.
     */
    public static final String SAMPLE_CSV_FILE = "test/net/sf/anupam/csv/beans/sample.csv";

    /**
     * The logger to use.
     */
    private Log                log             = LogFactory
                                                       .getLog(CSVParserTest.class);

    /**
     * Constructor for CSVParserTest.
     * 
     * @param name
     *            name of the test
     */
    public CSVParserTest(final String name) {
        super(name);
    }

    /**
     * Main method to run the test.
     * 
     * @param args
     *            Program arguments
     */
    public static void main(final String [] args) {
        junit.textui.TestRunner.run(CSVParserTest.class);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test method for 'com.tcs.mis.csv.utilities.CSVParser.getMappedBeans()'.
     */
    public void testGetMappedBeans() {
        final CSVParserFactory factory = CSVParserFactory.getSingleton();
        assertNotNull(factory);
        try {
            final CSVParser parser = factory.getCSVParser("employeeBean",
                    SAMPLE_CSV_FILE, true, true);
            assertNotNull(parser);
            for (Object bean : parser) {
                assertTrue(bean instanceof Employee);
                final Employee empl = (Employee) bean;
                assertEquals("123456", empl.getEmployeeID());
                assertEquals("John", empl.getFirstName());
                assertEquals("Doe", empl.getLastName());
                assertEquals("BILLID01", empl.getClientSuppliedID());
                assertEquals("CONTRACTOR007", empl.getClientSuppliedSecondaryID());
                final Designation desgn = empl.getDesignation();
                assertNotNull(desgn);
                assertEquals("Lead", desgn.getDesignation());
                log.info(empl);
            }
        } catch (final FileNotFoundException e) {
            fail("Unexcepted exception: "
                    + e.getLocalizedMessage());
        }

    }

}
