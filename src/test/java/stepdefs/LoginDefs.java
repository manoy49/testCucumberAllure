package stepdefs;

import entity.Employee;
import entity.TestConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BasePage;
import pages.LoginPage;
import utils.ReadAndWrite;

import java.util.List;

public class LoginDefs {
    LoginPage loginPage = new LoginPage();
    public Employee employee;
    public TestConfig testConfig;

    @Before
    public void setup(Scenario scenario) {
        loginPage.scenario = scenario;
    }

    @Given("Login Test Initialization")
    public void login_test_initialization(DataTable dataTable) {
        List<String> data = dataTable.cells().get(1);
        String url = ReadAndWrite.getProperty("url", BasePage.CONFIG_LOCATION);
        testConfig = new TestConfig.TestConfigBuilder(data.get(0), url).build();
        loginPage.setup(testConfig);
    }

    @Given("^Employee is on login page$")
    public void user_is_on_login_page() {
        try {
            loginPage.userIsAtLoginPage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Employee tries logging in$")
    public void user_tries_logging_in(DataTable dataTable) {
        List<String> data = dataTable.cells().get(1);
        employee =  new Employee.EmployeeBuilder(data.get(0), data.get(1)).build();
        loginPage.userLoggingWithCreds(employee);
    }

    @Then("^Employee is logged in$")
    public void user_is_logged_in() {
        loginPage.userGetsLoggedIn(employee);
    }

    @Then("^Employee is redirected back to login page$")
    public void user_is_redirected_back_to_login_page() {
       loginPage.userGetsRedirected();
    }

    @And("Employee logs out")
    public void employee_logs_out() {
        loginPage.logout();
    }
}
