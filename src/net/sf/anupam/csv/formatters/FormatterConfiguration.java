/*
 * FormatConfiguration.java 
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * FormatConfiguration.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 */
public class FormatterConfiguration
        implements Comparable<FormatterConfiguration> {

    /**
     * Name of this formatter.
     */
    private String  formatterName;

    /**
     * Fully qualified class name of the formatter.
     */
    private String  formatterClass;

    /**
     * Whether special formatter construction is to be performed by the factory
     * methods.
     */
    private boolean constructionNeeded;

    /**
     * Constructor for FormatConfiguration.
     */
    public FormatterConfiguration() {
        super();
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final FormatterConfiguration other) {

        return new CompareToBuilder()
                .append(formatterName, other.formatterName).append(
                        formatterClass, other.formatterClass).toComparison();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("formatterName", formatterName)
                .append("formatterClass", formatterClass).append(
                        "constructionNeeded", constructionNeeded).toString();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(formatterName).append(
                formatterClass).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FormatterConfiguration)) {
            return false;
        }

        final FormatterConfiguration castOther = (FormatterConfiguration) other;
        return new EqualsBuilder().append(formatterName,
                castOther.formatterName).append(formatterClass,
                castOther.formatterClass).isEquals();
    }

    /**
     * Returns value of the formatName.
     * 
     * @return Returns the formatName.
     */
    public String getFormatterName() {
        return this.formatterName;
    }

    /**
     * Sets value of the formatName.
     * 
     * @param formatName
     *            The formatName to set.
     */
    public void setFormatterName(final String formatName) {
        this.formatterName = formatName;
    }

    /**
     * Returns value of the formatterClass.
     * 
     * @return Returns the formatterClass.
     */
    public String getFormatterClass() {
        return this.formatterClass;
    }

    /**
     * Sets value of the formatterClass.
     * 
     * @param formatterClass
     *            The formatterClass to set.
     */
    public void setFormatterClass(final String formatterClass) {
        this.formatterClass = formatterClass;
    }

    /**
     * Returns value of the constructionNeeded.
     * 
     * @return Returns the constructionNeeded.
     */
    public boolean isConstructionNeeded() {
        return this.constructionNeeded;
    }

    /**
     * Sets value of the constructionNeeded.
     * 
     * @param constructionNeeded
     *            The constructionNeeded to set.
     */
    public void setConstructionNeeded(final boolean constructionNeeded) {
        this.constructionNeeded = constructionNeeded;
    }

}
