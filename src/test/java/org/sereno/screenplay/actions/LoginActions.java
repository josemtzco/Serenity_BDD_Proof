package org.sereno.screenplay.actions;

import org.openqa.selenium.By;

import static net.serenitybdd.core.Serenity.getWebdriverManager;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;

public class LoginActions {

    // Define page elements as Targets
    private static final Target USERNAME_FIELD = Target.the("username field").located(By.id("username"));
    private static final Target PASSWORD_FIELD = Target.the("password field").located(By.id("password"));
    private static final Target LOGIN_BUTTON = Target.the("login button").located(By.cssSelector("button[type='submit']"));

    private String getBaseUrl() {
        // For now, return the configured base URL directly
        // This can be made configurable later
        return "http://127.0.0.1:5500/";
    }

    public void loginAsAdmin(Actor actor) {
        login(actor, "admin", "admin");
    }

    public void login(Actor actor, String username, String password) {
        actor.whoCan(BrowseTheWeb.with(getWebdriverManager().getCurrentDriver()));
        actor.attemptsTo(
            Open.url(getBaseUrl()),
            Enter.theValue(username).into(USERNAME_FIELD),
            Enter.theValue(password).into(PASSWORD_FIELD),
            Click.on(LOGIN_BUTTON)
        );
    }
} 