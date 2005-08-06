/*
 * CSVFormatterConfigParserTest.java
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * CSVFormatterConfigParserTest.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 */
public class CSVFormatterConfigParserTest
        extends TestCase {

    /**
     * The logger to use.
     */
    private static final Log LOG = LogFactory
            .getLog(CSVFormatterConfigParserTest.class);

    /**
     * Constructor for CSVFormatterConfigParserTest.
     *
     * @param name name of the test
     */
    public CSVFormatterConfigParserTest(final String name) {
        super(name);
    }

    /**
     * Main method to run the test.
     *
     * @param args program arguments
     */
    public static void main(final String [] args) {
        junit.textui.TestRunner.run(CSVFormatterConfigParserTest.class);
    }

    /**
     * Test method for
     * 'net.sf.anupam.csv.formatters.CSVFormatterConfigParser.getFormatMappings(String,
     * boolean)'.
     */
    public void testGetFormatMappings() {
        final CSVFormatterConfigParser parser = CSVFormatterConfigParser.getConfigParser();
        assertNotNull("The FMT configuration parser cannot be null", parser);

        final Map<String, FormatterConfiguration> formatterMap = parser
                .getFormatMappings(
                        "net/sf/anupam/csv/formatters/csv-formatter-config.xml",
                        true);
        assertNotNull("Formatter Map Should not be Null", formatterMap);
        assertFalse("Formatter Map should not be Empty", formatterMap.isEmpty());

        for (String formatterName : formatterMap.keySet()) {
            final FormatterConfiguration formatterConfig = formatterMap
                    .get(formatterName);
            assertNotNull("The formatter configuration cannot be null", formatterConfig);
        }

    }

}
