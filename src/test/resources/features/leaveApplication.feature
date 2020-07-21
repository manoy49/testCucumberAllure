Feature: Leave Application feature
  Scenario: Application for leave
    Given User is logged in
    And User goes to leave application page
    When User clicks on new button
    And User fill the application form
    And User submits the form
    Then User verify the details
    And Submit the leave application
    And User logouts from the system
