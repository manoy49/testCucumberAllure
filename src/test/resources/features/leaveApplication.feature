Feature: Leave Application feature
  Background:
    Given Leave Test Initialization
    |browser|url|
    |chrome|http://kaala.herokuapp.com/users/sign_in|
  Scenario: Application for leave
    Given Employee is logged in the app
    |email|password|
    |manishak@testvagrant.com|147258369|
    And Employee is on leave page
    When Employee tries creating new leave request
    |leaveType|description|toDate|fromDate|
    |Holiday|Making plans for rock climbing|21/07/2018|11/07/2018|
    And Employee submits leave request
    Then Employee verify leave details
    And Employee submits the final request
    And Employee logs out from app
