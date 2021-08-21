package com.softserve.edu;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

public class SearchExplicitTest {
    private final String BASE_URL = "http://taqc-opencart.epizy.com/";
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
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
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
        //driver.findElement(By.cssSelector("#logo .img-responsive")).click();
        //driver.findElement(By.cssSelector("#logo > a")).click();
        driver.findElement(By.xpath("//img[contains(@src, '/logo.png')]/..")).click();
        presentationSleep(); // For Presentation ONLY
    }



    @DataProvider
    public Object[][] dataCheckAddToCart() {
        return new Object[][] {
            { "Xiaomi Mi 8", "3", "13" },
            { "Canon EOS 5D", "2", "16" },
            { "MacBook Pro", "1", "" },
        };
    }
    
    @Test(dataProvider = "dataCheckAddToCart")
    public void checkAddToCart(String searchName, String quantity, String selectValue) {
        System.out.println("searchName = " + searchName
                + "quantity = " + quantity
                + "selectValue = " + selectValue);
        //
        //
        // Type to Search Field
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search"))).click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search"))).clear();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search"))).sendKeys(searchName);
        presentationSleep(); // For Presentation ONLY
        //
        // Click Search Button
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#search button"))).click();
        presentationSleep(); // For Presentation ONLY
        //
        // Click Add to Cart Button
        //driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.product-layout button[onclick*='cart']"))).click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4/a[text()='" + searchName
                + "']/../../following-sibling::div/button[contains(@onclick, 'cart.')]"))).click();
        //
        //
        // TODO use explicit wait
        //
        presentationSleep(2); // Change Code
        //
        //
        //
        //
        //
        WebElement searchBreadcrumb = driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.breadcrumb")));
        System.out.println("searchBreadcrumb = " + searchBreadcrumb.getText());
        //
        // Check Options
        String url = driver.getCurrentUrl();
        System.out.println("url = " + url);
        if (url.contains("product_id")) {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-quantity"))).clear();
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-quantity"))).sendKeys(quantity);
            Select options = new Select(driverWait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//select[contains(@id,'input-option')]"))));
            options.selectByValue(selectValue);
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-cart"))).click();
        }
        //presentationSleep(); // For Presentation ONLY
        //
        // Click Shopping Cart Button
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='Shopping Cart']"))).click();
        presentationSleep(); // For Presentation ONLY
        //
        // Check
        WebElement actualQuantity = driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//a[contains(text(),'"
                + searchName + "')]/../following-sibling::td//input")));
        Assert.assertEquals(actualQuantity.getAttribute("value"), quantity);
        presentationSleep(); // For Presentation ONLY
    }

    
    @DataProvider
    public Object[][] dataAddAppleCinema30ToCart() {
        return new Object[][] {
            { "Small", "Checkbox 3", "Text Data", "Blue",
               "Text Text Text Text Text", "4b441b3db2464b7a54857a93a8d6e214b5a21a6b",
               "2021-08-22", "22:25", "2021-08-22 22:25", "4"},
        };
    }
    
    @Test(dataProvider = "dataAddAppleCinema30ToCart")
    public void checkAddAppleCinema30ToCart(String radioSize, String checkboxCurrency,
            String textData, String colorSelect, String textArea, String fileUpload,
            String data, String time, String dateTime, String quantity) {
        String searchName = "Apple Cinema 30";
        //
        // TODO
        //
    }
    
    @DataProvider
    public Object[][] datacheckAddToWishList() {
        return new Object[][] {
            { "xdknxusqvjeovowpfk@awdrt.com", "awdrt123", "Xiaomi", "Xiaomi Mi 8" },
            { "xdknxusqvjeovowpfk@awdrt.com", "awdrt123", "Canon", "Canon EOS 5D" },
        };
    }
    
    @Test(dataProvider = "datacheckAddToWishList")
    public void checkAddToWishList(String emailAddress, String password,
            String searchName, String expectedName) {
        System.out.println("emailAddress = " + emailAddress 
                + " password = " + password
                + " searchName = " + searchName
                + " expectedName = " + expectedName);
        //
        // TODO
        //
    }
    
}
