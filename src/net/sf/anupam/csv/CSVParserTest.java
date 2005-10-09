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

import junit.framework.TestCase;
import test.net.sf.anupam.csv.beans.Designation;
import test.net.sf.anupam.csv.beans.Employee;
import test.net.sf.anupam.csv.beans.Person;
import net.sf.anupam.csv.exceptions.CSVOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;

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
    private static final String SAMPLE_CSV_FILE = "test/net/sf/anupam/csv/beans/sample.csv";

    /**
     * The second sample data file to use for the test.
     */
    private static final String SECOND_SAMPLE_CSV_FILE = "test/net/sf/anupam/csv/beans/sample-2.csv";

    /**
     * The logger to use.
     */
    private static final Log LOG = LogFactory
            .getLog(CSVParserTest.class);

    /**
     * Constructor for CSVParserTest.
     *
     * @param name name of the test
     */
    public CSVParserTest(final String name) {
        super(name);
    }

    /**
     * Main method to run the test.
     *
     * @param args Program arguments
     */
    public static void main(final String [] args) {
        junit.textui
                .TestRunner
                .run(CSVParserTest.class);
    }

    /**
     * Test method for 'com.tcs.mis.csv.utilities.CSVParser.getMappedBeans()'.
     *
     * @throws net.sf.anupam.csv.exceptions.CSVOException
     *
     */
    public void testGetMappedBeans()
            throws CSVOException {
        final CSVParserFactory factory = CSVParserFactory.getSingleton();
        assertNotNull("The singleton instance of parser factory should not be null", factory);
        try {
            final CSVParser parser = factory.getCSVParser("employeeBean",
                                                          SAMPLE_CSV_FILE, true);
            assertNotNull("The parser for employeeBean should not be null", parser);
            for (Object bean : parser) {
                assertTrue("The parsed bean should be an instance of Employee", bean instanceof Employee);
                final Employee empl = (Employee) bean;
                assertEquals("The employee ID does not match", "123456", empl.getEmployeeID());
                assertEquals("The employee first name does not match", "John", empl.getFirstName());
                assertEquals("The employee last name does not match", "Doe", empl.getLastName());
                assertEquals("The employee client ID does not match", "BILLID01", empl.getClientSuppliedID());
                assertEquals("The employee secondary ID does not match", "CONTRACTOR007",
                             empl.getClientSuppliedSecondaryID());
                final Designation desgn = empl.getDesignation();
                assertNotNull("The employee designation should not be null", desgn);
                assertEquals("The employee designation does not match", "Lead", desgn.getDesignation());
                LOG.info(empl);
            }
            parser.close();
        } catch (final FileNotFoundException e) {
            fail("Unexpected exception: "
                 + e.getLocalizedMessage());
        }

    }

    /**
     * Test case to simulate a multiple record to single record mapping scenario.
     */
    public void testMultipleRecordScenario()
            throws Exception {
        final CSVParserFactory factory = CSVParserFactory.getSingleton();
        assertNotNull("The singleton instance of parser factory should not be null", factory);
        try {
            final CSVParser parser = factory.getCSVParser("personBean",
                                                          SECOND_SAMPLE_CSV_FILE, true);
            assertNotNull("The parser for personBean should not be null", parser);
            LOG.info(parser);
            for (Object bean : parser) {
                assertTrue("The parsed bean should be an instance of Person", bean instanceof Person);
                final Person person = (Person) bean;
                LOG.info(person);
            }
            parser.close();
        } catch (final FileNotFoundException e) {
            fail("Unexpected exception: "
                 + e.getLocalizedMessage());
        }
    }

}
