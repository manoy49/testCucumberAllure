package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BasePage {
    public static WebDriver driver = null;
    private final String basePath = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\";
    public final static String ELEMENT_FILE_LOCATION = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "elementFinder.properties";

    private final static String FIREFOX_DRIVER = "geckodriver-v0.26.0-win64\\geckodriver.exe";
    private final static String CHROME_DRIVER = "chromedriver_win32\\chromedriver.exe";
    private final static String EDGE_DRIVER = "edgedriver_win64\\msedgedriver.exe";

    public WebDriver handleDriver(String browser) {
        browser = browser.toUpperCase();
        boolean newDriver = driver != null && !driver.toString().toLowerCase().contains(browser.toLowerCase());
        if(newDriver)
            driver.quit();
        switch (browser) {
            case "FIREFOX" :
                System.setProperty("webdriver.gecko.driver", basePath + FIREFOX_DRIVER);
                driver =  (driver == null || newDriver) ? new FirefoxDriver(): driver;
                break;
            case "EDGE" :
                System.setProperty("webdriver.edge.driver", basePath + EDGE_DRIVER);
                driver = (driver == null || newDriver) ? new EdgeDriver(): driver;
                break;
            default:
                System.setProperty("webdriver.chrome.driver", basePath + CHROME_DRIVER);
                driver = (driver == null || newDriver) ? new ChromeDriver(): driver;
        }
        return driver;
    }
}
