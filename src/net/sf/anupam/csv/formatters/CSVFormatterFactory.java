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

import net.sf.anupam.csv.exceptions.CSVOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton factory which creates and caches the
 * {@link CSVFieldFormatter csv field formatters}. The factory
 * maintains a cache of CSV formatters that are reentrant (i.e.,
 * the formatters that do not maintain any instance specific state).
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @see CSVFieldFormatter
 * @since 1.5
 */
public final class CSVFormatterFactory {

    /**
     * The CSV formatter mapping file name. This file assumed to be present in the
     * classpath.
     */
    private static final String FMT_MAPPING_FILE_NAME = "net/sf/anupam/csv/formatters/csv-formatter-config.xml";

    /**
     * The singleton instance of the factory.
     */
    private static CSVFormatterFactory singleton;

    /**
     * The generic NO-OP formatter which is used when no explicit formatter is
     * defined.
     */
    private static final CSVFieldFormatter DO_NOTHING_FORMATTER = new DoNothingFormatter();

    /**
     * The logger to use.
     */
    private static final Log LOG = LogFactory
            .getLog(CSVFormatterFactory.class);

    /**
     * Mapping of the formatter name and the configuration.
     */
    private Map<String, FormatterConfiguration> formatterLookupMap;

    /**
     * The cached formatters.
     */
    private Map<String, CSVFieldFormatter> formatterCache;


    /**
     * Constructor for CSVFormatterFactory. Private to prevent direct
     * instantiation.
     */
    private CSVFormatterFactory() {
        super();
        formatterLookupMap = new HashMap<String, FormatterConfiguration>();
        formatterCache = new HashMap<String, CSVFieldFormatter>();
    }

    /**
     * Returns the singleton instance of this factory.
     *
     * @return the singleton instance
     */
    public synchronized static CSVFormatterFactory getSingleton() {
        if (singleton == null) {
            singleton = new CSVFormatterFactory();
            singleton.loadMappings();
            LOG.info("Created the CSVFormatter Factory");
        }
        return singleton;
    }

    /**
     * Loads all mappings from the formatter configuration file.
     */
    private void loadMappings() {
        final CSVFormatterConfigParser parser = CSVFormatterConfigParser.getConfigParser();
        final FormatterConfiguration doNothingConfiguration = new FormatterConfiguration();
        doNothingConfiguration.setFormatterName("none");
        doNothingConfiguration.setFormatterClass("net.sf.anupam.csv.formatters.DoNothingFormatter");
        doNothingConfiguration.setConstructionNeeded(false);
        formatterLookupMap.put("none", doNothingConfiguration);
        formatterLookupMap.putAll(parser.getFormatMappings(FMT_MAPPING_FILE_NAME,
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
        for (String formatterName : formatterLookupMap.keySet()) {
            final FormatterConfiguration currentFormatter = formatterLookupMap
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
     * @param className the formatter class
     * @return the created formatter
     */
    private CSVFieldFormatter createFormatterForClass(final String className) {

        Object formatter;
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
     * @param formatterName the formatter to return
     * @return the requested formatter
     * @throws CSVOException thrown if the formatter cannot be created
     */
    public CSVFieldFormatter createFormatterFor(final String formatterName)
            throws CSVOException {

        // If a cache hit, then return the cached formatter
        if (formatterCache.containsKey(formatterName)) {
            return formatterCache.get(formatterName);
        } else {
            LOG.warn("Formatter: " + formatterName + " not found");
            throw new CSVOException("Formatter: " + formatterName + " not found");
        }

    }
}
