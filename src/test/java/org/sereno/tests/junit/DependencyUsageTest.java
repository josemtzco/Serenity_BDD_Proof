// File: DependencyUsageTest.java
package org.sereno.tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import net.serenitybdd.core.Serenity;    // serenity-junit5
import net.serenitybdd.junit5.SerenityJUnit5Extension;                    // serenity-core
import static net.serenitybdd.rest.SerenityRest.lastResponse;                  // serenity-screenplay
import net.serenitybdd.screenplay.Actor;  // serenity-rest-assured
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;


@ExtendWith(SerenityJUnit5Extension.class)                 // Serenity–JUnit5 integration
public class DependencyUsageTest extends BaseJUnitTest {

    Actor anna = Actor.named("Anna");

    @BeforeEach
    void setUp() {// uses serenity-core
        Serenity.recordReportData().withTitle("Setup").andContents("Starting a new test");
        anna = Actor.named("Anna");
        anna.can(BrowseTheWeb.with(driver)); // Now driver is ready
        anna.can(CallAnApi.at("https://jsonplaceholder.typicode.com")); // base URL only
    }

    @Test                                                   // junit-jupiter-engine & junit-jupiter
    @DisplayName("Simple JUnit assertion")
    void junitTest() {
        assertEquals(2, 1 + 1, "1+1 should be 2");
    }

    @ParameterizedTest                                      // junit-jupiter
    @ValueSource(strings = {"foo", "bar"})
    void parameterized(String str) {
        assertTrue(str.length() > 0, "String is not empty");
    }

    @Test
    @Tag("rest")
    void apiTest() {
        // serenity-rest-assured + screenplay
        anna.entersTheScene();
        anna.attemptsTo(Get.resource("/posts/1"));
        System.out.println("RESPONSE: ");
        lastResponse().getBody().prettyPrint();
    }

    @Test
    @Tag("ui")
    void uiTest() throws InterruptedException {
        // serenity-screenplay-webdriver
        anna.attemptsTo(Open.url("http://example.com"));
        Thread.sleep(5000);
        driver.navigate().refresh();
        Thread.sleep(5000);

    }
}
