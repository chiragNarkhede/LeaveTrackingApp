package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.support.How;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Listeners;

import base.TestBase;
import commonUtils.ListenerTestNG;
import commonUtils.PropertyReader;

import org.openqa.selenium.support.FindBy;

public class LandingPage {
	WebDriver driver;

	/* Navigation bar  */
	public enum NavigationBar {
		Home,
		Leaves,
		Calender,
		LeaveSummary,
		Reviews,
		TimeSheets,
		LogOutBtn
		
	}


	@FindBy(how = How.XPATH,using = "//div[@class=\"col-md-offset-0 col-md-11\"]//li")
	List<WebElement> topNavigation;
	
	@FindBy(how = How.XPATH, using = "//a[@href=\"/aways\"]")
	WebElement leavesBtn;
	
	@FindBy(how = How.XPATH,using = "span[@class=\"glyphicon glyphicon-home\"]")
	WebElement homeBtn;
	
	@FindBy(how = How.XPATH,using = "//a[@href=\"/calendar/index\"]")
	WebElement calenderBtn;
	@FindBy(how = How.XPATH,using = "//a[@href=\"/aways_report/index\"]")
	WebElement leaveSummaryBtn;
	@FindBy(how = How.XPATH,using = "//a[@href=\"/reviews\"]")
	WebElement reviewBtn;
	@FindBy(how = How.XPATH,using = "//a[@href=\"/timesheets\"]")
	WebElement timeSheetBtn;
	
	@FindBy(how = How.XPATH,using = "//span[@class=\"glyphicon glyphicon-log-out\"]")
	WebElement logOutBtn;
	
	public LandingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/* Select Navigation bar */

	public LeavesPage navigationBar(NavigationBar navigateTo) {
				
		switch(navigateTo) {
		case Home:
			homeBtn.click();
			break;
		case Leaves:
			leavesBtn.click();
			break;
		case Calender:
			calenderBtn.click();
			break;
		case LeaveSummary:
			leaveSummaryBtn.click();
			break;
		case Reviews:
			reviewBtn.click();
			break;
		case TimeSheets:
			timeSheetBtn.click();
			break;
		case LogOutBtn:
			logOutBtn.click();
		}
		TestBase.captureScreenShot(driver,true);
		return new LeavesPage(driver);
	}

	/*  Verify landing page. */
	@Step("Verify user is landed on Landing page.")
	public boolean verifyLandingPage() {
		return driver.getCurrentUrl().contains(PropertyReader.getUrl());
	}

	/*Log out from application */
	@Step("Logout from the application.")
	public LoginPage logOut(NavigationBar navigationBar) {
		navigationBar(navigationBar);
		return new LoginPage(driver);
	}
}
