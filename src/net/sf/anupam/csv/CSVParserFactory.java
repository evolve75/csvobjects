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

import net.sf.anupam.csv.formatters.CSVFieldFormatter;
import net.sf.anupam.csv.formatters.CSVFormatterFactory;
import net.sf.anupam.csv.mapping.CSVBeanMapping;
import net.sf.anupam.csv.mapping.CSVFieldMapping;
import net.sf.anupam.csv.mapping.CSVMappingParser;
import net.sf.anupam.csv.exceptions.CSVOException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton factory for creating the {@link CSVParser CSVParser} parser objects
 * for clients' of the framework. This factory uses the
 * <code>csv-mapping.xml</code> mapping configuration to create CSV parsers
 * customized for the POJO bean to parse. This is the first interface for
 * clients of the framework.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @see CSVParser
 * @since 1.5
 */
public final class CSVParserFactory {

    /**
     * The Mapping file name.
     */
    private static final String MAPPING_FILE_NAME = "csv-mapping.xml";

    /**
     * The logger to use.
     */
    private static final Log LOG = LogFactory.getLog(CSVParserFactory.class);

    /**
     * The singleton factory instance.
     */
    private static CSVParserFactory singleton;

    private static final CSVFormatterFactory FORMATTER_FACTORY = CSVFormatterFactory
            .getSingleton();

    /**
     * The CSV to POJO mapping repository.
     */
    private transient final Map<String, CSVBeanMapping> beanMappings;

    /**
     * Constructor for CSVParserFactory. Private as this is a singleton.
     */
    private CSVParserFactory() {
        super();
        beanMappings = new HashMap<String, CSVBeanMapping>();
    }

    /**
     * Returns the singleton instance of this factory.
     *
     * @return the singleton parser factory
     * @throws CSVOException thrown if the singleton cannot be created
     */
    public synchronized static CSVParserFactory getSingleton()
            throws CSVOException {
        if (singleton == null) {
            // Create the singleton at startup.
            singleton = new CSVParserFactory();
            singleton.loadMappings();
            LOG.info("Created the Singleton for: " + CSVParserFactory.class);
        }
        return singleton;
    }

    /**
     * Loads the bean mapping configuration from the XML mapping file.
     *
     * @throws CSVOException thrown if the mapping cannot be loaded
     */
    private void loadMappings()
            throws CSVOException {
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
        LOG.debug("Loaded the CSV Mapping configuration from "
                + MAPPING_FILE_NAME);
    }

    /**
     * Creates any necessary field formatters for the specified field mapping.
     *
     * @param fieldMapping the field for which formatters should be created
     * @throws net.sf.anupam.csv.exceptions.CSVOException
     *          thrown if the specified formatters cannot be created
     */
    private void createFormattersFor(final CSVFieldMapping fieldMapping)
            throws CSVOException {

        final CSVFieldFormatter formatter = FORMATTER_FACTORY
                .createFormatterFor(fieldMapping.getReformatterName());
        fieldMapping.setFormatter(formatter);

    }

    /**
     * Resolves bean references for the specified field, and sets the bean
     * mapping hierarchy accordingly.
     *
     * @param fieldMapping the field for which references need to be resolved
     */
    private void resolveBeanReferencesFor(final CSVFieldMapping fieldMapping) {

        final String beanRefName = fieldMapping.getBeanReferenceName();
        if (!beanRefName.equalsIgnoreCase("none")) {
            final CSVBeanMapping referencedBean = getBeanMapping(beanRefName);

            if (referencedBean == null) {
                LOG.warn("For field " + fieldMapping
                        + " the referenced bean does not exist");
                fieldMapping.setBeanReferenceName("none");
            } else {
                fieldMapping.setBeanReference(referencedBean);
            }
        }

    }

    /**
     * Returns the requested bean mapping configuration.
     *
     * @param beanName the POJO bean for which the mapping is to be returned
     * @return the CSV bean mapping, or <code>null</code> if not found
     */
    public CSVBeanMapping getBeanMapping(final String beanName) {
        return beanMappings.get(beanName);
    }

    /**
     * Returns a new CSV file parser for the specified mapping, and the
     * specified CSV file.
     *
     * @param mappingName the CSV mapping to for which the parser should be created
     * @param csvFileName the CSV file to be parsed
     * @param inClassPath indicates whether the CSV file is in the classpath
     * @return the CSV Parser, or <code>null</code> if not found
     * @throws FileNotFoundException thrown if the specified CSV file cannot be found
     * @see #getCSVParser(String,java.io.Reader)
     */
    public CSVParser getCSVParser(final String mappingName,
                                  final String csvFileName, final boolean inClassPath
    ) throws FileNotFoundException {

        if (StringUtils.isEmpty(csvFileName)) {
            LOG.warn("The specified CSV Filename is empty");
            throw new IllegalArgumentException("File Name is empty");
        }

        final Reader reader;

        try {
            if (inClassPath) {
                final InputStream inStream = ClassLoader
                        .getSystemResourceAsStream(csvFileName);
                if (inStream == null) {
                    throw new FileNotFoundException("The CSV File: "
                            + csvFileName + " was not found in the classpath");
                }
                reader = new InputStreamReader(inStream);
            } else {
                reader = new FileReader(csvFileName);

            }
            LOG.debug("Successfully read the CSV file");
        } catch (final FileNotFoundException e) {
            LOG.warn("The specified CSV File: " + csvFileName
                    + " was not found", e);
            throw e;
        }

        return getCSVParser(mappingName, reader);
    }

    /**
     * Returns a new CSV file parser for the specified mapping and the specified
     * CSV reader stream.
     *
     * @param mappingName the CSV mapping for which the parser should be returned
     * @param csvReader   the CSV stream to parse
     * @return the CSV Parser, or <code>null</code> if not found
     * @see #getCSVParser(String,String,boolean)
     */
    public CSVParser getCSVParser(final String mappingName,
                                  final Reader csvReader) {

        final CSVBeanMapping beanMapping = getBeanMapping(mappingName);

        if (beanMapping == null) {
            LOG.warn("Specified bean mapping was not found");
            throw new IllegalArgumentException(
                    "Specified bean mapping was not found");
        }

        if (csvReader == null) {
            LOG.warn("Specified CSV IO Reader was null");
            throw new IllegalArgumentException(
                    "Specified CSV IO Reader was null");
        }

        final CSVReader reader = new CSVReader(csvReader, beanMapping
                .isCsvHeaderPresent());

        return new CSVParser(beanMapping, reader);
    }
}
