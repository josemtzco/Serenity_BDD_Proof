package org.sereno.tests.junit;

import org.openqa.selenium.WebDriver;

import net.serenitybdd.annotations.Managed;

public abstract class BaseJUnitTest {

    @Managed(driver = "chrome", uniqueSession = true)
    WebDriver driver;

}
