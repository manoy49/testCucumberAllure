package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tests.LeavePageTest;
import tests.LoginTest;

public class LeaveApplicationDefs {

    LeavePageTest leavePageTest = new LeavePageTest();
    LoginTest loginTest = new LoginTest();

    @Before
    public void setup(Scenario scenario) {
        leavePageTest.setup(scenario);
    }

    @After
    public void tearDown() {
        leavePageTest.tearDown();
    }

    @Given("User is logged in")
    public void user_is_logged_in() {
        try {
            loginTest.setDriver(leavePageTest.getDriver());
            loginTest.userIsAtLoginPage(leavePageTest.getLocation());
            loginTest.userLoggingWithCreds(leavePageTest.getLocation());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("User goes to leave application page")
    public void user_goes_to_leave_application_page() {
        leavePageTest.userIsAtLeavePage();
    }

    @When("User clicks on new button")
    public void user_clicks_on_new_button() {
        leavePageTest.userClicksOnNewLeaveButton();
    }

    @When("User fill the application form")
    public void user_fill_the_application_form() {
        leavePageTest.UserFillUpApplicationForm();
    }

    @When("User submits the form")
    public void user_submits_the_form() {
       leavePageTest.clickSubmit();
    }

    @Then("User verify the details")
    public void user_verify_the_details() {
        leavePageTest.verifyDetail();
    }

    @Then("Submit the leave application")
    public void submit_the_leave_application() {
        leavePageTest.submitApplication();
    }
}
