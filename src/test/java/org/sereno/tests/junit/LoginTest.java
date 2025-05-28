package org.sereno.tests.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sereno.screenplay.actions.LoginTask;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

@ExtendWith(SerenityJUnit5Extension.class)
public class LoginTest extends BaseJUnitTest {
    
    private Actor user;

    @BeforeEach
    void setUp() {
        user = Actor.named("Test User");
        user.can(BrowseTheWeb.with(driver));
    }
    
    @Test
    void shouldLoginAsAdminUsingTask() {
        user.attemptsTo(LoginTask.asAdmin());
    }

    @Test
    void shouldLoginWithCustomCredentialsUsingTask() {
        user.attemptsTo(LoginTask.withCredentials("testuser", "password"));
    }
} 