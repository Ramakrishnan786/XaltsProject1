package Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import DriverManager.DriverManager;

public class ScreenshotUtil {
    public static String captureScreenshot( String screenshotName) {
    	WebDriver driver = DriverManager.getDriver();
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String destPath = System.getProperty("user.dir")+"test-output/screenshots/" + screenshotName + ".png";
        File dest = new File(destPath);
        try {
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destPath;
    }
}
