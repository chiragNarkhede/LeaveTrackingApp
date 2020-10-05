package functionTest;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import commonUtils.LeaveObject;
import commonUtils.ListenerTestNG;
import commonUtils.PropertyReader;
import pageObjects.LandingPage;
import pageObjects.LeavesPage;
import pageObjects.LoginPage;
import pageObjects.LandingPage.NavigationBar;
import base.TestBase;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/* Create New Leave with New Approach */
@Listeners(ListenerTestNG.class)
public class CreateNewLeave extends TestBase {
	WebDriver driver;
	@BeforeTest
	public void setup() throws IOException {
		PropertyReader.ReadFile();
		driver = driverInitializer();
		openApp().
		loginToApp().
		navigationBar(NavigationBar.Leaves);
	}
	
	@Test(description = "Verify new leave is created.")
	@Description("Verify and create new leave.")
	@Story("Create New Leave")
	public void check() throws IOException{

		new LeavesPage(driver).clickAddNewBtn()
				.createLeave()
				.verifyLeavesDetails()
				.clickOnSubmitLeave()
				.assertLeaveSubmit();
	}
	
	@AfterTest
	public void tearDown() {
		new LandingPage(driver).
		logOut(NavigationBar.LogOutBtn);
		closeApp();
	}
}
