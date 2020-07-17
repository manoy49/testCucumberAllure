package tests;

import io.cucumber.java.*;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import utils.ReadAndWrite;

public class LoginTest extends BaseTest {
    private WebDriver driver = null;
    private Scenario scenario;
    final String testDataLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\testData.properties";

    public void setup(Scenario scenario) {
        this.scenario = scenario;
        String browser = ReadAndWrite.getProperty("browser", testDataLocation);
        driver = handleDriver(browser);
    }

    public void after() {
        quit(driver);
    }

    @Step("User is at login page")
    public void userIsAtLoginPage() throws InterruptedException {
        String URL = ReadAndWrite.getProperty("url", testDataLocation);
        driver.get(URL);
        Thread.sleep(5);
    }

    @Step("User is logging in with credentials")
    public void userLoggingWithCreds() {
        String username = ReadAndWrite.getProperty("username", testDataLocation);
        String password = ReadAndWrite.getProperty("password", testDataLocation);

        WebElement element = driver.findElement(By.id("user_email"));
        element.sendKeys(username);

        element = driver.findElement(By.id("user_password"));
        element.sendKeys(password);

        element = driver.findElement(By.xpath("//input[@type =\"submit\"]"));
        element.click();
    }

    @Step("Post login page")
    public void userGetsRedirected() {
       if(driver.getCurrentUrl().trim().equals(ReadAndWrite.getProperty("url", testDataLocation)))
           takeScreenshot(this.scenario.getName());
    }

    @Attachment(value = "{name}", type = "image/png")
    private byte[] takeScreenshot(String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
