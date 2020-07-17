package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
    private WebDriver driver = null;

    private final String basePath = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\";

    private final static String FIREFOX_DRIVER = "geckodriver-v0.26.0-win64\\geckodriver.exe";
    private final static String CHROME_DRIVER = "chromedriver_win32\\chromedriver.exe";
    private final static String EDGE_DRIVER = "edgedriver_win64\\msedgedriver.exe";

    public WebDriver handleDriver(String browser) {
        browser = browser.toUpperCase();
        switch (browser) {
            case "FIREFOX" :
                System.setProperty("webdriver.gecko.driver", basePath + FIREFOX_DRIVER);
                driver = new FirefoxDriver();
                break;
            case "EDGE" :
                System.setProperty("webdriver.edge.driver", basePath + EDGE_DRIVER);
                driver = new EdgeDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", basePath + CHROME_DRIVER);
                driver = new ChromeDriver();
        }
        return driver;
    }

    public void quit(WebDriver driver) {
        driver.quit();
    }

}
