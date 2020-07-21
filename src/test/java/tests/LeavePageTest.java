package tests;

import io.cucumber.java.Scenario;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.ReadAndWrite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LeavePageTest extends BaseTest {
    private WebDriver driver = null;
    private Scenario scenario ;
    final String testDataLocation = System.getProperty("user.dir") +
            "\\src\\test\\resources\\" +
            this.getClass().getSimpleName() +
            ".properties";

    public void setup(Scenario scenario) {
        this.scenario = scenario;
        this.driver = handleDriver(ReadAndWrite.getProperty("browser", testDataLocation));
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public String getLocation() {
        return this.testDataLocation;
    }

    public void tearDown() {
        quit(driver);
    }

    @Step("User is at leave page")
    public void userIsAtLeavePage() {

        WebElement element = driver.findElement(By.xpath(
                ReadAndWrite.getProperty("xpath-leave", testDataLocation)
                ));
        element.click();
    }

    @Step("User clicked on new button")
    public void userClicksOnNewLeaveButton() {

        driver.findElement(
                By.xpath(ReadAndWrite.getProperty("xpath-new", testDataLocation))
        ).click();
    }

    @Step("User is filling up application form")
    public void UserFillUpApplicationForm() {
        int days = 0;
        WebElement element = driver.findElement(
                        By.id(ReadAndWrite.getProperty("id-leave-type", testDataLocation))
                );
        Select leaveTypeSelect = new Select(element);
        leaveTypeSelect.selectByValue(ReadAndWrite.getProperty("leaveType", testDataLocation));

        element = driver.findElement(
                By.id(ReadAndWrite.getProperty("id-leave-description", testDataLocation)));
        element.sendKeys(ReadAndWrite.getProperty("description", testDataLocation));

        String idFromDate = ReadAndWrite.getProperty("id-from-date", testDataLocation);
        String idToDate = ReadAndWrite.getProperty("id-to-date", testDataLocation);
        String from = ReadAndWrite.getProperty("from", testDataLocation);
        String to = ReadAndWrite.getProperty("to", testDataLocation);

        datePicker("id", idFromDate, idFromDate, from);
        datePicker("id", idToDate, idToDate, to);

        try {
           days = getDays(from, to);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        element = driver.findElement(By.id(ReadAndWrite.getProperty("id-num-of-days", testDataLocation)));
        Select daysSelection = new Select(element);
        if(days > 15)
            daysSelection.selectByValue("15");
        else if(days == 0)
            daysSelection.selectByIndex(days);
        else
            daysSelection.selectByValue(String.valueOf(days));
    }

    @Step("User clicks create button")
    public void clickSubmit() {
        WebElement element = driver.findElement(By.xpath(ReadAndWrite.getProperty("xpath-create", testDataLocation)));
        element.click();
    }

    @Step("verifying application detail")
    public void verifyDetail() {
        String leaveType = ReadAndWrite.getProperty("leaveType", testDataLocation);
        WebElement element = driver.findElement(
                By.xpath(ReadAndWrite.getProperty("xpath-leave-type", testDataLocation)));
        Assert.assertEquals(leaveType.toLowerCase(), element.getText().toLowerCase());
    }

    @Step ("submit application")
    public void submitApplication() {
        WebElement element = driver.findElement(By.xpath(
                ReadAndWrite.getProperty("xpath-leave", testDataLocation)
        ));
        element.click();
        String type = ReadAndWrite.getProperty("leaveType", testDataLocation);
        String submitXpath = ReadAndWrite.getProperty("xpath-leave-list-type", testDataLocation);
        submitXpath = submitXpath + type + "\"]";
        submitXpath = submitXpath + ReadAndWrite.getProperty("xpath-leave-list-submit", testDataLocation);

        element = driver.findElement(By.xpath(submitXpath));
        element.click();

    }



    private void datePicker(String identifier, String identifierValue, String selectorId,  String date) {

        ((JavascriptExecutor) driver).
                executeScript("document.getElementById('"+selectorId+"')." +
                        "removeAttribute('readonly');");

        WebElement element;

        switch (identifier) {
            case "id" : {
                element = driver.findElement(By.id(identifierValue));
                break;
            }
            case "xpath" : {
                element = driver.findElement(By.xpath(identifierValue));
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + identifier);
        }

        element.clear();
        element.sendKeys(date);
        element.click();
    }

    private int getDays(String from, String to) throws ParseException {
        Date start = new SimpleDateFormat("dd/MM/yyyy").parse(from);
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse(to);
        long diff = end.getTime() - start.getTime();
        int days = (int) (diff / (1000*60*60*24));
        return days;
    }
}
