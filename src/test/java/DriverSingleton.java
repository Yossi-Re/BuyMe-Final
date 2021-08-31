import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class DriverSingleton {
    private static WebDriver driver;

    private static String getData (String keyName) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        //File fXmlFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\data.xml");
        File fXmlFile = new File(System.getProperty("user.dir") + "/src/main/resources/data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(keyName).item(0).getTextContent();
    }

    public static WebDriver getDriverInstance() throws Exception {
        if(driver == null){
            String type= getData("browserType");;
            if(type.equals("Chrome")){
                ///Users/liortamir/SDK/chrome-web-driver
                 System.setProperty("webdriver.chrome.driver" , "C:\\Users\\ereuv\\Downloads\\chromedriver_win32 (4)\\chromedriver.exe");
                //System.setProperty("webdriver.chrome.driver" , "/Users/liortamir/SDK/chrome-web-driver/chromedriver");
                driver = new ChromeDriver();
            }else if(type.equals("FF")){
                System.setProperty("webdriver.firefox.driver", "C:\\geckodriver\\geckodriver.exe");
                driver = new FirefoxDriver();
            }
//            System.setProperty("webdriver.chrome.driver" , "C:\\Users\\ereuv\\Downloads\\chromedriver_win32 (4)\\chromedriver.exe");
//            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            driver.get("https://buyme.co.il/");
        }

        return driver;
    }
}
