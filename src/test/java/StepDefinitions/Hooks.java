package StepDefinitions;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.testng.annotations.Parameters;

import DriverManager.DriverManager;
import ScreenRecorer.MyScreenRecorder;
import Utils.ConfigReader;
import Utils.ExtentReportManager;
import Utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	MyScreenRecorder screenRecorder;
	String videoName;

	@Before
	public void setUp(Scenario scenario) throws IOException, AWTException {
		videoName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"); // Clean filename
		File file = new File("test-recordings");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		screenRecorder = new MyScreenRecorder(gc, captureSize,
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
						Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				null, null, file, videoName);

		ConfigReader.initProperties();
		DriverManager.initDriver();
		ExtentReportManager.initReport();
		ExtentReportManager.createTest(scenario.getName());
		screenRecorder.start();
	}

	@After
	public void tearDown(Scenario scenario) throws IOException {
		String pathToVideo = System.getProperty("user.dir") +"/test-recordings/" + videoName + ".avi";
		ExtentReportManager.getTest().info(
		    "<a href='" + pathToVideo + "' target='_blank'>▶ Watch Recording</a>");
		if (scenario.isFailed()) {
			ExtentReportManager.getTest().fail("Test Failed");
		} else {
			ExtentReportManager.getTest().pass("Test Passed");
		}
		screenRecorder.stop();
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
	

		if (stepName == null)
			stepName = scenario.getName(); // fallback

		if (result) {
			ExtentReportManager.getTest().pass("✅ " + stepName);
		} else {
			ExtentReportManager.getTest().fail("❌ " + stepName);
			throw new AssertionError("Step failed: " + stepName);
		}

		if (scenario.isFailed()) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName());
			ExtentReportManager.getTest().fail("Test failed. Screenshot attached.")
					.addScreenCaptureFromPath(screenshotPath);
		}

	}

}
