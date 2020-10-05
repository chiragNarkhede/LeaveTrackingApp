package base;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import commonUtils.PropertyReader;
import pageObjects.LandingPage.NavigationBar;
import pageObjects.LoginPage;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;



public class TestBase {

	public WebDriver driver;
	public WebDriverWait wait;
	private String url;
	private String browserName;
	public static Logger log = LogManager.getLogger(TestBase.class.getName());

	@Step("Initialize the driver.")
	public WebDriver driverInitializer() throws IOException {

	
			browserName = PropertyReader.getBrowserName();
			
			if (browserName.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + PropertyReader.getChromePath());
				driver = new ChromeDriver();
			} else if (browserName.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + PropertyReader.getDriverPath());
				driver = new FirefoxDriver();
			}

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		
		return driver;
	}

	@Step("Open application.")
	public LoginPage openApp() {
		url = PropertyReader.getUrl();
		driver.get(url);
		driver.manage().window().maximize();
		captureScreenShot(driver,true);
		return new LoginPage(driver);
	}


	/*
	Close the browser.
	 */
	@Step("Close the Application.")
	public void closeApp() {

		driver.close();
		log.debug("Browser closed");
	}


	/*  Capture the Screen shot.
	* @param webdriver
	* @param boolean
	* */

	public static void captureScreenShot(WebDriver driver, boolean error) {

			String folder = PropertyReader.getScreenShotFolderPath();
			String destinationFile;
			if (!error) {
				destinationFile = System.getProperty("user.dir")+ folder  + "//Failed-" + System.currentTimeMillis()
						+ ".png";
			} else {
				destinationFile = System.getProperty("user.dir") + folder + "//Passed-" + System.currentTimeMillis()
						+ ".png";
			}
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			try 
			{
				FileUtils.copyFile(source, new File(destinationFile));
			}catch(Exception e) {
				log.error(e.getMessage().toString() +
						e.getStackTrace().toString()
				);
			}
	}
	
	public static String  captureScreen(WebDriver driver,String testCaseName ) {

	
		String destinationFile =System.getProperty("user.dir")+ "\\reports\\" + testCaseName+ ".png";
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		try 
		{
			FileUtils.copyFile(source, new File(destinationFile));
		}catch(Exception e) {
			log.error(e.getMessage().toString() +
					e.getStackTrace().toString()
				);
		}
	return destinationFile;
}

	
	public static void wait(WebDriver driver,WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	@Step("Verify that alert is present.")
	public static Alert isAlertPresent(WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver,20);
		Alert alert = null;
		try{
			 alert = wait.until(ExpectedConditions.alertIsPresent());
		}catch(Exception e){
			log.error("Alert is not visible "+e.getMessage()+
			e.getStackTrace().toString());
		}
		return alert;
	}

}
