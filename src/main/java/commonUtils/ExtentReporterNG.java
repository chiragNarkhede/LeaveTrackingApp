package commonUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class ExtentReporterNG {
	
	String systemAdmin = PropertyReader.getTesterName();
	ExtentReports extent;
	
	public ExtentReports extendReportObject()
	{
		
		String path = System.getProperty("user.dir") + "//reports//index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Leave Application Report");
		reporter.config().setDocumentTitle("Test Results");
		extent =new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo(systemAdmin, systemAdmin);
		System.out.println(extent.toString());
		return extent;
		
	}

}
