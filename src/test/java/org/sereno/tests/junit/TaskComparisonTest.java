package org.sereno.tests.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sereno.screenplay.actions.AdvancedLoginTask;
import org.sereno.screenplay.actions.LoginTask;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

/**
 * Test class demonstrating the difference between:
 * 1. Tasks.instrumented() - enables dependency injection and Serenity features
 * 2. Direct instantiation - simple object creation
 */
@ExtendWith(SerenityJUnit5Extension.class)
public class TaskComparisonTest extends BaseJUnitTest {
    
    private Actor user;

    @BeforeEach
    void setUp() {
        user = Actor.named("Test User");
        user.can(BrowseTheWeb.with(driver));
    }
    
    @Test
    @DisplayName("Login using Tasks.instrumented() - enables dependency injection")
    void shouldLoginUsingInstrumentedTask() {
        // This uses Tasks.instrumented() which enables:
        // - Dependency injection (@Steps fields get injected)
        // - Enhanced reporting with step parameters
        // - Serenity lifecycle management
        user.attemptsTo(LoginTask.asAdmin());
    }
    
    @Test
    @DisplayName("Login using simple instantiation - basic functionality")
    void shouldLoginUsingSimpleTask() {
        // This uses direct instantiation - simpler but no dependency injection
        user.attemptsTo(LoginTask.asAdminSimple());
    }
    
    @Test
    @DisplayName("Advanced login with dependency injection and validation")
    void shouldLoginWithAdvancedFeatures() {
        // This demonstrates the full power of Tasks.instrumented():
        // - Dependency injection of validation steps
        // - Enhanced reporting with custom data
        // - Automatic screenshot capture
        // - Parameter-based conditional logic
        user.attemptsTo(AdvancedLoginTask.asAdmin());
    }
    
    @Test
    @DisplayName("Login with remember me functionality")
    void shouldLoginWithRememberMe() {
        user.attemptsTo(AdvancedLoginTask.withRememberMe("testuser", "password"));
    }
} 