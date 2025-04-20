package DriverManager;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import Utils.ConfigReader;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

//	public static void initDriver() {
//		String browser = ConfigReader.getProperty("browser");
//		boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
//
//		switch (browser.toLowerCase()) {
//		case "chrome":
//			WebDriverManager.chromedriver().setup();
//			ChromeOptions chromeOptions = new ChromeOptions();
//			if (isHeadless)
//				chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
//			driver.set(new ChromeDriver(chromeOptions));
//			break;
//
//		case "firefox":
//			WebDriverManager.firefoxdriver().setup();
//			FirefoxOptions firefoxOptions = new FirefoxOptions();
//			if (isHeadless)
//				firefoxOptions.addArguments("--headless");
//			driver.set(new FirefoxDriver(firefoxOptions));
//			break;
//
//		case "edge":
//			WebDriverManager.edgedriver().setup();
//			EdgeOptions edgeOptions = new EdgeOptions();
//			if (isHeadless)
//				edgeOptions.addArguments("--headless=new");
//			driver.set(new EdgeDriver(edgeOptions));
//			break;
//
//		default:
//			throw new RuntimeException("Invalid browser in config.properties");
//		}
//
//		getDriver().manage().window().maximize();
//	}
	
	public static void initDriver() {
		// Get browser from system property or fallback to config
		String browser = System.getProperty("browser");
		if (browser == null || browser.isEmpty()) {
			browser = ConfigReader.getProperty("browser");
		}

		// Get headless setting
		boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.getProperty("headless")));

		switch (browser.toLowerCase()) {
		case "chrome":
			//WebDriverManager.chromedriver().setup();
			String currentDir = System.getProperty("user.dir");
	        
	        // Set the path to chromedriver relative to the current working directory
	        String chromeDriverPath = currentDir + System.getProperty("file.separator")+"chromedriver.exe";
	        
	        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions chromeOptions = new ChromeOptions();
			if (isHeadless)
				chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
			driver.set(new ChromeDriver(chromeOptions));
			break;
			

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (isHeadless)
				firefoxOptions.addArguments("--headless");
			driver.set(new FirefoxDriver(firefoxOptions));
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeOptions edgeOptions = new EdgeOptions();
			if (isHeadless)
				edgeOptions.addArguments("--headless=new");
			driver.set(new EdgeDriver(edgeOptions));
			break;

		default:
			throw new RuntimeException("Invalid browser specified: " + browser);
		}

		getDriver().manage().window().maximize();
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
}