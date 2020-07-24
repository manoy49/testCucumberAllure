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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LeavePage extends BasePage {
    private WebDriver driver = null;
    private Scenario scenario ;
    private TestConfig testConfig;

    final String configLocation = BasePage.CONFIG_LOCATION;
    String leaveListPartialXpath = "//td[text()=";
    String submitRequestXpath = "/parent::tr//input[@value='Submit']";

    @FindBy(xpath = "//a[text()=\"Leaves\"]")
    WebElement leave;

    @FindBy(xpath = "//a[text()=\"New\"]")
    WebElement newLeave;

    @FindBy(id = "away_leave_type")
    WebElement leaveType;

    @FindBy(id = "away_description")
    WebElement leaveDescription;

    @FindBy(id = "away_num_of_days")
    WebElement numOfDays;

    @FindBy(xpath = "//input[@value=\"Create Away\"]")
    WebElement createAway;

    @FindBy(id = "year")
    WebElement year;

    @FindBy(id = "away_to_date")
    WebElement toDate;

    @FindBy(id = "away_from_date")
    WebElement fromDate;

    @FindBy(xpath = "//input[@value=\"Search\"]")
    WebElement search;

    @FindBy(xpath = "//dd[1]")
    WebElement leaveTypePostForm;

    @FindBy(xpath = "//dd[2]")
    WebElement descriptionPostForm;

    @FindBy(xpath = "//dd[3]")
    WebElement fromDatePostForm;

    @FindBy(xpath = "//dd[4]")
    WebElement toDatePostForm;

    @FindBy(xpath = "//dd[5]")
    WebElement numOfDaysPostForm;

    @FindBy(xpath = "//span[@class=\"glyphicon glyphicon-log-out\"]")
    WebElement logout;


    public void setup( TestConfig testConfig) {
        this.testConfig = testConfig;
        this.driver = handleDriver(testConfig.getBrowser());
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public String getLocation() {
        return configLocation;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Step("User is at leave page")
    public void userIsAtLeavePage() {
        leave.click();
    }

    @Step("User clicked on new button")
    public void userClicksOnNewLeaveButton() {
        newLeave.click();
    }

    @Step("User is filling up application form")
    public void UserFillUpApplicationForm(LeaveApplication leaveApplication) {
        int days = 0;

        Select leaveTypeSelect = new Select(leaveType);
        leaveTypeSelect.selectByValue(leaveApplication.getLeaveType());

        leaveDescription.sendKeys(leaveApplication.getDescription());

        String idFromDate = fromDate.getAttribute("id");
        String idToDate = toDate.getAttribute("id");
        String from = leaveApplication.getFromDate();
        String to = leaveApplication.getToDate();

        datePicker(fromDate, idFromDate, from);
        datePicker(toDate, idToDate, to);

        try {
           days = getDays(from, to);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Select daysSelection = new Select(numOfDays);

        if(days > 15) {
            daysSelection.selectByValue("15");
            leaveApplication.setNumOfDays("15.0");
        }
        else if(days == 0) {
            daysSelection.selectByIndex(days);
            leaveApplication.setNumOfDays(String.valueOf(days));
        }
        else {
            daysSelection.selectByValue(String.valueOf(days));
            leaveApplication.setNumOfDays(days + ".0" );
        }
    }

    @Step("User clicks create button")
    public void clickSubmit() {
        createAway.click();
    }

    @Step("verifying application detail")
    public void verifyDetail(LeaveApplication leaveApplication) {

        LeaveApplication expectedLeave = new LeaveApplication.
                LeaveApplicationBuilder(leaveTypePostForm.getText().trim(),
                descriptionPostForm.getText().trim(),
                toDatePostForm.getText().trim(),
                fromDatePostForm.getText().trim()).
                setNumOfDays(numOfDaysPostForm.getText().trim()).build();

        Assert.assertEquals(expectedLeave, leaveApplication);
    }

    @Step ("submit application")
    public void submitApplication(LeaveApplication leaveApplication) {
        WebElement element;
        leave.click();
        String type = leaveApplication.getLeaveType();
        String submitXpath = leaveListPartialXpath  + "\"" + type + "\"" + "]";
        submitXpath = submitXpath + submitRequestXpath;

        try {
            element = driver.findElement(By.xpath(submitXpath));

        } catch (Exception noSuchElementException) {

            Select yearSelector = new Select(year);
            String year = leaveApplication.getFromDate();
            year = year.split("/")[2];
            yearSelector.selectByValue(year);
            search.click();
            element = driver.findElement(By.xpath(submitXpath));

        }
        element.click();

    }

    @Step("User log out")
    public void logout() {
        logout.click();
    }

    private void datePicker(WebElement element, String selectorId,  String date) {

        ((JavascriptExecutor) driver).
                executeScript("document.getElementById('"+selectorId+"')." +
                        "removeAttribute('readonly');");

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
