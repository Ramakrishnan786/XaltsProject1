package PageObjects;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import FuntionalUtilities.FuntionalUtilities;
import Utils.JsonReader;
import junit.framework.Assert;

public class SignIn extends FuntionalUtilities {
	WebDriver driver;

	public SignIn(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	private By emailField = By.xpath("//label[text()='E-Mail']/following-sibling::div/input");
	private By passwordField = By.xpath("//label[text()='Password']/following-sibling::div/input");
	private By signInButton = By.xpath("//button[text()='Sign In']");
	private By navigateToSignUpPage = By.xpath("//button[contains(text(),'Click here to sign up')]");
	private By SignOutButton = By.xpath("//button[text()='Sign Out']");
	private By emailBorderColor = By.xpath("//label[text()='E-Mail']/following-sibling::div");
	
	public Boolean EnterDetails(String email, String password) {
		boolean isEmailEntered = sendKeysAndValidate(emailField, email);
		boolean isPasswordEntered = sendKeysAndValidate(passwordField, password);

		if (isEmailEntered && isPasswordEntered) {
			System.out.println("✅ Email and Password entered successfully.");
			return true;
		} else {
			System.out.println(
					"❌ Failed to enter credentials. Email: " + isEmailEntered + ", Password: " + isPasswordEntered);
			return false;
		}
	}

	public void clickSignInButton() {
		safeClick(signInButton);
	}

	public Boolean ValidateSignIn() {
		return verifyPageLoaded(SignOutButton);
	}

	public void validateUnRegisteredLoginMessage() {
		String alertText = waitForAlertAndAccept();
		Map<String, String> ErrorMessagges = JsonReader.getJsonDataFromJsonFile("SignIn", "ErrorMessages");
		Assert.assertEquals(ErrorMessagges.get("UnregisterSignInAlertMessage").trim(), alertText.trim());
	}

	public Boolean isSignInButtonEnabled() {
		return getElement(signInButton).isEnabled();
	}

	public void AcceptAlert() {
		waitForAlertAndAccept();
	}
	
	public Boolean Bordercolorvalidation() {
		WebElement EmailField = getElement(emailBorderColor);
		String classes = EmailField.getAttribute("class");
		Assert.assertTrue(classes.contains("Mui-error"));
		return true;
	}
	
	public void ClickOnSignOutButton() {
		safeClick(SignOutButton);
	}
	
	public void ValidateUserSignOutSuccessfully() {
		Boolean isEmpty = isElementNotPresent(SignOutButton);
			Assert.assertTrue("Signout button is not avaialble User successfully logged out",isEmpty);
			
		
	}
}
