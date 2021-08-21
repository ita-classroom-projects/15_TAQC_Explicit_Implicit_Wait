package com.softserve.edu;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AjaxTest {
    private final String BASE_URL = "https://devexpress.github.io/devextreme-reactive/react/grid/docs/guides/paging/";
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private final Long EXPLICITLY_WAIT_SECONDS = 10L;
    private final Long ONE_SECOND_DELAY = 1000L;
    private WebDriver driver;
    private WebDriverWait driverWait;

    private void presentationSleep() {
        presentationSleep(1);
    }

    private void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        // System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        // WebDriverManager.firefoxdriver().setup();
    }

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, EXPLICITLY_WAIT_SECONDS);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        presentationSleep(); // For Presentation ONLY
        // driver.close();
        driver.quit();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(BASE_URL);
        presentationSleep(); // For Presentation ONLY
        closePopup();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        presentationSleep(); // For Presentation ONLY
        if (!result.isSuccess()) {
            String testName = result.getName();
            System.out.println("***TC error, name = " + testName + " ERROR");
            // Take Screenshot, Save sourceCode, Save to log, Prepare report, Return to previous state, logout, etc.
            // driver.manage().deleteAllCookies(); // clear cache; delete cookie; delete session;
        }
        presentationSleep(); // For Presentation ONLY
    }


    private void closePopup() {
        //
        // TODO
        //
        //
    }

    @DataProvider
    public Object[][] dataAjaxIframePage() {
        return new Object[][] {
            { "Nevada", "2", "2013/12/07" },
        };
    }
    
    @Test(dataProvider = "dataAjaxIframePage")
    public void checkAjaxIframePage(String storeState, String page, String expectedOrderDate) {
        // Move to Element by JavaScript Injection
        WebElement position = driver.findElement(By.id("use-paging-with-other-data-processing-plugins"));
        //
        Actions action = new Actions(driver);       
        action.moveToElement(position).perform();
        //
//      JavascriptExecutor js = (JavascriptExecutor) driver;
//      js.executeScript("arguments[0].scrollIntoView(true);", position);
        //
        // TODO
        //
        //
    }
    
}
