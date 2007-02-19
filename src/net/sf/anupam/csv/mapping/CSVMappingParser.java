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
package net.sf.anupam.csv.mapping;

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
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * XML Parser (based on Commons Digester) to parse and return the mapping
 * configuration.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @see org.apache.commons.digester.Digester
 * @since 1.5
 */
public class CSVMappingParser {

    /**
     * The logger to use.
     */
    private static final Log LOG = LogFactory
            .getLog(CSVMappingParser.class);

    /**
     * The digester rule set for parsing the mapping file.
     */
    private static FromXmlRuleSet ruleSet;

    /**
     * The digester to use for parsing the mapping file.
     */
    private transient Digester digester = new Digester();

    static {

        final InputStream inStream = ClassLoader
                .getSystemResourceAsStream("net/sf/anupam/csv/mapping/csv-mapping-digester-rules.xml");
        if (inStream == null) {
            LOG.error("The CSV Mapping Digester Rules XML was not found");
        } else {
            final InputSource isrc = new InputSource(inStream);
            ruleSet = new FromXmlRuleSet(isrc);
            LOG.info("Loaded Digester Rules for "
                    + CSVMappingParser.class);
        }

    }

    /**
     * Constructor for CSVMappingParser.
     */
    public CSVMappingParser() {
        super();
        digester.clear();

        CSVMappingParser.ruleSet.addRuleInstances(digester);
        digester.push(new ArrayList<CSVBeanMapping>());

    }

    /**
     * Finalizes this mapping parser.
     *
     * @throws Throwable thrown if the finalization fails
     * @see Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        if (digester != null) {
            digester.clear();
            digester = null;
        }
        super.finalize();
    }

    /**
     * Returns the map of parsed mapping configuration beans.
     *
     * @param xmlFileName the XML mapping configuration file
     * @param inClassPath flag indicating whether the XML file is in the classpath
     * @return a map of CSV bean mappings. An empty map is returned if an error
     *         occurs
     */
    @SuppressWarnings("unchecked")
	public Map<String, CSVBeanMapping> getMappings(final String xmlFileName,
                                                   final boolean inClassPath) {

        final Map<String, CSVBeanMapping> beanMap = new WeakHashMap<String, CSVBeanMapping>();

        try {
            final InputStream xmlStream = (inClassPath)
                    ? ClassLoader.getSystemResourceAsStream(xmlFileName)
                    : new BufferedInputStream(new FileInputStream(xmlFileName));

            final InputSource inputSrc = new InputSource(xmlStream);

            final List<CSVBeanMapping> mappingList = (List<CSVBeanMapping>) digester
                    .parse(inputSrc);

            for (CSVBeanMapping mappedBean : mappingList) {
                beanMap.put(mappedBean.getBeanName(), mappedBean);
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
        return beanMap;
    }

}
