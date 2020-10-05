package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.support.How;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import base.TestBase;
import commonUtils.LeaveDetailsObject;
import commonUtils.LeaveObject;
import commonUtils.LeaveSummary;
import commonUtils.ListenerTestNG;
import commonUtils.PropertyReader;

import org.openqa.selenium.support.FindBy;

public class LeavesPage {

	WebDriver driver;
	LeaveObject leaveObject;
	

	public LeavesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	@FindBy(how = How.XPATH, using = "//a[@class=\"btn btn-primary\"]")
	WebElement newBtn;

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

	@Step("Click on Add New Btn")
	public AddLeavePage clickAddNewBtn() {

		newBtn.click();
		return new AddLeavePage(driver);
	}

	@Step("Verify user landed on Leave Page.")
	public boolean leavesTitle() {
		return leaveTitle.getText().contains("Leaves");
	}

	/* Get LeaveTable summary*/
	@Step("Get leave summary table data.")
	public LeaveObject getLeaveSummaryTable() {
		ArrayList<LeaveSummary> leaveSummaryList = new ArrayList<LeaveSummary>();
		LeaveObject leaveObject = new LeaveObject();
		int row = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody//tr")).size();
		for (int index = 1; index <= row; index++) {
			LeaveSummary leaveSummary = new LeaveSummary();
			String id = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[1]"))
					.getText();
			leaveSummary.setId(id);
			String leaveType = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[2]"))
					.getText();
			leaveSummary.setLeaveType(leaveType);
			String descp = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[3]"))
					.getText();
			leaveSummary.setDescription(descp);
			String fromDate = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[4]"))
					.getText();
			leaveSummary.setFromDate(fromDate);
			String toDate = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[5]"))
					.getText();
			leaveSummary.setToDate(toDate);
			String noOFDays = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[6]"))
					.getText();
			leaveSummary.setNoOfDays(noOFDays);
			WebElement arrayElement = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[7]"));
			WebElement deleteButton = arrayElement.findElement(By.xpath("//a[@class=\"btn btn-xs btn-danger\"]"));
			leaveSummary.setDeleteButton(deleteButton);
			WebElement submitbuttom = driver
					.findElement(By.xpath("//table[@class='table table-striped']//tbody//tr[" + index + "]//td[8]"));
			leaveSummary.setSubmitButton(submitbuttom);
			leaveSummaryList.add(leaveSummary);
		}
		leaveObject.setLeaveSumamry(leaveSummaryList);
	return leaveObject;
	}


	/* Verify leave details from table and leave details page */
	@Step("Get leave data from table.")
	public List<LeaveSummary> getLeaveRow(String leaveId){
		LeaveObject leaveObject = getLeaveSummaryTable();
		List<LeaveSummary> list = leaveObject.getLeaveSumamry();
		List<LeaveSummary> leaveSummaryList = list.stream().
				filter(s->s.getId().contains(leaveId))
				.collect(Collectors.toList());
		return leaveSummaryList;
	}

	/* Click on submit leave */
	@Step("Click on submit leave")
	public LeavesPage clickOnSubmitLeave() {
		boolean result = true;
		LeavesDetails leavesDetails = new LeavesDetails(driver);

		String leaveId = leavesDetails.getLeaveId();
		List<LeaveSummary> leaveSummaryList = getLeaveRow(leaveId);
		leaveSummaryList.stream().forEach(s->s.getSubmitButton().click());
		TestBase.captureScreenShot(driver,true);
		return new LeavesPage(driver);
	}
	/* Delete leave */
	@Step("Delete leave.")

	public boolean deleteLeaves(){
		LeavesDetails leavesDetails = new LeavesDetails(driver);
		String leaveId = leavesDetails.getLeaveId();
		List<LeaveSummary> leaveSummaryList = getLeaveRow("1700");
		leaveSummaryList.stream().forEach(s->s.getDeleteButton().click());
		TestBase.captureScreenShot(driver,true);
		String alertText = TestBase.isAlertPresent(driver).getText();
		TestBase.isAlertPresent(driver).accept();
		TestBase.captureScreenShot(driver,true);
		return alertText.contains("Are you sure");

	}
	@Step("Verify leave is submitted.")
	public void assertLeaveSubmit() {
		boolean result =true;
		LeavesDetails leavesDetails = new LeavesDetails(driver);
		String leaveId = leavesDetails.getLeaveId();
		List<LeaveSummary> leaveSummaryList = getLeaveRow(leaveId);
		int size = leaveSummaryList.stream().
				map(s->s.getSubmitButton().getText()).
				collect(Collectors.toList()).size();

		if(size!=1) {
			result =false;
		}
		Assert.assertTrue(result);
	}
}