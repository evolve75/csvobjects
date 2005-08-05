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

import net.sf.anupam.csv.formatters.CSVFieldFormatter;
import net.sf.anupam.csv.mapping.CSVBeanMapping;
import net.sf.anupam.csv.mapping.CSVFieldMapping;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

/**
 * Parses CSV files and creates the mapped POJO objects. This is the primary
 * interface into the CSV parsing framework.
 * <p/>
 * The class implements {@link Iterable Iterable} interface and can be
 * used in the new <em>Tiger</em> for loops to iterate over all the CSV
 * records in the file.
 * </p>
 * <p/>
 * Configuration of the parser is performed via the <code>csv-mapping.xml</code>
 * file. See the package description for more details.
 * </p>
 * <p/>
 * Note that the class is not meant to be instantiated directly. Instead, the
 * {@link CSVParserFactory CSVParserFactory} factory should be
 * used for creation of instances.
 * </p>
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @see CSVParserFactory
 * @since 1.5
 */
public class CSVParser implements Iterable<Object> {

    /**
     * The logger to use.
     */
    private static final Log LOG = LogFactory.getLog(CSVParser.class);

    /**
     * The CSV Reader to use for this parser.
     */
    private CSVReader reader;

    /**
     * The root bean mapping configuration for this parser.
     */
    private CSVBeanMapping rootBeanMapping;

    /**
     * Constructor for CSVParser. The constructor accepts the bean mapping to
     * use as the starting CSV mapping configuration
     * <em>(a.k.a the root bean mapping)</em> and the CSV reader/parser engine
     * to use for actual parsing.
     *
     * @param rootBeanMapping the bean mapping to use as the starting configuration
     * @param reader          the CSV Reader object which will actually parse the CSV file
     */
    public CSVParser(final CSVBeanMapping rootBeanMapping,
                     final CSVReader reader) {
        super();
        this.rootBeanMapping = rootBeanMapping;
        this.reader = reader;
    }

    /**
     * Dumps the root bean mapping configuration for this parser. This is meant
     * for <strong>debugging</strong> only.
     *
     * @return the string representation of this parser
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("beanMapping", rootBeanMapping)
                .toString();
    }

    /**
     * Finalizes this parser and closes the reader.
     *
     * @throws Throwable thrown if the finalization fails
     * @see Object#finalize()
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
     */
    private final class MappedObjectIterator implements Iterator<Object> {

        /**
         * The actual line iterator to use.
         */
        private Iterator<List<String>> csvLineIter;

        /**
         * The iterator constructor.
         *
         * @param csvLineIter The actual line iterator to use
         */
        MappedObjectIterator(final Iterator<List<String>> csvLineIter) {
            super();
            this.csvLineIter = csvLineIter;
        }

        /**
         * Finalizes this iterator and nullifies all instance variables.
         *
         * @throws Throwable if the finalization fails
         * @see Object#finalize()
         */
        @Override
        protected final void finalize() throws Throwable {
            super.finalize();
            csvLineIter = null;
        }

        /**
         * Indicates whether more parsed POJO beans exist.
         *
         * @return indicates whether there are any more parsed beans
         * @see java.util.Iterator#hasNext()
         */
        public final boolean hasNext() {
            return csvLineIter.hasNext();
        }

        /**
         * Returns the parsed and mapped POJO bean corresponding to the current
         * CSV line. Each subsequent invocation will parse and return the next
         * parsed POJO, until end of the CSV stream is reached.
         *
         * @return the parsed bean
         * @see java.util.Iterator#next()
         */
        public Object next() {
            final List<String> csvLine = csvLineIter.next();
            return getMappedBean(csvLine, getRootBeanMapping());
        }

        /**
         * This operation is not supported.
         *
         * @see java.util.Iterator#remove()
         */
        public final void remove() {
            csvLineIter.remove();
        }

        /**
         * Applies the field formatters if present.
         *
         * @param csvFieldValue the field to format
         * @param fieldMapping  the field mapping from which the formatter should be used
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
         * @param csvLine the CSV line to parse
         * @param beanMap the bean mapping to use
         * @return the mapped bean
         */
        private Object getMappedBean(final List<String> csvLine,
                                     final CSVBeanMapping beanMap) {

            try {
                final Object bean = Class.forName(beanMap.getBeanClass())
                        .newInstance();

                for (CSVFieldMapping fieldMapping : beanMap) {
                    final Object formattedFieldValue;

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
                LOG.warn("The Bean for class: " + beanMap.getClass()
                        + " could not be instantiated", e);
                return null;

            } catch (final IllegalAccessException e) {
                LOG.warn("The Bean for class: " + beanMap.getClass()
                        + " could not be instantiated", e);
                return null;
            } catch (final InstantiationException e) {
                LOG.warn("The Bean for class: " + beanMap.getClass()
                        + " could not be instantiated", e);
                return null;
            }
        }

        /**
         * Returns the parsed field value.
         *
         * @param csvLine      the CSV line to parse
         * @param fieldMapping the field mapping to use
         * @return the mapped field value
         */
        private Object getMappedField(final List<String> csvLine,
                                      final CSVFieldMapping fieldMapping) {

            final String csvFieldValue = csvLine.get(fieldMapping
                    .getFieldPosition());
            return formatValue(csvFieldValue, fieldMapping);

        }

    }

    /**
     * Returns the iterator for retrieving the parsed POJO beans.
     *
     * @return the iterator over the parsed beans
     * @see Iterable#iterator()
     */
    public Iterator<Object> iterator() {

        return new MappedObjectIterator(reader.iterator());
    }

    /**
     * Returns the root bean mapping. The root bean mapping is the bean mapping
     * with which the Parser is configured. "Child" bean mappings (which are not
     * directly accessible) are the bean mapping configurations which may be
     * present as references from the root mapping.
     *
     * @return Returns the root bean mapping.
     */
    private CSVBeanMapping getRootBeanMapping() {
        return this.rootBeanMapping;
    }

}
