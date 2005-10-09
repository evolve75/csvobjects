/*
 * EmailDetails.java
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Sample bean to represent email ID details.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @csv.bean-mapping bean-name="emailIDBean" csv-header="true"
 */
public class EmailDetails {

    private String emailID;
    private String emailProvider;

    /**
     * Default constructor.
     */
    public EmailDetails() {
        super();
    }

    /**
     * Compares this email against another for equality. The comparision is based on the email ID and the provider
     * name.
     *
     * @param other the other email to compare against
     *
     * @return <code>true</code> if equal, <code>false</code> otherwise
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EmailDetails)) {
            return false;
        }

        final EmailDetails castOther = (EmailDetails) other;

        return new EqualsBuilder().append(emailID, castOther.getEmailID())
                .append(emailProvider, castOther.emailProvider)
                .isEquals();
    }

    /**
     * Returns the hashcode for this email. The hash code is based on the email ID and the provider name.
     *
     * @return the hash code
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(emailID)
                .append(emailProvider)
                .toHashCode();
    }

    /**
     * Returns a string representation of this email for <code>debugging</code> purposes only.
     *
     * @return the string representation
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("emailID", emailID)
                .append("emailProvider", emailProvider)
                .toString();
    }

    /**
     * Returns the email ID.
     *
     * @return the email ID
     *
     * @csv.field-mapping field-name="emailID" position="3"
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     * Sets the email ID.
     *
     * @param emailID the email ID to set
     */
    public void setEmailID(final String emailID) {
        this.emailID = emailID;
    }

    /**
     * Returns the email ID provider name.
     *
     * @return the email ID provider name
     *
     * @csv.field-mapping field-name="emailProvider" position="4"
     */
    public String getEmailProvider() {
        return emailProvider;
    }

    /**
     * Sets the email provider name for this email ID.
     *
     * @param emailProvider the email provider name to set
     */
    public void setEmailProvider(final String emailProvider) {
        this.emailProvider = emailProvider;
    }
}
