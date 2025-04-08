package Utils;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	private static ExtentReports extent;
	private static ExtentTest test;

	public static void initReport() {
		if (extent == null) {
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
			sparkReporter.config().setDocumentTitle("Automation Report");
			sparkReporter.config().setReportName("Xalts Automation Results");

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User", System.getProperty("user.name"));
		}
	}

	public static void createTest(String testName) {
		test = extent.createTest(testName);
	}

	public static ExtentTest getTest() {
		return test;
	}

	public static void flushReport() {
		if (extent != null) {
			extent.flush();
		}
	}
	
	public static void logFailureWithScreenshot(String message, String screenshotPath) {
        try {
            getTest().fail(message).addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            getTest().fail("Screenshot not available: " + e.getMessage());
        }
    }
}
