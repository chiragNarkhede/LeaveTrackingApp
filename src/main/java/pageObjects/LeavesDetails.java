package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import commonUtils.LeaveDetailsObject;
import commonUtils.PropertyReader;

public class LeavesDetails {
	
	
	WebDriver driver;

	private static String leaveId ;
	
	public LeavesDetails(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using = "//div[@class=\"belowHeader\"]//h1")
	WebElement leavesDetailsHeader;

	@FindBy(id = "away_leave_type")
	WebElement leaveTypeDropDown;
	
	@FindBy(how = How.XPATH, using = "//dl[@class=\"dl-horizontal\"]//dd[1]")
	WebElement leaveType;
	

	@FindBy(how = How.XPATH, using = "//dl[@class=\"dl-horizontal\"]//dd[2]")
	WebElement description;
	

	@FindBy(how = How.XPATH, using = "//dl[@class=\"dl-horizontal\"]//dd[3]")
	WebElement fromDate;
	

	@FindBy(how = How.XPATH, using = "//dl[@class=\"dl-horizontal\"]//dd[4]")
	WebElement toDate;
	

	@FindBy(how = How.XPATH, using = "//dl[@class=\"dl-horizontal\"]//dd[5]")
	WebElement noOfDays;
	

	@FindBy(how = How.XPATH, using = "//dl[@class=\"dl-horizontal\"]//dd[6]")
	WebElement employee;
	
		
	@FindBy(how = How.XPATH, using = "//a[@class=\"btn btn-default\"][@href=\"/aways\"]")
	WebElement backBtn;
	@FindBy(how = How.XPATH, using = "//a[@class=\"btn btn-default\"][@href=\"/aways/1573/edit\"]")
	WebElement editBtn;
	@FindBy(how = How.XPATH, using = "//a[@class=\"btn btn-danger\"]")
	WebElement deleteBtn;

	@Step("Click on the Back btn.")
	public LeavesPage clickOnBackBtn() {
		 backBtn.click();
		 return new LeavesPage(driver);
	}

	@Step("Click on the edit btn.")

	public void clickOnEditBtn() {
		editBtn.click();
	}

	@Step("Click on the Delete button.")
	public void clickOnDeleteBtn() {
		deleteBtn.click();
	}

	@Step("Verify leaves Details Page.")
	public boolean verifyLeavesDetailPage() {
		return leavesDetailsHeader.getText().contains("Leave Details");
	}


	/*  Set value to leave object for comparison */
	@Step("Set leave details objec.")
	public LeaveDetailsObject setLeaveDetails() {
		
		LeaveDetailsObject leaveDetailObject = new LeaveDetailsObject();
		leaveDetailObject.setLeaveType(leaveType.getText());
		leaveDetailObject.setDescription(description.getText());
		leaveDetailObject.setFromDate(fromDate.getText());
		leaveDetailObject.setToDate(toDate.getText());
		leaveDetailObject.setNoOfDays(noOfDays.getText());
		leaveDetailObject.setEmployee(employee.getText());
		return leaveDetailObject;
	}

	/*  Fetch leave ID */
	@Step("Fetch leave Id.")
	public String fetchLeaveId() {
		String leaveId = driver.getCurrentUrl().substring(33);
		return leaveId;
	}

	@Step("Set leave id.")
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	@Step("Get leave Id.")
	public String getLeaveId() {
		return leaveId;
	}

	@Step("Verify leave Details.")
	public LeavesPage verifyLeavesDetails() {
		verifyLeavesDetailPage();
		setLeaveDetails();
		String leaveID = fetchLeaveId();
		setLeaveId(leaveID);
		clickOnBackBtn();
		return new LeavesPage(driver);
	}

}
