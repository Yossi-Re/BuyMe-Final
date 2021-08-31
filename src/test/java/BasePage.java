import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class BasePage {


    protected WebDriver driver;

    public BasePage() {
        try {
            this.driver = DriverSingleton.getDriverInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void clearText(By locator) {
        WebElement element = null;

        try {
            element = getWebElement(locator);
            element.clear();
        } catch (Exception e) {
            e.printStackTrace();
            String timestamp = String.valueOf(System.currentTimeMillis());
            MainTest.test.info("details", MediaEntityBuilder.createScreenCaptureFromPath(takeElementScreenshot()).build());
        }
    }

    protected void sendKeysToElement(By locator, String text) {
        WebElement element = null;

        try {
            element = getWebElement(locator);
            element.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
            String timestamp = String.valueOf(System.currentTimeMillis());
            MainTest.test.info("details", MediaEntityBuilder.createScreenCaptureFromPath(takeElementScreenshot()).build());
        }
    }

    private String takeScreenShot(String pic) {
        return System.getProperty("user.dir") + "/" + pic;
    }

    private String takeElementScreenshot() {
        String fileName = String.valueOf(System.currentTimeMillis());
        fileName += ".png";
        try {
            File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShotFile, new File(fileName)); // save screenshot to disk

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    protected void sendKeysToElement(By locator, Keys key) {
        getWebElement(locator).sendKeys(key);
    }

    protected void clickElement(By locator) {
        try {
            getWebElement(locator).click();
        } catch (ElementClickInterceptedException e) {
            try {
                driver.wait(500);
            } catch (InterruptedException ex) { }
            getWebElement(locator).click();
        }
    }

    protected void navigate(String url) throws Exception {
        driver.get(url);
    }

    private WebElement getWebElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            System.out.println("url: " + driver.getCurrentUrl());
            e.printStackTrace();
        }
        return null;
    }
}



