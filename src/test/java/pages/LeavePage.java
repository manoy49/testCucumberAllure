package pages;

import entity.LeaveApplication;
import entity.TestConfig;
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

public class LeavePage extends BasePage {
    private WebDriver driver = null;
    private Scenario scenario ;
    private TestConfig testConfig;

    final String elementFinderLocation = BasePage.ELEMENT_FILE_LOCATION;


    public void setup( TestConfig testConfig) {
        this.testConfig = testConfig;
        this.driver = handleDriver(testConfig.getBrowser());
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public String getLocation() {
        return elementFinderLocation;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Step("User is at leave page")
    public void userIsAtLeavePage() {
        WebElement element = driver.findElement(By.xpath(
                ReadAndWrite.getProperty("xpath-leave", elementFinderLocation)
                ));
        element.click();
    }

    @Step("User clicked on new button")
    public void userClicksOnNewLeaveButton() {

        driver.findElement(
                By.xpath(ReadAndWrite.getProperty("xpath-new", elementFinderLocation))
        ).click();
    }

    @Step("User is filling up application form")
    public void UserFillUpApplicationForm(LeaveApplication leaveApplication) {
        int days = 0;
        WebElement element = driver.findElement(
                        By.id(ReadAndWrite.getProperty("id-leave-type", elementFinderLocation))
                );
        Select leaveTypeSelect = new Select(element);
        leaveTypeSelect.selectByValue(leaveApplication.getLeaveType());

        element = driver.findElement(
                By.id(ReadAndWrite.getProperty("id-leave-description", elementFinderLocation)));
        element.sendKeys(leaveApplication.getDescription());

        String idFromDate = ReadAndWrite.getProperty("id-from-date", elementFinderLocation);
        String idToDate = ReadAndWrite.getProperty("id-to-date", elementFinderLocation);
        String from = leaveApplication.getFromDate();
        String to = leaveApplication.getToDate();

        datePicker("id", idFromDate, idFromDate, from);
        datePicker("id", idToDate, idToDate, to);

        try {
           days = getDays(from, to);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        element = driver.findElement(By.id(ReadAndWrite.getProperty("id-num-of-days", elementFinderLocation)));
        Select daysSelection = new Select(element);

        if(days > 15) {
            daysSelection.selectByValue("15");
            leaveApplication.setNumOfDays("15");
        }
        else if(days == 0) {
            daysSelection.selectByIndex(days);
            leaveApplication.setNumOfDays(String.valueOf(days));
        }
        else {
            daysSelection.selectByValue(String.valueOf(days));
            leaveApplication.setNumOfDays(String.valueOf(days));
        }
    }

    @Step("User clicks create button")
    public void clickSubmit() {
        WebElement element = driver.findElement(By.xpath(ReadAndWrite.getProperty("xpath-create", elementFinderLocation)));
        element.click();
    }

    @Step("verifying application detail")
    public void verifyDetail(LeaveApplication leaveApplication) {
        String leaveType = leaveApplication.getLeaveType();
        WebElement element = driver.findElement(
                By.xpath(ReadAndWrite.getProperty("xpath-leave-type", elementFinderLocation)));
        Assert.assertEquals(leaveType.toLowerCase(), element.getText().toLowerCase());
    }

    @Step ("submit application")
    public void submitApplication(LeaveApplication leaveApplication) {
        WebElement element = driver.findElement(By.xpath(
                ReadAndWrite.getProperty("xpath-leave", elementFinderLocation)
        ));
        element.click();
        String type = leaveApplication.getLeaveType();
        String submitXpath = ReadAndWrite.getProperty("xpath-leave-list-type", elementFinderLocation);
        submitXpath = submitXpath + type + "\"]";
        submitXpath = submitXpath + ReadAndWrite.getProperty("xpath-leave-list-submit", elementFinderLocation);

        try {
            element = driver.findElement(By.xpath(submitXpath));
        } catch (Exception noSuchElementException) {
            String year = leaveApplication.getFromDate();
            year = year.split("/")[2];
            element = driver.findElement(By.id("year"));
            Select yearSelector = new Select(element);
            yearSelector.selectByValue(year);
            element = driver.findElement(By.xpath("//input[@value=\"Search\"]"));
            element.click();
            element = driver.findElement(By.xpath(submitXpath));

        }
        element.click();

    }

    @Step("User log out")
    public void logout() {
        WebElement element = driver.findElement(By.xpath(ReadAndWrite.getProperty("xpath-logout", elementFinderLocation)));
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
