/*
 * CSVMappingParser.java
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

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XML Parser (based on Commons Digester) to parse and return the CSV formatter configuration. This is for internal use
 * within the framwork.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @see org.apache.commons.digester.Digester
 * @since 1.5
 */
class CSVFormatterConfigParser {

    /**
     * The logger to use.
     */
    private static final Log LOG = LogFactory
            .getLog(CSVFormatterConfigParser.class);

    /**
     * The rule set for parsing formatter configuration.
     */
    private static FromXmlRuleSet ruleSet;

    private static CSVFormatterConfigParser singleton;
    private static final Map<String, FormatterConfiguration> formatCfgMapping = new HashMap<String, FormatterConfiguration>();
    private static boolean isLoaded;

    /**
     * Constructor for CSVMappingParser.
     */
    private CSVFormatterConfigParser() {
        super();


    }

    /**
     * Returns the map of parsed format configuration beans.
     *
     * @param xmlFileName the XML mapping configuration file
     * @param inClassPath flag indicating whether the XML file is in the classpath
     * @return a map of format mappings. An empty map is returned if an error occurs
     */
    public synchronized Map<String, FormatterConfiguration> getFormatMappings(
            final String xmlFileName, final boolean inClassPath) {

        if (!isLoaded) {
            loadMappings(xmlFileName, inClassPath);
            isLoaded = true;
        }


        return formatCfgMapping;
    }


    /**
     * Load the formatter mappings.
     *
     * @param xmlFileName the XML file to load the mappings from
     * @param inClassPath indicates whether the mapping file is in the classpath
     */
    private void loadMappings(final String xmlFileName, final boolean inClassPath) {
        try {
            final InputStream xmlStream = (inClassPath)
                    ? getClass().getClassLoader()
                    .getResourceAsStream(xmlFileName)
                    : new BufferedInputStream(new FileInputStream(xmlFileName));

            final InputSource inputSrc = new InputSource(xmlStream);
            final Digester digester = new Digester();
            digester.clear();

            CSVFormatterConfigParser.ruleSet
                    .addRuleInstances(digester);
            digester.push(new ArrayList<FormatterConfiguration>());
            final List<FormatterConfiguration> formatMappingList = (List<FormatterConfiguration>) digester
                    .parse(inputSrc);

            for (FormatterConfiguration formatConfig : formatMappingList) {
                formatCfgMapping.put(formatConfig.getFormatterName(), formatConfig);
            }
        } catch (final FileNotFoundException e) {
            LOG.warn("The XML File: "
                    + xmlFileName + " was not found", e);
        } catch (final IOException e) {
            LOG.warn("The XML File: "
                    + xmlFileName + " could not be read", e);
        } catch (final SAXException e) {
            LOG.warn("The XML File: "
                    + xmlFileName + " could not be parsed", e);
        }
    }

    public synchronized static CSVFormatterConfigParser getConfigParser() {

        if (singleton == null) {
            final InputStream is = CSVFormatterConfigParser.class.getClassLoader()
                    .getResourceAsStream("net/sf/anupam/csv/formatters/csv-formatter-config-digester-rules.xml");
            if (is != null) {
                final InputSource isrc = new InputSource(is);
                ruleSet = new FromXmlRuleSet(isrc);
                LOG.info("Loaded Digester Rules for "
                        + CSVFormatterConfigParser.class);
            } else {
                LOG
                        .error("The CSV Formatter Configuration Digester Rules XML was not found");
            }
            singleton = new CSVFormatterConfigParser();
        }
        return singleton;
    }
}
