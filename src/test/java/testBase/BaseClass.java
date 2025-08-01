package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;   //log4j

 public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @Parameters({"os","browser"})
    @BeforeClass(groups = {"Sanity","Regression","Master","Datadriven"})
    public void setUp(String os, String br) throws IOException {
        // Loading config.properties

        FileReader file=new FileReader("./src//test//resources//config.properties");
        p=new Properties();
        p.load(file);


        logger= LogManager.getLogger(this.getClass());
        if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities capabilities=new DesiredCapabilities();

            //os
            if(os.equalsIgnoreCase("windows"))
            {
                capabilities.setPlatform(Platform.WIN11);
            }
            else if (os.equalsIgnoreCase("mac"))
            {
                capabilities.setPlatform(Platform.MAC);
            }
            else
            {
                System.out.println("No matching os");
                return;
            }

            //browser
            switch(br.toLowerCase())
            {
                case "chrome": capabilities.setBrowserName("chrome"); break;
                case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
                default: System.out.println("No matching browser"); return;
            }

            driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
        }


        if(p.getProperty("execution_env").equalsIgnoreCase("local"))
        {

            switch(br.toLowerCase())
            {
                case "chrome" : driver=new ChromeDriver(); break;
                case "edge" : driver=new EdgeDriver(); break;
                case "firefox": driver=new FirefoxDriver(); break;
                default : System.out.println("Invalid browser name.."); return;
            }
        }


        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(p.getProperty("appURL")); // reading url from properties file
      //  driver.get("https://tutorialsninja.com/demo/");
        driver.manage().window().maximize();

    }

    @AfterClass
    public void tearDown() {
        driver.quit();

    }

    public String randomString() {
        String generatedstring = RandomStringUtils.randomAlphabetic(5);
        return generatedstring;
    }

    public String randomNumber() {
        String generatednumber = RandomStringUtils.randomNumeric(10);
        return generatednumber;
    }

    public String alphaNumeric() {
        String generatedstring = RandomStringUtils.randomAlphabetic(3);
        String generatednumber = RandomStringUtils.randomNumeric(3);
        return (generatedstring + "@" + generatednumber);
    }
    public String captureScreen(String tname) throws IOException{

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
        File sourcefile =takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile=new File(targetFilePath);
        sourcefile.renameTo(targetFile);
        return targetFilePath;

    }

}
