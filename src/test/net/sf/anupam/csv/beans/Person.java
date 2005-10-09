/*
 * Person.java
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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Sample bean to represent a Person with an Email ID.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @csv.bean-mapping bean-name="personBean" csv-header="true"
 */
public class Person {

    private String firstName;
    private String lastName;
    private String location;
    private EmailDetails emailID;


    /**
     * Default Constructor.
     */
    public Person() {
        super();
    }

    /**
     * Returns the first name of this person.
     *
     * @return the first name
     *
     * @csv.field-mapping field-name="firstname" position="0"
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of this person.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of this person.
     *
     * @return the last name
     *
     * @csv.field-mapping field-name="lastname" position="1"
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of this person.
     *
     * @param lastName the last name to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the location of this person.
     *
     * @return the location
     *
     * @csv.field-mapping field-name="location" position="2"
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location of this person.
     *
     * @param location the location
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * Returns the emailID details of this person.
     *
     * @return the emailID details
     *
     * @csv.field-mapping field-name="emailID" position="3" bean-ref="emailIDBean"
     */
    public EmailDetails getEmailID() {
        return emailID;
    }

    /**
     * Sets the emailID details for this person.
     *
     * @param emailID the emailID details to set
     */
    public void setEmailID(final EmailDetails emailID) {
        this.emailID = emailID;
    }

    /**
     * Returns a string representation of this person for <code>debugging</code> purposes only.
     *
     * @return the string representation
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("firstName", firstName)
                .append("lastName", lastName)
                .append("location", location)
                .toString();
    }
}
