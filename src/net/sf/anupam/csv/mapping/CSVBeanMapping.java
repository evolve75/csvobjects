/*
 * CSVBeanMapping.java
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

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Represents the CSV to Java Bean mapping for a single Java bean. Instances of
 * this class store the bean name, the fully qualified class name, CSV header
 * indicator, as well as individual CSV field mappings.
 * 
 * <p>
 * The bean name (which is user defined) acts as the name of the mapping as
 * well, and is used for looking up or accessing the specific
 * 
 * @link{net.sf.anupam.csv.CSVParser parsers} for this mapping.
 *                                   </p>
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see CSVFieldMapping
 */
public class CSVBeanMapping implements Iterable<CSVFieldMapping> {

    /**
     * Name of the Java bean being mapped. This is a user defined name, and need
     * not be same as the bean's class name.
     */
    private String beanName;

    /**
     * Fully qualified class name of the Java bean being mapped.
     */
    private String beanClass;

    /**
     * Indicates whether the source CSV has a header row.
     */
    private boolean csvHeaderPresent;

    /**
     * List of mapped CSV fields for this bean mapping.
     */
    private SortedSet<CSVFieldMapping> fields;

    /**
     * The highest field position number.
     */
    private int maxFieldPosition;

    /**
     * Constructor for CSVBeanMapping.
     */
    public CSVBeanMapping() {
        super();
        fields = new TreeSet<CSVFieldMapping>();
    }

    /**
     * Dumps content of the bean mapping. This is meant for <strong>debugging</strong>
     * only.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final ToStringBuilder strBuilder = new ToStringBuilder(this).append(
                "beanName", beanName).append("beanClass", beanClass);

        strBuilder.append("Number of Fields", fields.size());
        strBuilder.append("Max Field Position", getMaxFieldPosition());
        strBuilder.append("CSV Header Present", isCsvHeaderPresent());
        return strBuilder.toString();
    }

    /**
     * Provides an iterator over the CSV field mappings present in this bean
     * mapping.
     * 
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<CSVFieldMapping> iterator() {
        return fields.iterator();
    }

    /**
     * Returns the mapped bean's fully qualified class name.
     * 
     * @return Returns the mapped bean's class name
     */
    public String getBeanClass() {
        return this.beanClass;
    }

    /**
     * Sets the mapped bean's fully qualified class name.
     * 
     * @param beanClass
     *            The mapped bean's class name
     */
    public void setBeanClass(final String beanClass) {
        this.beanClass = StringUtils.trim(beanClass);
    }

    /**
     * Returns the user defined name of the mapped bean.
     * 
     * @return Returns the mapped bean's name
     */
    public String getBeanName() {
        return this.beanName;
    }

    /**
     * Sets the mapped bean's user defined name.
     * 
     * @param beanName
     *            The mapped bean's user defined name
     */
    public void setBeanName(final String beanName) {
        this.beanName = StringUtils.trim(beanName);
    }

    /**
     * Adds a new field mapping for this bean mapping.
     * 
     * @param fieldMapping
     *            the field mapping to add
     */
    public void addFieldMapping(final CSVFieldMapping fieldMapping) {
        fields.add(fieldMapping);
        maxFieldPosition = Math.max(maxFieldPosition, fieldMapping
                .getFieldPosition());
    }

    /**
     * Returns the maximum (i.e. highest) field position present in this bean
     * mapping. All field positions start from zero.
     * 
     * @return Returns the highest field position
     */
    public int getMaxFieldPosition() {
        return this.maxFieldPosition;
    }

    /**
     * Indicates whether a header row is present in the CSV mapping.
     * 
     * @return Returns <code>true</code> if the mapped CSV file or stream has
     *         a header
     */
    public boolean isCsvHeaderPresent() {
        return this.csvHeaderPresent;
    }

    /**
     * Sets the flag which indicates whether a header row is present in the
     * mapped CSV file or stream.
     * 
     * @param csvHeaderPresent
     *            The flag value to set
     */
    public void setCsvHeaderPresent(final boolean csvHeaderPresent) {
        this.csvHeaderPresent = csvHeaderPresent;
    }
}
