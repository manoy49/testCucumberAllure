Feature: Login feature
  Scenario: Login with incorrect credentials
    Given User is on login page
    When User try logging in with incorrect username and password
    Then User is redirected back to login page