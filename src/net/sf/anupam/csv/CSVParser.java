/*
 * CSVParser.java 
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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import net.sf.anupam.csv.formatters.CSVFieldFormatter;
import net.sf.anupam.csv.mapping.CSVBeanMapping;
import net.sf.anupam.csv.mapping.CSVFieldMapping;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Parses CSV files and creates the mapped POJO objects. This is the primary
 * interface into the CSV parsing framework.
 * <p>
 * The class implements {@link java.lang.Iterable Iterable} interface and can be
 * used in the new <em>Tiger</em> for loops to iterate over all the CSV
 * records in the file.
 * </p>
 * <p>
 * Configuration of the parser is performed via the <code>csv-mapping.xml</code>
 * file. See the package description for more details.
 * </p>
 * <p>
 * Note that the class is not meant to be instantiated directly. Instead, the
 * {@link net.sf.anupam.csv.CSVParserFactory CSVParserFactory} factory should be
 * used for creation of instances.
 * </p>
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 */
public class CSVParser
        implements Iterable<Object> {

    /** The logger to use. */
    protected static final Log LOG = LogFactory.getLog(CSVParser.class);

    /** The CSV Reader to use for this parser. */
    private CSVReader          reader;

    /** The root bean mapping configuration for this parser. */
    private CSVBeanMapping     rootBeanMapping;

    /**
     * Constructor for CSVParser.
     * 
     * @param beanMapping
     *            the bean mapping to use
     * @param reader
     *            the CSV Reader object
     */
    public CSVParser(final CSVBeanMapping beanMapping, final CSVReader reader) {
        super();
        this.rootBeanMapping = beanMapping;
        this.reader = reader;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("beanMapping", rootBeanMapping)
                .toString();
    }

    /**
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (reader != null) {
            reader.close();
            reader = null;
        }
        rootBeanMapping = null;
    }

    /**
     * The iterator to provide the Iterable interface to the parser.
     * 
     * @author Anupam Sengupta
     * @version $Revision$
     */
    private class MappedObjectIterator
            implements Iterator<Object> {

        /** The actual line iterator to use. */
        private Iterator<List<String>> csvLineIter;

        /**
         * The iterator constructor.
         * 
         * @param csvLineIter
         *            The actual line iterator to use
         */
        MappedObjectIterator(final Iterator<List<String>> csvLineIter) {
            super();
            this.csvLineIter = csvLineIter;
        }

        /**
         * @see java.lang.Object#finalize()
         */
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            csvLineIter = null;
        }

        /**
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            return csvLineIter.hasNext();
        }

        /**
         * @see java.util.Iterator#next()
         */
        public Object next() {
            final List<String> csvLine = csvLineIter.next();
            return getMappedBean(csvLine, getRootBeanMapping());
        }

        /**
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            csvLineIter.remove();
        }

        /**
         * Applies the field formatters if present.
         * 
         * @param csvFieldValue
         *            the field to format
         * @param fieldMapping
         *            the field mapping from which the formatter should be used
         * @return the formatted value
         */
        private String formatValue(final String csvFieldValue,
                final CSVFieldMapping fieldMapping) {
            final CSVFieldFormatter formatter = fieldMapping.getFormatter();
            if (formatter == null) {
                return csvFieldValue;
            }

            return formatter.format(csvFieldValue);
        }

        /**
         * Returns the mapped bean from the specified list of CSV values.
         * 
         * @param csvLine
         *            the CSV line to parse
         * @param beanMap
         *            the bean mapping to use
         * @return the mapped bean
         */
        private Object getMappedBean(final List<String> csvLine,
                final CSVBeanMapping beanMap) {

            try {
                final Object bean = Class.forName(beanMap.getBeanClass())
                        .newInstance();

                for (CSVFieldMapping fieldMapping : beanMap) {
                    Object formattedFieldValue;

                    if (fieldMapping.getBeanReferenceName().equals("none")) {
                        formattedFieldValue = getMappedField(csvLine,
                                fieldMapping);

                    } else {
                        // Recurse and get the value.
                        formattedFieldValue = getMappedBean(csvLine,
                                fieldMapping.getBeanReference());
                    }

                    try {
                        BeanUtils.setProperty(bean, fieldMapping
                                .getAttributeName(), formattedFieldValue);
                    } catch (final IllegalAccessException e) {
                        LOG.warn(e);
                    } catch (final InvocationTargetException e) {
                        LOG.warn(e);
                    }
                }
                return bean;

            } catch (final ClassNotFoundException e) {
                LOG.warn("The Bean for class: "
                        + beanMap.getClass() + " could not be instantiated", e);
                return null;

            } catch (final IllegalAccessException e) {
                LOG.warn("The Bean for class: "
                        + beanMap.getClass() + " could not be instantiated", e);
                return null;
            } catch (final InstantiationException e) {
                LOG.warn("The Bean for class: "
                        + beanMap.getClass() + " could not be instantiated", e);
                return null;
            }
        }

        /**
         * Returns the parsed field value.
         * 
         * @param csvLine
         *            the CSV line to parse
         * @param fieldMapping
         *            the field mapping to use
         * @return the mapped field value
         */
        private Object getMappedField(final List<String> csvLine,
                final CSVFieldMapping fieldMapping) {

            final String csvFieldValue = csvLine.get(fieldMapping
                    .getFieldPosition());
            final Object formattedFieldValue = formatValue(csvFieldValue,
                    fieldMapping);
            return formattedFieldValue;
        }

    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Object> iterator() {

        return new MappedObjectIterator(reader.iterator());
    }

    /**
     * Returns value of the rootBeanMapping.
     * 
     * @return Returns the rootBeanMapping.
     */
    protected CSVBeanMapping getRootBeanMapping() {
        return this.rootBeanMapping;
    }

}
