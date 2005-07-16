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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML Parser (based on Commons Digester) to parse and return the CSV formatter
 * configuration.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see org.apache.commons.digester.Digester
 */
class CSVFormatterConfigParser {

    /**
     * The logger to use.
     */
    private static final Log      LOG      = LogFactory
                                                   .getLog(CSVFormatterConfigParser.class);

    /**
     * The rule set for parsing formatter configuration.
     */
    private static FromXmlRuleSet ruleSet;

    /**
     * The digester to use for parsing the formatter configuration.
     */
    private Digester              digester = new Digester();

    static {

        final InputStream is = ClassLoader
                .getSystemResourceAsStream("net/sf/anupam/csv/formatters/csv-formatter-config-digester-rules.xml");
        if (is != null) {
            final InputSource isrc = new InputSource(is);
            ruleSet = new FromXmlRuleSet(isrc);
            LOG.info("Loaded Digester Rules for "
                    + CSVFormatterConfigParser.class);
        } else {
            LOG
                    .error("The CSV Formatter Configuration Digester Rules XML was not found");
        }

    }

    /**
     * Constructor for CSVMappingParser.
     */
    public CSVFormatterConfigParser() {
        super();
        digester.clear();

        CSVFormatterConfigParser.ruleSet.addRuleInstances(digester);
        digester.push(new ArrayList<FormatterConfiguration>());

    }

    /**
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (digester != null) {
            digester.clear();
            digester = null;
        }
    }

    /**
     * Returns the map of parsed format configuration beans.
     * 
     * @param xmlFileName
     *            the XML mapping configuration file
     * @param inClassPath
     *            flag indicating whether the XML file is in the classpath
     * @return a map of format mappings. An empty map is returned if an error
     *         occurs
     */
    public Map<String, FormatterConfiguration> getFormatMappings(
            final String xmlFileName, final boolean inClassPath) {

        final Map<String, FormatterConfiguration> formatCfgMapping = new WeakHashMap<String, FormatterConfiguration>();

        try {
            final InputStream xmlStream = (inClassPath)
                    ? ClassLoader.getSystemResourceAsStream(xmlFileName)
                    : new BufferedInputStream(new FileInputStream(xmlFileName));

            final InputSource inputSrc = new InputSource(xmlStream);

            final List<FormatterConfiguration> formatMappingList = (List<FormatterConfiguration>) digester
                    .parse(inputSrc);

            for (FormatterConfiguration formatConfig : formatMappingList) {
                formatCfgMapping.put(formatConfig.getFormatterName(),
                        formatConfig);
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
        return formatCfgMapping;
    }

}
