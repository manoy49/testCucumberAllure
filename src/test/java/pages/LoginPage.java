package pages;

import entity.Employee;
import entity.TestConfig;
import io.cucumber.java.*;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import utils.ReadAndWrite;

public class LoginPage extends BasePage {
    private WebDriver driver = null;
    public Scenario scenario;
    private TestConfig testConfig;

    final String elementFinderLocation = BasePage.ELEMENT_FILE_LOCATION;

    public void setup(TestConfig testConfig) {
        this.testConfig = testConfig;
        driver = handleDriver(testConfig.getBrowser());
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getLocation() {
        return this.elementFinderLocation;
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
        WebElement element = driver.findElement(By.id("user_email"));
        element.sendKeys(employee.getEmail());

        element = driver.findElement(By.id("user_password"));
        element.sendKeys(employee.getPassword());

        element = driver.findElement(By.xpath("//input[@type =\"submit\"]"));
        element.click();
    }

    @Step("Post login page")
    public void userGetsRedirected() {
       if(driver.getCurrentUrl().trim().equals(testConfig.getUrl()))
           takeScreenshot(this.scenario.getName());
    }

    @Step("User is logged in")
    public void userGetsLoggedIn(Employee employee) {
        String firstName = driver.findElement(By.xpath("//h1[contains(@class, Name)]")).getText().trim().split(" ")[0];
        boolean isLoggedIn = employee.getEmail().toLowerCase().contains(firstName.toLowerCase());
        Assert.assertTrue(isLoggedIn);
    }

    @Attachment(value = "{name}", type = "image/png")
    private byte[] takeScreenshot(String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("User logs out")
    public void logout() {
        WebElement element = driver.findElement(By.xpath(ReadAndWrite.getProperty("xpath-logout", elementFinderLocation)));
        element.click();
    }
}
