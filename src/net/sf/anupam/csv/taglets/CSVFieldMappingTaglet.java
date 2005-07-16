/*
 * CSVFieldMappingTaglet.java
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

import java.util.Map;
import java.util.StringTokenizer;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.internal.toolkit.taglets.LegacyTaglet;
import com.sun.tools.doclets.internal.toolkit.taglets.Taglet;

/**
 * Taglet for outputting the field mapping tag information.  The 
 * <code>@csv.field-mapping</code> tags will be displayed for classes
 * that have CSV field mapping set in the method Javadoc documentation.
 * 
 * @author Anupam Sengupta
 * @version $Revision$
 * @since 1.5
 */
public class CSVFieldMappingTaglet
        implements com.sun.tools.doclets.Taglet {

    /** name of the tag. */
    private static final String NAME   = "csv.field-mapping";

    /** The Javadoc header to print. */
    private static final String HEADER = "CSV Field Mapping:";

    /**
     * Constructor for CSVBeanMappingTaglet.
     */
    public CSVFieldMappingTaglet() {
        super();
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#getName()
     */
    public String getName() {
        return NAME;
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inConstructor()
     */
    public boolean inConstructor() {
        return false;
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inField()
     */
    public boolean inField() {
        return false;
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inMethod()
     */
    public boolean inMethod() {
        return true;
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inOverview()
     */
    public boolean inOverview() {
        return false;
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inPackage()
     */
    public boolean inPackage() {
        return false;
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#inType()
     */
    public boolean inType() {
        return false;
    }

    /**
     * @see com.sun.tools.doclets.internal.toolkit.taglets.Taglet#isInlineTag()
     */
    public boolean isInlineTag() {
        return false;
    }

    /**
     * Register a new instance of this taglet to the Javadoc taglet set.
     * 
     * @param tagletMap
     *            the Javadoc taglet set.
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        if (tagletMap.containsKey(NAME)) {
            tagletMap.remove(NAME);
        }
        tagletMap.put(NAME, new LegacyTaglet(new CSVFieldMappingTaglet()));
    }

    /**
     * Given the <code>Tag</code> representation of this custom tag, return
     * its string representation.
     * 
     * @param tag
     *            the <code>Tag</code> representation of this custom tag.
     * @return String representation of the tag
     */
    public String toString(final Tag tag) {

        final StringTokenizer tokenizer = new StringTokenizer(tag.text());
        String position = "Unknown";
        String reformatter = "None";
        while (tokenizer.hasMoreTokens()) {

            final String [] nameValuePair = tokenizer.nextToken().split("=");

            if (nameValuePair != null) {

                if (nameValuePair[0].equalsIgnoreCase("position")) {
                    position = nameValuePair[1];
                } else if (nameValuePair[0].equalsIgnoreCase("reformat")) {
                    reformatter = nameValuePair[1];
                }
            }
        }
        return "<DT><B>"
                + HEADER + "</B><DD>" + "<table>" + "<tr>" + "<td>"
                + "CSV Field Position: " + position + "<BR>Reformatter: "
                + reformatter + "</td>" + "</tr>" + "</table>" + "</DD>\n";
    }

    /**
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
