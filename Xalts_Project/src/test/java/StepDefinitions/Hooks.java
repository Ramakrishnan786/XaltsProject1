package StepDefinitions;

import java.util.List;

import DriverManager.DriverManager;
import Utils.ConfigReader;
import Utils.ExtentReportManager;
import Utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void setUp(Scenario scenario) {
		ConfigReader.initProperties();
		DriverManager.initDriver();
		ExtentReportManager.initReport();
		ExtentReportManager.createTest(scenario.getName());
	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			ExtentReportManager.getTest().fail("Test Failed");
		} else {
			ExtentReportManager.getTest().pass("Test Passed");
		}
		DriverManager.quitDriver();
		ExtentReportManager.flushReport();
	}

//	@AfterStep
//	public void afterEachStep(Scenario scenario) {
//		Boolean result = StepContext.stepResult.get();
//		String message = StepContext.stepMessage.get();
//
//		// Use defaults if step didn't explicitly set them
//		if (result == null)
//			result = true; // Assume step passed if not set
//		if (message == null)
//			message = "Step executed";
//
//		if (result) {
//			ExtentReportManager.getTest().pass("✅ " + message);
//		} else {
//			ExtentReportManager.getTest().fail("❌ " + message);
//			// Optional: attach screenshot or more debug info
//			throw new AssertionError("Step failed: " + message);
//		}
//
//		// Clean up for next step
//		StepContext.stepResult.remove();
//		StepContext.stepMessage.remove();
//	}
//	public void logStep(boolean result, String infoMessage, Scenario scenario) {
//	    String stepName = scenario.getName(); // Or use EventPublisher for actual Gherkin step
//	    if (result) {
//	        ExtentReportManager.getTest().pass("✅ " + stepName);
//	        ExtentReportManager.getTest().info("ℹ️ " + infoMessage);
//	    } else {
//	        ExtentReportManager.getTest().fail("❌ " + stepName);
//	        ExtentReportManager.getTest().info("ℹ️ " + infoMessage);
//	        throw new AssertionError("Step failed: " + stepName);
//	    }
//	}

	@AfterStep
	public void afterStep(Scenario scenario) {
	    String stepName = StepTracker.getCurrentStep();
	    boolean result = StepContext.getResult(); // your own context to track results
	   
	   // ExtentReportManager.getTest().info("Step: " + stepName);
	    
	    if (stepName == null) stepName = scenario.getName(); // fallback

	    if (result) {
	        ExtentReportManager.getTest().pass("✅ " + stepName);
	    } else {
	        ExtentReportManager.getTest().fail("❌ " + stepName);
	        throw new AssertionError("Step failed: " + stepName);
	    }
	    
	    if (scenario.isFailed()) {
	        String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName());
	        ExtentReportManager.getTest()
	            .fail("Test failed. Screenshot attached.")
	            .addScreenCaptureFromPath(screenshotPath);
	    }


	}



}
