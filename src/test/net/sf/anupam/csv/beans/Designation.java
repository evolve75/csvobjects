/*
 * Designation.java 
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
package test.net.sf.anupam.csv.beans;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Sample bean to represent an employee designation.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @csv.bean-mapping bean-name="designationBean" csv-header="true"
 */
public class Designation
        implements Comparable<Designation> {

    /**
     * The designation.
     */
    private String designation;

    /**
     * Constructor for Designation.
     */
    public Designation() {
        super();
    }

    /**
     * Compares this designation to another designation.
     *
     * @param other the other designation to compare against
     * @return <code>0</code> if the two designations are equal, <code>-1</code> if this
     *         designation ranks lower, <code>+1</code> if this designation ranks higher
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(final Designation other) {

        return new CompareToBuilder().append(designation, other.designation)
                .toComparison();
    }

    /**
     * Returns the string representation of this designation for <strong>debugging</strong>
     * purposes only.
     *
     * @return the string representation
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("designation", designation)
                .toString();
    }

    /**
     * Returns the hash code for this designation. The hash code is based on the designation string.
     *
     * @return the hash code for this designation
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(designation).toHashCode();
    }

    /**
     * Compares this designation with another for equality. The equality is based on the
     * designation string.
     *
     * @param other the other designation to compare against
     * @return <code>true</code> if the designations are equal, <code>false</code> otherwise
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Designation)) {
            return false;
        }
        final Designation castOther = (Designation) other;
        return new EqualsBuilder().append(designation, castOther.designation)
                .isEquals();
    }

    /**
     * Returns value of the designation.
     *
     * @return Returns the designation.
     * @csv.field-mapping position="4"
     */
    public String getDesignation() {
        return this.designation;
    }

    /**
     * Sets value of the designation.
     *
     * @param designation The designation to set.
     */
    public void setDesignation(final String designation) {
        this.designation = designation;
    }

}
