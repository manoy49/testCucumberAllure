package pages;

import entity.Employee;
import entity.TestConfig;
import io.cucumber.java.*;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ReadAndWrite;

public class LoginPage extends BasePage {
    private WebDriver driver = null;
    public Scenario scenario;
    private TestConfig testConfig;

    @FindBy(id = "user_email")
    WebElement email;

    @FindBy(id = "user_password")
    WebElement password;

    @FindBy(xpath = "//input[@type =\"submit\"]")
    WebElement submit;

    @FindBy(xpath = "//h1[contains(@class, Name)]")
    WebElement name;

    @FindBy(xpath = "//span[@class=\"glyphicon glyphicon-log-out\"]")
    WebElement logout;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public LoginPage() {
    }

    final String configLocation = BasePage.CONFIG_LOCATION;

    public void setup(TestConfig testConfig) {
        this.testConfig = testConfig;
        driver = handleDriver(testConfig.getBrowser());
        PageFactory.initElements(this.driver, this);
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getLocation() {
        return this.configLocation;
    }

    public void setTestConfig(TestConfig testConfig) {
        this.testConfig = testConfig;
    }

    @Step("User is at login page")
    public void userIsAtLoginPage() throws InterruptedException {
        String URL = testConfig.getUrl();
        if(null == driver) {
            driver = BasePage.driver;
        }
        driver.get(URL);
        Thread.sleep(5);
    }

    @Step("User is logging in with credentials")
    public void userLoggingWithCreds(Employee employee) {
        email.sendKeys(employee.getEmail());
        password.sendKeys(employee.getPassword());
        submit.click();
    }

    @Step("Post login page")
    public void userGetsRedirected() {
       if(driver.getCurrentUrl().trim().equals(testConfig.getUrl()))
           takeScreenshot(this.scenario.getName());
    }

    @Step("User is logged in")
    public void userGetsLoggedIn(Employee employee) {
        String firstName = name.getText().trim().split(" ")[0];
        boolean isLoggedIn = employee.getEmail().toLowerCase().contains(firstName.toLowerCase());
        Assert.assertTrue(isLoggedIn);
    }

    @Attachment(value = "{name}", type = "image/png")
    private byte[] takeScreenshot(String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("User logs out")
    public void logout() {
        logout.click();
    }
}
