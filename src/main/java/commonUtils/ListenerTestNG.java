package commonUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import base.TestBase;

public class ListenerTestNG extends TestBase implements ITestListener {

	public static Logger log = LogManager.getLogger(ListenerTestNG.class.getName());
	
	ExtentTest test;
	ExtentReporterNG reporter = new ExtentReporterNG();

	ExtentReports extent = reporter.extendReportObject();
	ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();
	
	public void onStart(ITestContext context) {
		log.debug("onStart method started");

	}

	public void onFinish(ITestContext context) {

		log.debug("onFinish method started");
		extent.flush();
		
	}

	public void onTestStart(ITestResult result) {
		log.debug("Test Started." + result.getMethod().getMethodName());
		test = extent.createTest(result.getMethod().getMethodName());
		threadLocal.set(test);
		
		
	}

	public void onTestSuccess(ITestResult result) {
		log.debug("Success Test Method" + result.getName().toString());
		String testCaseName = result.getMethod().getMethodName();
		threadLocal.get().log(Status.PASS, testCaseName+"Test Passed");
	}

	public void onTestFailure(ITestResult result) {
		String testCaseName= result.getMethod().getMethodName();
		WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
		test.fail("FailedScreen shot",MediaEntityBuilder.createScreenCaptureFromPath(TestBase.captureScreen(driver, testCaseName)).build());
	}
	

	public void onTestSkipped(ITestResult result) {
		log.debug("Test Method skipped." + result.getName().toString());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.debug("Test failed with success Percentage" + result.getName().toString());
	}
}
	
