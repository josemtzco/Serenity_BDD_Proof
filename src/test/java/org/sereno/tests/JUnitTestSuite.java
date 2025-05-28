// File: AllTestsSuite.java
package org.sereno.tests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import net.serenitybdd.junit5.SerenityJUnit5Extension;                // junit-platform-suite

@Suite
@SelectPackages("org.sereno.tests")
@ExtendWith(SerenityJUnit5Extension.class)
public class JUnitTestSuite {
    // nothing to write here—this class just groups your tests

    // junit-platform.properties have higher commands on how tests are running
    // in the serenity plugin. It seems like if we are thinking of configuration is better to that there.
}
