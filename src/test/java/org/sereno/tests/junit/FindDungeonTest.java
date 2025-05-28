package org.sereno.tests.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sereno.normalweb.steps.LoginSteps;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityJUnit5Extension;

@ExtendWith(SerenityJUnit5Extension.class)
public class FindDungeonTest extends BaseJUnitTest{

    @Steps
    LoginSteps loginSteps;

    @Test
    void playerOpenDungeonGroupFinder() throws InterruptedException {
        Serenity.recordReportData().withTitle("Google").andContents("Starting a new test");
        loginSteps.login("admin", "admin");
        Thread.sleep(3*1000);
    }
}
