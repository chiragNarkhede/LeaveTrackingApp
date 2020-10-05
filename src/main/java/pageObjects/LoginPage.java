package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;

import base.TestBase;
import commonUtils.ListenerTestNG;
import commonUtils.PropertyReader;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage {

	WebDriver driver;
	String user = PropertyReader.getUserName();
	String userPassword = PropertyReader.getPassword();

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "user_email")
	WebElement userName;

	@FindBy(id = "user_password")
	WebElement passWord;

	@FindBy(how = How.XPATH, using = "//input[@type=\"submit\"]")
	WebElement submitBtn;

	@Step("Enter email id Email ID : {0}")
	private void insertUserName(String email) {

		userName.sendKeys(email);
	}
	@Step("Enter password  Password : {0}")
	private void insertPassword(String password) {
		passWord.sendKeys(password);
	}

	@Step("Click on submit button.")
	private void clickSubmit() {
		submitBtn.click();
	}

	/* Login to application */

	@Step("Login to Leave Application.")
	public LandingPage loginToApp() {
		TestBase.captureScreenShot(driver,true);
		insertUserName(user);
		insertPassword(userPassword);
		clickSubmit();
		TestBase.captureScreenShot(driver,true);
		return new LandingPage(driver);
	}
}
