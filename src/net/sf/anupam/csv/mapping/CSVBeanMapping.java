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
 * Represents the CSV to Java Bean mapping of a single Java bean.
 * Instances of this class store the bean name, the fully qualified 
 * class name, CSV header indicator, as well as individual CSV field
 * mappings. 
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see CSVFieldMapping
 */
public class CSVBeanMapping
        implements Iterable<CSVFieldMapping> {

    /**
     * Name of the Java bean being mapped.
     */
    private String                     beanName;

    /**
     * Fully qualified class name of the Java bean being mapped.
     */
    private String                     beanClass;

    /**
     * Indicates whether the source CSV has a header row.
     */
    private boolean                    csvHeaderPresent;

    /**
     * List of mapped CSV fields for this bean mapping.
     */
    private SortedSet<CSVFieldMapping> fields;

    /**
     * The highest field position number.
     */
    private int                        maxFieldPosition;

    /**
     * Constructor for CSVBeanMapping.
     */
    public CSVBeanMapping() {
        super();
        fields = new TreeSet<CSVFieldMapping>();
    }

    /**
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
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<CSVFieldMapping> iterator() {
        return fields.iterator();
    }

    /**
     * Returns value of the beanClass.
     * 
     * @return Returns the beanClass.
     */
    public String getBeanClass() {
        return this.beanClass;
    }

    /**
     * Sets value of the beanClass.
     * 
     * @param beanClass
     *            The beanClass to set.
     */
    public void setBeanClass(final String beanClass) {
        this.beanClass = StringUtils.trim(beanClass);
    }

    /**
     * Returns value of the beanName.
     * 
     * @return Returns the beanName.
     */
    public String getBeanName() {
        return this.beanName;
    }

    /**
     * Sets value of the beanName.
     * 
     * @param beanName
     *            The beanName to set.
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
     * Returns value of the maxFieldPosition.
     * 
     * @return Returns the maxFieldPosition.
     */
    public int getMaxFieldPosition() {
        return this.maxFieldPosition;
    }

    /**
     * Returns value of the csvHeaderPresent.
     * 
     * @return Returns the csvHeaderPresent.
     */
    public boolean isCsvHeaderPresent() {
        return this.csvHeaderPresent;
    }

    /**
     * Sets value of the csvHeaderPresent.
     * 
     * @param csvHeaderPresent
     *            The csvHeaderPresent to set.
     */
    public void setCsvHeaderPresent(final boolean csvHeaderPresent) {
        this.csvHeaderPresent = csvHeaderPresent;
    }
}
