Feature: Login feature
  Background:
    Given Login Test Initialization
      |browser|url|
      |firefox|http://kaala.herokuapp.com/users/sign_in|
  Scenario: Login Registered Employee
    Given Employee is on login page
    When Employee tries logging in
      |email|password|
      |manishak@testvagrant.com|147258369|
    Then Employee is logged in
    And Employee logs out

  Scenario: Login UnAuthorized Employee
    Given Employee is on login page
    When Employee tries logging in
      |email|password|
      |test@email.com|test123|
    Then Employee is redirected back to login page