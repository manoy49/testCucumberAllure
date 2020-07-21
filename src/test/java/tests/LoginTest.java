package tests;

import io.cucumber.java.*;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import utils.ReadAndWrite;

public class LoginTest extends BaseTest {
    private WebDriver driver = null;
    private Scenario scenario;
    final String testDataLocation = System.getProperty("user.dir") +
            "\\src\\test\\resources\\" +
            this.getClass().getSimpleName() +
            ".properties";

    public void setup(Scenario scenario) {
        this.scenario = scenario;
        String browser = ReadAndWrite.getProperty("browser", testDataLocation);
        driver = handleDriver(browser);
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getTestDataLocation() {
        return this.testDataLocation;
    }

    public void after() {
        quit(driver);
    }

    @Step("User is at login page")
    public void userIsAtLoginPage(String... dataLocation) throws InterruptedException {
        String URL = ReadAndWrite.getProperty("url", dataLocation.length == 0 ? testDataLocation : dataLocation[0]);
        if(driver == null)
            setup(this.scenario);
        driver.get(URL);
        Thread.sleep(5);
    }

    @Step("User is logging in with credentials")
    public void userLoggingWithCreds(String... dataLocation) {
        String username = ReadAndWrite.getProperty("username", dataLocation.length == 0 ? testDataLocation : dataLocation[0]);
        String password = ReadAndWrite.getProperty("password", dataLocation.length == 0 ? testDataLocation : dataLocation[0]);

        WebElement element = driver.findElement(By.id("user_email"));
        element.sendKeys(username);

        element = driver.findElement(By.id("user_password"));
        element.sendKeys(password);

        element = driver.findElement(By.xpath("//input[@type =\"submit\"]"));
        element.click();
    }

    @Step("Post login page")
    public void userGetsRedirected(String... dataLocation) {
       if(driver.getCurrentUrl().trim().equals(ReadAndWrite.getProperty("url", dataLocation.length == 0 ? testDataLocation : dataLocation[0])))
           takeScreenshot(this.scenario.getName());
    }

    @Attachment(value = "{name}", type = "image/png")
    private byte[] takeScreenshot(String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
