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

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.ExcelCSVParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Reads a CSV file and parses the individual fields for each CSV record in the
 * file. The default delimiter is assumed to be the <code>,</code> (comma).
 * <p/>
 * <p/>
 * The class uses the CSV Parser engines from <a
 * href="http://ostermiller.org/utils/" target="_blank">Steven Ostermiller's
 * site</a>.
 * </p>
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @see com.Ostermiller.util.CSVParse
 * @since 1.5
 */
class CSVReader implements Iterable<List<String>> {

    /**
     * Logger to use.
     */
    protected static final Log LOG = LogFactory.getLog(CSVReader.class);

    /**
     * The CSV parser engine.
     */
    private transient CSVParse parser;

    /**
     * Flag which indicates whether the reader has read all the records.
     */
    private transient boolean readingComplete;

    /**
     * Flag which indicates whether the CSV file has a header row.
     */
    private transient boolean headerPresent;

    /**
     * Constructor which accepts a reader on the CSV stream to parse. The
     * presence of a CSV header row is also specified. If present, the header
     * row will be skipped.
     *
     * @param csvReader     the CSV stream reader from which to parse
     * @param headerPresent indicates whether the CSV stream has a header record
     */
    public CSVReader(final Reader csvReader, final boolean headerPresent) {
        super();
        this.headerPresent = headerPresent;

        parser = new ExcelCSVParser(csvReader);

    }

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
     * Finalizes this CSV reader and closes the IO connections.
     *
     * @throws Throwable thrown if the finalization fails.
     * @see Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    /**
     * Returns an iterator over the parsed lines. The iterator returns a list of
     * the CSV field values as a single value over each iteration.
     *
     * @return an iterator over the lines.
     */
    public Iterator<List<String>> iterator() {
        return new LineIterator();
    }

    // ~ Inner Classes
    // ----------------------------------------------------------

    /**
     * Inner iterator class to provide the Iterable interface to the reader.
     */
    private class LineIterator implements Iterator<List<String>> {

        /**
         * The parsed CSV field values.
         */
        private transient String[] parsedValues;

        /**
         * Flag indicating whether the previous line was read.
         */
        private transient boolean haveReadPreviousLine;

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
         * Returns <code>true</code> if there is at least one more parsed CSV line.
         *
         * @return <code>true></code> if there is at least one more parsed line
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
         * Returns a list of the CSV field values for the current line.
         *
         * @return the next list of parsed CSV field values
         * @see java.util.Iterator#next()
         */
        public List<String> next() {

            if (haveReadPreviousLine) {
                haveReadPreviousLine = false;
            } else {
                readOneLine();
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
         * Reads one CSV line using the CSV parser engine and stores the parsed
         * line fields.
         */
        private void readOneLine() {
            try {
                parsedValues = getParser().getLine();
                if (parsedValues == null) {
                    readingIsComplete();
                }
            } catch (final IOException e) {
                LOG.warn("Error in reading a line from the CSV stream ", e);
                readingIsComplete();
            }

        }

        /**
         * This method is not supported.
         *
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
     * Indicates whether the header row is present or not.
     *
     * @return Returns <code>true</code> if the header row is present
     */
    public boolean isHeaderPresent() {
        return this.headerPresent;
    }

    /**
     * Indicates whether the reader has read all CSV lines.
     *
     * @return Returns <code>true</code> if all CSV lines have been read
     */
    public boolean isReadingComplete() {
        return this.readingComplete;
    }

    /**
     * Sets the flag to denote that all lines have been read.
     */
    protected void readingIsComplete() {
        this.readingComplete = true;
    }

    /**
     * Returns the internal CSV parser engine instance for this reader.
     *
     * @return Returns the parser instance
     */
    protected CSVParse getParser() {
        return this.parser;
    }
}
