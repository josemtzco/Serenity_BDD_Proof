Feature: Dungeon Finder Queue Tests

  Scenario: Actor can queue for a dungeon.
    Given Actor logged in with admin account
    When Actor clicks on the first dungeon available
    And Actor Click on Queue Button
    And Actor Fill their name
    And Actor Check experience level "Experienced"
    Then Actor should see the provided name in the queued players.


