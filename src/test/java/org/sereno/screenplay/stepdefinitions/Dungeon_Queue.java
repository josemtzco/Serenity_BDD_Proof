package org.sereno.screenplay.stepdefinitions;

import org.sereno.screenplay.actions.LoginActions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

public class Dungeon_Queue {

    private final Actor actor = Actor.named("Player");
    private final LoginActions loginActions = new LoginActions();

    @Given("Actor logged in with admin account")
    public void actorLoggedInWithAdminAccount() {
        actor.assignName("Jose");
        loginActions.loginAsAdmin(actor);
    }

    @When("Actor clicks on the first dungeon available")
    public void actorClicksOnTheFirstDungeonAvailable() {
        // Implementation for clicking first dungeon
    }

    @And("Actor Click on Queue Button")
    public void actorClickOnQueueButton() {
        // Implementation for queue button
    }

    @And("Actor Fill their name")
    public void actorFillTheirName() {
        // Implementation for filling name
    }

    @And("Actor Check experience level {string}")
    public void actorCheckExperienceLevel(String experience) {
        // Implementation for experience level
    }

    @Then("Actor should see the provided name in the queued players.")
    public void actorShouldSeeTheProvidedNameInTheQueuedPlayers() {
        // Implementation for verification
    }
}
