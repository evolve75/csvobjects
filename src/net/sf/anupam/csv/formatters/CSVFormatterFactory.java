/*
 * CSVFormatterFactory.java 
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A singleton factory which creates and caches the 
 * {@link CSVFieldFormatter csv field formatters}. The factory 
 * maintains a cache of CSV formatters that are reentrant.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see CSVFieldFormatter
 */
public final class CSVFormatterFactory {

    /**
     * The CSV formatter mapping file name. This file is present in the
     * CSVParser jar.
     */
    public static final String                  FMT_MAPPING_FILE_NAME = "net/sf/anupam/csv/formatters/csv-formatter-config.xml";

    /**
     * The singleton instance of the factory.
     */
    private static CSVFormatterFactory          singleton;

    /**
     * The generic NO-OP formatter which is used when no explicit formatter is
     * defined.
     */
    private static final CSVFieldFormatter      DO_NOTHING_FORMATTER  = new DoNothingFormatter();

    /**
     * The logger to use.
     */
    private static final Log                    LOG                   = LogFactory
                                                                              .getLog(CSVFormatterFactory.class);

    /**
     * Mapping of the formatter name and the configuration.
     */
    private Map<String, FormatterConfiguration> formatterMap;

    /**
     * The cached formatters.
     */
    private Map<String, CSVFieldFormatter>      formatterCache;

    static {
        singleton = new CSVFormatterFactory();
        singleton.loadMappings();
        LOG.info("Created the CSVFormatter Factory");
    }

    /**
     * Constructor for CSVFormatterFactory. Private to prevent direct
     * instantiation.
     */
    private CSVFormatterFactory() {
        super();
        formatterMap = new HashMap<String, FormatterConfiguration>();
        formatterCache = new HashMap<String, CSVFieldFormatter>();
    }

    /**
     * Returns the singleton instance.
     * 
     * @return the singleton instance
     */
    public static CSVFormatterFactory getSingleton() {
        return singleton;
    }

    /**
     * Loads all mappings.
     */
    private void loadMappings() {
        final CSVFormatterConfigParser parser = new CSVFormatterConfigParser();
        formatterMap.putAll(parser.getFormatMappings(FMT_MAPPING_FILE_NAME,
                true));
        createCache();
        LOG.debug("Loaded the CSV Mapping configuration from "
                + FMT_MAPPING_FILE_NAME);
    }

    /**
     * Creates the cached formatters for the ones which do not need special
     * construction.
     */
    private void createCache() {
        for (String formatterName : formatterMap.keySet()) {
            final FormatterConfiguration currentFormatter = formatterMap
                    .get(formatterName);
            // If the formatter does not require special construction,
            // then create it one time and cache it.
            if (!currentFormatter.isConstructionNeeded()) {

                final CSVFieldFormatter formatter = createFormatterForClass(currentFormatter
                        .getFormatterClass());

                formatterCache.put(formatterName, formatter);
            }
        }
    }

    /**
     * Creates a formatter from the specified class.
     * 
     * @param className
     *            the formatter class
     * @return the created formatter
     */
    private CSVFieldFormatter createFormatterForClass(final String className) {

        Object formatter = DO_NOTHING_FORMATTER;
        try {
            formatter = Class.forName(className.trim()).newInstance();
        } catch (final InstantiationException e) {
            LOG.warn("Could not create formatter for class: "
                    + className, e);
            formatter = DO_NOTHING_FORMATTER;
        } catch (final IllegalAccessException e) {
            LOG.warn("Could not create formatter for class: "
                    + className, e);
            formatter = DO_NOTHING_FORMATTER;
        } catch (final ClassNotFoundException e) {
            LOG.warn("Could not create formatter for class: "
                    + className, e);
            formatter = DO_NOTHING_FORMATTER;
        }
        return (CSVFieldFormatter) formatter;

    }

    /**
     * Creates a new instance of the specified formatter. The cache is used
     * whenever possible.
     * 
     * @param formatterName
     *            the formatter to return
     * @return the requested formatter, or <code>null</code> if not found
     */
    public CSVFieldFormatter createFormatterFor(final String formatterName) {

        // If a cache hit, then return the cached formatter
        if (formatterCache.containsKey(formatterName)) {
            return formatterCache.get(formatterName);
        }

        return DO_NOTHING_FORMATTER;
    }
}