import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MainTest {


    public static WebDriver driver;
    private static WebDriverWait wait;
    private static ExtentReports extent= new ExtentReports();
    public static ExtentTest test = extent.createTest("MyBuyMeTest", "Sample description");
    private Throwable e;


    @BeforeClass
    public static void runOnceBeforeClass() throws Exception {
       try{

        driver = DriverSingleton.getDriverInstance();
        String reportPath = System.getProperty("user.dir");
           ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath +"/extent.html");
           extent.attachReporter(htmlReporter);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        driver.get("https://buyme.co.il/");
        test.log(Status.INFO, "before test method");

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            test.log(Status.PASS, "Driver established successfully");
        } catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Driver connection failed! " + e.getMessage());
            throw new Exception("Driver failed");
        }


    }

    @Test(groups = "step1")
    public void assertAllFields() throws Exception {
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath())).click();
        IntroRegistrationScreen step1 = new IntroRegistrationScreen();
        step1.register();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@placeholder='שם פרטי']")));
        Assert.assertEquals(Constants.NAME, driver.findElement(By.xpath("//*[@placeholder='שם פרטי']")).getAttribute("value"));
        Assert.assertEquals(Constants.EMAIL, driver.findElement(By.xpath("//*[@placeholder='מייל']")).getAttribute("value"));
        Assert.assertEquals(Constants.PASSWORD, driver.findElement(By.xpath("//*[@placeholder='סיסמה']")).getAttribute("value"));
        Assert.assertEquals(Constants.PASSWORD, driver.findElement(By.xpath("//*[@placeholder='אימות סיסמה']")).getAttribute("value"));

        step1.login();
        /**
         * Call Set Search For Gift options.
         */
        HomeScreen step2 = new HomeScreen();
        step2.setSearchForGift();
//        test.log(Status.FAIL, "Credentials were not filled " + e.getMessage());
    }

    /**
     * Test for Step C - Assert website URL
     */
    @Test(groups = "step3", dependsOnGroups = "step1")
    public void assertURLTest() {
        String buyMeURL = "https://buyme.co.il/search?budget=5&category=16&region=13";
        Assert.assertEquals(driver.getCurrentUrl(), buyMeURL);
        PickBusiness step3 = new PickBusiness();
        step3.setChoosingBusiness();
        test.log(Status.FAIL, "URL is not correct " + driver.getCurrentUrl());
    }

    /**
     * Assert tests for Step 4.
     */
    @Test(groups = "step4", dependsOnGroups = "step3")
    public void assertSenderReceiverNames() {
        SenderReceiverScreen step4 = new SenderReceiverScreen();
        step4.giftForSomeoneElse();
        try {
            Assert.assertEquals(Constants.NAME, driver.findElement(By.xpath("//*[@id=\"ember2187\"]")).getText());
            Assert.assertEquals(Constants.receiverName, driver.findElement(By.name("מי הזוכה המאושר? יש להשלים את שם המקבל/ת")).getText());
        }catch(NoSuchElementException e){
            test.log(Status.FAIL, "Send/Received names were not completed " + e.getMessage());
            test.info("details", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
        }

    }

    @AfterClass
    public static void afterClass() {
        test.log(Status.INFO, "@After test " + "After test method");
        //driver.quit();
        // build and flush report
        extent.flush();
    }

    private static String takeScreenShot() {
        String ImageFinal = String.valueOf(System.currentTimeMillis());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImageFinal + ".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return ImageFinal + ".png";
    }

}