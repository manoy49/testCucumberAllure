package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.LoginTest;

public class LoginDefs {
    LoginTest loginTest = new LoginTest();

    @Before
    public void setup(Scenario scenario) {
        loginTest.scenario = scenario;
    }

    @After
    public void tearDown() {
        loginTest.after();
    }

    @Given("^User is on login page$")
    public void user_is_on_login_page() {
        try {
            loginTest.userIsAtLoginPage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^User try logging in with username and password$")
    public void user_try_logging_in_with_username_and_password() {
        loginTest.userLoggingWithCreds();
    }

    @Then("^User is redirected back to login page$")
    public void user_is_redirected_back_to_login_page() {
       loginTest.userGetsRedirected();
    }

}
