/*
 * CSVFieldMapping.java 
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

import net.sf.anupam.csv.formatters.CSVFieldFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents a single CSV field to Java Bean attribute mapping. 
 * The mapping can be for basic data types, or point to other
 * referenced CSV bean mappings for representing nested beans.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 * @see CSVBeanMapping
 */
public class CSVFieldMapping
        implements Comparable<CSVFieldMapping> {

    /**
     * Name of CSV field being mapped.
     */
    private String            fieldName;

    /**
     * Fully qualified class name of the field being mapped.
     */
    private String            fieldType;

    /**
     * The CSV field position (starting at 0).
     */
    private int               fieldPosition;

    /**
     * Name of the bean's attribute.
     */
    private String            attributeName;

    /**
     * The CSV field formatter to use for this field.
     */
    private CSVFieldFormatter formatter;

    /**
     * Declarative name of the CSV field formatter to use.
     */
    private String            reformatterName;

    /**
     * Declarative bean name of the being being referenced by this field
     * mapping.
     */
    private String            beanReferenceName;

    /**
     * The CSV bean mapping referenced by this field mapping.
     */
    private CSVBeanMapping    beanReference;

    /**
     * Constructor for CSVFieldMapping.
     */
    public CSVFieldMapping() {
        super();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(fieldName).append(fieldPosition)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CSVFieldMapping)) {
            return false;
        }
        final CSVFieldMapping castOther = (CSVFieldMapping) other;
        return new EqualsBuilder().append(fieldName, castOther.fieldName)
                .append(fieldPosition, castOther.fieldPosition).isEquals();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final ToStringBuilder strBuilder = new ToStringBuilder(this);
        strBuilder.append("fieldName", fieldName)
                .append("fieldType", fieldType).append("fieldPosition",
                        fieldPosition).append("attributeName", attributeName)
                .append("reformatterName", reformatterName);

        strBuilder.append("FormatterClass", (formatter != null
                ? formatter.getClass()
                : "None"));
        strBuilder.append("beanReferenceName", beanReferenceName);
        strBuilder.append("Bean Reference Class", (beanReference != null)
                ? beanReference.getBeanClass()
                : "None");
        return strBuilder.toString();
    }

    /**
     * @see java.lang.Comparable#compareTo
     */
    public int compareTo(final CSVFieldMapping other) {
        if (this.equals(other)) {
            return 0;
        } else {
            return (this.getFieldPosition() < other.getFieldPosition())
                    ? -1
                    : +1;
        }

    }

    /**
     * Returns value of the attributeName.
     * 
     * @return Returns the attributeName.
     */
    public String getAttributeName() {
        return this.attributeName;
    }

    /**
     * Sets value of the attributeName.
     * 
     * @param attributeName
     *            The attributeName to set.
     */
    public void setAttributeName(final String attributeName) {
        this.attributeName = StringUtils.trim(attributeName);
    }

    /**
     * Returns value of the fieldName.
     * 
     * @return Returns the fieldName.
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Sets value of the fieldName.
     * 
     * @param fieldName
     *            The fieldName to set.
     */
    public void setFieldName(final String fieldName) {
        this.fieldName = StringUtils.trim(fieldName);
    }

    /**
     * Returns value of the fieldPosition.
     * 
     * @return Returns the fieldPosition.
     */
    public int getFieldPosition() {
        return this.fieldPosition;
    }

    /**
     * Sets value of the fieldPosition.
     * 
     * @param fieldPosition
     *            The fieldPosition to set.
     */
    public void setFieldPosition(final int fieldPosition) {
        this.fieldPosition = fieldPosition;
    }

    /**
     * Returns value of the fieldType.
     * 
     * @return Returns the fieldType.
     */
    public String getFieldType() {
        return this.fieldType;
    }

    /**
     * Sets value of the fieldType.
     * 
     * @param fieldType
     *            The fieldType to set.
     */
    public void setFieldType(final String fieldType) {
        this.fieldType = StringUtils.trim(fieldType);
    }

    /**
     * Returns value of the formatter.
     * 
     * @return Returns the formatter.
     */
    public CSVFieldFormatter getFormatter() {
        return this.formatter;
    }

    /**
     * Sets value of the formatter.
     * 
     * @param formatter
     *            The formatter to set.
     */
    public void setFormatter(final CSVFieldFormatter formatter) {
        this.formatter = formatter;
    }

    /**
     * Returns value of the reformatterName.
     * 
     * @return Returns the reformatterName.
     */
    public String getReformatterName() {
        return this.reformatterName;
    }

    /**
     * Sets value of the reformatterName.
     * 
     * @param reformatterName
     *            The reformatterName to set.
     */
    public void setReformatterName(final String reformatterName) {
        this.reformatterName = reformatterName;
    }

    /**
     * Returns value of the bean Reference name.
     * 
     * @return Returns the beanReference.
     */
    public String getBeanReferenceName() {
        return this.beanReferenceName;
    }

    /**
     * Sets value of the beanReference name.
     * 
     * @param beanReferenceName
     *            The beanReference to set.
     */
    public void setBeanReferenceName(final String beanReferenceName) {
        this.beanReferenceName = beanReferenceName;
    }

    /**
     * Sets value of the beanReference.
     * 
     * @param beanReference
     *            The beanReference to set.
     */
    public void setBeanReference(final CSVBeanMapping beanReference) {
        this.beanReference = beanReference;
    }

    /**
     * Returns value of the beanReference.
     * 
     * @return Returns the beanReference.
     */
    public CSVBeanMapping getBeanReference() {
        return this.beanReference;
    }

}
