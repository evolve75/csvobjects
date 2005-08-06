/*
 * CSVParserTimedTest.java
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
package net.sf.anupam.csv;

import com.clarkware.junitperf.ConstantTimer;
import com.clarkware.junitperf.LoadTest;
import junit.extensions.RepeatedTest;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * A load test for the CSVParser.
 *
 * @author Anupam Sengupta
 * @version $Revision$
 */
public class CSVParserTimedTest extends TestCase {

    public static Test suite() {

        final long maxElapsedTime = 100L;
        final int users = 100;
        final int iterations = 10;

        final ConstantTimer constantTimer = new ConstantTimer(maxElapsedTime);


        final Test testCase = new CSVParserTest("testGetMappedBeans");
        final Test repeatedTest = new RepeatedTest(testCase, iterations);
        return new LoadTest(repeatedTest, users, constantTimer);


    }

    public static void main(String[] args) {
        junit.textui
                .TestRunner
                .run(suite());
    }
}
