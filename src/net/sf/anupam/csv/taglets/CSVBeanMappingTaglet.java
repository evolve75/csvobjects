/*
 * CSVBeanMappingTaglet.java
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Version: $Revision$
 */
package net.sf.anupam.csv.taglets;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.internal.toolkit.taglets.LegacyTaglet;
import com.sun.tools.doclets.internal.toolkit.taglets.Taglet;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * Taglet for outputting the CSV bean mapping information. The
 * <code>@csv.bean-mapping</code> tags will be displayed for classes
 * that have CSV bean mapping set in the type Javadoc comment. The tag can
 * only be specified in the type Javadoc.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 */
public class CSVBeanMappingTaglet
        implements com.sun.tools.doclets.Taglet {

    /**
     * name of the tag.
     */
    private static final String NAME = "csv.bean-mapping";

    /**
     * The Javadoc header to print.
     */
    private static final String HEADER = "CSV Mapping:";

    /**
     * Constructor for CSVBeanMappingTaglet.
     */
    public CSVBeanMappingTaglet() {
        super();
    }

    /**
     * Returns the name of this Javadoc tag.
     *
     * @return the name of this tag
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#getName()
     */
    public String getName() {
        return NAME;
    }

    /**
     * Indicates whether this tag can be used in a constructor Javadoc.
     *
     * @return <code>false</code>
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inConstructor()
     */
    public boolean inConstructor() {
        return false;
    }

    /**
     * Indicates whether this tag can be used in a field Javadoc.
     *
     * @return <code>false</code>
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inField()
     */
    public boolean inField() {
        return false;
    }

    /**
     * Indicates whether this tag can be used in a method Javadoc.
     *
     * @return <code>false</code>
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inMethod()
     */
    public boolean inMethod() {
        return false;
    }

    /**
     * Indicates whether this tag can be used in the overview Javadoc.
     *
     * @return <code>false</code>
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inOverview()
     */
    public boolean inOverview() {
        return false;
    }

    /**
     * Indicates whether this tag can be used in the package Javadoc.
     *
     * @return <code>false</code>
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inPackage()
     */
    public boolean inPackage() {
        return false;
    }

    /**
     * Indicates whether this tag can be used in a Type Javadoc.
     *
     * @return <code>true</code>
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inType()
     */
    public boolean inType() {
        return true;
    }

    /**
     * Indicates whether this is an inline tag.
     *
     * @return <code>false</code>
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#isInlineTag()
     */
    public boolean isInlineTag() {
        return false;
    }

    /**
     * Register a new instance of this taglet to the Javadoc taglet set.
     *
     * @param tagletMap the Javadoc taglet set.
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        if (tagletMap.containsKey(NAME)) {
            tagletMap.remove(NAME);
        }
        tagletMap.put(NAME, new LegacyTaglet(new CSVBeanMappingTaglet()));
    }

    /**
     * Given the <code>Tag</code> representation of this custom tag, return
     * its string representation.
     *
     * @param tag the <code>Tag</code> representation of this custom tag.
     * @return String representation of the tag
     */
    public String toString(final Tag tag) {

        final StringTokenizer tokenizer = new StringTokenizer(tag.text());
        String beanName = "Unknown";
        String hasCVSHeader = "False";
        while (tokenizer.hasMoreTokens()) {

            final String [] nameValuePair = tokenizer.nextToken().split("=");

            if (nameValuePair != null) {

                if (nameValuePair[0].equalsIgnoreCase("bean-name")) {
                    beanName = nameValuePair[1];
                } else if (nameValuePair[0].equalsIgnoreCase("csv-header")) {
                    hasCVSHeader = nameValuePair[1];
                }
            }
        }
        return "<DT><B>"
                + HEADER + "</B><DD>" + "<table>" + "<tr>" + "<td>"
                + "Mapped Bean Name: " + beanName + "<BR>Has CSV Header: "
                + hasCVSHeader + "</td>" + "</tr>" + "</table>" + "</DD>\n";
    }

    /**
     * Returns the string to be displayed on the Javadoc for the specified tags.
     *
     * @param tags the list of tags for which the string representation should be returned
     * @return the string to display on the Javadoc
     * @see com.sun.tools.doclets.Taglet#toString(com.sun.javadoc.Tag[])
     */
    public String toString(final Tag [] tags) {
        if (tags.length == 0) {
            return null;
        }
        final StringBuffer strBuffer = new StringBuffer();
        for (Tag tag : tags) {
            strBuffer.append(this.toString(tag));
        }
        return strBuffer.toString();
    }
}
