/*
 * CSVReader.java
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
 * Version $Revision$
 */
package net.sf.anupam.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.ExcelCSVParser;

/**
 * Reads a CSV file and parses the individual fields for each CSV record in the
 * file. The default delimiter is assumed to be <code>,</code> (comma).
 * 
 * <p>
 * The class uses the CSV Parser engines from 
 *  <a href="http://ostermiller.org/utils/">Steven Ostermiller's site</a>.
 * </p>
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see com.Ostermiller.util.CSVParse
 */
class CSVReader
        implements Iterable<List<String>> {
    // ~ Instance fields
    // --------------------------------------------------------

    /**
     * Logger to use.
     */
    protected static final Log LOG = LogFactory.getLog(CSVReader.class);

    /**
     * The CSV parser engine.
     */
    private CSVParse           parser;

    /**
     * Flag which indicates whether the reader has read all the records.
     */
    private boolean            readingComplete;

    /**
     * Flag which indicates whether the CSV file has a header row.
     */
    private boolean            headerPresent;

    // ~ Constructors
    // -----------------------------------------------------------

    /**
     * Constructor which accepts a reader on the CSV stream to parse.
     * 
     * @param csvReader
     *            the CSV stream reader from which to parse
     * @param headerPresent
     *            indicates whether the CSV stream has a header record
     */
    public CSVReader(final Reader csvReader, final boolean headerPresent) {
        super();
        this.headerPresent = headerPresent;

        parser = new ExcelCSVParser(csvReader);

    }

    // ~ Methods
    // ----------------------------------------------------------------

    /**
     * Releases all system resources.
     */
    public void close() {
        try {
            if (parser != null) {
                parser.close();
                LOG.debug("Closed the CSV Reader");
            }
        } catch (final IOException e) {
            // Do nothing
        } finally {
            parser = null;
        }
    }

    /**
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    /**
     * Returns an iterator over the parsed lines.
     * 
     * @return an iterator over the lines.
     */
    public Iterator<List<String>> iterator() {
        return new LineIterator();
    }

    // ~ Inner Classes
    // ----------------------------------------------------------

    /**
     * Inner iterator class to provide the Iterable interface.
     */
    private class LineIterator
            implements Iterator<List<String>> {
        // ~ Methods
        // ------------------------------------------------------------

        /**
         * The parsed CSV field values.
         */
        private String [] parsedValues;

        /**
         * Flag indicating whether the previous line was read.
         */
        private boolean   haveReadPreviousLine;

        /**
         * Default Constructor.
         */
        public LineIterator() {
            super();
            if (isHeaderPresent()) {
                readOneLine();
            }
        }

        /**
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            if (isReadingComplete()) {
                return false;
            }

            if (!haveReadPreviousLine) {
                readOneLine();
                haveReadPreviousLine = true;
            }
            return !isReadingComplete();
        }

        /**
         * @see java.util.Iterator#next()
         */
        public List<String> next() {

            if (!haveReadPreviousLine) {
                readOneLine();
            } else {
                haveReadPreviousLine = false;
            }

            if (isReadingComplete()) {
                throw new NoSuchElementException();
            }

            final List<String> valueList = new ArrayList<String>(
                    parsedValues.length);
            CollectionUtils.addAll(valueList, parsedValues);

            return valueList;

        }

        /**
         * Read one CSV line using the CSV parser engine and store the parsed
         * line fields.
         */
        private void readOneLine() {
            try {
                parsedValues = getParser().getLine();
                if (parsedValues == null) {
                    setReadingComplete(true);
                }
            } catch (final IOException e) {
                LOG.warn("Error in reading a line from the CSV stream ", e);
                setReadingComplete(true);
            }

        }

        /**
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            LOG
                    .debug("Invalid call to the unsupported remove() method on the iterator");
            throw new UnsupportedOperationException(
                    "This method is not supported");
        }
    }

    /**
     * Returns value of the headerPresent.
     * 
     * @return Returns the headerPresent.
     */
    public boolean isHeaderPresent() {
        return this.headerPresent;
    }

    /**
     * Returns value of the readingComplete.
     * 
     * @return Returns the readingComplete.
     */
    public boolean isReadingComplete() {
        return this.readingComplete;
    }

    /**
     * Sets value of the readingComplete.
     * 
     * @param readingComplete
     *            The readingComplete to set.
     */
    protected void setReadingComplete(final boolean readingComplete) {
        this.readingComplete = readingComplete;
    }

    /**
     * Returns value of the parser.
     * 
     * @return Returns the parser.
     */
    protected CSVParse getParser() {
        return this.parser;
    }
}
