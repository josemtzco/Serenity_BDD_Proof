package org.sereno.screenplay.actions;

import org.openqa.selenium.By;

import net.serenitybdd.annotations.Step;
import static net.serenitybdd.core.Serenity.getWebdriverManager;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;

public class LoginTask implements Task {
    
    private final String username;
    private final String password;
    
    // Page elements
    private static final Target USERNAME_FIELD = Target.the("username field").located(By.id("username"));
    private static final Target PASSWORD_FIELD = Target.the("password field").located(By.id("password"));
    private static final Target LOGIN_BUTTON = Target.the("login button").located(By.cssSelector("button[type='submit']"));
    
    public LoginTask(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // CORRECT way to use Tasks.instrumented() - pass constructor parameters directly
    public static LoginTask asAdmin() {
        return instrumented(LoginTask.class, "admin", "admin");
    }
    
    public static LoginTask withCredentials(String username, String password) {
        return instrumented(LoginTask.class, username, password);
    }
    
    // Alternative: Simple factory methods without instrumented (for comparison)
    public static LoginTask asAdminSimple() {
        return new LoginTask("admin", "admin");
    }
    
    public static LoginTask withCredentialsSimple(String username, String password) {
        return new LoginTask(username, password);
    }
    
    @Override
    @Step("{0} logs in with username: {1}")
    public <T extends Actor> void performAs(T actor) {
        // Ensure actor has browsing ability
        try {
            actor.abilityTo(BrowseTheWeb.class);
        } catch (Exception e) {
            actor.can(BrowseTheWeb.with(getWebdriverManager().getCurrentDriver()));
        }
        
        actor.attemptsTo(
            Open.url("http://127.0.0.1:5500/"),
            Enter.theValue(username).into(USERNAME_FIELD),
            Enter.theValue(password).into(PASSWORD_FIELD),
            Click.on(LOGIN_BUTTON)
        );
    }
    
    // Getter methods for step reporting (used by @Step annotation)
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return "****"; // Don't expose password in reports
    }
} 