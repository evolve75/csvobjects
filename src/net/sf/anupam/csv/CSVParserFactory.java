/*
 * CSVParserFactory.java 
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
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import net.sf.anupam.csv.formatters.CSVFieldFormatter;
import net.sf.anupam.csv.formatters.CSVFormatterFactory;
import net.sf.anupam.csv.mapping.CSVBeanMapping;
import net.sf.anupam.csv.mapping.CSVFieldMapping;
import net.sf.anupam.csv.mapping.CSVMappingParser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Singleton factory for instantiating the {@link CSVParser CSVParser} parser
 * objects. This factory uses the <code>csv-mapping.xml</code> mapping
 * configuration to create CSV parsers customized for the POJO bean to parse.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see CSVParser
 */
public final class CSVParserFactory {

    /** The Mapping file name. */
    public static final String          MAPPING_FILE_NAME = "csv-mapping.xml";

    /** The singleton factory instance. */
    private static CSVParserFactory     singleton;

    /** The logger to use. */
    private static Log                  log               = LogFactory
                                                                  .getLog(CSVParserFactory.class);

    /** The CSV to POJO mapping beans repository. */
    private Map<String, CSVBeanMapping> beanMappings;

    static {
        singleton = new CSVParserFactory();
        singleton.loadMappings();
        log.info("Created the Singleton for: "
                + CSVParserFactory.class);
    }

    /**
     * Constructor for CSVParserFactory. Private as this is a singleton.
     */
    private CSVParserFactory() {
        super();
        beanMappings = new HashMap<String, CSVBeanMapping>();
    }

    /**
     * Return the singleton instance of the factory.
     * 
     * @return the parser factory
     */
    public static CSVParserFactory getSingleton() {
        return singleton;
    }

    /**
     * Creates a new CSV parser and loads the bean mapping configuration.
     */
    private void loadMappings() {
        final CSVMappingParser parser = new CSVMappingParser();
        beanMappings.putAll(parser.getMappings(MAPPING_FILE_NAME, true));

        for (String beanNames : beanMappings.keySet()) {
            final CSVBeanMapping currentBeanMapping = beanMappings
                    .get(beanNames);
            for (CSVFieldMapping currentFieldMapping : currentBeanMapping) {
                createFormattersFor(currentFieldMapping);
                resolveBeanReferencesFor(currentFieldMapping);
            }
        }
        log.debug("Loaded the CSV Mapping configuration from "
                + MAPPING_FILE_NAME);
    }

    /**
     * Creates any necessary field formatters.
     * 
     * @param fieldMapping
     *            the field for which formatters should be created
     */
    private void createFormattersFor(final CSVFieldMapping fieldMapping) {
        final CSVFormatterFactory formatterFactory = CSVFormatterFactory
                .getSingleton();

        final CSVFieldFormatter formatter = formatterFactory
                .createFormatterFor(fieldMapping.getReformatterName());
        fieldMapping.setFormatter(formatter);

    }

    /**
     * Resolve bean references for the specified field.
     * 
     * @param fieldMapping
     *            the field for which references need to be resolved
     */
    private void resolveBeanReferencesFor(final CSVFieldMapping fieldMapping) {

        final String beanRefName = fieldMapping.getBeanReferenceName();
        if (!beanRefName.equalsIgnoreCase("none")) {
            final CSVBeanMapping referencedBean = getBeanMapping(beanRefName);

            if (referencedBean != null) {
                fieldMapping.setBeanReference(referencedBean);
            } else {
                log.warn("For field "
                        + fieldMapping + " the referenced bean does not exist");
                fieldMapping.setBeanReferenceName("none");
            }
        }

    }

    /**
     * Returns the requested bean mapping.
     * 
     * @param beanName
     *            the mapping to return
     * @return the CSV bean mapping, or <code>null</code> if not found
     */
    public CSVBeanMapping getBeanMapping(final String beanName) {
        return beanMappings.get(beanName);
    }

    /**
     * Returns a new CSV file parser.
     * 
     * @param mappingName
     *            the CSV mapping to use
     * @param csvFileName
     *            the CSV file to parse
     * @param inClassPath
     *            indicates whether the CSV file is in the classpath
     * @param csvHasHeader
     *            indicates whether the CSV file has a header row
     * @return the CSV Parser, or <code>null</code> if not found
     * @exception FileNotFoundException
     *                thrown if the specified CSV file cannot be found
     */
    public CSVParser getCSVParser(final String mappingName,
            final String csvFileName, final boolean inClassPath,
            final boolean csvHasHeader) throws FileNotFoundException {

        if (StringUtils.isEmpty(csvFileName)) {
            log.warn("The specified CSV Filename is empty");
            throw new IllegalArgumentException("File Name is empty");
        }

        Reader reader;

        try {
            if (inClassPath) {
                final InputStream is = ClassLoader
                        .getSystemResourceAsStream(csvFileName);
                if (is == null) {
                    throw new FileNotFoundException("The CSV File: "
                            + csvFileName + " was not found in the classpath");
                }
                reader = new InputStreamReader(is);
            } else {
                reader = new FileReader(csvFileName);

            }
            log.debug("Successfully read the CSV file");
        } catch (final FileNotFoundException e) {
            log.warn("The specified CSV File: "
                    + csvFileName + " was not found", e);
            throw e;
        }

        return getCSVParser(mappingName, reader, csvHasHeader);
    }

    /**
     * Returns a new CSV file parser.
     * 
     * @param mappingName
     *            the CSV mapping to use
     * @param csvReader
     *            the CSV stream to parse
     * @param csvHasHeader
     *            indicates whether the CSV file has a header row
     * @return the CSV Parser, or <code>null</code> if not found
     */
    public CSVParser getCSVParser(final String mappingName,
            final Reader csvReader, final boolean csvHasHeader) {

        final CSVBeanMapping beanMapping = getBeanMapping(mappingName);

        if (beanMapping == null) {
            log.warn("Specified bean mapping was not found");
            throw new IllegalArgumentException(
                    "Specified bean mapping was not found");
        }

        if (csvReader == null) {
            log.warn("Specified CSV IO Reader was null");
            throw new IllegalArgumentException(
                    "Specified CSV IO Reader was null");
        }

        final CSVReader reader = new CSVReader(csvReader, beanMapping
                .isCsvHeaderPresent());

        final CSVParser parser = new CSVParser(beanMapping, reader);

        return parser;
    }
}
