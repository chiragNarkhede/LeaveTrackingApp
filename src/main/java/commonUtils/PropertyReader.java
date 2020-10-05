package commonUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


	/* Read a data from config file. */

public class PropertyReader {

	private static String url;
	private static String userName;
	private static String passWord;
	private static String driverPath;
	private static Properties prop;
	private static String browserName;
	private static String chromePath;
	private static String screenShotFolderPath;
	private static String reportFolder;
	private static String testerName;
	private static String leaveFrom ;
	private static String leaveTo ;
	private static String leaveType;
	
	
	
	public static String getPassWord() {
		return passWord;
	}


	public static Properties getProp() {
		return prop;
	}


	public static String getLeaveFrom() {
		return leaveFrom;
	}


	public static String getLeaveTo() {
		return leaveTo;
	}


	public static String getLeaveType() {
		return leaveType;
	}


	public static String getReportFolder() {
		return reportFolder;
	}


	public static String getTesterName() {
		return testerName;
	}

	public static String getChromePath() {
		return chromePath;
	}


	public static String getBrowserName() {
		return browserName;
	}

	public static String getDriverPath() {
		return driverPath;
	}
	
	public static String getUrl() {
		return url;
	}
	
	public static String getUserName() {
		return userName;
	}
	
	public static String getScreenShotFolderPath() {
		return screenShotFolderPath;
	}

	public static String getPassword() {
		return passWord;
	}
	
	
	
	public static void ReadFile() throws IOException
	{
		prop = new Properties();
		FileInputStream fileInput = new FileInputStream(System.getProperty("user.dir") + "//resources//data.properties");
		prop.load(fileInput);
		 url =  prop.getProperty("url");
		 userName =  prop.getProperty("userName");
		 passWord =  prop.getProperty("password");
		 driverPath =  prop.getProperty("driverPath");
		 chromePath = prop.getProperty("chromePath");
		 browserName=prop.getProperty("browserName");
		 screenShotFolderPath=prop.getProperty("screenshot");
		 reportFolder= prop.getProperty("reportPath");
		 testerName= prop.getProperty("tester");
		 leaveFrom =prop.getProperty("leaveFrom");
		 leaveTo= prop.getProperty("leaveTo");
		 leaveType= prop.getProperty("leaveType");
		 
		 
	}
	
	
}
