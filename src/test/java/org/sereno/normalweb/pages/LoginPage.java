package org.sereno.normalweb.pages;

import java.util.ArrayList;

import net.serenitybdd.core.annotations.findby.FindBy;
import static net.serenitybdd.core.pages.ClickStrategy.WAIT_UNTIL_PRESENT;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class LoginPage extends PageObject {

    @FindBy(css = "#username")
    private WebElementFacade userInput;

    @FindBy(css = "#password")
    private WebElementFacade  passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElementFacade  loginBtn;

    public void loginToApp(String user, String password){
        open();
        userInput.shouldBeCurrentlyVisible();
        userInput.type(user);
        passwordInput.shouldBeCurrentlyVisible();
        passwordInput.type(password);
        loginBtn.click(WAIT_UNTIL_PRESENT);
    }

    public void switchToSecondTab() {
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
    }

}
