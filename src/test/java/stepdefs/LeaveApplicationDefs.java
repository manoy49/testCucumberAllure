package stepdefs;

import entity.Employee;
import entity.LeaveApplication;
import entity.TestConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LeavePage;
import pages.LoginPage;

import java.util.List;

public class LeaveApplicationDefs {
    public Employee employee;
    public LeaveApplication leaveApplication;
    public TestConfig testConfig;

    LeavePage leavePage = new LeavePage();
    LoginPage loginPage = new LoginPage();

    @Before
    public void setup(Scenario scenario) {
        leavePage.setScenario(scenario);
    }

    @Given("Leave Test Initialization")
    public void leave_test_initialization(DataTable dataTable) {
        List<String> data = dataTable.cells().get(1);
        testConfig = new TestConfig.TestConfigBuilder(data.get(0), data.get(1)).build();
        leavePage.setup(testConfig);
        loginPage.setTestConfig(testConfig);
    }

    @Given("Employee is logged in the app")
    public void user_is_logged_in(DataTable dataTable) {
        try {
            loginPage.setDriver(leavePage.getDriver());
            loginPage.userIsAtLoginPage();

            List<String> data = dataTable.cells().get(1);
            employee =  new Employee.EmployeeBuilder(data.get(0), data.get(1)).build();

            loginPage.userLoggingWithCreds(employee);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("Employee is on leave page")
    public void user_goes_to_leave_application_page() {
        leavePage.userIsAtLeavePage();
    }

    @When("Employee tries creating new leave request")
    public void user_fill_the_application_form(DataTable dataTable) {
        List<String> data = dataTable.cells().get(1);
        leaveApplication = new LeaveApplication.
                LeaveApplicationBuilder(data.get(0), data.get(1), data.get(2), data.get(3)).build();
        leavePage.userClicksOnNewLeaveButton();
        leavePage.UserFillUpApplicationForm(leaveApplication);
    }

    @When("Employee submits leave request")
    public void user_submits_the_form() {
       leavePage.clickSubmit();
    }

    @Then("Employee verify leave details")
    public void user_verify_the_details() {
        leavePage.verifyDetail(leaveApplication);
    }

    @Then("Employee submits the final request")
    public void submit_the_leave_application() {
        leavePage.submitApplication(leaveApplication);
    }

    @And("Employee logs out from app")
    public void user_logouts_from_the_system() {
        leavePage.logout();
    }
}
