package pageObjects;

import java.util.List;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import base.TestBase;
import commonUtils.PropertyReader;


/* Create Leave Page Object */

public class AddLeavePage {
	
	WebDriver driver;
	
	public AddLeavePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	private By tableHead = By.xpath("//table[@class=\"table-condensed\"]//following ::th[@class=\"datepicker-switch\"]");
	private By selectMonths = By.xpath("//table[@class=\"table-condensed\"]//following ::span[@class=\"month\"]");
	private By selectDays = By.xpath("//table[@class=\"table-condensed\"]//following ::td[@class=\"day\"]");

	
	@FindBy(id = "away_leave_type")
	WebElement leaveTypeDropDown;

	@FindBy(id = "away_description")
	WebElement descriptionBox;

	@FindBy(id = "away_from_date")
	WebElement fromDate;

	@FindBy(id = "away_to_date")
	WebElement toDate;

	@FindBy(id = "away_num_of_days")
	WebElement noOfDays;

	@FindBy(how = How.XPATH, using = "//input[@type=\"submit\"]")
	WebElement submitBtn;

	@FindBy(how = How.XPATH, using = "//a[@class=\"btn btn-default\"]")
	WebElement cancelBtn;
	

	@FindBy(how = How.XPATH, using = "//div[@class=\"belowHeader\"]//following :: h1")
	WebElement leaveTitle;

	@Step("Select leave type LeaveType:{0}")
	private void selectLeave(String leaveType) {
		leaveTypeDropDown.click();
		Select select = new Select(leaveTypeDropDown);
		select.selectByValue(leaveType);
		TestBase.captureScreenShot(driver, true);

	}
	@Step("Enter Leave Description.{0}")
	private void AddDescription(String description) {
		descriptionBox.sendKeys(description);

	}
	@Step("Enter No of days {0}")
	private void EnterNoOfDays(String numberOfDays) {
		noOfDays.sendKeys(numberOfDays);

	}
	@Step("Click on Submit button.")
	public LeavesDetails ClickOnSubmit() {
		submitBtn.click();
		return new LeavesDetails(driver);
	}

	/* Select date using js executor */
	private void SelectDate() {

		List<WebElement> inputs = driver.findElements(By.tagName("input"));
		for (WebElement input : inputs) {
			((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','readonly')", input);
		}
		fromDate.sendKeys(PropertyReader.getLeaveFrom());
		fromDate.sendKeys(Keys.ENTER);
		toDate.sendKeys(PropertyReader.getLeaveTo());
		toDate.sendKeys(Keys.ENTER);
	}

	/* Select date using dropdown. */
	private void getDate(String inputDate) {

		String date[] = inputDate.split("/");
		String day = date[0];
		String month = date[1];
		driver.findElement(tableHead).click();
		List<WebElement> monthList = driver.findElements(selectMonths);
		for (WebElement el : monthList) {
			String getMonth = el.getText();
			if (getMonth.equalsIgnoreCase(month)) {
				el.click();
				break;

			}
		}
		try {
			Thread.sleep(100);
		} catch (Exception e) {

			e.printStackTrace();
		}
		List<WebElement> days = driver.findElements(selectDays);
		for (WebElement ele : days) {
			String getDay = ele.getText();

			if (getDay.equalsIgnoreCase(day)) {
				ele.click();
				break;
			}
		}
	}
	/* Verify add Leave page title. */
	@Step("Verify leave page Title.")
	public boolean LeavesTitle() {
		return leaveTitle.getText().contains("Leaves");
	}

	/* Create leaves  */
	@Step("Create leave.")
	public LeavesDetails createLeave() {

		String leaveFrom = PropertyReader.getLeaveFrom();
		String leaveTo = PropertyReader.getLeaveTo();
		TestBase.captureScreenShot(driver, true);
		selectLeave(PropertyReader.getLeaveType());
		AddDescription(PropertyReader.getLeaveType());
		SelectDate();
//		fromDate.click();
//		getDate(leaveFrom);
//		toDate.click();
//		getDate(leaveTo);
		EnterNoOfDays("1");
		ClickOnSubmit();
		return new LeavesDetails(driver);
	}
}
