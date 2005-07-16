/*
 * Employee.java 
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
package test.net.sf.anupam.csv.beans;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Sample bean to represent an employee.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @csv.bean-mapping bean-name="employeeBean" csv-header="true"
 */
public class Employee
        implements Comparable<Employee> {
    // ~ Instance fields
    // --------------------------------------------------------

    /**
     * Employee ID.
     */
    private String      employeeID;

    /**
     * First name of the employee.
     */
    private String      firstName;

    /**
     * Last name of the employee.
     */
    private String      lastName;

    /**
     * The client supplied identifier.
     */
    private String      clientSuppliedID;

    /**
     * An alternate client supplied identifier.
     */
    private String      clientSuppliedSecondaryID;

    /**
     * the designation of this employee.
     */
    private Designation designation;

    /**
     * Default Constructor.
     */
    public Employee() {
        super();
    }

    /**
     * Returns value of the clientSuppliedID.
     * 
     * @return Returns the clientSuppliedID.
     * @csv.field-mapping field-name="MyTimeID" position="8"
     */
    public String getClientSuppliedID() {
        return this.clientSuppliedID;
    }

    /**
     * Sets value of the clientSuppliedID.
     * 
     * @param clientSuppliedID
     *            The clientSuppliedID to set.
     */
    public void setClientSuppliedID(final String clientSuppliedID) {
        this.clientSuppliedID = clientSuppliedID;
    }

    /**
     * Returns value of the clientSuppliedSecondaryID.
     * 
     * @return Returns the clientSuppliedSecondaryID.
     * @csv.field-mapping field-name="contractorID" position="7"
     */
    public String getClientSuppliedSecondaryID() {
        return this.clientSuppliedSecondaryID;
    }

    /**
     * Sets value of the clientSuppliedSecondaryID.
     * 
     * @param clientSuppliedSecondaryID
     *            The clientSuppliedSecondaryID to set.
     */
    public void setClientSuppliedSecondaryID(
            final String clientSuppliedSecondaryID) {
        this.clientSuppliedSecondaryID = clientSuppliedSecondaryID;
    }

    /**
     * Sets the employee ID.
     * 
     * @param employeeID
     *            The employeeID to set.
     */
    public void setEmployeeID(final String employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * Return the employee ID.
     * 
     * @return Returns the employeeID.
     * @csv.field-mapping position="1"
     */
    public String getEmployeeID() {
        return this.employeeID;
    }

    /**
     * Set the first name.
     * 
     * @param firstName
     *            The firstName to set.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the first name.
     * 
     * @return Returns the firstName.
     * @csv.field-mapping position="2" reformat="firstWord"
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the last name.
     * 
     * @param lastName
     *            The lastName to set.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the last name.
     * 
     * @return Returns the lastName.
     * @csv.field-mapping position="2" reformat="lastWord"
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Employee other) {

        return new CompareToBuilder().append(employeeID, other.employeeID)
                .toComparison();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Employee)) {
            return false;
        }

        final Employee castOther = (Employee) other;

        return new EqualsBuilder().append(employeeID, castOther.employeeID)
                .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(employeeID).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("employeeID", employeeID)
                .append("firstName", firstName).append("lastName", lastName)
                .append("clientSuppliedID", clientSuppliedID).append(
                        "clientSuppliedSecondayID", clientSuppliedSecondaryID)
                .append("designation", designation).toString();
    }

    /**
     * Returns value of the designation.
     * 
     * @return Returns the designation.
     * @csv.field-mapping position="3" bean-ref="designationBean"
     */
    public Designation getDesignation() {
        return this.designation;
    }

    /**
     * Sets value of the designation.
     * 
     * @param designation
     *            The designation to set.
     */
    public void setDesignation(final Designation designation) {
        this.designation = designation;
    }
}
