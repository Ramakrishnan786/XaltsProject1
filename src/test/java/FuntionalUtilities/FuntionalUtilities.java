package FuntionalUtilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FuntionalUtilities {

	protected WebDriver driver;
	private static final int DEFAULT_TIMEOUT = 10; // You can change this in one place only

	public FuntionalUtilities(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getElement(By locator) {
		return new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementVisible(By locator) {
		return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(15)).pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForDocumentReady() {
		new WebDriverWait(driver, Duration.ofSeconds(15)).until(webDriver -> ((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState").equals("complete"));
	}

	// ✅ Wait for loader/spinner to disappear (customizable)
	public void waitForLoaderToDisappear(By loaderLocator) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
		} catch (TimeoutException e) {

		}
	}

	// ✅ Wait for a specific element to confirm page is ready
	public void waitForElement(By locator) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// ✅ Unified page ready method (call this in child pages)
	public void waitUntilPageIsReady(By pageIdentifierLocator, By optionalLoaderLocator) {
		waitForDocumentReady();
		waitForLoaderToDisappear(optionalLoaderLocator); // optional
		waitForElement(pageIdentifierLocator); // confirms page-specific load
	}

	// Without loader
	public void waitUntilPageIsReady(By pageIdentifierLocator) {
		waitForDocumentReady();
		waitForElement(pageIdentifierLocator);
	}

	public void safeClick(By locator) {
		try {
			// Try regular Selenium click
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			element.click();
			System.out.println("✅ Clicked using regular click()");
		} catch (Exception e1) {
			System.out.println("⚠️ Regular click failed. Trying JavaScript click...");

			try {
				// Fallback: JavaScript click
				WebElement element = driver.findElement(locator);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				System.out.println("✅ Clicked using JavaScriptExecutor");
			} catch (Exception e2) {
				System.out.println("⚠️ JS click failed. Trying Actions click...");

				try {
					// Fallback: Actions click
					WebElement element = driver.findElement(locator);
					Actions actions = new Actions(driver);
					actions.moveToElement(element).click().perform();
					System.out.println("✅ Clicked using Actions class");
				} catch (Exception e3) {
					System.out.println("❌ All click methods failed for locator: " + locator);
					throw new RuntimeException("Click failed with all strategies", e3);
				}
			}
		}
	}

	public boolean verifyPageLoaded(By uniqueElementLocator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
			wait.until(ExpectedConditions.visibilityOfElementLocated(uniqueElementLocator));
			System.out.println("✅ Navigation successful. Required page is loaded.");
			return true;
		} catch (TimeoutException e) {
			System.out.println("❌ Navigation failed. Required element not found: " + uniqueElementLocator);
			return false;
		}
	}

	public boolean verifyPageLoaded(String expectedUrlContains, By elementLocatorById) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
		try {
			// Check if URL contains expected part
			boolean urlMatched = wait.until(ExpectedConditions.urlContains(expectedUrlContains));
			if (urlMatched) {
				System.out.println("✅ URL check passed: " + expectedUrlContains);
			}

			// Check if element is visible
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocatorById));
			System.out.println("✅ Element visibility check passed: " + elementLocatorById);

			return true;
		} catch (TimeoutException e) {
			System.out.println("❌ Page did not load as expected.");
			System.out.println("Expected URL (partial): " + expectedUrlContains);
			System.out.println("Expected element: " + elementLocatorById);
			return false;
		}
	}

	public boolean sendKeysAndValidate(By locator, String inputText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
		String actualValue = "";

		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			element.clear();
			element.sendKeys(inputText);

			// Let the value settle
			Thread.sleep(300);

			actualValue = element.getAttribute("value");
		} catch (StaleElementReferenceException e) {
			System.out.println("⚠️ Retrying after stale element...");
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(inputText);
			actualValue = element.getAttribute("value");
		} catch (ElementNotInteractableException e) {
			System.out.println("⚠️ Element not interactable, using JS...");
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", element, inputText);
			actualValue = element.getAttribute("value");
		} catch (TimeoutException e) {
			System.out.println("❌ Timeout waiting for element: " + locator);
			return false;
		} catch (Exception e) {
			System.out.println("❌ Unexpected error: " + e.getMessage());
			return false;
		}

		boolean isMatch = inputText.equals(actualValue);
		if (isMatch) {
			System.out.println("✅ Input match confirmed: " + inputText);
		} else {
			System.out.println("❌ Input mismatch. Expected: " + inputText + ", Found: " + actualValue);
		}

		return isMatch;
	}

	public String waitForAlertAndAccept() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
	        wait.until(ExpectedConditions.alertIsPresent());

	        Alert alert = driver.switchTo().alert();

	        String alertText = null;
	        try {
	            alertText = alert.getText();
	            System.out.println("Alert Text: " + alertText);
	        } catch (Exception e) {
	            System.out.println("Alert is present but text could not be retrieved.");
	        }

	        alert.accept();
	        System.out.println("Alert accepted successfully.");
	        return alertText;

	    } catch (NoAlertPresentException e) {
	        System.out.println("No alert is present.");
	        return null;
	    } catch (Exception e) {
	        System.out.println("Unexpected error while handling alert: " + e.getMessage());
	        return null;
	    }

}
	
	public boolean isElementNotPresent(By locator) {
	    List<?> elements = driver.findElements(locator);
	    return elements.isEmpty(); // true if not present, false if found
	}
}
