package org.sereno.screenplay.actions;

import org.openqa.selenium.By;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Advanced LoginTask that demonstrates the full power of Tasks.instrumented()
 * This shows when and why you would use instrumented() over simple instantiation
 */
public class AdvancedLoginTask implements Task {
    
    private final String username;
    private final String password;
    private final boolean rememberMe;
    
    // Page elements
    private static final Target USERNAME_FIELD = Target.the("username field").located(By.id("username"));
    private static final Target PASSWORD_FIELD = Target.the("password field").located(By.id("password"));
    private static final Target REMEMBER_ME_CHECKBOX = Target.the("remember me checkbox").located(By.id("remember"));
    private static final Target LOGIN_BUTTON = Target.the("login button").located(By.cssSelector("button[type='submit']"));
    
    public AdvancedLoginTask(String username, String password, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
    }
    
    // Factory methods using instrumented() - enables dependency injection
    public static AdvancedLoginTask asAdmin() {
        return instrumented(AdvancedLoginTask.class, "admin", "admin", false);
    }
    
    public static AdvancedLoginTask asUser(String username, String password) {
        return instrumented(AdvancedLoginTask.class, username, password, false);
    }
    
    public static AdvancedLoginTask withRememberMe(String username, String password) {
        return instrumented(AdvancedLoginTask.class, username, password, true);
    }
    
    @Override
    @Step("{0} performs advanced login with username: {1}")
    public <T extends Actor> void performAs(T actor) {
        // Record test data for reporting
        Serenity.recordReportData().withTitle("Login Attempt")
            .andContents("Username: " + username + ", Remember Me: " + rememberMe);
        
        actor.attemptsTo(
            Open.url("http://127.0.0.1:5500/"),
            Enter.theValue(username).into(USERNAME_FIELD),
            Enter.theValue(password).into(PASSWORD_FIELD)
        );
        
        // Conditional logic based on parameters
        if (rememberMe) {
            actor.attemptsTo(Click.on(REMEMBER_ME_CHECKBOX));
        }
        
        actor.attemptsTo(Click.on(LOGIN_BUTTON));
        
        // Add screenshot for reporting
        Serenity.takeScreenshot();
    }
    
    // Getter methods for step reporting
    public String getUsername() {
        return username;
    }
    
    public boolean isRememberMe() {
        return rememberMe;
    }
} 