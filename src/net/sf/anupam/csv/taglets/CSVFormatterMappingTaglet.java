/*
 * CSVFormatterMappingTaglet.java
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
 * Taglet for outputting the CSV Formatter tag's name attribute.  The
 * <code>@csv.formatter-mapping</code> tags will be displayed for classes
 * that have the formatter mapping set in the type documentation. This tag
 * can only be used on the formatter class' type Javadoc.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 */
public class CSVFormatterMappingTaglet
        implements com.sun.tools.doclets.Taglet {

    /**
     * name of the tag.
     */
    private static final String NAME = "csv.formatter-mapping";

    /**
     * The Javadoc header to print.
     */
    private static final String HEADER = "CSV Formatter Mapping:";

    /**
     * Constructor for CSVBeanMappingTaglet.
     */
    public CSVFormatterMappingTaglet() {
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
     * Indicates whether this tag can be used in a type Javadoc.
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
        tagletMap.put(NAME, new LegacyTaglet(new CSVFormatterMappingTaglet()));
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
        String name = "Unknown";

        while (tokenizer.hasMoreTokens()) {

            final String [] nameValuePair = tokenizer.nextToken().split("=");

            if (nameValuePair != null) {

                if ("name".equalsIgnoreCase(nameValuePair[0])) {
                    name = nameValuePair[1];
                }
            }
        }
        return "<DT><B>"
                + HEADER + "</B><DD>" + "<table>" + "<tr>" + "<td>"
                + "CSV Formatter Name: " + name + "</td>" + "</tr>"
                + "</table>" + "</DD>\n";
    }

    /**
     * Returns the string to be included the the output Javadoc for the specified tags.
     *
     * @param tags the tags for which the string representation should be returned
     * @return the string representation to include in the output Javadoc
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
